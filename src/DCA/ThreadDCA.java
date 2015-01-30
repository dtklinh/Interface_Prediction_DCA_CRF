/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import MultipleCore.MyObject;
import Support.MyIO;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class ThreadDCA extends MyObject {

    private ArrayList<MSA> Lst_MSA;
    private String DirForOut;

    public ThreadDCA(ArrayList<MSA> lst, String dir) {
        Lst_MSA = lst;
        DirForOut = dir;
    }

    public void run() throws IOException {
        for (MSA m : Lst_MSA) {
            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+m.getName());
            utils.Utils.tic();
            double[][] d = m.GetResult2();
            MyIO.WriteToFile(DirForOut+m.getName()+".dca", d);
            utils.Utils.tac();
        }
    }
}
