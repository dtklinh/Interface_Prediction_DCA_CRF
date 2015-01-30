/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import DCA.MSA;
import DCA.ThreadDCA;
import MultipleCore.MyThread;
import Support.StaticMethod;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class RunDCA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        int num_cpu = 2;
        String DirMSAFile = "EVCoupling/MSA/";
        String DirOut_DCAFile = "EVCoupling/DCA/";
        List<String> lst_tmp = utils.Utils.dir2list(DirMSAFile);
        ArrayList<String> lst_filename = new ArrayList<>();
        lst_filename.addAll(lst_tmp);
        
        ArrayList<MSA> lst_MSA = new ArrayList<MSA>();
        for(String s: lst_filename){
            MSA m = new MSA(DirMSAFile, s);
            lst_MSA.add(m);
        }
        ArrayList<ArrayList<MSA>> arr_MSA = StaticMethod.Divide(lst_MSA, num_cpu);
        for(int k=0; k<arr_MSA.size(); k++){
            ThreadDCA dca = new ThreadDCA(arr_MSA.get(k), DirOut_DCAFile);
            MyThread th = new MyThread(dca);
            th.start();
        }
    }
}
