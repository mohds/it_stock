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
                
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtp.host", "mail.almayadeen.net");
        properties.put("mail.smtp.port", "443");
        properties.put("mail.smtp.auth", "true");

        final String username = "it.sup";
        final String password = "123456";
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Transport transport = null;

        try {
            Session session = Session.getInstance(properties, authenticator);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("it.sup@almayadeen.net"));
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
            
            mimeMessage.setContent(content,"text/html");
            mimeMessage.setSubject(subject);
            transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        finally {
            if (transport != null) try { transport.close(); } catch (MessagingException logOrIgnore) {}
        }
    }
}
