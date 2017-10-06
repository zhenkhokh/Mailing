/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 *
 * @author zhenya
 */
public class Store {
    String boundary;
    String storeName;
    String curSegment=null;
    int segmentId=0;
    InputStream is;
    private Scanner scanner;
    public static Logger logger = Logger.getLogger(Store.class.getName());
    boolean isBoundaryNull = false;
    public static final String rootStore="store2";
    public static final String pathSeparator = java.io.File.pathSeparator;

    //String metaData;
    //static String "filename="
    public Store(String storeName,String Content_TypeVal) throws Exception{
        this.storeName = storeName;
        setBoundary(Content_TypeVal);
    }
    public Store(String storeName,String Content_TypeVal,InputStream is) throws Exception{
        this(storeName, Content_TypeVal);
        this.scanner=new Scanner(is);
        if (boundary!=null)
            this.scanner.useDelimiter(Pattern.compile(boundary));
        else
            this.scanner.useDelimiter(Pattern.compile("$"));// not work, do not try "" delimeter
        isBoundaryNull = boundary==null;
    }
    public boolean getIsBoundaryNull(){
        return isBoundaryNull;
    }
    public void setName(String storeName){
        this.storeName = storeName;
    }
    public String getCurSegment(){
        return curSegment;
    }
    public String getName(){
        return storeName;
    }
    public void createNext(){
        curSegment=scanner.next();
        System.out.println("mailClient.Store.createNext() curSegment="+curSegment);
    }
    public boolean hasNext(){
        return scanner.hasNext();
    }
        
    public void createNext(InputStream is){
        Scanner scanner=new Scanner(is);
        scanner.useDelimiter(Pattern.compile(boundary));
        curSegment=scanner.next();
    }
    
    public boolean hasNext(InputStream is){
        Scanner scanner=new Scanner(is);
        scanner.useDelimiter(Pattern.compile(boundary));
        return scanner.hasNext();
    }
    void setBoundary(String header) throws Exception{
        int start=getNextInd(header, "\"");
        if (start==-1){
            //throw new Exception("Store.getNextInd(): 1 boundary is not matched: "+header);
            boundary = null;
            return;
        }
        header = header.substring(start);
        int last = getNextInd(header, "\"");
        if (last==-1 && last>1)
            throw new Exception("Store.getNextInd(): 2 boundary is not matched: "+header);
        boundary = header.substring(0,last-1);//boundary="----==--bound.6104.web34o.yandex.ru"
        boundary = "--"+boundary;// imap exception can be met
        System.out.println("apimail.Store.setBoundary() boundary="+boundary);
        if (getNextInd(boundary, "\"")!=-1)
            throw new Exception("Store.getNextInd(): 3 boundary is not matched: "+header);
        //return boundary;
    }
    static public int getNextInd(String header,String tag) {
        int i = header.indexOf(tag);
        if (i!=-1)
            i += tag.length();
        return i;
    }
    public void clean(){
        
        try {
            java.nio.file.Files.createDirectories(new java.io.File(rootStore).toPath());
            Stream<Path> allFiles = java.nio.file.Files.list(new java.io.File(rootStore).toPath());

            Iterator<Path> it = allFiles.filter(new Predicate<Path>() {
                @Override
                public boolean test(Path t) {
                    return t.toString().contains(storeName);
                }
            }).iterator();
            for (; it.hasNext();) {
                Path next = it.next();
                logger.log(Level.FINE,"mailClient.Store.clean() fileName="+next);
                InputStream is = new FileInputStream(next.toFile());
                Scanner scanner = new Scanner(is);
                scanner.useDelimiter(Pattern.compile("(-\\s*)?+"));// --\\s*)?+ <--do not try it, (-+\\s)?+ - keep --
                boolean isFileRemoved = false;
                // scan 2-nd level depth
                if (!scanner.hasNext()){
                    logger.log(Level.FINE,"mailClient.Store.clean() has no next");
                    if (!scanner.hasNextLine()){
                        logger.log(Level.INFO,"mailClient.Store.clean() "+next+" such file has removed 1");
                        next.toFile().deleteOnExit();
                        isFileRemoved = true;
                    }else{
                        String s = scanner.nextLine(); 
                        logger.log(Level.FINE, "mailClient.Store.clean() has next line.len:"+ s.length());
                        if (s.isEmpty() && !scanner.hasNext()){
                            logger.log(Level.INFO,"mailClient.Store.clean() "+next+" such file has removed 2");
                            next.toFile().deleteOnExit();
                            isFileRemoved = true;
                        }else{//do not try to remove here !!!
                            if (s.isEmpty())
                                s = scanner.next();
                            logger.log(Level.FINE, "mailClient.Store.clean() has nextLine nextLine.len:"+ s.length());
                            //logger.log(Level.INFO,"mailClient.Store.clean() "+next+" such file has removed 3");
                        }
                    }
                    //String s = scanner.next(); 
                }else{                    
                    String s = scanner.next();
                    logger.log(Level.FINE, "mailClient.Store.clean() has next.len:"+ s.length()); 
                    if (s.isEmpty() && !scanner.hasNext()){
                        logger.log(Level.INFO,"mailClient.Store.clean() "+next+" such file has removed 3");
                        next.toFile().deleteOnExit();
                        isFileRemoved = true;
                    }else {
                          if (s.isEmpty())
                            s = scanner.next();            
                          logger.log(Level.FINE, "mailClient.Store.clean() has next nextLine.len:"+ s.length());
                          if (s.isEmpty()){
                            logger.log(Level.INFO,"mailClient.Store.clean() "+next+" such file has removed 4");
                            next.toFile().deleteOnExit();
                            isFileRemoved = true;
                          }
                    }
                        
                }
                //if (!isFileRemoved)
                    //cleanFile(next.toFile(), is);
            }
        } catch (IOException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //TODO move to File
    public void cleanFile(java.io.File file){
        try {            
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(Pattern.compile("--"));
            if (!scanner.hasNext()){
                logger.info("miss file:"+file.toPath());
                return;
            }
            String s = scanner.next();
            logger.log(Level.INFO,"mailClient.Store.cleanFile() s="+s);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(s.getBytes());//buggy-part 
            fos.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}