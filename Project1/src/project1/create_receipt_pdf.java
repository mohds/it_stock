package project1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.net.MalformedURLException;

import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


public class create_receipt_pdf


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
    
    connect_to_db connect= new connect_to_db();
    Connection con = connect.connect();
    
    String[] records_items_id = null; 
    String[] records_expected_date_of_return = null; 
    String[] records_notes = null;
    String[] records_returning = null; 
    
    String client_name = request.getParameter("client_name");
    String receipt_notes = request.getParameter("receipt_notes");
    String admin_id = request.getParameter("admin_id");
    String global_expected_date = request.getParameter("global_expected_date");
    String receipt_country = request.getParameter("receipt_country");
    String it_name = "Wassim";
    int receipt_id = 0;
    
    if(request.getParameterValues("records_items_id[]") != null)
    {
      records_items_id = request.getParameterValues("records_items_id[]");
    }
    
    if(request.getParameterValues("records_expected_dates_of_return[]") != null)
    {
      records_expected_date_of_return = request.getParameterValues("records_expected_dates_of_return[]");
    }
    
    if(request.getParameterValues("records_notes[]") != null)
    {
      records_notes = request.getParameterValues("records_notes[]");
    }
    
    if(request.getParameterValues("records_returning[]") != null)
    {
      records_returning = request.getParameterValues("records_returning[]");
    }
    
    
    String sql_get_receipt_id = "SELECT max(ID) from RECEIPTS";
    try
    {
      Statement stat_get_receipt_id = con.createStatement();
      ResultSet rs_get_receipt_id = stat_get_receipt_id.executeQuery(sql_get_receipt_id);
      rs_get_receipt_id.next();
      receipt_id = rs_get_receipt_id.getInt(1);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    
    Calendar c = Calendar.getInstance();
    Date today_date = c.getTime();
    String current_date = String.valueOf(c.get(Calendar.DATE) + "-" + String.valueOf(today_date.getMonth() + 1) + "-" + String.valueOf(c.get(Calendar.YEAR)));
     
    
    Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD, BaseColor.RED);
    
    try
    {
      File file = new File("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\Receipt_" + receipt_id + ".pdf");
      FileOutputStream fileout = new FileOutputStream(file);
      Document document = new Document();
      PdfWriter.getInstance(document, fileout);
      
      document.addAuthor("Me");
      document.addTitle("My iText Test");
      document.open();
      
      Paragraph receipt_id_par = new Paragraph("Receipt ID: " + receipt_id ,catFont);
      receipt_id_par.setAlignment(Element.ALIGN_LEFT);
      document.add(receipt_id_par);
      
      Image image;
      try 
      {
        image = Image.getInstance("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\mayadeen.png");
        image.setAlignment(Image.ALIGN_RIGHT);
        document.add(image);
      } 
      catch (MalformedURLException e) 
      {
        e.printStackTrace();
      } 
      catch (IOException e) 
      {
        e.printStackTrace();
      }
      
      Paragraph receipt_text = new Paragraph("Receipt",catFont);
      receipt_text.setAlignment(Element.ALIGN_CENTER);
      document.add(receipt_text);
      
      Paragraph paragraph = new Paragraph();
      paragraph.add(new Paragraph(""));
      paragraph.add(new Paragraph("Received on: " + current_date ,subFont));
      paragraph.add(new Paragraph("Received for: " + client_name ,subFont));
      paragraph.add(new Paragraph("IT: " + it_name ,subFont));
      paragraph.add(new Paragraph("Expected date of items return: " + global_expected_date ,subFont));
      paragraph.setAlignment(Element.ALIGN_LEFT);
      document.add(paragraph);
      
      document.add( Chunk.NEWLINE );
      
      PdfPTable table = new PdfPTable(6);
      
      PdfPCell c1 = new PdfPCell(new Phrase("Item ID"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);

      c1 = new PdfPCell(new Phrase("Label"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);

      c1 = new PdfPCell(new Phrase("Serial Number"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase("Expected date of return"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase("Notes"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase("Item Returning"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      
      table.setHeaderRows(1);
      
      for(int i = 0 ; i < records_items_id.length ; i++)
      {
        String sql_get_item_info = "SELECT LABEL, SERIAL_NUMBER FROM ITEMS WHERE ID = '" + records_items_id[i] + "'";
        Statement stat_get_item_info = con.createStatement();
        ResultSet rs_get_item_info = stat_get_item_info.executeQuery(sql_get_item_info);
        rs_get_item_info.next();
        
        table.addCell(records_items_id[i]);
        
        if(rs_get_item_info.getString(1) != null)
        {
          table.addCell(rs_get_item_info.getString(1));
        }
        else
        {
          table.addCell("-");
        }
        
        if(rs_get_item_info.getString(2) != null)
        {
          table.addCell(rs_get_item_info.getString(2));
        }
        else
        {
          table.addCell("-");
        }
        
        if(records_expected_date_of_return[i] != null)
        {
          table.addCell(records_expected_date_of_return[i]);
        }
        else
        {
          table.addCell("-");
        }
        
        if(records_notes[i] != null)
        {
          table.addCell(records_notes[i]);
        }
        else
        {
          table.addCell("-");
        }
        
        if(records_returning[i] != null)
        {
          if(records_returning[i].equals("1"))
          {
            table.addCell("Yes");
          }
          else
          {
            table.addCell("No");
          }
        }
        else
        {
          table.addCell("-");
        }
      }
      
      document.add(table);
      
      Paragraph receipt_notes_par = new Paragraph();
      receipt_notes_par.add(new Paragraph(""));
      receipt_notes_par.add(new Paragraph("Country: " + receipt_country ,subFont));
      receipt_notes_par.add(new Paragraph("Receipt notes: " + receipt_notes,subFont));
      receipt_notes_par.setAlignment(Element.ALIGN_LEFT);
      document.add(receipt_notes_par);           
      
      Paragraph please_note_text = new Paragraph("Kindly note that the return of any of the items listed in this receipt is not considered official unless signed by bot the receiver and the IT employee.",redFont);
      please_note_text.setAlignment(Element.ALIGN_LEFT);
      document.add(please_note_text);
      
      document.add( Chunk.NEWLINE );
      
      Paragraph reception_title_text = new Paragraph("Return",redFont);
      reception_title_text.setAlignment(Element.ALIGN_CENTER);
      document.add(reception_title_text);
      
      Paragraph reception_par = new Paragraph();
      reception_par.add(new Paragraph(""));
      reception_par.add(new Paragraph("Returned on: " ,subFont));
      reception_par.add(new Paragraph("Returned by: " ,subFont));
      reception_par.add(new Paragraph("IT: " ,subFont));
      reception_par.setAlignment(Element.ALIGN_LEFT);
      document.add(reception_par);           
      
      document.close();
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      e.printStackTrace();
    }
  }
}
