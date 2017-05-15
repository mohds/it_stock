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
import com.itextpdf.text.PageSize;
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

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import sun.security.util.Password;

public class create_receipt_form


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
    
    //Creation of the PDF file and the printable html form will be done simultanuously
    //Whenever data is written in the pdf form, it will be followed up by an addition in the html page
    //
    
    out.println("<html><body>"); 
    
    connect_to_db connect= new connect_to_db();
    Connection con = connect.connect();
    
    String[] records_items_id = null; //contains ids of items in the receipt
    String[] records_expected_date_of_return = null;  //expected dates of return of items
    String[] records_notes = null;  //notes of each item 
    String[] records_returning = null;  //whether or not item is returning
    
    String client_name = request.getParameter("client_name");
    String receiver_name = request.getParameter("receiver_name");
    String receipt_notes = request.getParameter("receipt_notes");
    String global_expected_date = request.getParameter("global_expected_date");
    String receipt_country = request.getParameter("receipt_country");
    String it_name = request.getParameter("admin");
    String admin_id = request.getParameter("admin_id");
    String admin_full_name = "";
    
    String sql_admin_full_name = "SELECT ADMINS.NAME FROM ADMINS WHERE ADMINS.ID = '" + admin_id +"'";
    try
    {
      Statement stat_admin_full_name = con.createStatement();
      ResultSet rs_admin_full_name = stat_admin_full_name.executeQuery(sql_admin_full_name);
      rs_admin_full_name.next();
      admin_full_name = rs_admin_full_name.getString(1);
    }
    catch(Exception e)
    {
      out.println(e.toString());
    }
    
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
    
    
    String sql_get_receipt_id = "SELECT max(ID) from RECEIPTS"; //get latest receipt ID (receipt that was just created)
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
     
    
    Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD); //fonts to be used when creating pdf file
    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD, BaseColor.RED);
    
    try
    {
      
      String user = "it.stock";
      String pass ="it$t0cK*543";
      String server_ip = "140.125.2.102";
      String sharedFolder="IT/IT Support/_Receipts/IT_STOCK";
      String path="smb://"+ server_ip +"/"+sharedFolder+"/Receipt_"+ receipt_id +".pdf";
      //System.out.println("path: " + path); // test
      NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
      SmbFile smbFile = new SmbFile(path,auth);
      SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
      //smbfos.write("testing....and writing to a file".getBytes()); // test
        
      Document document = new Document();
      PdfWriter.getInstance(document, smbfos);
      document.addAuthor("Wassim El Ahmar");
      document.addTitle("Stock Receipt");
      document.open();
      
      Paragraph receipt_id_par = new Paragraph("Receipt ID: " + receipt_id ,catFont);
      receipt_id_par.setAlignment(Element.ALIGN_LEFT);
      document.add(receipt_id_par);
      
      out.println("<h3>Receipt ID: " + receipt_id + "</h3>");
      
      Image image;
      //try 
      //{
      //  image = Image.getInstance("\\\\nas5\\IT\\IT Support\\_Receipts\\IT_STOCK\\mayadeen.png");
      //  image.setAlignment(Image.ALIGN_RIGHT);
      //  document.add(image);
      //  out.println("<img align='right' src = 'images/mayadeen.png'>");
      //} 
      //catch (MalformedURLException e) 
      //{
      //  e.printStackTrace();
      //} 
      //catch (IOException e) 
      //{
      //  e.printStackTrace();
      //}
      
      out.println("<h2 style = 'color: red; text-align: center;'>Al Mayadeen IT Department</h2>");
      
      Paragraph receipt_text = new Paragraph("Receipt",catFont);
      receipt_text.setAlignment(Element.ALIGN_CENTER);
      document.add(receipt_text);
      
      Paragraph paragraph = new Paragraph();
      paragraph.add(new Paragraph(""));
      paragraph.add(new Paragraph("Received on: " + current_date ,subFont));
      paragraph.add(new Paragraph("Received for: " + client_name ,subFont));
      paragraph.add(new Paragraph("Handed to: " + receiver_name + " ----------------Signature:___________________",subFont));
      paragraph.add(new Paragraph("IT: " + admin_full_name ,subFont));
      paragraph.add(new Paragraph("Expected date of items return: " + global_expected_date ,subFont));
      paragraph.setAlignment(Element.ALIGN_LEFT);
      document.add(paragraph);
      
      out.println("<p>Received on: " + current_date + "</p>");
      out.println("<p>Received for: " + client_name + "</p>");
      out.println("<p>Handed to: " + receiver_name + " ----------------Signature:___________________</p>");
      out.println("<p>IT: " + admin_full_name + "</p>");
      out.println("<p>Expected date of items return: " + global_expected_date + "</p>");
      
      document.add( Chunk.NEWLINE );  //empty line
      
      PdfPTable table = new PdfPTable(8); //create table with 8 columns
      table.setTotalWidth(PageSize.A4.getWidth());
      
      PdfPCell c1 = new PdfPCell(new Phrase("Item ID"));  //Column headers
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase("Type"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase("Brand"));
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
      
      out.println("<table border = '1'>");
      
      out.println("<th>Item ID</th>");
      out.println("<th>Type</th>");
      out.println("<th>Brand</th>");
      out.println("<th>Label</th>");
      out.println("<th>Serial Number</th>");
      out.println("<th>Expected date of return</th>");
      out.println("<th>Notes</th>");
      out.println("<th>Item Returning</th>");
      
      for(int i = 0 ; i < records_items_id.length ; i++)
      {
        String sql_get_item_info = "SELECT TYPES.NAME, BRANDS.NAME, ITEMS.LABEL, ITEMS.SERIAL_NUMBER FROM TYPES,BRANDS,ITEMS WHERE TYPES.ID = ITEMS.TYPE_ID AND BRANDS.ID = ITEMS.BRAND_ID AND ITEMS.ID = '" + records_items_id[i] + "'";
        Statement stat_get_item_info = con.createStatement();
        ResultSet rs_get_item_info = stat_get_item_info.executeQuery(sql_get_item_info);
        rs_get_item_info.next();
        
        out.println("<tr>");
        
        table.addCell(records_items_id[i]);
        out.println("<td>" + records_items_id[i] + "</td>");
        
        if(rs_get_item_info.getString(1) != null)
        {
          table.addCell(rs_get_item_info.getString(1));
          out.println("<td>" + rs_get_item_info.getString(1) + "</td>");
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        
        if(rs_get_item_info.getString(2) != null)
        {
          table.addCell(rs_get_item_info.getString(2));
          out.println("<td>" + rs_get_item_info.getString(2) + "</td>");
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        
        if(rs_get_item_info.getString(3) != null)
        {
          table.addCell(rs_get_item_info.getString(3));
          out.println("<td>" + rs_get_item_info.getString(3) + "</td>");
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        
        if(rs_get_item_info.getString(4) != null)
        {
          table.addCell(rs_get_item_info.getString(4));
          out.println("<td>" + rs_get_item_info.getString(4) + "</td>");
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        
        if(records_expected_date_of_return[i] != null)
        {
          table.addCell(records_expected_date_of_return[i]);
          out.println("<td>" + records_expected_date_of_return[i] + "</td>");
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        
        if(records_notes[i] != null)
        {
          table.addCell(records_notes[i]);
          out.println("<td>" + records_notes[i] + "</td>");
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        
        if(records_returning[i] != null)
        {
          if(records_returning[i].equals("1"))
          {
            table.addCell("Yes");
            out.println("<td>Yes</td>");
          }
          else
          {
            table.addCell("No");
            out.println("<td>No</td>");
          }
        }
        else
        {
          table.addCell("-");
          out.println("<td>-</td>");
        }
        out.println("</tr>");
      }
      
      document.add(table);
      out.println("</table>");
      
      Paragraph receipt_notes_par = new Paragraph();
      receipt_notes_par.add(new Paragraph(""));
      receipt_notes_par.add(new Paragraph("Country: " + receipt_country ,subFont));
      receipt_notes_par.add(new Paragraph("Receipt notes: " + receipt_notes,subFont));
      receipt_notes_par.setAlignment(Element.ALIGN_LEFT);
      document.add(receipt_notes_par);
      
      out.println("<p>Country: " + receipt_country + "</p>");
      out.println("<p>Receipt notes: " + receipt_notes + "</p>");
      
      Paragraph please_note_text = new Paragraph("Kindly note that the return of any of the items listed in this receipt is not considered official unless signed by both the receiver and the IT employee.",redFont);
      please_note_text.setAlignment(Element.ALIGN_LEFT);
      document.add(please_note_text);
      
      out.println("<h4 style = 'color: red; text-align: center;'>Kindly note that the return of any of the items listed in this receipt is not considered official unless signed by bot the receiver and the IT employee</h4>");
      
      
      document.close();
      out.println("</body></html>");
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
      e.printStackTrace();
    }
  }
}
