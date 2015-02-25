/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HomologousProteinsType;

import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import DCA.MSA;
import MultipleCore.MyObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class ThreadComplex extends MyObject{
    private ArrayList<MSA> Lst_MSA;
    private String DirForOut;
    private int Separate;

    public ThreadComplex(ArrayList<MSA> lst, String dir, int s) {
        Lst_MSA = lst;
        DirForOut = dir;
        Separate = s;
    }

    public void run() throws IOException {
        List<String> lst = utils.Utils.dir2list(Configuration.Dir2PDB);
        for (MSA m : Lst_MSA) {
            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+m.getName());
            utils.Utils.tic();
            String[] id = StaticMethod.findChainID(lst, m.getName().substring(0, 4));
            Complex c = new Complex(m, Separate, id[0], id[1]);
            //print MSA
//            MyIO.WriteToFile(Configuration.DirTest_DCA+m.getName()+".algn", m.getAlgnMx());
            c.refineMSA();
            String[][] d = c.getResult();
            MyIO.WriteToFile(DirForOut+m.getName()+".dca", d);
            utils.Utils.tac();
        }
    }
}
