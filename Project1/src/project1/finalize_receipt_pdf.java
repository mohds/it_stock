package project1;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Calendar;
import java.util.Date;

public class finalize_receipt_pdf
{
    public finalize_receipt_pdf() 
    {
        super();
    }

    public static void finalize_receipt(String it_name, String returner_name, String receipt_id)
    {    
        Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD, BaseColor.RED);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);         
        
        Calendar c = Calendar.getInstance();
        Date today_date = c.getTime();
        String current_date = String.valueOf(c.get(Calendar.DATE) + "-" + String.valueOf(today_date.getMonth() + 1) + "-" + String.valueOf(c.get(Calendar.YEAR)));
            
        File dest = new File("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Complete\\Receipt_" + receipt_id + "_complete.pdf"); // replaced receipt_id
        try
        {
          Paragraph reception_title_text = new Paragraph("RETURNED",redFont);
          reception_title_text.setAlignment(Element.ALIGN_CENTER);
          
          Paragraph reception_par = new Paragraph();
          reception_par.add(new Paragraph(""));
          reception_par.add(new Paragraph("Returned on: " + current_date ,subFont));
          reception_par.add(new Paragraph("Returned by: " + returner_name,subFont));
          reception_par.add(new Paragraph("IT: " + it_name ,subFont));
          reception_par.setAlignment(Element.ALIGN_LEFT);
          
          
          Document document = new Document(PageSize.A4);
          PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
          document.open();
          PdfContentByte cb = writer.getDirectContent();
        
          // Load existing PDF
          PdfReader reader = new PdfReader("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Receipt_" + receipt_id + ".pdf");  // replaced receipt_id
          PdfImportedPage page = writer.getImportedPage(reader, 1); 
        
          // Copy first page of existing PDF into output PDF
          document.newPage();
          cb.addTemplate(page,0,0);
        
          // Add your new data / text here
          // for example...
          document.add(reception_title_text);
          document.add( Chunk.NEWLINE );
          document.add(reception_par);
          document.close();
          page.closePath();
          reader.close();
          
          File source_to_delete = new File("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Receipt_" + receipt_id + ".pdf"); // replaced receipt_id
            
            if(source_to_delete.delete()) {}
            // System.out.println("JA");
        }
        catch(Exception e)
        {
          System.out.println(e.toString());
        }
    }
}
