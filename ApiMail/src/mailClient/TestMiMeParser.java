/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

//import com.sun.xml.internal.ws.encoding.MimeMultipartParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.mail.Header;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeBodyPart;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.parser.MimeStreamParser;
import org.apache.james.mime4j.stream.BodyDescriptor;
import org.apache.james.mime4j.stream.EntityState;
import org.apache.james.mime4j.stream.Field;
import org.apache.james.mime4j.stream.MimeConfig;
import org.apache.james.mime4j.stream.MimeTokenStream;

/**
 *
 * @author zhenya
 */
public class TestMiMeParser {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("testCreateFile1.txt");
            //MimeStreamParser msp = new MimeStreamParser();
            //msp.parse(fis);
            // see http://james.apache.org/mime4j/usage.html#Sample_Token_Stream
            MimeConfig cfg=new MimeConfig();
            cfg.setMaxContentLen(4000000);
            cfg.setMaxLineLen(4000000);
            cfg.setMaxHeaderLen(4000000);
            MimeTokenStream stream = new MimeTokenStream(cfg);
            stream.parse(fis);
            for (EntityState state = stream.getState();
                 state != EntityState.T_END_OF_STREAM;
                 state = stream.next()) {
              switch (state) {
                case T_BODY:
                  System.out.println("Body detected, contents = "
                    + stream.getInputStream() + ", header data = "
                    + stream.getBodyDescriptor());
                  break;
                case T_FIELD:
                  System.out.println("Header field detected: "
                    + stream.getField());
                  break;
                case T_START_MULTIPART:
                  System.out.println("Multipart message detexted,"
                    + " header data = "
                    + stream.getBodyDescriptor());
              }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestMiMeParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestMiMeParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MimeException ex) {
            Logger.getLogger(TestMiMeParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
