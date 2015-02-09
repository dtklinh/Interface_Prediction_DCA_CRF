/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.StaticMethod;
import MultipleCore.MyThread;
import Protein.ProteinChain;
import Protein.ThreadProteinChain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class Calculate3DDistance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int num_cpu= 1;
        String Dir2PDB = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
        String Dir2Score3D = "Input/Magnus_DB/Magnus_3D/";
        List<String> lst = utils.Utils.dir2list(Dir2PDB);
        ArrayList<ProteinChain> lst_Prot = new ArrayList<>();
        for(String s: lst){
            ProteinChain p = new ProteinChain(Dir2PDB, s);
            lst_Prot.add(p);
        }
        ArrayList<ArrayList<ProteinChain>> arr = StaticMethod.Divide(lst_Prot, num_cpu);
        for(int i=0; i<num_cpu; i++){
            ThreadProteinChain p = new ThreadProteinChain(Dir2Score3D, arr.get(i));
            MyThread th = new MyThread(p);
            th.start();
        }
    }
}
