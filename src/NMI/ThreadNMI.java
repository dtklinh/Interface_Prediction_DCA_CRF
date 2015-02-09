/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NMI;

import Common.MyIO;
import DCA.MSA;
import MultipleCore.MyObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class ThreadNMI extends MyObject{
    private ArrayList<MSA> Lst_MSA;
    private String DirForOut;
    private double[][] DSM;
    public ThreadNMI(ArrayList<MSA> lst, String dirOut, double[][] dsm){
        Lst_MSA = lst;
        DirForOut = dirOut;
        DSM = dsm;
    }
    public void run() throws IOException{
        for (MSA m : Lst_MSA) {
            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+m.getName());
            utils.Utils.tic();
            
            double[][] d = m.NormalizedMutualInformation(DSM);
            MyIO.WriteToFile(DirForOut+m.getName()+".nmi", d);
            utils.Utils.tac();
        }
    }
}
