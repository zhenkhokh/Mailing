/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author zhenya
 */
public class TestConvertUnicToRaw {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("0605656.pdf");
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] b= new byte[bis.available()];
            bis.read(b);
            FileOutputStream fosA = new FileOutputStream("apacheEnc.pdf");//testCreateFile1_0605656.pdf "apacheEnc.pdf"
            fosA.write(org.apache.commons.codec.binary.Base64.encodeBase64(b));//TODO encode UTF-7 way
            //fosA.write(new sun.misc.BASE64Encoder().);
            FileOutputStream fosU = new FileOutputStream("utilEnc.pdf");
            fosU.write(java.util.Base64.getMimeEncoder().encode(b));//!
            //fosU.write(Base64Coder.encodeLines(b).getBytes("UTF-8"));
            fosU.close();
            FileInputStream fisU = new FileInputStream("utilEnc.pdf");
            bis = new BufferedInputStream(fisU);
            byte[] bb = new byte[fisU.available()];
            fisU.read(bb);
            //bis.read(bb,0,8);
            //bb[77]=13;//CRi
            //bb[78]=10;//LF
            FileOutputStream fosReU = new FileOutputStream("reUtilCode.pdf");
            fosReU.write(java.util.Base64.getMimeDecoder().decode(bb));//!
            //fosReU.write(Base64Coder.decode(new String(bb)));
            //MimeUtility.decode    
            //System.out.println(new String(java.util.Base64.getEncoder().encode(b)));System.getProperty("line.separator")
            System.out.println("mailClient.TestConvertUnicToRaw.main()"+new String(b,Charset.forName("UTF-8")).replace("\\u000D","LLLLL"));
           /*
            String coding="UTF-8";//"ISO-8859-1";
            FileInputStream fis = new FileInputStream("0605656.pdf");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            //BASE64Encoder encoder = new BASE64Encoder();
            org.apache.commons.codec.binary.Base64 encoder = new Base64();
            FileOutputStream fos = new FileOutputStream("encode.pdf");
            //fos.write(encoder.encode(b).getBytes(coding));
            fos.write(encoder.encode(b));
            fos.close();
            FileInputStream fis1 = new FileInputStream("encode.pdf");
            FileOutputStream fos2 = new FileOutputStream("rec.pdf");
            byte[] bb = new byte[fis1.available()];//encoder.encode(b).getBytes("ISO-8859-1");//
            fis1.read(bb, 0, bb.length);
            //BASE64Decoder decoder = new BASE64Decoder();
            org.apache.commons.codec.binary.Base64 decoder = new Base64();
            //byte[] dec = decoder.decodeBuffer(new String(bb,coding));
            byte[] dec = decoder.decode(bb);
            fos2.write(dec);
            */
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestConvertUnicToRaw.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestConvertUnicToRaw.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
