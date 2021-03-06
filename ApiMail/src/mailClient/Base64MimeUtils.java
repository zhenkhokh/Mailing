/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author zhenya
 */
public class Base64MimeUtils {
   
  private Base64MimeUtils() {}

  public static byte[] encode(byte[] b) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStream b64os = MimeUtility.encode(baos, "base64");
    b64os.write(b);
    b64os.close();
    return baos.toByteArray();
  }

  public static byte[] decode(byte[] b) throws Exception {
    ByteArrayInputStream bais = new ByteArrayInputStream(b);
    InputStream b64is = MimeUtility.decode(bais, "base64");
    byte[] tmp = new byte[b.length];
    int n = b64is.read(tmp);
    byte[] res = new byte[n];
    System.arraycopy(tmp, 0, res, 0, n);
    return res;
  }

  public static void main(String[] args) throws Exception {
    String test = "realhowto";
    byte res1[] = Base64MimeUtils.encode(test.getBytes());
    System.out.println(test + " base64 -> " + java.util.Arrays.toString(res1));
    System.out.println(new String(res1));
    byte res2[] = Base64MimeUtils.decode(res1);
    System.out.println("");
    System.out.println( java.util.Arrays.toString(res1) + " string --> "
        + new String(res2));
    /*
     * output
     * realhowto base64 ->
     *     [99, 109, 86, 104, 98, 71, 104, 118, 100, 51, 82, 118]
     *     cmVhbGhvd3Rv
     * [99, 109, 86, 104, 98, 71, 104, 118, 100, 51, 82, 118]
     *     string --> realhowto
     */
    }
 
}
