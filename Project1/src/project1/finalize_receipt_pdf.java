package project1;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class finalize_receipt_pdf
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1256";

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    
    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD, BaseColor.RED);
    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);         
    
    Calendar c = Calendar.getInstance();
    Date today_date = c.getTime();
    String current_date = String.valueOf(c.get(Calendar.DATE) + "-" + String.valueOf(today_date.getMonth() + 1) + "-" + String.valueOf(c.get(Calendar.YEAR)));
  
    String it_name = "Coal"; //edit this
    String returner_name = "hi";  //edit this
    File dest = new File("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Complete\\Receipt_" + "29" + "_complete.pdf"); //replace 29 by receipt id
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
      PdfReader reader = new PdfReader("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Receipt_" + "29" + ".pdf");  //replace 29 by receipt id
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
      
      File source_to_delete = new File("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Receipt_" + "29" + ".pdf");
      if(source_to_delete.delete())
        System.out.println("JA");
      
      
      
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    
    out.close();
  }
}
