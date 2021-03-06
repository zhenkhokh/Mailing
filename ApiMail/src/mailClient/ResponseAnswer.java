/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import api.MailHandler;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

/**
 *
 * @author zhenya
 */
public class ResponseAnswer {
    byte[] buffer;
    InputStream out;
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> mails = new ArrayList<>();
    ArrayList<String> statuses = new ArrayList<>();
    ArrayList<String> replyStatuses = new ArrayList<>();
    ArrayList<String> replyDates = new ArrayList<>();
    ArrayList<Date> replyRealDates = new ArrayList<>();
    ArrayList<String> storeSendingNames = new ArrayList<>();
    ArrayList<String> storeReplyNames = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    MailHandler mailHandler = new MailHandler();
            
    class BufferInputStream extends InputStream {
            int i=0;
            @Override
            public int read() throws IOException {
                if (i>=buffer.length)
                    return -1;
                return buffer[i++];
            }
        }
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("replyStatus16_08");//"delivStatus_16_08.txt" "testJson" delivStatus_15_08_raw.txt
            ResponseAnswer r = new ResponseAnswer();
            //r.parseDeliv(r.prepare(fis));
            r.parseResponse(r.prepare(fis));
            Iterator<String> it = r.replyDates.iterator();
            Iterator<Date> it1 = r.replyRealDates.iterator();
            for(;it.hasNext();){
                String dateS = it.next();
                //Date date = //DateFormat.getDateTimeInstance(, timeStyle)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //sdf.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")));                
                //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                //Calendar.
                //Date date = sdf.parse(dateS+"+03:00");
                //Date date = sdf.parse(dateS);
               // Date date1 = sdf.parse(dateS);
                //System.out.println("date:"+date);
                //System.out.println("isEqual:"+date.equals(date1));
                //System.out.println("format date:"+sdf.format(date));
                System.out.println("format date:"+it1.next());
            }
            //r.prepare(fis);
            //System.out.println("mailClient.ResposeAnswer.prepare() "+match);
           FileOutputStream fos = new FileOutputStream("testJson");
           fos.write(r.buffer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    InputStream prepare(InputStream in){
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("(\\{\"request.id\":)");
        StringBuilder sb = new StringBuilder();
        //scanner.findInLine("(\\{\"request.id\":)?");
        String lastPart = scanner.next();
        if (scanner.hasNext())
            sb.append("{\"request.id\":").append(scanner.next());
        else
            sb.append("{\"request.id\":").append(lastPart);
        //sb.append("{\"request.id\":");
        buffer = sb.toString().getBytes();
        scanner = new Scanner(new BufferInputStream());
        scanner.useDelimiter(",\\[");
        boolean isFirst = true;
        String newLine = System.getProperty("line.separator");
        sb = new StringBuilder();
        while(scanner.hasNext()){
            if (!isFirst)
                sb.append(newLine).append(",[").append(scanner.next());
            else
                sb.append(scanner.next());
            isFirst = false;
        }
        
        buffer = sb.toString().getBytes();
        InputStream matched = new BufferInputStream();
        scanner = new Scanner(matched);
        scanner.useDelimiter("\n");
        sb  = new StringBuilder();
        String line;
        while(scanner.hasNext()){
            line = scanner.next();
            //System.out.println("mailClient.ResposeAnswer.prepare() line:"+line);
            sb.append(line);
            //if (line.length()>=1 && line.charAt(line.length()-1)==']')
             //   sb.append("\n");
            //else 
                //sb.append("----------");
        }
        //String match = sb.toString();
        buffer = sb.toString().getBytes();
        //String match = scanner.findInLine("(\\{\"request.id\":)?.*");
        //match = match.replaceAll("([^\\]\\w]\\n){1}", "------------");

        //match = match.replaceAll("([\\]]\\n){1}", "\n");
              
        out = new BufferInputStream();
        return out;
    }
    
    void parseDeliv(InputStream is){

        try {
            //parser = factory.createParser(new FileInputStream("delivStatus_15_08.txt"));
            parse(is, false);
            Iterator<String> iterator = mails.iterator();
            Iterator<String> iterator1 = dates.iterator();
            Iterator<String> iterator2 = statuses.iterator();
            java.nio.file.Path dir = java.nio.file.Files.createDirectories(new java.io.File(Store.rootStore).toPath());
            for (; iterator.hasNext();) {
                String mail = iterator.next();
                String date = iterator1.next();
                String status = iterator2.next();
                StringBuilder storeName = new StringBuilder();         
                storeName.append(dir.toUri().getPath())
                        .append(mail).append("_")
                        .append(status).append("_")
                        .append(date);
                storeSendingNames.add(storeName.toString().replace(":",";"));//!
                System.out.println("mailClient.ResposeAnswer.main()  sb="+storeName.toString());
                java.io.File file = new java.io.File(storeName.toString().replace(":",";"));//!
                FileOutputStream fos = new FileOutputStream(file);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void parseResponse(InputStream is){
               try {
            //parser = factory.createParser(new FileInputStream("delivStatus_15_08.txt"));
            parse(is, true);
            Iterator<String> iterator = mails.iterator();
            Iterator<String> iterator1 = dates.iterator();
            Iterator<String> iterator2 = statuses.iterator();
            Iterator<String> iterator3 = replyDates.iterator();
            Iterator<String> iterator4 = replyStatuses.iterator();
            java.nio.file.Path dir = java.nio.file.Files.createDirectories(new java.io.File(Store.rootStore).toPath());
            for (; iterator.hasNext();) {
                String mail = iterator.next();
                String date = iterator1.next();
                String status = iterator2.next();
                String rDate = iterator3.next();
                String rStatus = iterator4.next();
                
                StringBuilder storeName = new StringBuilder();
                mail = mailHandler.getAc13(mail);   
                storeName.append(dir.toUri().getPath())
                        .append(mail).append("_")
                        //.append(status).append("_")
                        //.append(date).append("_")
                        //.append(rStatus).append("_")
                        .append(rDate);
                storeReplyNames.add(storeName.toString().replace(":",";"));//!
                System.out.println("mailClient.ResposeAnswer.main()  sb="+storeName.toString());
                java.io.File file = new java.io.File(storeName.toString().replace(":",";"));//!
                FileOutputStream fos = new FileOutputStream(file);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    void parse(InputStream is,boolean isReplyParsing){
            JsonParserFactory factory = Json.createParserFactory(null);        
            JsonParser parser;            
            parser = factory.createParser(is);
            int i=-3;
            boolean startArray = false;
            dates = new ArrayList<>();
            mails = new ArrayList<>();
            statuses = new ArrayList<>();
            if (isReplyParsing){
                replyStatuses = new ArrayList<>();
                replyDates = new ArrayList<>();
            }
            int pLen = 5;
            if (isReplyParsing)
                pLen = 7;
            
            while(parser.hasNext()){
                JsonParser.Event e =  parser.next();
                if (startArray)
                    i++;
                if (!startArray && e.compareTo(Event.KEY_NAME)==0 && parser.getString().contentEquals("list"))
                    startArray = true;
                if (e.compareTo(Event.VALUE_STRING)==0 && i%pLen==1){
                    mails.add(parser.getString());
                    //System.out.println("mailClient.ResposeAnswer.main() "+i+" "+e.name()+", "+parser.getString());
                }
                if (e.compareTo(Event.VALUE_STRING)==0 && i%pLen==0){
                    dates.add(parser.getString());
                    //System.out.println("mailClient.ResposeAnswer.main() "+i+" "+e.name()+", "+parser.getString());
                }
                if (e.compareTo(Event.VALUE_STRING)==0 && i%pLen==2){
                    statuses.add(parser.getString());
                    //System.out.println("mailClient.ResposeAnswer.main() "+i+" "+e.name()+", "+parser.getString());
                }
                if (isReplyParsing){
                    if (e.compareTo(Event.VALUE_STRING)==0 && i%pLen==3){
                        replyStatuses.add(parser.getString());
                        //System.out.println("mailClient.ResposeAnswer.main() "+i+" "+e.name()+", "+parser.getString());
                    }
                    if (e.compareTo(Event.VALUE_STRING)==0 && i%pLen==4){
                        try {
                            replyDates.add(parser.getString());
                            replyRealDates.add(sdf.parse(parser.getString()));
                            //System.out.println("mailClient.ResposeAnswer.main() "+i+" "+e.name()+", "+parser.getString());
                        } catch (ParseException ex) {
                            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
    }
    String parseString(InputStream is,String name){
        JsonParserFactory factory = Json.createParserFactory(null);        
        JsonParser parser;            
        parser = factory.createParser(is);
        String val = null;
        boolean isMet = false;
        while(parser.hasNext()){
            JsonParser.Event e = parser.next();
            if (e.compareTo(JsonParser.Event.KEY_NAME)==0 && parser.getString().contentEquals(name))
                isMet = true;
            if (isMet && e.compareTo(JsonParser.Event.VALUE_STRING)==0 ){
                val = parser.getString();
                break;
            }
        }
        return val;
    }
    
    static void testParseString(){
        ResponseAnswer r = new ResponseAnswer();
        try {
            String name = "request.id";
            String val = r.parseString(new FileInputStream("testJson"),name);
            System.out.println("mailClient.ResponseAnswer.main() name="+name+" "+"val= "+val);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResponseAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
