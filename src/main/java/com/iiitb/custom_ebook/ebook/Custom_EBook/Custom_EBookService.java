package com.iiitb.custom_ebook.ebook.Custom_EBook;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsService;
import com.iiitb.custom_ebook.ebook.User.User;
import com.iiitb.custom_ebook.ebook.User.UserService;
import org.apache.fop.apps.*;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class Custom_EBookService {

    @Autowired
    private BookComponentsService bookComponentsService;

    @Autowired
    private Custom_EBookRepository custom_eBookRepository;

    @Autowired
    private UserService userService;

    @Value("generated")
    private String GENERATED_FOLDER;

    @Value("toc")
    private String INDEX_FOLDER;

    @Value("merged")
    private String MERGE_FOLDER;

    public List<BookComponents> getBookComponents(List<String> ids) {
        return bookComponentsService.getRequestedBookComp(ids);
    }

    public String generate_ebook(List<BookComponents> bookComponents,int uid,int toc) throws IOException {


       // String dir_path = Paths.get("").toAbsolutePath().toString();
        String file_name = Integer.toString(bookComponents.get(0).getId());
        for (int i = 1; i < bookComponents.size(); i++) {
            file_name += "_" + bookComponents.get(i).getId();
        }

        file_name = GENERATED_FOLDER + File.separator + file_name + ".pdf";
        File f = new File(file_name);

        //to check if existing ebook component is present

        if (f.exists()) {
            String newFilename=MERGE_FOLDER + File.separator+uid+"_"+toc+"_"+f.getName();
            File newFile = new File(newFilename);
            Files.copy(f.toPath(),newFile.toPath());
            return newFile.getPath();
        }


        //merging logic
        PDFMergerUtility merge = new PDFMergerUtility();
        merge.setDestinationFileName(f.getPath());
        try {
            for (int i = 0; i < bookComponents.size(); i++) {

                File f1 = new File(bookComponents.get(i).getLocation());
                PDDocument doc = PDDocument.load(f1);
                merge.addSource(f1);
            }
            merge.mergeDocuments(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        generate_page_numbers(file_name);

        String newFilename=MERGE_FOLDER + File.separator+uid+"_"+toc+"_"+f.getName();
        File newFile = new File(newFilename);
        Files.copy(f.toPath(),newFile.toPath());
        return newFile.getPath();
    }

    public void generate_page_numbers(String file_name) {
        File load_file = new File(file_name);
        PDDocument doc=null;
        try {

           doc = PDDocument.load(load_file);
            int page_number = 1;
            for (PDPage page : doc.getPages()) {
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, false);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ITALIC, 10);
                contentStream.setStrokingColor(Color.BLACK);
                PDRectangle pageSize = page.getCropBox();
                float x = pageSize.getLowerLeftX();
                float y = pageSize.getLowerLeftY();
                contentStream.newLineAtOffset(x + pageSize.getWidth() - 60, y + 20);
                contentStream.showText(Integer.toString(page_number));
                contentStream.endText();
                contentStream.close();
                ++page_number;
            }

            doc.save(load_file);
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String generate_index(List<BookComponents> book_components,String ebookname,String generatedby) {

        String index_file_name= Integer.toString(book_components.get(0).getId());

        for (int i = 1; i < book_components.size(); i++) {
            index_file_name += "_" + book_components.get(i).getId();
        }
        File index_folder=new File(INDEX_FOLDER);
        if(!index_folder.exists())
        {
            index_folder.mkdir();
        }

        File indexfile=new File(INDEX_FOLDER+File.separator+index_file_name+"_index.pdf");

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element -index
            Element rootElement = doc.createElement("index");
            doc.appendChild(rootElement);

            //<cover> name </cover>
            Element cover=doc.createElement("cover");
            cover.appendChild(doc.createTextNode(ebookname));
            rootElement.appendChild(cover);


            //   <generatedby> name </generatedby>
            Element generated_by=doc.createElement("generatedby");
            generated_by.appendChild(doc.createTextNode(generatedby));
            rootElement.appendChild(generated_by);

            // heading element child of root
            Element heading = doc.createElement("heading");
            heading.appendChild(doc.createTextNode("Table of contents"));
            rootElement.appendChild(heading);
            int i = 0;
            int pages = 1;
            for (BookComponents components : book_components) {
                Element topic = doc.createElement("topic");
                rootElement.appendChild(topic);


                //sno element child of topic
                Element sno = doc.createElement("Sno");
                sno.appendChild(doc.createTextNode(Integer.toString(i+1) + "."));
                topic.appendChild(sno);

                //component name child of topic
                Element component_name = doc.createElement("component-name");
                component_name.appendChild(doc.createTextNode(components.getComponent_name()));
                topic.appendChild(component_name);

                //book-name child of topic
                Element book_name = doc.createElement("book-name");
                book_name.appendChild(doc.createTextNode("From:- " + components.getBook().getBook_name()));
                topic.appendChild(book_name);


                //page-end child of topic
                Element page_to = doc.createElement("page-to");
                page_to.appendChild(doc.createTextNode(Integer.toString(pages)));
                topic.appendChild(page_to);
                File temp=new File(components.getLocation());
                PDDocument document=PDDocument.load(temp);
                pages += document.getNumberOfPages();
                document.close();
                i++;
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("contents.xml").getAbsolutePath());
            transformer.transform(source, result);



            //generate pdf from xml
            transformationXMLtoPDF(indexfile);




        } catch (Exception e) {
            e.printStackTrace();
        }

        return indexfile.getPath();
    }

    //transform xml to pdf----here xmlfo
    public void transformationXMLtoPDF(File file_name) {
        try {
            String dir_path = Paths.get("").toAbsolutePath().toString();
            String xmlpath = dir_path + File.separator + "contents.xml";
            String xslpath = dir_path + File.separator + "contents_style.xsl";
            String generatedPDFpath = dir_path + File.separator + INDEX_FOLDER;
            File xmlfile = new File(xmlpath);
            File xsltfile = new File(xslpath);

            File pdfDir = new File(generatedPDFpath);
            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }
            File pdfFile = file_name;
            System.out.println(pdfFile.createNewFile());
            System.out.println(pdfFile.getAbsolutePath());
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // configure foUserAgent as desired

            // Setup output
            OutputStream out = new FileOutputStream(pdfFile);
            out = new java.io.BufferedOutputStream(out);
            try {
                // Construct fop with desired output format
                Fop fop;

                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));

                // Setup input for XSLT transformation
                Source src = new StreamSource(xmlfile);

                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());

                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);
            } catch (FOPException | TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String merge_toc_main(File generated,File index) throws IOException {

            File merge_dir=new File(MERGE_FOLDER);
            if(!merge_dir.exists())
            {
                merge_dir.mkdir();
            }
            PDFMergerUtility merge = new PDFMergerUtility();
            merge.setDestinationFileName(merge_dir.getAbsolutePath()+File.separator+generated.getName());
            merge.addSource(index);
            merge.addSource(generated);
            merge.mergeDocuments(null);
            File temp_file=new File(merge_dir.getPath()+File.separator+generated.getName());
            System.out.println(temp_file+"++++");
            if(!temp_file.exists())
            {
                return null;
            }
            return temp_file.getPath();
    }

    public void addToUser(int uid,List<BookComponents> components,int toc,String ebookname,String genby)
    {
        User user=userService.getUserbyId(uid);

        //book_components merge file name  toc_uid_component1id_component2id..
        String filename= Integer.toString(components.get(0).getId());
        for (int i = 1; i < components.size(); i++) {
                filename += "_" + components.get(i).getId();
        }

        String location=toc+"_"+user.getId()+"_"+filename;


        //total price of this component
        double price =components.stream().mapToDouble(x->x.getPrice()).sum();
        Custom_EBook c=new Custom_EBook();
        c.seteBookName(ebookname);
        c.setGeneratedBy(genby);
        c.setPrice(price);
        c.setLocation(location);
        c.setStatus((byte)1);
        c.setUser(user);
        custom_eBookRepository.save(c);
    }

    public void updateLocation(String location,byte status,int ebookId)
    {
        Custom_EBook ebook=custom_eBookRepository.findById(ebookId).get();
        ebook.setLocation(location);
        ebook.setStatus(status);
        custom_eBookRepository.save(ebook);
    }

    public String buildEBook(Custom_EBook eBook) throws IOException {

        String[] location=eBook.getLocation().split("_");

        List<String> component_ids=new ArrayList<String>();
        int toc=Integer.parseInt(location[0]);//0or1
        int uid=Integer.parseInt(location[1]);//user id
        for(int i=2;i<location.length;i++)
        {
           component_ids.add(location[i]);
        }
        List<BookComponents> bookComponentsList=bookComponentsService.getRequestedBookComp(component_ids);
        String filename=null;
        filename=generate_ebook(bookComponentsList,uid,toc);
        System.out.println(filename+"==");
        if(toc==1)
        {
              String index=generate_index(bookComponentsList,eBook.geteBookName(),eBook.getGeneratedBy());
              System.out.println(index+"--");
              filename=merge_toc_main(new File(filename),new File(index));
              System.out.println(filename+"**");
        }
        //update location and status
        updateLocation(filename,(byte)3,eBook.getId());
        return "Success";
    }

    public Custom_EBook getEBookbyId(int id)
    {
        return custom_eBookRepository.findById(id).get();
    }





//    @Scheduled(cron = "* 0/30 * * * *") // every 30min cron job
//    public void destroy_generated_toc()
//    {
//        File folder_generated=new File(GENERATED_FOLDER);
//        File[] file_list=folder_generated.listFiles();
//        File folder_toc=new File(INDEX_FOLDER);
//        File[] files=folder_toc.listFiles();
//        List<Long> out=new ArrayList<Long>();
//        Date date=new Date();
//        System.out.println(Calendar.getInstance().getTime()+" executed");
//        long time=date.getTime();
//
//        long deadline=30*60*1000;  //30 min in millisec
//        for(File file:file_list)
//        {
//
//            if(time-file.lastModified()>deadline)
//            {
//                file.delete();
//                System.out.println("file deleted 1");
//            }
//        }
//        for(File file:files)
//        {
//
//            if(time-file.lastModified()>deadline)
//            {
//                file.delete();
//                System.out.println("file deleted 1");
//            }
//        }
//
//    }


}
