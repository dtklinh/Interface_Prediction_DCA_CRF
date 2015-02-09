/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.MyIO;
import DCA.MSA;
import DCA.ThreadDCA;
import MultipleCore.MyThread;
import Common.StaticMethod;
import NMI.ThreadNMI;
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

        int num_cpu = 1;
        String DirMSAFile = "Input/Magnus_DB/Magnus_MSA/";
        String DirResultFile = "Input/Magnus_DB/Magnus_NMI/";
        List<String> lst_tmp = utils.Utils.dir2list(DirMSAFile);
        ArrayList<String> lst_filename = new ArrayList<>();
        lst_filename.addAll(lst_tmp);

        List<String> Done = utils.Utils.dir2list(DirResultFile);
        for (int i = lst_filename.size() - 1; i >= 0; i--) {
            String s = lst_filename.get(i).substring(0, 6) + StaticMethod.FindEndName(DirResultFile);
            if (Done.contains(s)) {
                lst_filename.remove(i);
            }
        }

        ArrayList<MSA> lst_MSA = new ArrayList<MSA>();
        for (String s : lst_filename) {
            MSA m = new MSA(DirMSAFile, s);
            lst_MSA.add(m);
        }
        double[][] DSM = MyIO.ReadDSM("Input/Magnus_DB/newDSM.out");

        ArrayList<ArrayList<MSA>> arr_MSA = StaticMethod.Divide(lst_MSA, num_cpu);
        for (int k = 0; k < arr_MSA.size(); k++) {
            // Run DCA
            /*
            ThreadDCA dca = new ThreadDCA(arr_MSA.get(k), DirResultFile);
            MyThread th = new MyThread(dca);
            th.start();
            //*/ 
            // end Run DCA

            // Run NMI (Normalized Mutual Information)
            ThreadNMI nmi = new ThreadNMI(arr_MSA.get(k), DirResultFile, DSM);
            MyThread th = new MyThread(nmi);
            th.start();
            // end Run NMI (Normalized Mutual Information)
        }



    }
}
