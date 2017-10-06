package mailClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhenya
 */
public class TestReadSegment {
    static public void main(String[] a){
        try {
        Level level = Level.INFO;
            for(Handler h : java.util.logging.Logger.getLogger("").getHandlers())    
                h.setLevel(level);
            Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
            for (;loggers.hasMoreElements();){
                Logger logger = Logger.getLogger(loggers.nextElement());
                logger.setLevel(level);
            }
        
            String storeName="testCreateFile";
            //String storeName="testCreateFile1";
            InputStream is = new FileInputStream(storeName+".txt");
            String ct="boundary=\"--=_mixed 0040A7D44325816B_=\"";
            //String ct = "boundary=\"------==--bound.6104.web34o.yandex.ru\"";
            Store store = new Store(storeName, ct,is);
            while (store.hasNext()){
                store.createNext();
                String segment=store.getCurSegment();
                System.out.println("mailClient.TestCreateFile.main() segment="+segment);
                System.out.println("mailClient.TestReadSegment.main()-----------------------------");
                File file = new File(store);
                file.prepare();
                System.out.println("mailClient.TestReadSegment.main() typeFile="+file.getType());
                System.out.println("mailClient.TestReadSegment.main() transfer-encoding="+file.getTransferEncoding());
                System.out.println("mailClient.TestReadSegment.main() charset="+file.getCharset());
                System.out.println("mailClient.TestReadSegment.main() mimeVersion="+file.getMimeVersion());
                //System.out.println("mailClient.TestReadSegment.main() content="+file.getContent());
                System.out.println("mailClient.TestReadSegment.main() msgBoundary-->"+file.getMsgBoundary());    
            }
            store.clean();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestReadSegment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestReadSegment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
