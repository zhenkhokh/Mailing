/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author zhenya
 */
public class TestMimeMessage {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("testCreateFile.txt");
            Session session = Session.getDefaultInstance(new Properties());
            //javax.mail
            MimeMessage message = new MimeMessage(session, fis);
            Enumeration<Header> headers = message.getAllHeaders();
            for(;headers.hasMoreElements();){
                Header header = headers.nextElement();
                System.out.println("apimail.ApiMail.check(): name="+header.getName()+" val="+header.getValue());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestMimeMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(TestMimeMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
