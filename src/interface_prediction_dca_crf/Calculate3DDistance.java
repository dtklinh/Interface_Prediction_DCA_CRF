/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.Configuration;
import Common.StaticMethod;
import MultipleCore.MyThread;
import Protein.ProteinChain;
import Protein.ProteinComplex;
import Protein.ThreadProteinChain;
import Protein.ThreadProteinComplex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.biojava.bio.structure.StructureException;
import utils.Utils;

/**
 *
 * @author t.dang
 */
public class Calculate3DDistance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here
        
//        int num_cpu= 1;
//        String Dir2PDB = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
//        String Dir2Score3D = "Input/Magnus_DB/Magnus_3D/";
        
        // for residue in one chain
        /*
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
                */
        
        // for residue from 2 chains
        int cpu = 1;
        List<String> lst = Utils.dir2list(Configuration.Dir2PDB);
        List<String> lst_chain = Utils.dir2list(Configuration.Dir2PDBSingleChain);
        Collections.sort(lst_chain);
        ArrayList<ProteinComplex> lst_ProtCmlx = new ArrayList<>();
        for(String s: lst){
            String[] id = StaticMethod.findChainID(lst_chain, s.substring(0, 4));
            ProteinComplex p = new ProteinComplex(s, id[0], id[1], Configuration.Dir2PDB);
            lst_ProtCmlx.add(p);
        }
        ArrayList<ArrayList<ProteinComplex>> arr = StaticMethod.Divide(lst_ProtCmlx, cpu);
        for(int i=0; i<cpu; i++){
            ThreadProteinComplex p = new ThreadProteinComplex(Configuration.Dir23DComplex, arr.get(i));
            MyThread th = new MyThread(p);
            th.start();
        }
    }
}
