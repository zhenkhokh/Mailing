/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author zhenya
 */
public class Base64Apache {
      public static void main(String[] args) {
    try {
      String clearText = "Hello world";
      String encodedText;
      // Base64
      encodedText = new String(Base64.encodeBase64(clearText.getBytes()));
      System.out.println("Encoded: " + encodedText);
      System.out.println("Decoded:"
          + new String(Base64.decodeBase64(encodedText.getBytes())));
      //
      // output :
      //   Encoded: SGVsbG8gd29ybGQ=
      //   Decoded:Hello world
      //
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
