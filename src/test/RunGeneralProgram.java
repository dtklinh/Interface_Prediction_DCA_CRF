/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Common.GeneralProgram;
import Common.StaticMethod;
import MultipleCore.MyThread;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class RunGeneralProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int num_cpus = 1;
        List<String> lst = utils.Utils.dir2list("Input/Magnus_DB/Magnus_NMI/NotAdjustIndex/");
        ArrayList<ArrayList<String>> arr = StaticMethod.Divide(lst, num_cpus);
        for(int i=0; i<num_cpus;i++){
            GeneralProgram p = new GeneralProgram(arr.get(i));
            MyThread thr = new MyThread(p);
            thr.run();
        }
    }
}
