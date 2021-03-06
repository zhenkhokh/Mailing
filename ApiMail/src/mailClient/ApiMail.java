/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterInputStream;
import javax.activation.CommandInfo;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.NoSuchProviderException;
import javax.mail.MessagingException;
import javax.mail.Header;
import javax.mail.internet.MimeMessage;
        
/**
 *
 * @author zhenya
 */
public class ApiMail {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Level level = Level.INFO;
        for(Handler h : java.util.logging.Logger.getLogger("").getHandlers())    
            h.setLevel(level);
        Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
        for (;loggers.hasMoreElements();){
            Logger logger = Logger.getLogger(loggers.nextElement());
            logger.setLevel(level);
        }
        
      String host = "imap.yandex.ru";
      String mailStoreType = "imap";
      String username = "debthelp@debt-rosbank.ru"; //!
      String password = "";// !

      check(host, mailStoreType, username, password);
    }
       public static void check(String host, String storeType, String user,
      String password) 
   {
      try {

      //create properties field
      Properties properties = new Properties();

      properties.put("mail.imap.host", host);
      properties.put("mail.imap.port", "143");
      //properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      javax.mail.Store store = emailSession.getStore("imaps");

      store.connect(host, user, password);

      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
         
      System.out.println("messages.length---" + messages.length);
                  FileInputStream fis = new FileInputStream("replyStatus_"+api.Messenger.datePr+".txt");//"delivStatus_16_08.txt" "testJson" delivStatus_15_08_raw.txt
            ResponseAnswer r = new ResponseAnswer();
            r.parseResponse(r.prepare(fis));
int k=1;
        for (int i = 0, n = messages.length; i < n; i++) {
      //for (int i = messages.length-k*70, n = messages.length-(k-1)*70; i < n; i++) {
      //for (int i = 1, n = messages.length; i < n; i++) {
        Message message = messages[i];
          System.out.println("apimail.ApiMail.check() "+i+" "+message.getSubject());
          System.out.println("apimail.ApiMail.check() "+i+" "+message.getReceivedDate());          
         //if (message.getFrom()[0].toString().equalsIgnoreCase("eikhokhryakov@rosbank.ru")){
         //if (message.getSubject()!=null && message.getSubject().equalsIgnoreCase("Re: Срочно выйти на связь")){
         //if (message.getSubject()!=null && message.getSubject().equalsIgnoreCase("рассылка")){
          Address[] addresses = message.getFrom();
          //System.out.println("apimail.ApiMail.check() "+i+" "+ Arrays.asList(addresses));
          boolean isFromAddres = false;
          String address=null;
          for (int j = 0; j < addresses.length; j++) {
              address = addresses[j].toString();
              Pattern p = Pattern.compile("= <.*>");
              Matcher matcher = p.matcher(address);              
              if (matcher.find()){
                  address = address.substring(matcher.start()+3,matcher.end()-1);
              }
              System.out.println("apimail.ApiMail.check() "+i+" "+ address);
              if (r.mails.contains(address)){
                  isFromAddres = true;
                  break;
              }
          }
         if (r.replyRealDates.contains(message.getReceivedDate()) && isFromAddres){
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent());
           
            int key = r.mails.indexOf(address);
            String storeName=r.storeReplyNames.get(key);//String.valueOf(i);
            //storeName = storeName.replace("; ", "_");
            
            String ct=null;
            Enumeration<Header> headers=message.getAllHeaders();
            for (;headers.hasMoreElements();){
                Header header=headers.nextElement();
                if (header.getName().equalsIgnoreCase("Content-Type"))
                    ct = header.getValue();
                System.out.println("apimail.ApiMail.check(): name="+header.getName()+" val="+header.getValue());
            }
             putToStore(storeName, ct, message);
             
            /*CommandInfo[] commands = message.getDataHandler().getAllCommands();
             for (int j = 0; j < commands.length; j++) {
                 CommandInfo command = commands[j];
                 System.out.println("mailClient.ApiMail.check() command="+command.getCommandName());
             }
            */
            //message.getDataHandler().
         }
      }

      //close the store and folder objects
      emailFolder.close(false);
      //store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   static void putToStore(String storeName,String Content_TypeVal,Message message){
        try {
            InputStream in = message.getDataHandler().getInputStream();

            /*Logger.getLogger(ApiMail.class.getName()).info("create file:"+performer.getName());
            FileInputStream fis = new FileInputStream(performer);
            DataInputStream data = new DataInputStream(in);
            */
            ////Logger.getLogger(ApiMail.class.getName()).info("avaliable data:"+in.available());
            ////byte[] b = new byte[in.available()];
            ////data.read(b);
            //java.nio.file.Path dir = java.nio.file.Files.createDirectories(new java.io.File(Store.rootStore).toPath());
            System.out.println("storeName:"+storeName);
            java.io.File performer = new java.io.File(storeName+"_perfom.txt");
            FileOutputStream fos = new FileOutputStream(performer);
            Scanner scanner= new Scanner(in);
            scanner.useDelimiter(Pattern.compile("=\\n"));// =\\s - is uncop with this line break go from MIMO
            while(scanner.hasNext()){
                fos.write(scanner.next().getBytes("UTF-8"));// always use name=Content-Type val=text/plain; charset=utf-8
            }
            fos.flush();
            FileInputStream fis = new FileInputStream(performer);
            
            File.resetId();
            Store store = new Store(storeName,Content_TypeVal,fis);
            if (store.isBoundaryNull /*&& !store.hasNext()*/){
                final String content = message.getContent().toString();
                writeFile(new java.io.File(/*Store.rootStore+Store.pathSeparator+*/storeName+"_utf8Contain_msg"), content,"UTF-8");//!
                //no delimiters use nextLine
                //int b=data.read();            
                //while(b!=-1){
                    //fos.write(b);
                    //b = data.read();
                //}  
            }
            
            while(store.hasNext() && !store.isBoundaryNull){
                store.createNext();
                File file = new File(store);
                if (!file.isPrepared)
                    file.prepare();
                //System.out.println("mailClient.ApiMail.putToStore() segment="+store.getCurSegment());
                System.out.println("mailClient.ApiMail.putToStore() fileType="+file.getType());
                System.out.println("mailClient.ApiMail.putToStore() contentType="+file.getContentType());
                System.out.println("mailClient.ApiMail.putToStore() contentSubType="+file.getContentSubType());
                System.out.println("mailClient.ApiMail.putToStore() trasferEncoding="+file.getTransferEncoding());
                System.out.println("mailClient.ApiMail.putToStore() mimeVersion="+file.getMimeVersion());
                System.out.println("mailClient.ApiMail.putToStore() msgBoundary="+file.getMsgBoundary());
            }
            store.clean();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
   }
   static void writeFile(java.io.File file, String in,String code){
                     
        try {
            /*StringBuilder sb=new StringBuilder(); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(in)); 
            String inputLine; 
            while ((inputLine = reader.readLine()) != null) { 
                //inputLine=inputLine.replace("\"","\\\""); 
                sb.append(inputLine); 
            }*/
            //TODO move to File.prepare()
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(in.getBytes(code));
        } catch (FileNotFoundException ex) {
            System.out.println("error not found "+file.getName());
        } catch (IOException ex) {
            System.out.println("error read line");
        }
   }
}
