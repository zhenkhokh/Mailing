import java.io.BufferedInputStream; 
import java.io.OutputStreamWriter; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.MalformedURLException; 
import java.net.URL; 
import java.net.URLConnection; 
import java.net.HttpURLConnection; 
import javax.json.JsonObject; 
import javax.json.JsonBuilderFactory; 
import javax.json.Json; 

import java.io.File; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.FileInputStream; 
import java.io.Reader; 
import java.io.BufferedReader; 

//import org.json.JSONObject; 

public class Messenger { 
/** 
 * 
 * @param args args[0] - repeat number, args[1] - phoneNumber as XXXxxxYYYY, args[3] - message 
 * @throws MalformedURLException 
 */ 
        public static void main(String[] args) { 
                try{ 
                                                   String msg="hello(%04%3f%04%40\u0438\u0432\u0435\u0442)îòapi.sendsay"; 
                                                   //byte[] bts=msg.getBytes("Windows-1251");//java.nio.charset.Charset.defaultCharset() 
                                                   //String msgBuf=new String(bts,"UTF-8"); 
                                                   //msgBuf=java.net.URLEncoder.encode(msgBuf,"UTF-8")+"ñûðîå"; 
                                                   String msgBuf=null; 
                                                   try{msgBuf=getHTMLbody();}catch(IOException e){System.out.println("error: no html file found"); return;} 
                                                   msgBuf=getJSONString(msgBuf); 
System.out.println(msgBuf); 

                    JsonObject js=null; 
                    JsonBuilderFactory factory = javax.json.Json.createBuilderFactory(null); 
                    if (args[0].equalsIgnoreCase("1")){ 
                                        System.out.println("start login");                 
                      js=javax.json.Json.createObjectBuilder() 
                      .add("action", "login") 
                      .add("login","rosbank") 
                      .add("passwd","uchi8Yo") 
                      .build(); 
                    }else if (args[0].equalsIgnoreCase("2")){ 
                                                   System.out.println("start issue.send"); 
                                                   //java.nio.ByteBuffer msgBuf=java.nio.charset.Charset.forName("UTF-8").encode(msg); 
                       js=javax.json.Json.createObjectBuilder() 
                      .add("action","issue.send") 
                      //.add("session","rosbank_rosbank:CuLHybdyt3Ob-gYG/OQfiAt5H5Iu9BLnqknE1RKTCmLQ:149845847583334988.170589651851676.660389824963") 
                                        .add("session",args[2]) 
                                        .add("login","rosbank") 
                      .add("passwd","uchi8Yo") 
                      .add("sendwhen","now") 
                      .add("group","personal") 
                      .add("email", "anufrieva87@gmail.com") 
                                        //.add("reply.email","debthelp@dept-rosbank.ru")//see stat.uni debthelp@dept-rosbank.ru 
                      .add("letter",factory.createObjectBuilder().add("subject", "test") 
                          .add("from.email","debthelp@dept-rosbank.ru")//  "ask@sendsay.ru"
                          .add("message",factory.createObjectBuilder().add("html",msgBuf).build())
			  .add("reply.email","debthelp@dept-rosbank.ru") 
                          ).build(); 
                    }else if (args[0].equalsIgnoreCase("3")){ 
                                                   // status >=-1 is ok 
                                                   System.out.println("start track.get"); 
                                                   js=javax.json.Json.createObjectBuilder() 
                                                   .add("action","track.get") 
                        .add("login","rosbank") 
                        .add("passwd","uchi8Yo") 
                                                   .add("id",args[2]) 
                                                   .add("session",args[3]) 
                                                   .build(); 
                                      }else if (args[0].equalsIgnoreCase("4")){//denied 
                                                   System.out.println("start issue.emailsender.list"); 
                                                   js=javax.json.Json.createObjectBuilder() 
                                                   .add("action","issue.emailsender.list") 
                        .add("login","rosbank") 
                        .add("passwd","uchi8Yo") 
                                                   .add("session",args[2]) 
                                                   .build(); 
                                      }else if (args[0].equalsIgnoreCase("6")){// 
                                                   System.out.println("start stat.uni"); 
                                                   js=javax.json.Json.createObjectBuilder() 
                                                   .add("action","stat.uni") 
                        .add("login","rosbank") 
                        .add("passwd","uchi8Yo") 
                                                   .add("session",args[2]) 
                                                   .add("select",factory.createArrayBuilder() 
                                                                    /*.add(Json.createObjectBuilder() 
                                                                                     .add("field","read.dt")// read time 
                                                                                     .build() 
                                                                                     ) 
                                                                   .add(Json.createObjectBuilder() 
                                                                                     .add("field","reply.email") 
                                                                                     .build() 
                                                                                     ) 
       								    */.add(Json.createObjectBuilder() 
                                                                                     .add("field","deliv.letter.dt") 
                                                                                     .build() 
                                                                                     ) 
                                                                    .add(Json.createObjectBuilder() 
                                                                                     .add("field","deliv.member.email") 
                                                                                     .build() 
                                                                                     ) 
                                                                    .add(Json.createObjectBuilder() 
                                                                                     .add("field","deliv.status") 
                                                                                     .build() 
                                                                                     ) 
                                                                    .add(Json.createObjectBuilder() 
                                                                                     .add("field","deliv.replyed.status") 
                                                                                     .build() 
                                                                                     ) 
                                                                    /**/                                                                     
                                                                    .build() 
                                                                    ) 
                                                   .add("filter",factory.createArrayBuilder() 
                                                                    .add(factory.createObjectBuilder() 
                                                                                     .add("a","deliv.letter.dt")//delive.issue.dt - personal 
                                                                                     .add("op",">=") 
                                                                                     .add("v",getJSONString("current - 0 days - 1 second")) 
                                                                                     .build() 
                                                                                     ) 
                                                                    .build() 
                                                                     ) 
                                                   .build(); 
                                      } 
                    //URL url = new URL("https://api.sendsay.ru/?apiversion=100&json=1&request={%22action%22:%22ping%22}"); 
                    URL url = new URL("https://api.sendsay.ru/clu206"); 
                    //URL url = new URL("http://ip.jsontest.com/?callback=showMyIP"); 

                    HttpURLConnection uRLConnection = (HttpURLConnection) url.openConnection(); 
uRLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded,charset=UTF-8"); // application/json "application/json" multipart/form-data 
uRLConnection.setRequestProperty("Accept", "application/json"); 
uRLConnection.setRequestMethod("POST"); 
uRLConnection.setDoOutput(true); 
uRLConnection.setDoInput(true); 
                    uRLConnection.connect(); 
                    //OutputStreamWriter wr = new OutputStreamWriter(uRLConnection.getOutputStream()); 
                    DataOutputStream wr = new DataOutputStream(uRLConnection.getOutputStream()); 
System.out.println(js.toString()); 
wr.writeBytes("apiversion=100&json=1&request.id="+args[1]+"&request="+js.toString()); 
//wr.write("{%22action%22:%22ping%22}"); 
//wr.write("%22ip%22:%228.8.8.8%22"); 
                    BufferedInputStream buf = new BufferedInputStream(uRLConnection.getInputStream()); 
                    StringBuilder sb = new StringBuilder(); 
                    int b = buf.read();//sInputStream io; io.re 
                    while (b!=-1){ 
                        sb.append((char)b); 
                        b = buf.read(); 
                    } 
                        System.out.println(sb);                                                                                                       
                    }catch (MalformedURLException e){ 
                        e.printStackTrace(); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
        } 

        static String getJSONString(String in){ 
                String out=null; 
                try{ 
                        out=java.net.URLEncoder.encode(in,"UTF-8"); 
                        return out; 
                }catch(java.io.UnsupportedEncodingException e){ return out;} 
        } 
        static String getHTMLbody() throws IOException{ 
                InputStream in = new FileInputStream(new File("msg.html")); 
                                  StringBuilder sb=new StringBuilder(); 
                /*Reader reader = new InputStreamReader(in, "UTF-8");   
                int r=0; 
                while ((r = reader.read()) != -1) { 
                        char ch = (char) r; 
                        sb.append(ch); 
                } 
                                  */ 
                                  BufferedReader reader = new BufferedReader( 
                        new InputStreamReader(in)); 
                                  String inputLine; 
                while ((inputLine = reader.readLine()) != null) { 
                                                   inputLine=inputLine.replace("\"","\\\""); 
                        sb.append(inputLine).append("\\n"); 
                } 
System.out.println("------------------------"); 
System.out.println(sb); 
System.out.println("------------------------"); 
                return sb.toString(); 
        } 
} 