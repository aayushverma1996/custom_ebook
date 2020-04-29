package com.iiitb.custom_ebook.ebook.donwloadFile;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsService;
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

import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class DownloadService {

    @Autowired
    private BookComponentsService bookComponentsService;
    @Value("generated")
    private String GENERATED_FOLDER;

    @Value("toc")
    private String INDEX_FOLDER;

    @Value("merged")
    private String MERGE_FOLDER;

    public List<BookComponents> getBookComponents(String[] ids) {
        return bookComponentsService.getRequestedBookComp(ids);
    }

    public String generate_ebook(List<BookComponents> bookComponents) {


       // String dir_path = Paths.get("").toAbsolutePath().toString();
        String file_name = Integer.toString(bookComponents.get(0).getId());

        for (int i = 1; i < bookComponents.size(); i++) {
            file_name += "_" + bookComponents.get(i).getId();
        }
        file_name = GENERATED_FOLDER + File.separator + file_name + ".pdf";



        File f = new File(file_name);

        //to check if existing book is present

        if (f.exists()) {

            return f.getPath();
        }

        //merging logic

        PDFMergerUtility merge = new PDFMergerUtility();
        merge.setDestinationFileName(file_name);

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

        return f.getPath();
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


    public String generate_index(List<BookComponents> book_components) {

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
        if(indexfile.exists())
        {
            return indexfile.getPath();
        }

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element -index
            Element rootElement = doc.createElement("index");
            doc.appendChild(rootElement);

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

    //transform xml to pdf
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
        merge.setDestinationFileName(merge_dir.getAbsolutePath()+File.separator+"temp.pdf");
        merge.addSource(index);
        merge.addSource(generated);
        merge.mergeDocuments(null);
        File temp_file=new File(merge_dir.getPath()+File.separator+"temp.pdf");
        if(!temp_file.exists())
        {
            return null;
        }
        return temp_file.getPath();
    }
   @Scheduled(cron = "* 0/2 * * * *")
    public  void destroy_generated_toc()
    {
        File folder_generated=new File(GENERATED_FOLDER);
        File[] file_list=folder_generated.listFiles();
        File folder_toc=new File(INDEX_FOLDER);
        File[] files=folder_toc.listFiles();
        List<Long> out=new ArrayList<Long>();
        Date date=new Date();
        System.out.println(Calendar.getInstance().getTime()+" executed");
        long time=date.getTime();

        long deadline=2*60*1000;  //30 min in millisec
        for(File file:file_list)
        {

            if(time-file.lastModified()>deadline)
            {
                file.delete();
                System.out.println("file deleted 1");
            }
        }
        for(File file:files)
        {

            if(time-file.lastModified()>deadline)
            {
                file.delete();
                System.out.println("file deleted 1");
            }
        }

    }


}
