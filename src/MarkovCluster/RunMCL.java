/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MarkovCluster;

import Common.MyIO;
import DCA.MyOwnMatrix;
import Protein.Protein_PairwiseScore;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class RunMCL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        String Dir2File = "Input/Magnus_DB/Magnus_DCA/NotAdjustIndex/";
        String Dir2Output = "Input/Magnus_DB/Magnus_DCA/SelfMultiple/";
        String Dir2PDB = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
        int neighbor = 4;
        double epsilon = 1e-3;
        double distance_contact = 8.5d;
        List<String> lst = utils.Utils.dir2list(Dir2File);
        for(String s: lst){
            MyOwnMatrix m = MyOwnMatrix.fromPairScore2Matrix(Dir2File+s, epsilon);
//            m.makeDSM();
            m = Algorithm.process(m);
//            if(!m.checkSymetric()){
//                System.err.println("Result matrix is not symetric");
//            }
            Protein_PairwiseScore p = new Protein_PairwiseScore(Dir2File,s,neighbor,m);
            p.AdjustIndex(Dir2PDB);
            MyIO.WriteLstToFile(Dir2Output+p.getProteinChain()+".mcl", p.getLstScore());
        }
    }
}
