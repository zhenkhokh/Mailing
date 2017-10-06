/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author zhenya
 */
public class File {
    java.io.File file;
    IBinarable binarable;
    String curSegment;
    static final String undef="und";
    static int idUnd=0;
    String transferEncoding=null;
    String charset="US-ASCII";
    int lastHeader=0;
    String mimeVersion="1.0";
    String type;
    String msgBoundary=null;//use to define html part
    private String content;
    byte[] contentBuffer;
    String contentType="text";//Content-Type:
    String contentSubType="plain";//Content-Type:
    boolean isPrepared = false;
    Logger logger = Logger.getLogger(File.class.getName());
    
    //TODO encoding Content-Type: text/plain; charset="utf-8"MIME-Version: 1.0Content-Transfer-Encoding
    //Content-Type: multipart/alternative; boundary="===============1114245704382971886=="MIME-Version
    
    final static String[] encodingTypes={"base64","quoted-printable","8bit","7bit","binary","x-token"};// bug this is not case sensetive,can be quoted-string
    final static String[] charsetTypes={"utf-8","us-ascii","iso-8859-1","iso-8859-5"};// this is case sensetive,can be quoted-string,can be quoted-string
    //final static String[] contentTypes={"text/plain","text/html","multipart/alternative"};// bug this is not case sensetive
    private String fName;
    
    private boolean isBinaryType(String fName) {
        fName=fName.toUpperCase();
        if (fName.contains(".TXT") || fName.contains(".DOC")
                || fName.contains(".XML") ||fName.contains(".DOCX")
                || fName.contains(".DOCM") || fName.contains(".ODT")
                || fName.contains(".WPS") || fName.contains(".RTF")
                || fName.contains(".HTML") || fName.contains(".HTM")
                || fName.contains(".DOT") || fName.contains(".DOTX")
                || fName.contains(".DOTM")
                )
            return false;
        contentType = contentType.toUpperCase();
        if (contentType.contains("TEXT") || contentType.contains("MESSAGE"))// ignore multipart
            return false;
        return true;
    }
    static public void resetId(){
        idUnd = 0;
    }
    
    public String getMimeVersion() {
        return mimeVersion;
    }

    public String getCharset() {
        return charset;
    }

    public String getType() {
        return type;
    }
    // Content-type
    private void setMsgBoundary() throws Exception{
        int start=Store.getNextInd(this.curSegment, "boundary=");
        
        if (msgBoundary!=null)
            return;
        if (start==-1)
            return;
        msgBoundary = this.curSegment.substring(start);
        //System.out.println("mailClient.File.setMsgBoundary() 1 msgBoundary="+msgBoundary);
        start=Store.getNextInd(msgBoundary, "\"");
        if (start==-1)
            throw new Exception("File.setMsgBoundary(): 1 boundary is not matched: "+msgBoundary);
        
        msgBoundary = msgBoundary.substring(start);
        //System.out.println("mailClient.File.setMsgBoundary() 2 msgBoundary="+msgBoundary);
        int last = Store.getNextInd(msgBoundary, "\"");
        if (last==-1)
            throw new Exception("File.setMsgBoundary(): 2 boundary is not matched: "+msgBoundary);
        msgBoundary = msgBoundary.substring(0,last-1);//boundary="----==--bound.6104.web34o.yandex.ru"
        //System.out.println("mailClient.File.setMsgBoundary() 3 msgBoundary="+msgBoundary);
        if (Store.getNextInd(msgBoundary, "\"")!=-1)
            throw new Exception("File.setMsgBoundary(): 3 boundary is not matched: "+msgBoundary);
        
    }
    // use Content-Type
    private void setCharSet() throws Exception{
        //charset =  null;
        if (curSegment==null){
            return;
        }
        int start = Store.getNextInd(curSegment, "charset=");
        if (start==-1)
            return;
        String c = curSegment.substring(start);
        //TODO move to quoteDefine()
        int q = Store.getNextInd(c, "\"");
        if (q<=2 && q!=-1){
            start+=q;
            c = c.substring(q);
            q = Store.getNextInd(c, "\"");
            if (q==-1 && q>1)
                throw new Exception("File.setCharSet(): charset is not matched "+msgBoundary);
            charset = c.substring(0,q-1);
            if (lastHeader<start+q)
                lastHeader = start+q;
            return;
        }
        for (int i = 0; i < charsetTypes.length; i++) {
            String charsetType = charsetTypes[i];
            int last=Store.getNextInd(c, charsetType);
            if (last!=-1){
                int offset = getLeftSpaceOffset(c.substring(0,last));
                if (last==charsetType.length()+offset){
                    charset = charsetType;
                    if (lastHeader < start + last)
                        lastHeader = start + last;
                    return;
                }
            }
        }
    }
    private void setContentType(){
        //contentType = null;
        if (curSegment==null){
            return;
        }
        int start = Store.getNextInd(curSegment, "Content-Type:");
        if (start==-1)
            return;
        String ct = curSegment.substring(start);
        int last=Store.getNextInd(ct, "/");        
        if (last!=-1){            
            int offset = getLeftSpaceOffset(ct.substring(0,last-1));
            contentType=ct.substring(offset,last-1);
            start += last;
            ct = curSegment.substring(start);
            last = Store.getNextInd(ct, ";");
            if (last!=-1){
                contentSubType = ct.substring(0,last-1);
                if (lastHeader < start + last)
                   lastHeader = start + last; 
            }
        }
    }

    public String getMsgBoundary() {
        return msgBoundary;
    }
    
    public String getContent(){
        return content;
    }
    private void setContent(){
        if (curSegment.length()>=lastHeader)
            content = curSegment.substring(lastHeader);
        else
            content=null;
    }
    // use Content-Transfer-Encoding
    private void setTransferEncoding(){
        transferEncoding =  null;
        if (curSegment==null){
            return;
        }
        int start = Store.getNextInd(curSegment, "Content-Transfer-Encoding:");
        if (start==-1){
            return;
        }  
        String te = curSegment.substring(start);

        for (int i = 0; i < encodingTypes.length; i++) {
            String encodingType = encodingTypes[i];
            int last=Store.getNextInd(te, encodingType);
            if (last!=-1){
                int offset = getLeftSpaceOffset(te.substring(0,last));            
                if (last==encodingType.length()+offset){                   
                    transferEncoding = encodingType;
                    if (lastHeader < start + last)
                        lastHeader = start + last;
                    return;
                }
            }
        }
        //System.out.println("mailClient.File.setTransferEncoding() te="+te);
        //System.out.println("mailClient.File.setTransferEncoding() ind="+Store.getNextInd(te, "quoted-printable"));
        //System.out.println("mailClient.File.setTransferEncoding() len="+"quoted-printable".length());
       /*if (Store.getNextInd(te, "base64")=="base64".length())
            transferEncoding = "base64";
        if (Store.getNextInd(te, "quoted-printable")=="quoted-printable".length())
            transferEncoding = "quoted-printable";
        if (Store.getNextInd(te, "8bit")=="8bit".length())
            transferEncoding = "8bit";
        if (Store.getNextInd(te, "8bit")=="8bit".length())
            transferEncoding = "8bit";
        if (Store.getNextInd(te, "7bit")=="7bit".length())
            transferEncoding = "7bit";
        if (Store.getNextInd(te, "binary")=="binary".length())
            transferEncoding = "binary";
        if (Store.getNextInd(te, "x-token")=="x-token".length())
            transferEncoding = "x-token";
*/
    }

    public String getTransferEncoding() {
        return transferEncoding;
    }
    
    private String getUndId() {
        //TODO define txt format: have no <HTML> or <html> tag
        return String.valueOf(++idUnd)+".html";
    }
// use mime
    private void setMimeVersion() {
        if (curSegment==null){
            return;
        }        
        int start = Store.getNextInd(curSegment, "MIME-Version:");
        if (start!=-1){
            mimeVersion = curSegment.substring(start);
            int mimeLen=3;
            int offset = getLeftSpaceOffset(mimeVersion.substring(0,mimeLen));
            mimeVersion = mimeVersion.substring(0,mimeLen+offset);
                if (lastHeader < start + mimeVersion.length())
                    lastHeader = start + mimeVersion.length();
            if (!mimeVersion.contentEquals("1.0"))
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, "Mime is not 1.0 version, for more details see rfc-1521:"+mimeVersion);
        }
    }

    public String getContentType() {
        return contentType;
    }
    
    public String getContentSubType() {
        return contentSubType;
    }
    boolean getIsPrepared(){
        return isPrepared;
    }
 private void setFile(String storeName){
        StringBuilder fileName = new StringBuilder();
        System.out.println("mailClient.<init> fName="+fName);
        if (fName==null)// set msg
            fName=getFileName(curSegment);
        System.out.println("mailClient.setFile fName="+fName);
        fileName.append(storeName)
                .append("_")
                .append(fName);        
        file=new java.io.File(/*Store.rootStore+Store.pathSeparator+*/fileName.toString());
        System.out.println("mailClient.File.setFile() fileName="+fileName);
 }    
    class SimpleBinary implements IBinarable{

        @Override
        public byte[] getBuffer() {
            return contentBuffer;
        }
    }
    SimpleBinary simpleBinary = new SimpleBinary();

    public  File(Store store,String fName,String msgBoundary){
        this.fName=fName;
        this.msgBoundary = msgBoundary;
        init(store);
    }
    public  File(Store store,String fName){
        this.fName=fName;
        init(store);
    }
    public  File(Store store){
        init(store);        
    } 
    private void init(Store store){
        curSegment=store.getCurSegment();
        //Logger.getLogger(File.class.getName()).log(Level.INFO, "curSegment="+curSegment);
        setFile(store.getName());
        setTransferEncoding();        
        setMimeVersion();
        setContentType();        
        try {
            setCharSet();
            setMsgBoundary();//TODO move to MsgBinarable
        } catch (Exception ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        setContent();// use after all other setters        
        if (msgBoundary!=null){
            System.out.println("mailClient.File.<init>() start init msg ...");
            InputStream is = new InputStream() {
                int i=0;
                @Override
                public int read() throws IOException {
                    if (lastHeader==-1)
                        //return -1;
                        throw new IOException();
                    int ii = i+lastHeader;                    
                    if (curSegment!=null && ii<curSegment.length()){
                        i++;
                        return (int)curSegment.codePointAt(ii);//! charAt
                    }
                    //return -1;
                    throw new IOException();
                }
            };
            try {
                String msgName = store.getName()+"_"+"msg";
                Store storeMsg = new Store(msgName,"boundary=\""+msgBoundary+"\"",is);
                int msgId = 1;
                while(storeMsg.hasNext()){
                    System.out.println("mailClient.File.<init>() start init msg "+msgId);
                    //storeMsg.setName(msgName+String.valueOf(msgId++));
                    storeMsg.createNext();
                    File fileMsg = new File(storeMsg,String.valueOf(msgId++)+".txt");
                    fileMsg.prepare();
                }
                isPrepared = true;
                //return;// all job is already 
            } catch (Exception ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
                String transferEncoding=null;

    String type;
    String msgBoundary;//use to define html part

             //Logger.getLogger(File.class.getName()).log(Level.INFO, "content="+content);
             Logger.getLogger(File.class.getName()).log(Level.INFO, "charset="+charset);
             Logger.getLogger(File.class.getName()).log(Level.INFO, "mimeVersion="+mimeVersion);
             Logger.getLogger(File.class.getName()).log(Level.INFO, "contentType="+contentType);
             Logger.getLogger(File.class.getName()).log(Level.INFO, "contentSubType"+contentSubType);
        }
        
//use lastIndex all setter goes above
        String chSet = "UTF-8";
        if (charset!=null)
            chSet = charset.toUpperCase();
        /*
   A Content-Transfer-Encoding appropriate for this body is applied.
   Note that there is no fixed relationship between the content type and
   the transfer encoding.  In particular, it may be appropriate to base
   the choice of base64 or quoted-printable on character frequency
   counts which are specific to a given instance of a body.
        */
        if (transferEncoding!=null && transferEncoding.contains("base64")){
            try {
                //content = Base64Utils.decode(content);
                //content = new String(Base64MimeUtils.decode(content.getBytes()));
                //content = new String(Base64.decodeBase64(content.getBytes()));
                /*
                */
                logger.fine("base64 is aplied");
                contentBuffer = java.util.Base64.getMimeDecoder().decode(content.getBytes(chSet));//"ISO-8859-1"
                content = new String(contentBuffer,chSet);//"ISO-8859-1"
                //FileOutputStream fos = new FileOutputStream(fileName.toString()+".alt");
                //fos.write(new String(contentBuffer,"ISO-8859-1").getBytes("ISO-8859-1"));
            } catch (Exception ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {// quoted-printable
            logger.fine("not base64 is aplyed");
            try {  contentBuffer = content.getBytes(chSet);} catch (UnsupportedEncodingException ex) {} //"KOI8"           
        }
        //}else
        //    try {  contentBuffer = content.getBytes("ISO-8859-1");} catch (UnsupportedEncodingException ex) {}
        
        //Content-type: name atribute or contentType
        if (fName.equalsIgnoreCase(undef+String.valueOf(idUnd)+".txt") || isBinaryType(fName) 
                || transferEncoding!=null && transferEncoding.contains("base64")){
            logger.fine("simple binary is aplied");
            this.binarable = simpleBinary;
        }else{// no base64 ! 
            logger.fine("msgHtml is aplied");
            if (transferEncoding!=null && transferEncoding.contains("base64"))
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, "this is a Quoted-Printable Content-Transfer-Encoding: base64 is applyed here");
            binarable = new MsgHtml(contentBuffer);
            //this.binarable = new NoneBinary(contentBuffer);
            this.type = fName.substring(fName.lastIndexOf(".")+1);
        }
    }
    // Content-Disposition or Content-Type header. no other meet file name
    private String getFileName(String curSegment){
        if (curSegment==null){
            return undef+getUndId();
        }
        int start = Store.getNextInd(curSegment, "filename=");
        if (start==-1){
            return undef+getUndId();
        }
        int s=start;
        curSegment = curSegment.substring(start);
        start = Store.getNextInd(curSegment, "name=");// get max lastHeader
        if (start!=-1){
            curSegment = curSegment.substring(start);
            s+=start;
            //System.out.println("mailClient.File.getFileName() <<<<<<<<<<<<<<<<<<<");
        }
        start=Store.getNextInd(curSegment, "\"");
        s+=start;
        if (start==-1){
            return undef;
        }
        curSegment = curSegment.substring(start);
        int last = Store.getNextInd(curSegment, "\"");
        if (last==-1){
            return undef;
        }
        curSegment = curSegment.substring(0,last-1);
        if (lastHeader < s+last)
            lastHeader = s+last;//with qutos        
        
        return curSegment;//Content-Type
    }
    
    public void prepare(){
        writeToDisk();
    }
    void writeToDisk(){
        try {
            FileOutputStream fos=new FileOutputStream(file);
            //System.out.println("file name "+file.getName());
            //System.out.println("file binarable "+binarable);
            fos.write(binarable.getBuffer());
            isPrepared = true;
        } catch (FileNotFoundException ex) {
            System.out.println("error not found "+file.getName());
        } catch (IOException ex) {
            System.out.println("error write line"+file.getName());
        } catch (Exception ex){
            Logger.getLogger(File.class.getName()).log(Level.SEVERE,"unexepected",ex);
        }
    }
    static int getLeftSpaceOffset(String s){
        //System.out.println("mailClient.File.getLeftSpaceOffset() s="+s);
        int offset = s.lastIndexOf(" ");
        //System.out.println("mailClient.File.getLeftSpaceOffset() offset="+offset);
        if (offset!=-1 && offset<s.length()-1){
            return offset+1;
        }
        return 0;
    }
}
class NoneBinary implements IBinarable{
    private final byte[] b;
    public NoneBinary(byte[] b){
        this.b=b;
    }
    @Override
    public byte[] getBuffer() {
        //TODO encode from unicode: =3D=C2=C8=D2=C0=CB=C8=C9
        return b;
    }    
}