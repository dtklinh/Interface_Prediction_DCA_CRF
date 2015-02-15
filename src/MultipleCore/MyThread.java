/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MultipleCore;

import BLAST.BLAST;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author t.dang
 */
public class MyThread extends Thread {

    private MyObject bls;

    public MyThread(MyObject b) {
        bls = b;

    }

    public void run() {

//        try {
            try {
                bls.run();
            } catch (Exception ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
//        } 
//        catch (InterruptedException ex) {
//            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
//        } 
//        catch (IOException ex) {
//            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
