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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class DownloadService {

    @Autowired
    private BookComponentsService bookComponentsService;
    @Value("generated")
    private String GENERATED_FOLDER;

    public List<BookComponents> getBookComponents(String[] ids) {
        return bookComponentsService.getRequestedBookComp(ids);
    }

    public String generate_ebook(List<BookComponents> bookComponents, List<Integer> no_pages) {


        String dir_path = Paths.get("").toAbsolutePath().toString();
        String file_name = Integer.toString(bookComponents.get(0).getId());

        for (int i = 1; i < bookComponents.size(); i++) {
            file_name += "_" + bookComponents.get(i).getId();
        }
        file_name = dir_path + File.separator + GENERATED_FOLDER + File.separator + file_name + ".pdf";

        System.out.println(file_name);

        File f = new File(file_name);
        //to check if existing book is present
        if (f.exists()) {
            //System.out.println("here");
            return f.getAbsolutePath();
        }

        //merging logic

        PDFMergerUtility merge = new PDFMergerUtility();
        merge.setDestinationFileName(file_name);

        try {
            for (int i = 0; i < bookComponents.size(); i++) {

                File f1 = new File(bookComponents.get(i).getLocation());
                PDDocument doc = PDDocument.load(f1);
                no_pages.add(doc.getNumberOfPages());
                merge.addSource(f1);


            }
            merge.mergeDocuments(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("merge successfully");
        System.out.println(no_pages.size() + "***");
        generate_page_numbers(file_name);

        return file_name;
    }

    public void generate_page_numbers(String file_name) {
        File load_file = new File(file_name);
        try {
            PDDocument doc = PDDocument.load(load_file);
            int page_number = 1;
            for (PDPage page : doc.getPages()) {
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, false);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ITALIC, 10);
                PDRectangle pageSize = page.getCropBox();
                float x = pageSize.getLowerLeftX();
                float y = pageSize.getLowerLeftY();
                contentStream.newLineAtOffset(x + pageSize.getWidth() - 60, y + 20);
                //System.out.println("page"+page_number);
                contentStream.showText(Integer.toString(page_number));
                contentStream.endText();
                contentStream.close();
                ++page_number;
            }

            doc.save(load_file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void generate_index(List<BookComponents> book_components, List<Integer> no_pages, File f) {
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element -index
            Element rootElement = doc.createElement("index");
            doc.appendChild(rootElement);
            System.out.println(no_pages.size() + "**");
            System.out.println(book_components.size());
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
                pages += no_pages.get(i);
                i++;
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("contents.xml").getAbsolutePath());
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            //generate pdf from xml
            transformationXMLtoPDF();

            //joining index and main file
            String dir_path = Paths.get("").toAbsolutePath().toString();
            String generatedPDFpath = dir_path +File.separator +"toc";
            File contents=new File(generatedPDFpath+File.separator+"contents.pdf");
            PDFMergerUtility merge=new PDFMergerUtility();
            merge.setDestinationFileName(f.getAbsolutePath());


            merge.addSource(contents);
            merge.addSource(f);
            merge.mergeDocuments(null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //transform xml to pdf
    public void transformationXMLtoPDF() {
        try {
            String dir_path = Paths.get("").toAbsolutePath().toString();
            String xmlpath = dir_path + File.separator + "contents.xml";
            String xslpath = dir_path + File.separator + "contents_style.xsl";
            String generatedPDFpath = dir_path + File.separator + "toc";
            File xmlfile = new File(xmlpath);
            File xsltfile = new File(xslpath);

            File pdfDir = new File(generatedPDFpath);
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }
            File pdfFile = new File(pdfDir, "contents.pdf");
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
}
