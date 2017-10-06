/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import com.sun.mail.util.ASCIIUtility;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import static javafx.scene.input.KeyCode.R;
import javafx.util.converter.ByteStringConverter;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author zhenya
 */
public class MsgHtml implements IBinarable{
    Logger logger = Logger.getLogger(MsgHtml.class.getName());
    byte[] stub;
     final ArrayList<Byte> fileBuffer = new ArrayList<Byte>();
    static int codeLen=2;
    static final String codePattern = "[\\dA-F]{2}";
    static final String hexVal = "0123456789ABCDEF";
     byte[] buffer;
         MyInputStream is = new MyInputStream();
        
         class MyInputStream extends InputStream {
        public int i=0;
            @Override
            public int read() throws IOException {
                //if ()
                int c = -1;
                if (i < buffer.length)
                    c = (int)buffer[i++];
                return c;
            };
            public void resetPos(){
                i=0;
            }
            public int getPos(){
                return i;
            }
            public char getChar(int offset) throws ArrayIndexOutOfBoundsException{
                int ii=i+offset;
                if (ii<0 && ii>=buffer.length)
                    new ArrayIndexOutOfBoundsException("position is "+ii);
                return (char)buffer[ii];
            }
        }
    public static void main(String[] args) {
        //Logger.get.setLevel(Level.OFF);
        Level level = Level.ALL;
        for(Handler h : java.util.logging.Logger.getLogger("").getHandlers())    
            h.setLevel(level);
        Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
        for (;loggers.hasMoreElements();){
            Logger logger = Logger.getLogger(loggers.nextElement());
            logger.setLevel(level);
        }     
        //MsgHtml m = new MsgHtml("sd=sd=3D=C2=C8=D2=C0=CB=C8=C9\"=0D=0A\"g\\utg".getBytes());
        //MsgHtml m = new MsgHtml("1=3D=C2=C8=D2=C0=CB=C8=C9 =C2=C8=CA=D2=CE=D0=CE=C2=C8=D7".getBytes());  
        //MsgHtml m = new MsgHtml("109=3D=C2=DF=D7=C5=D1=CB=C0=C2 =C0=CD=C0=D2=CE=CB=DC=C5=C2=C8=D7".getBytes());
        //MsgHtml m = new MsgHtml("=C2=AB=D0=9D=D0=B0=D1=81=D1=82=D0=BE=D1=8F=D1=89=D0=B5=D0=B5".getBytes()); 
        //MsgHtml m = new MsgHtml("=D0=B7=D0=B0==D1=89=D0=B8=D1=89=D0=B5=D0=BD=D1=8B".getBytes());
        //MsgHtml m = new MsgHtml("=C2=AB=D0=9D=D0=B0=D1=81=D1=82=D0=BE=D1=8F=D1=89=D0=B5=D0=B5 =D1=81=D0=BE==D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5 =D0=B8 =D0=BB=D1=8E=D0=B1=D1=8B==D0=B5 =D0=BF=D1=80=D0=B8=D0=BB=D0=BE=D0=B6=D0=B5=D0=BD=D0=B8=D1=8F =D0=BA ==D0=BD=D0=B5=D0=BC=D1=83 (=D0=B4=D0=B0=D0=BB=D0=B5=D0=B5 =C2=AB=D1=81=D0=BE==D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5=C2=BB) =D0=BA=D0=BE=D0=BD=D1=84==D0=B8=D0=B4=D0=B5=D0=BD=D1=86=D0=B8=D0=B0=D0=BB=D1=8C=D0=BD=D1=8B, =D0=BF==D1=80=D0=B5=D0=B4=D0=BD=D0=B0=D0=B7=D0=BD=D0=B0=D1=87=D0=B5=D0=BD=D1=8B ==D0=B8=D1=81=D0=BA=D0=BB=D1=8E=D1=87=D0=B8=D1=82=D0=B5=D0=BB=D1=8C=D0=BD=D0==BE =D0=B4=D0=BB=D1=8F =D0=BB=D0=B8=D1=86, =D0=BA=D0=BE=D1=82=D0=BE=D1=80==D1=8B=D0=BC =D0=BE=D0=BD=D0=B8 =D0=B0=D0=B4=D1=80=D0=B5=D1=81=D0=BE=D0=B2==D0=B0=D0=BD=D1=8B, =D0=B8 =D0=BC=D0=BE=D0=B3=D1=83=D1=82 =D1=81=D0=BE=D0==B4=D0=B5=D1=80=D0=B6=D0=B0=D1=82=D1=8C =D0=B8=D0=BD=D1=84=D0=BE=D1=80=D0==BC=D0=B0=D1=86=D0=B8=D1=8E, =D1=80=D0=B0=D1=81=D0=BF=D1=80=D0=BE=D1=81=D1==82=D1=80=D0=B0=D0=BD=D0=B5=D0=BD=D0=B8=D0=B5 =D0=BA=D0=BE=D1=82=D0=BE=D1==80=D0=BE=D0=B9 =D0=BE=D0=B3=D1=80=D0=B0=D0=BD=D0=B8=D1=87=D0=B5=D0=BD=D0==BE =D0=B7=D0=B0=D0=BA=D0=BE=D0=BD=D0=BE=D0=BC. =D0=9B=D1=8E=D0=B1=D0=BE=D0==B5 =D0=BD=D0=B5=D1=81=D0=B0=D0=BD=D0=BA=D1=86=D0=B8=D0=BE=D0=BD=D0=B8=D1==80=D0=BE=D0=B2=D0=B0=D0=BD=D0=BD=D0=BE=D0=B5 =D0=B8=D1=81=D0=BF=D0=BE=D0==BB=D1=8C=D0=B7=D0=BE=D0=B2=D0=B0=D0=BD=D0=B8=D0=B5 =D0=B8=D0=BB=D0=B8 =D1==80=D0=B0=D1=81=D0=BF=D1=80=D0=BE=D1=81=D1=82=D1=80=D0=B0=D0=BD=D0=B5=D0=BD==D0=B8=D0=B5 =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D1=8F =D0=B7==D0=B0=D0=BF=D1=80=D0=B5=D1=89=D0=B0=D0=B5=D1=82=D1=81=D1=8F.=D0=AD=D0=BB=D0=B5=D0=BA=D1=82=D1=80=D0=BE=D0=BD=D0=BD=D1=8B=D0=B5 =D1=81==D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D1=8F =D0=BD=D0=B5 =D0=B7=D0=B0==D1=89=D0=B8=D1=89=D0=B5=D0=BD=D1=8B =D0=BE=D1=82 =D0=B8=D0=B7=D0=BC=D0=B5==D0=BD=D0=B5=D0=BD=D0=B8=D0=B9. =D0=9F=D0=90=D0=9E =D0=A0=D0=9E=D0=A1=D0=91==D0=90=D0=9D=D0=9A, =D0=B5=D0=B3=D0=BE =D0=B4=D0=BE=D1=87=D0=B5=D1=80=D0=BD==D0=B8=D0=B5 =D0=BE=D1=80=D0=B3=D0=B0=D0=BD=D0=B8=D0=B7=D0=B0=D1=86=D0=B8==D0=B8 =D0=B8 =D0=BF=D0=B0=D1=80=D1=82=D0=BD=D0=B5=D1=80=D1=8B =D0=BD=D0=B5= =D0=BD=D0=B5=D1=81=D1=83=D1=82 =D0=BE=D1=82=D0=B2=D0=B5=D1=82=D1=81=D1=82==D0=B2=D0=B5=D0=BD=D0=BD=D0=BE=D1=81=D1=82=D1=8C =D0=B7=D0=B0 =D1=81=D0=BE==D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5, =D0=B2 =D1=81=D0=BB=D1=83=D1=87==D0=B0=D0=B5 =D0=B5=D0=B3=D0=BE =D0=B8=D0=B7=D0=BC=D0=B5=D0=BD=D0=B5=D0=BD==D0=B8=D1=8F =D0=B8=D0=BB=D0=B8 =D1=84=D0=B0=D0=BB=D1=8C=D1=81=D0=B8=D1=84==D0=B8=D0=BA=D0=B0=D1=86=D0=B8=D0=B8.=D0=9B=D1=8E=D0=B1=D0=BE=D0=B5 =D0=BF=D1=80=D0=B5=D0=B4=D0=BE=D1=81=D1=82==D0=B0=D0=B2=D0=BB=D0=B5=D0=BD=D0=B8=D0=B5 =D0=9F=D0=90=D0=9E =D0=A0=D0=9E==D0=A1=D0=91=D0=90=D0=9D=D0=9A =D0=B8=D0=BD=D1=84=D0=BE=D1=80=D0=BC=D0=B0==D1=86=D0=B8=D0=B8 =D0=B2 =D1=80=D0=B0=D0=BC=D0=BA=D0=B0=D1=85 =D0=B4=D0=B0==D0=BD=D0=BD=D0=BE=D0=B3=D0=BE =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD==D0=B8=D1=8F =D0=BD=D0=B5 =D0=B4=D0=BE=D0=BB=D0=B6=D0=BD=D0=BE =D1=80=D0=B0==D1=81=D1=81=D0=BC=D0=B0=D1=82=D1=80=D0=B8=D0=B2=D0=B0=D1=82=D1=8C=D1=81=D1==8F =D0=BA=D0=B0=D0=BA =D0=BF=D1=80=D0=B5=D0=B4=D0=BE=D1=81=D1=82=D0=B0=D0==B2=D0=BB=D0=B5=D0=BD=D0=B8=D0=B5 =D0=BD=D0=B5=D0=BF=D0=BE=D0=BB=D0=BD=D0==BE=D0=B9 =D0=B8=D0=BB=D0=B8 =D0=BD=D0=B5=D0=B4=D0=BE=D1=81=D1=82=D0=BE=D0==B2=D0=B5=D1=80=D0=BD=D0=BE=D0=B9 =D0=B8=D0=BD=D1=84=D0=BE=D1=80=D0=BC=D0==B0=D1=86=D0=B8=D0=B8, =D0=B2 =D1=82=D0=BE=D0=BC =D1=87=D0=B8=D1=81=D0=BB==D0=B5 =D0=BA=D0=B0=D0=BA =D1=83=D0=BC=D0=BE=D0=BB=D1=87=D0=B0=D0=BD=D0=B8==D0=B5 =D0=B8=D0=BB=D0=B8 =D0=B7=D0=B0=D0=B2=D0=B5=D1=80=D0=B5=D0=BD=D0=B8==D0=B5 =D0=BE=D0=B1=D0=BE=D0=B1=D1=81=D1=82=D0=BE=D1=8F=D1=82=D0=B5=D0=BB==D1=8C=D1=81=D1=82=D0=B2=D0=B0=D1=85, =D0=B8=D0=BC=D0=B5=D1=8E=D1=89=D0=B8==D1=85 =D0=B7=D0=BD=D0=B0=D1=87=D0=B5=D0=BD=D0=B8=D0=B5 =D0=B4=D0=BB=D1=8F ==D0=B7=D0=B0=D0=BA=D0=BB=D1=8E=D1=87=D0=B5=D0=BD=D0=B8=D1=8F, =D0=B8=D1=81==D0=BF=D0=BE=D0=BB=D0=BD=D0=B5=D0=BD=D0=B8=D1=8F =D0=B8=D0=BB=D0=B8 =D0=BF==D1=80=D0=B5=D0=BA=D1=80=D0=B0=D1=89=D0=B5=D0=BD=D0=B8=D1=8F =D1=81=D0=B4==D0=B5=D0=BB=D0=BA=D0=B8, =D0=B8=D0=BB=D0=B8 =D0=BA=D0=B0=D0=BA =D0=BE=D0==B1=D1=8F=D0=B7=D0=B0=D1=82=D0=B5=D0=BB=D1=8C=D1=81=D1=82=D0=B2=D0=BE =D0==B7=D0=B0=D0=BA=D0=BB=D1=8E=D1=87=D0=B8=D1=82=D1=8C =D1=81=D0=B4=D0=B5=D0==BB=D0=BA=D1=83 =D0=BD=D0=B0 =D1=83=D1=81=D0=BB=D0=BE=D0=B2=D0=B8=D1=8F=D1==85, =D0=B8=D0=B7=D0=BB=D0=BE=D0=B6=D0=B5=D0=BD=D0=BD=D1=8B=D1=85 =D0=B2 ==D0=B4=D0=B0=D0=BD=D0=BD=D0=BE=D0=BC =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5==D0=BD=D0=B8=D0=B8, =D0=B8=D0=BB=D0=B8 =D0=BA=D0=B0=D0=BA =D0=BE=D1=84=D0==B5=D1=80=D1=82=D0=B0, =D0=B5=D1=81=D0=BB=D0=B8 =D1=82=D0=BE=D0=BB=D1=8C=D0==BA=D0=BE =D0=B8=D0=BD=D0=BE=D0=B5 =D0=BF=D1=80=D1=8F=D0=BC=D0=BE =D0=BD=D0==B5 =D1=83=D0=BA=D0=B0=D0=B7=D0=B0=D0=BD=D0=BE =D0=B2 =D0=B4=D0=B0=D0=BD=D0==BD=D0=BE=D0=BC =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B8 =D0==B8=D0=BB=D0=B8 =D0=B2 =D0=B4=D0=BE=D0=B3=D0=BE=D0=B2=D0=BE=D1=80=D0=B5.This message and any attachments (the =E2=80=9Cmessage=E2=80=9D) are confid=ential, intended solely for the addresses, and may contain legally privileg=ed information. Any unauthorized use or dissemination is prohibited. E-mail=s are susceptible to alteration. Neither PJSC ROSBANK nor any of its subsid=iaries or affiliates shall be liable for the message if altered, changed or= falsified.Any information presented by PJSC ROSBANK in this message shall not be cons=idered either as the delivery of incomplete or inaccurate information, in p=articular, as a non-disclosure of or a representation on the circumstances =that are of the importance for execution, performance or termination of tra=nsaction, or as a promise or obligation to execute transaction on terms and= conditions specified in this message, or as an offer, unless otherwise is =expressly specified in this message or in an agreement.=C2=BB".getBytes());
        //MsgHtml m = new MsgHtml("<p>=C2=AB=D0=9D=D0=B0=D1=81=D1=82=D0=BE=D1=8F=D1=89=D0=B5=D0=B5 =D1=81=D0==BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5 =D0=B8 =D0=BB=D1=8E=D0=B1=D1==8B=D0=B5 =D0=BF=D1=80=D0=B8=D0=BB=D0=BE=D0=B6=D0=B5=D0=BD=D0=B8=D1=8F =D0==BA =D0=BD=D0=B5=D0=BC=D1=83 (=D0=B4=D0=B0=D0=BB=D0=B5=D0=B5 =C2=AB=D1=81==D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5=C2=BB) ==D0=BA=D0=BE=D0=BD=D1=84=D0=B8=D0=B4=D0=B5=D0=BD=D1=86=D0=B8=D0=B0=D0=BB=D1==8C=D0=BD=D1=8B, =D0=BF=D1=80=D0=B5=D0=B4=D0=BD=D0=B0=D0=B7=D0=BD=D0=B0=D1==87=D0=B5=D0=BD=D1=8B =D0=B8=D1=81=D0=BA=D0=BB=D1=8E=D1=87=D0=B8=D1=82=D0==B5=D0=BB=D1=8C=D0=BD=D0=BE =D0=B4=D0=BB=D1=8F =D0=BB=D0=B8=D1=86, =D0=BA==D0=BE=D1=82=D0=BE=D1=80=D1=8B=D0=BC =D0=BE=D0=BD=D0=B8 ==D0=B0=D0=B4=D1=80=D0=B5=D1=81=D0=BE=D0=B2=D0=B0=D0=BD=D1=8B, =D0=B8 =D0=BC==D0=BE=D0=B3=D1=83=D1=82 =D1=81=D0=BE=D0=B4=D0=B5=D1=80=D0=B6=D0=B0=D1=82==D1=8C =D0=B8=D0=BD=D1=84=D0=BE=D1=80=D0=BC=D0=B0=D1=86=D0=B8=D1=8E, =D1=80==D0=B0=D1=81=D0=BF=D1=80=D0=BE=D1=81=D1=82=D1=80=D0=B0=D0=BD=D0=B5=D0=BD=D0==B8=D0=B5 =D0=BA=D0=BE=D1=82=D0=BE=D1=80=D0=BE=D0=B9 ==D0=BE=D0=B3=D1=80=D0=B0=D0=BD=D0=B8=D1=87=D0=B5=D0=BD=D0=BE =D0=B7=D0=B0==D0=BA=D0=BE=D0=BD=D0=BE=D0=BC. =D0=9B=D1=8E=D0=B1=D0=BE=D0=B5 =D0=BD=D0=B5==D1=81=D0=B0=D0=BD=D0=BA=D1=86=D0=B8=D0=BE=D0=BD=D0=B8=D1=80=D0=BE=D0=B2=D0==B0=D0=BD=D0=BD=D0=BE=D0=B5 =D0=B8=D1=81=D0=BF=D0=BE=D0=BB=D1=8C=D0=B7=D0==BE=D0=B2=D0=B0=D0=BD=D0=B8=D0=B5 =D0=B8=D0=BB=D0=B8 ==D1=80=D0=B0=D1=81=D0=BF=D1=80=D0=BE=D1=81=D1=82=D1=80=D0=B0=D0=BD=D0=B5=D0==BD=D0=B8=D0=B5 =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D1=8F =D0==B7=D0=B0=D0=BF=D1=80=D0=B5=D1=89=D0=B0=D0=B5=D1=82=D1=81=D1=8F.<br>=D0=AD=D0=BB=D0=B5=D0=BA=D1=82=D1=80=D0=BE=D0=BD=D0=BD=D1=8B=D0=B5 \n =D1=81==D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D1=8F =D0=BD=D0=B5 =D0=B7=D0=B0==D1=89=D0=B8=D1=89=D0=B5=D0=BD=D1=8B =D0=BE=D1=82 =D0=B8=D0=B7=D0=BC=D0=B5==D0=BD=D0=B5=D0=BD=D0=B8=D0=B9. =D0=9F=D0=90=D0=9E =D0=A0=D0=9E=D0=A1=D0=91==D0=90=D0=9D=D0=9A, =D0=B5=D0=B3=D0=BE ==D0=B4=D0=BE=D1=87=D0=B5=D1=80=D0=BD=D0=B8=D0=B5 =D0=BE=D1=80=D0=B3=D0=B0==D0=BD=D0=B8=D0=B7=D0=B0=D1=86=D0=B8=D0=B8 =D0=B8 =D0=BF=D0=B0=D1=80=D1=82==D0=BD=D0=B5=D1=80=D1=8B =D0=BD=D0=B5 =D0=BD=D0=B5=D1=81=D1=83=D1=82 =D0=BE==D1=82=D0=B2=D0=B5=D1=82=D1=81=D1=82=D0=B2=D0=B5=D0=BD=D0=BD=D0=BE=D1=81=D1==82=D1=8C =D0=B7=D0=B0 =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0==B5, =D0=B2 =D1=81=D0=BB=D1=83=D1=87=D0=B0=D0=B5 =D0=B5=D0=B3=D0=BE =D0=B8=D0=B7=D0=BC==D0=B5=D0=BD=D0=B5=D0=BD=D0=B8=D1=8F =D0=B8=D0=BB=D0=B8 =D1=84=D0=B0=D0=BB==D1=8C=D1=81=D0=B8=D1=84=D0=B8=D0=BA=D0=B0=D1=86=D0=B8=D0=B8.</p><p>=D0=9B==D1=8E=D0=B1=D0=BE=D0=B5 =D0=BF=D1=80=D0=B5=D0=B4=D0=BE=D1=81=D1=82=D0=B0==D0=B2=D0=BB=D0=B5=D0=BD=D0=B8=D0=B5 =D0=9F=D0=90=D0=9E ==D0=A0=D0=9E=D0=A1=D0=91=D0=90=D0=9D=D0=9A =D0=B8=D0=BD=D1=84=D0=BE=D1=80==D0=BC=D0=B0=D1=86=D0=B8=D0=B8 =D0=B2 =D1=80=D0=B0=D0=BC=D0=BA=D0=B0=D1=85 ==D0=B4=D0=B0=D0=BD=D0=BD=D0=BE=D0=B3=D0=BE =D1=81=D0=BE=D0=BE=D0=B1=D1=89==D0=B5=D0=BD=D0=B8=D1=8F =D0=BD=D0=B5 =D0=B4=D0=BE=D0=BB=D0=B6=D0=BD=D0=BE ==D1=80=D0=B0=D1=81=D1=81=D0=BC=D0=B0=D1=82=D1=80=D0=B8=D0=B2=D0=B0=D1=82=D1==8C=D1=81=D1=8F ==D0=BA=D0=B0=D0=BA =D0=BF=D1=80=D0=B5=D0=B4=D0=BE=D1=81=D1=82=D0=B0=D0=B2==D0=BB=D0=B5=D0=BD=D0=B8=D0=B5 =D0=BD=D0=B5=D0=BF=D0=BE=D0=BB=D0=BD=D0=BE==D0=B9 =D0=B8=D0=BB=D0=B8 =D0=BD=D0=B5=D0=B4=D0=BE=D1=81=D1=82=D0=BE=D0=B2==D0=B5=D1=80=D0=BD=D0=BE=D0=B9 =D0=B8=D0=BD=D1=84=D0=BE=D1=80=D0=BC=D0=B0==D1=86=D0=B8=D0=B8, =D0=B2 =D1=82=D0=BE=D0=BC =D1=87=D0=B8=D1=81=D0=BB=D0==B5 ==D0=BA=D0=B0=D0=BA =D1=83=D0=BC=D0=BE=D0=BB=D1=87=D0=B0=D0=BD=D0=B8=D0=B5 ==D0=B8=D0=BB=D0=B8 =D0=B7=D0=B0=D0=B2=D0=B5=D1=80=D0=B5=D0=BD=D0=B8=D0=B5 ==D0=BE=D0=B1 =D0=BE=D0=B1=D1=81=D1=82=D0=BE=D1=8F=D1=82=D0=B5=D0=BB=D1=8C==D1=81=D1=82=D0=B2=D0=B0=D1=85, =D0=B8=D0=BC=D0=B5=D1=8E=D1=89=D0=B8=D1=85 ==D0=B7=D0=BD=D0=B0=D1=87=D0=B5=D0=BD=D0=B8=D0=B5 =D0=B4=D0=BB=D1=8F ==D0=B7=D0=B0=D0=BA=D0=BB=D1=8E=D1=87=D0=B5=D0=BD=D0=B8=D1=8F, =D0=B8=D1=81==D0=BF=D0=BE=D0=BB=D0=BD=D0=B5=D0=BD=D0=B8=D1=8F =D0=B8=D0=BB=D0=B8 =D0=BF==D1=80=D0=B5=D0=BA=D1=80=D0=B0=D1=89=D0=B5=D0=BD=D0=B8=D1=8F =D1=81=D0=B4==D0=B5=D0=BB=D0=BA=D0=B8, =D0=B8=D0=BB=D0=B8 =D0=BA=D0=B0=D0=BA =D0=BE=D0==B1=D1=8F=D0=B7=D0=B0=D1=82=D0=B5=D0=BB=D1=8C=D1=81=D1=82=D0=B2=D0=BE ==D0=B7=D0=B0=D0=BA=D0=BB=D1=8E=D1=87=D0=B8=D1=82=D1=8C =D1=81=D0=B4=D0=B5==D0=BB=D0=BA=D1=83 =D0=BD=D0=B0 =D1=83=D1=81=D0=BB=D0=BE=D0=B2=D0=B8=D1=8F==D1=85, =D0=B8=D0=B7=D0=BB=D0=BE=D0=B6=D0=B5=D0=BD=D0=BD=D1=8B=D1=85 =D0=B2= =D0=B4=D0=B0=D0=BD=D0=BD=D0=BE=D0=BC =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5==D0=BD=D0=B8=D0=B8, =D0=B8=D0=BB=D0=B8 =D0=BA=D0=B0=D0=BA ==D0=BE=D1=84=D0=B5=D1=80=D1=82=D0=B0, =D0=B5=D1=81=D0=BB=D0=B8 =D1=82=D0=BE==D0=BB=D1=8C=D0=BA=D0=BE =D0=B8=D0=BD=D0=BE=D0=B5 =D0=BF=D1=80=D1=8F=D0=BC==D0=BE =D0=BD=D0=B5 =D1=83=D0=BA=D0=B0=D0=B7=D0=B0=D0=BD=D0=BE =D0=B2 =D0==B4=D0=B0=D0=BD=D0=BD=D0=BE=D0=BC =D1=81=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0==BD=D0=B8=D0=B8 =D0=B8=D0=BB=D0=B8 =D0=B2 ==D0=B4=D0=BE=D0=B3=D0=BE=D0=B2=D0=BE=D1=80=D0=B5.</p><p>This message and any attachments (the =E2=80=9Cmessage=E2=80=9D) are con=fidential, =intended solely for the addresses, and may contain legally privileged =information. Any unauthorized use or dissemination is prohibited. =E-mails are susceptible to alteration. Neither PJSC&nbsp;<span style=3D\"lin=e-height: 15.86px;\">ROSBANK&nbsp;</span><span style=3D\"line-height: 1.22;\">=nor any of its subsidiaries or affiliates shall be liable for the message if =altered, changed or falsified.</span></p><p>Any information presented by PJ=SC =ROSBANK in this message shall not be considered either as the delivery =of incomplete or inaccurate information, in particular, as a =non-disclosure of or a representation on the circumstances that are of =the importance for execution, performance or termination of transaction, or \n as a promise or obligation to execute transaction on terms and =conditions specified in this message, or as an offer, unless otherwise =is expressly specified in this message or in an agreemen".getBytes());
    MsgHtml m = new MsgHtml("<p>=C2=AB=D0=9D=D0=B0=D1=81=D1=82=D0=BE=D1=8F=D1=89=D0=B5=D0=B5 =D1=81=D0\n=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5 =D0=B8 =D0=BB=D1=8E=D0=B1=D1\n=8B=D0=B5 =D0=BF=D1=80=D0=B8=D0=BB=D0=BE=D0=B6=D0=B5=D0=BD=D0=B8=D1=8F =D0\n=BA =D0=BD=D0=B5=D0=BC=D1=83 (=D0=B4=D0=B0=D0=BB=D0=B5=D0=B5 =C2=AB=D1=81\n=D0=BE=D0=BE=D0=B1=D1=89=D0=B5=D0=BD=D0=B8=D0=B5=C2=BB)".getBytes());
    }
    private void process(){
        
        //byte[] b = "sd=sd=3D=C2=C8=D2=C0=CB=C8=C9\"=0D=0A\"g\\utg".getBytes();
        logger.log(Level.INFO,"mailClient.process buffer.length="+buffer.length);
        ByteArrayOutputStream b =  new ByteArrayOutputStream();
        //try {
        //    baos.write(b.array());
        //} catch (IOException ex) {
        //    Logger.getLogger(MsgHtml.class.getName()).log(Level.SEVERE, null, ex);
        //}

        Scanner scanner = new Scanner( is );
        scanner.useDelimiter(Pattern.compile("(\\\"=0D=0A\\\"|=\\s)?"));//=\\s line break go from MIMO, CRLF see https://www.ietf.org/rfc/rfc2045.txt
        StringBuilder sb = new StringBuilder();
        int i=1;
        for(;scanner.hasNext();i++){
            sb.append(scanner.next());
        }
 //System.out.println("mailClient.MsgHtml.main() "+sb.toString());       
        buffer = sb.toString().getBytes();
        is.resetPos();
        scanner = new Scanner(is);
        scanner.useDelimiter(Pattern.compile("\\n*={1,2}"));
        //scanner.useDelimiter(Pattern.compile("={1}"));     
        boolean isFirstStrange = false;
        if (sb.charAt(0)=='='){
            isFirstStrange = true;
        }
        boolean isFirst=true;
        sb = new StringBuilder();
        i=1;
          for(;scanner.hasNext();){
            String s = scanner.next();            
            boolean isMatched = false;
            if (s.length()>=codeLen)
                isMatched = s.substring(0,codeLen).matches(codePattern);
            if (isFirst && !isFirstStrange && isMatched)
                isMatched = false;   
            logger.log(Level.FINER,"mailClient.MsgHtml.main() "+isMatched+" "+s+" "+(i%2));
            if (s.length()==codeLen && isMatched){
                if (i%2==1){
                    sb.append("\\u");
                }
                sb.append(s);
                //i++;
            }else if(isMatched /*&& i%2==0*/){
                sb.append("\\u");//!
                sb.append(s);
                //i++;
            }else if(isFirst){
                sb.append(s);
            }else {
                sb.append("=").append(s);
            }
            isFirst=false;
        }
        //if (isFirstStrange && sb.charAt(0)!='=')
            //sb.insert(0,"=");
        //else if (!isFirstStrange && sb.charAt(0)=='=')
            //sb.delete(0, 1);
        logger.log(Level.FINEST,"mailClient.MsgHtml.main() 2 "+sb);
       buffer = sb.toString().getBytes();
       is.resetPos();
       scanner = new Scanner(is);
       scanner.useDelimiter(Pattern.compile("\\\\u"));
       isFirst=true;
       //b = new byte[buffer.length];
       i=0;
       try{
       for(;scanner.hasNext();){
           String s = scanner.next();
           boolean isMatched = false;
           if (s.length()>=codeLen){
               isMatched = s.substring(0,codeLen).matches("[\\dA-F]{2}");
           }
           if (isFirst && !isFirstStrange && isMatched)
                isMatched = false;   
           if (isMatched && s.length()==codeLen){
               logger.log(Level.FINER,"mailClient.MsgHtml.main() 1");
               //byte[] bb = getEncodedBytes(s.substring(0,4));
               //fileBuffer.add(bb[0]);
               //fileBuffer.add(bb[1]);
               //b[i++]=bb[0];b[i++]=bb[1];
               b.write(getEncodedByte(s.substring(0,codeLen)));
               //b[i++]=getEncodedByte(s.substring(0,codeLen));
           }else if (isMatched){
               byte bb[];
               logger.log(Level.FINER,"mailClient.MsgHtml.main() 2 "+s);
               //byte[] bb = getEncodedBytes(s.substring(0,4));
               //fileBuffer.add(bb[0]);
               //fileBuffer.add(bb[1]);
               //ArrayList a = new ArrayList(Arrays.asList(s.substring(4).getBytes()));//!
               //fileBuffer.addAll(a);
               //b[i++]=bb[0];b[i++]=bb[1];
               b.write(getEncodedByte(s.substring(0,codeLen)));
               //b[i++]=getEncodedByte(s.substring(0,codeLen));
               bb = s.substring(codeLen).getBytes();
               b.write(bb);
               //for (int j = 0; j < bb.length; j++) {
                   //b[i++] = bb[j];
               //    logger.log(Level.FINER,"mailClient.MsgHtml.main() 2.1 bb="+bb[j]);
               //}
           }else if (isFirst){
               logger.log(Level.FINER,"mailClient.MsgHtml.main() 3");
               //ArrayList a = new ArrayList(Arrays.asList(s.getBytes()));//!
               //fileBuffer.addAll(a);
               
               byte[] bb = s.getBytes();
               b.write(bb);
               //for (int j = 0; j < bb.length; j++) {
               //    b[i++] = bb[j];
               //}
           }else{//very rary case
               logger.log(Level.FINER,"mailClient.MsgHtml.main() 4");
               //ArrayList a = new ArrayList(Arrays.asList(s.getBytes()));//!
               //ileBuffer.addAll(new ArrayList(Arrays.asList("\\u".getBytes())));
               //fileBuffer.addAll(a);          
               byte[] bb="\\u".getBytes();
               //b[i++] = bb[0];
               //b[i++] = bb[1];
               b.write(bb);
               bb = s.getBytes();
               b.write(bb);
               //for (int j = 0; j < bb.length; j++) {
               //    b[i++] = bb[j];
               //}
           }
           logger.log(Level.FINER,"mailClient.MsgHtml.main() ...");
           isFirst = false;
       }
       }catch(IOException e){}
        try {
            if (Logger.getGlobal().getLevel().intValue() <= Level.FINEST.intValue()){
                logger.log(Level.FINEST,"mailClient.MsgHtml.main() "+new String(b.toByteArray(),"UTF-8"));
            }
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        //printStream(b);
       this.buffer = b.toByteArray();
    }
    public static IntStream intStream(byte[] array) {
        return IntStream.range(0, array.length).map(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {    
                return array[operand];
            }
            });
    }
    public static byte[] toByteArray(IntStream stream) {
        return stream.collect(ByteArrayOutputStream::new
                , new ObjIntConsumer<ByteArrayOutputStream>() {
            @Override
            public void accept(ByteArrayOutputStream baos, int i) {
                baos.write((byte) i);// byteValue()
           }
        }, new BiConsumer<ByteArrayOutputStream, ByteArrayOutputStream>() {
            @Override
            public void accept(ByteArrayOutputStream baos1, ByteArrayOutputStream baos2) {
                try {
                    baos1.write(baos2.toByteArray());
                } catch (IOException ex) {
                    Logger.getLogger(MsgHtml.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        })
                .toByteArray()
                ;
    }
    static private byte[] getEncodedBytes(String hex){
        byte[] b = new byte[2];
        int hn = hexVal.indexOf( hex.charAt( 0 ) );
        int ln = hexVal.indexOf( hex.charAt(  1 ) );
        int hn1 = hexVal.indexOf( hex.charAt( 2 ) );
        int ln1 = hexVal.indexOf( hex.charAt( 3 ) );
        b[0] = (byte)( ( hn << 4 ) | ln );
        b[1] = (byte)( ( hn1 << 4 ) | ln1 );
        return b;
    }
    static private byte getEncodedByte(String hex){        
        int hn = hexVal.indexOf( hex.charAt( 0 ) );
        int ln = hexVal.indexOf( hex.charAt(  1 ) );
        byte b = (byte)( ( hn << 4 ) | ln );
        return b;
    }
    static void printStream(byte[] b){
                String p;
            byte[] stub = b;
                 IntStream ints = intStream(stub);//fileBuffer.stream().mapToInt((Integer i1) -> i1);
                        /*.mapToInt(new ToIntFunction<Integer>() {
                     @Override
                     public int applyAsInt(Integer value) {
                        return value.intValue();
                     }
                 });*/
                        
                  byte[] stub1=ints.collect(ByteArrayOutputStream::new
                    , (ByteArrayOutputStream t, int value) -> {
                        t.write(value);
                    } //, (baos,e) -> baos
        , (ByteArrayOutputStream t, ByteArrayOutputStream u) -> {
            try {
                t.write(u.toByteArray());
            } catch (IOException ex) {
                Logger.getLogger(MsgHtml.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).toByteArray();
                 try {
                p = new String(//toByteArray(ints)
                        stub
                        ,"KOI8");
                System.out.println("mailClient.MsgHtml.main() "+p);
            } catch (UnsupportedEncodingException ex) {   Logger.getLogger(MsgHtml.class.getName()).log(Level.SEVERE, null, ex);}
 
    }
    static byte[] toByte(String s){
        byte[] b = new byte[s.length()/2];
        //ByteStringConverter bsc  = new ByteStringConverter();
        //bsc.
        String hexVal = "0123456789ABCDEF";
        for (int i=0;i<s.length();i+=4){
            //byte c = (byte) s.charAt(2*i);
            
            //b[i] = (byte) Integer.parseInt(s.substring(4*i,4*i+4),16);
            //b[i/4] = (byte) Integer.parseInt(s.substring(i,i+2),16);
            //b[i/4+1] = (byte) Integer.parseInt(s.substring(i+3,i+4),16);
            int hn = hexVal.indexOf( s.charAt( i ) );
            int ln = hexVal.indexOf( s.charAt( i + 1 ) );
            int hn1 = hexVal.indexOf( s.charAt( i + 2 ) );
            int ln1 = hexVal.indexOf( s.charAt( i + 3 ) );
            b[i/4] = (byte)( ( hn << 4 ) | ln );
            b[i/4+1] = (byte)( ( hn1 << 4 ) | ln1 );

            System.out.println("mailClient.MsgHtml.toByte() i="+i+" "+   b[i/4]+" "+  b[i/4+1] );
            
            //text += (char) Integer.parseInt(s.substring(4*i,4*i+4),16);
            //text += (char) Integer.parseInt(s.substring(4*i+3,4*i+4),16);
            //b[i] =  (byte) ((byte) c<<4);
            //c = (byte) s.charAt(2*i+1);
            //b[i] = (byte) (b[i] & 0xF0 | c & 0x0F);
        }
        return b;
    }
    public MsgHtml(byte[] b){
        buffer = b;
        process();
        //content;
        //try { content = new String(b,"ISO-8859-1");} catch (UnsupportedEncodingException ex) {}
        //content = ;
        //=3D=C2=C8=D2=C0=CB=C8=C9
    }
        
    @Override
    public byte[] getBuffer() {
        return buffer;
    }
    
}
