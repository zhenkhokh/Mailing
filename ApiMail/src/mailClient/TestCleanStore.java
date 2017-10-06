/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author zhenya
 */
public class TestCleanStore {
    public static void main(String[] args) {
        try {
            /*Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
            for (;loggers.hasMoreElements();){
            Logger logger = Logger.getLogger(loggers.nextElement());
            logger.setLevel(Level.ALL);
            }
             */
            //Logger.getGlobal().setLevel(Level.ALL);
            Level level = Level.ALL;
            for(Handler h : java.util.logging.Logger.getLogger("").getHandlers())    
                    h.setLevel(level);
            Store.logger.setLevel(level);           
            

            Store store = new Store("1928", "boundary=\"\"",new InputStream() {
                @Override
                public int read() throws IOException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            store.clean();
            //java.io.File file = new java.io.File("1928_NAME.txt");
            //store.cleanFile(file);
        } catch (Exception ex) {
            Logger.getLogger(TestCleanStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
