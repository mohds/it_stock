package project1;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.servlet.http.HttpSession;

public class SendEmail {
    public SendEmail() {
        super();
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public static void send_email(String content, String subject) {
        List<String> TO = new ArrayList<String>();
        List<String> CC = new ArrayList<String>();
        TO = Queries.get_emails("TO");
        CC = Queries.get_emails("CC");
        
        
        
        String smtp_hostname = "smtp.gmail.com";
        int smtp_port = 465;
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", smtp_hostname);
        //properties.put("mail.smtp.port", smtp_port);
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        final String username = "mayadeen.stock";
        final String password = "it@admin";
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Transport transport = null;

        try {
            Session session = Session.getInstance(properties, authenticator);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("mayadeen.stock@gmail.com"));
            if(CC != null){
                for(int i = 0 ; i < CC.size() ; i++){
                    mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(CC.get(i)));
                }
            }
            else{
                mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(""));
            }
            if(TO != null){
                for(int i = 0 ; i < TO.size() ; i++){
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(TO.get(i)));
                }
            }
            else{
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(""));
            } 
            
            // add signature
            String signature = "";
            signature = "<br><br><b>IT STOCK</b><br>" + 
            "Al Mayadeen Satellite Network<br>" + 
            "Phone: +9611828500 | Ext: 199 | <br>" + 
            "Email: mayadeen.stock@gmail.com<br>";
            content = content + signature;
            
            mimeMessage.setContent(content,"text/html");
            mimeMessage.setSubject(subject);
            transport = session.getTransport();
            transport.connect(smtp_hostname, username, password);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        finally {
            // if (transport != null) try { transport.close(); } catch (MessagingException logOrIgnore) {}
        }
    }
}
