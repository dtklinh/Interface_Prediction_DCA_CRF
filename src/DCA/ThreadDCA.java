/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import MultipleCore.MyObject;
import Common.Configuration;
import Common.MyIO;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class ThreadDCA extends MyObject {

    private ArrayList<MSA_FloatMatrix> Lst_MSA;
    private String DirForOut;

    public ThreadDCA(ArrayList<MSA_FloatMatrix> lst, String dir) {
        Lst_MSA = lst;
        DirForOut = dir;
    }

    public void run() throws IOException {
        for (MSA_FloatMatrix m : Lst_MSA) {
            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+m.getName());
            utils.Utils.tic();
            
            //print MSA_FloatMatrix
//            MyIO.WriteToFile(Configuration.DirTest_DCA+m.getName()+".algn", m.getAlgnMx());
            
            String[][] d = m.GetResult3(Configuration.Dir2PDBSingleChain);
            MyIO.WriteToFile(DirForOut+m.getName()+".dca", d);
            utils.Utils.tac();
        }
    }
}
