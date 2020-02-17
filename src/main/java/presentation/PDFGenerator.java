package presentation;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFGenerator {
    private String text;

    /**<p>public PDFGenerator(String text)</p>
     * Creates a pdf file with the content given by text parameter.
     * It sets the current date of creation too.
     * @param text - the text passed to its content
     */
    public PDFGenerator(String text){
        String fileName="C:\\Users\\pbm_l\\Documents\\PT2019\\pt2019_30228_podaru_bogdan_assignment_3\\Order.pdf";
        Document doc=new Document();

        try {
            PdfWriter.getInstance(doc,new FileOutputStream(fileName));
            doc.open();
            Paragraph titleParagraph=new Paragraph("Order Details");
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            titleParagraph.getFont().setSize(20);
            doc.add(titleParagraph);
            doc.add(new Paragraph("\n\n"));
            Paragraph paragraph=new Paragraph(text);
            doc.add(paragraph);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Paragraph dateParagraph=new Paragraph(dateFormat.format(date));
            dateParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            doc.add(dateParagraph);
            doc.addCreationDate();

            doc.close();
            try {
                int op= JOptionPane.showConfirmDialog(null, "Open pdf now?", "Question", JOptionPane.YES_NO_OPTION);
                if(op==0)
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        }

        this.text=text;
    }
}
