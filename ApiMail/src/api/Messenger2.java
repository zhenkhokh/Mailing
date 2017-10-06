/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
package api;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.json.JSONObject; 

public class Messenger2 {
/** 
 * 
 * @param args args[0] - repeat number, args[1] - phoneNumber as XXXxxxYYYY, args[3] - message 
 * @throws MalformedURLException 
 */     static String sessionIdStoreName="sessionId.txt";
        static String sessionId;
	static String name=null;
        static String phone=null;
        static String logFName = Messenger.datePr+".log";
        static FileOutputStream fos;
        static final String htmlFName="msgRossnet.html";// msg2.txt
        static final String replyToMail="rosnet@rosbank.ru";//"debthelp@debt-rosbank.ru"
        static final String subject="Дружить выгодно";//"Срочно выйти на связь"
        static final String passwd="";//!
        static final String login="rosbank";//!
        
        public static void main(String[] args) {
            sessionId=null;
     try {
         fos = new FileOutputStream(logFName);
     } catch (FileNotFoundException ex) {
         Logger.getLogger(Messenger2.class.getName()).log(Level.SEVERE, null, ex);
     }
            if (!args[0].equalsIgnoreCase("1")){
		try{
		  FileInputStream fis = new FileInputStream(sessionIdStoreName);
		  Scanner scanner = new Scanner(fis); 
		  sessionId = scanner.next();
		  System.out.println("sessionId="+sessionId);
		 }catch(FileNotFoundException ex){java.util.logging.Logger.getGlobal().log(java.util.logging.Level.SEVERE, null, ex);}
		}
	    if (args[0].equalsIgnoreCase("1")){
		oneSend(args,"");
		return;
	    }
            MailHandler m=new MailHandler();
            Enumeration<Object> keys=m.getAllKey();
            int startId=new Integer(args[1]);
            int id=startId;
	    String response="";
            for (; keys.hasMoreElements();){
             	System.out.println("------------------------");
                String key = (String) keys.nextElement();
		if (1==1/*m.isIncluded(key)==true && m.isExcluded(key)==false*/){
                	String mail=m.getMail(key);
                        mail = mail.replace(" ", "");// no space in mails
			name=m.getName(key);
                        phone = m.getPhone(key);
                	args[1]=String.valueOf(id++);
                System.out.println("send to key= "+key+" mail="+mail+" name="+name);	
                	response=oneSend(args,mail);
                	int c=1;
                	while (response.contains("error") && c++<=5){
				System.out.println("repeat sending ...");
				args[1]=String.valueOf(id++);	
				response=oneSend(args,mail);			
			}
			if (response.contains("error"))
				System.out.println("ERROR sending: key= "+key+" mail="+mail+" "+name);
		}else 
			System.out.println("excluded key="+key);
            }
        }
        public static String oneSend(String[] args,String mail) { 
                StringBuilder sb = new StringBuilder(); 
                try{ 
                     String msg="hello(%04%3f%04%40\u0438\u0432\u0435\u0442)отapi.sendsay"; 
                     //byte[] bts=msg.getBytes("Windows-1251");//java.nio.charset.Charset.defaultCharset() 
                     //String msgBuf=new String(bts,"UTF-8"); 
                     //msgBuf=java.net.URLEncoder.encode(msgBuf,"UTF-8")+"сырое"; 
                     String msgBuf=null; 
                     try{msgBuf=getHTMLbody();}catch(IOException e){System.out.println("error: no html file found"); return null;} 
                     msgBuf=getJSONString(msgBuf); 
//System.out.println(msgBuf); 

                    JsonObject js=null; 
                    JsonBuilderFactory factory = javax.json.Json.createBuilderFactory(null); 
                    if (args[0].equalsIgnoreCase("1")){ 
                                        System.out.println("start login");                 
                      js=javax.json.Json.createObjectBuilder() 
                      .add("action", "login") 
                      .add("login",login) 
                      .add("passwd",passwd) 
                      .build(); 
                    }else if (args[0].equalsIgnoreCase("2")){ 
                                                   System.out.println("start issue.send");	 
                                                   //java.nio.ByteBuffer msgBuf=java.nio.charset.Charset.forName("UTF-8").encode(msg); 
                       js=javax.json.Json.createObjectBuilder() 
                      .add("action","issue.send") 
                      //.add("session","rosbank_rosbank:CuLHybdyt3Ob-gYG/OQfiAt5H5Iu9BLnqknE1RKTCmLQ:149845847583334988.170589651851676.660389824963") 
                                        .add("session",sessionId) 
                                        .add("login","rosbank") 
                      .add("passwd",passwd) 
                      .add("sendwhen","now") 
                      .add("group","personal") 
                      .add("email", mail)//"zhenkhokh@yandex.ru" "anufrieva87@gmail.com"
                                        //.add("reply.email","debthelp@debt-rosbank.ru")//see stat.uni debthelp@debt-rosbank.ru 
                      .add("letter",factory.createObjectBuilder().add("subject", getJSONString(subject)) 
                          .add("from.email",replyToMail)//  "ask@sendsay.ru"
                          .add("message",factory.createObjectBuilder().add("html",msgBuf).build())
			  .add("reply.email",replyToMail) 
                          ).build();
                    }else if (args[0].equalsIgnoreCase("3")){ 
                                                   // status >=-1 is ok 
                                                   System.out.println("start track.get"); 
                                                   js=javax.json.Json.createObjectBuilder() 
                                                   .add("action","track.get") 
                        .add("login",login) 
                        .add("passwd",passwd) 
                                                   .add("id",args[2]) 
                                                   .add("session",sessionId) 
                                                   .build(); 
                                      }else if (args[0].equalsIgnoreCase("4")){//denied 
                                                   System.out.println("start issue.emailsender.list"); 
                                                   js=javax.json.Json.createObjectBuilder() 
                                                   .add("action","issue.emailsender.list") 
                        .add("login",login) 
                        .add("passwd",passwd) 
                                                   .add("session",sessionId) 
                                                   .build(); 
                                      }else if (args[0].equalsIgnoreCase("6")){// 
                                                   System.out.println("start stat.uni"); 
                                                   js=javax.json.Json.createObjectBuilder() 
                                                   .add("action","stat.uni") 
                        .add("login",login) 
                        .add("passwd",passwd) 
                                                   .add("session",sessionId) 
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
                                                                                     .add("v",getJSONString("current - 2 days - 1 second")) 
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
//System.out.println(js.toString()); 
wr.writeBytes("apiversion=100&json=1&request.id="+args[1]+"&request="+js.toString()); 
//wr.write("{%22action%22:%22ping%22}"); 
//wr.write("%22ip%22:%228.8.8.8%22"); 
                    BufferedInputStream buf = new BufferedInputStream(uRLConnection.getInputStream()); 

                    int b = buf.read();//sInputStream io; io.re 
                    while (b!=-1){ 
                        sb.append((char)b); 
                        b = buf.read(); 
                    } 
  System.out.println(sb);
  fos.write(sb.toString().getBytes());
		                                                                                                          
                    }catch (MalformedURLException e){ 
                        e.printStackTrace(); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    }
return sb.toString(); 
        } 

        static String getJSONString(String in){ 
                String out=null; 
                try{ 
                        out=java.net.URLEncoder.encode(in,"UTF-8"); 
                        return out; 
                }catch(java.io.UnsupportedEncodingException e){ return out;} 
        } 
        static String getHTMLbody() throws IOException{ 
                InputStream in = new FileInputStream(new File(htmlFName)); 
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
                        if (name!=null)
                            inputLine= inputLine.replace("$",getName());
                        if (phone!=null)
                            inputLine= inputLine.replace("@",getPhone());
			//inputLine=inputLine.replace("\t","");
                        sb.append(inputLine).append("\\n");
                } 
System.out.println("------------------------"); 
//System.out.println(sb); 
System.out.println("------------------------"); 
                return sb.toString(); 
        }
	static String getName(){
		return name;
	} 
        static String getPhone(){
		return phone;
	} 

} 


