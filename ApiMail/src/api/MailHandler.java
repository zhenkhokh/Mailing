package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class MailHandler {
    FileInputStream fi;
    FileInputStream fii;
    FileInputStream fiii;
    FileInputStream fexcl;
    FileInputStream fac13;
    FileInputStream fPhone;
    
    Properties p= new Properties();
    Properties pp= new Properties();
    Properties ppp= new Properties();
    Properties pppp= new Properties();
    Properties pAc13= new Properties();
    Properties pPhone= new Properties();
    
    public  MailHandler(){
        try {
            fi=new FileInputStream(new File("mails.txt"));
            p.load(fi);
            fii=new FileInputStream(new File("names.txt"));
            pp.load(fii);
            fiii=new FileInputStream(new File("incl.txt"));
            ppp.load(fiii);
            fexcl=new FileInputStream(new File("excl.txt"));
            pppp.load(fexcl);
            fac13=new FileInputStream(new File("ac13.txt"));
            pAc13.load(fac13);
            fPhone=new FileInputStream(new File("phone.txt"));
            pPhone.load(fPhone);
            
        } catch (FileNotFoundException ex) {
            System.out.println("MailHandler.getMail():such file is not exist");
        } catch (IOException ex) {
            System.out.println("MailHandler.getMail():io exception");;
        }
    }
    public Enumeration<Object> getAllKey(){
        return p.keys();
    }
    public String getMail(String key){
        return p.getProperty(key);
    }
    public String getName(String key){
        return pp.getProperty(key);
    }
    public boolean isIncluded(String val){
        return ppp.containsValue(val);
    }
    public boolean isExcluded(String val){
        return pppp.containsValue(val);
    }
    public String getAc13(String mail){
        String key=null;
        Enumeration<String> keys = (Enumeration<String>) p.propertyNames();
        for (;keys.hasMoreElements(); ) {
            key = keys.nextElement();
            if (p.getProperty(key).contentEquals(mail))
                break;
        }
        if (key!=null)
            return pAc13.getProperty(key);
        return null;
    }
    
static public void fixIncludeFile(String fileName){
                    InputStream in; 
        try {
            in = new FileInputStream(new File(fileName));
  
                                  StringBuilder sb=new StringBuilder(); 
                                  
                                  BufferedReader reader = new BufferedReader( 
                        new InputStreamReader(in)); 
                                  String inputLine; 
                while ((inputLine = reader.readLine()) != null) { 
                        inputLine=inputLine.replace("\t","=");
                        boolean isC=inputLine.contains("in");
                        System.out.println("MailHandler.printProp() isS="+isC);
                        sb.append(inputLine).append(System.getProperty("line.separator")); 
                }
                FileOutputStream fos=new FileOutputStream(new File(fileName));
                fos.write(sb.toString().getBytes());
	}catch (IOException ex) { System.out.println("no"+fileName+" is exist");}
    } 

    String getPhone(String key) {
        return pPhone.getProperty(key);
    }
}
