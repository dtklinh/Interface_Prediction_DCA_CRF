/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import DCA.MSA_FloatMatrix;
import Protein.Protein_PairwiseScore;
import java.io.IOException;
import java.util.List;
import org.biojava.bio.structure.StructureException;
import utils.Utils;

/**
 *
 * @author t.dang
 */
public class RunEVcomplex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here
        List<String> lst = utils.Utils.dir2list(Configuration.Dir2DCA);
        List<String> lst_pdbSingle = Utils.dir2list(Configuration.Dir2PDBSingleChain);
//
//        List<String> Done = utils.Utils.dir2list(Configuration.Dir2EVcomplex);
//        for (int i = lst.size() - 1; i >= 0; i--) {
//            String s = lst.get(i).substring(0, 6) + StaticMethod.FindEndName(Configuration.Dir2EVcomplex);
//            if (Done.contains(s)) {
//                lst.remove(i);
//            }
//        }
        
        for (String s : lst) {
            System.out.println("Run: " + s);
            Protein_PairwiseScore prot = new Protein_PairwiseScore(Configuration.Dir2DCA,
                    s.substring(0, 6), 1);

            String[] ids = StaticMethod.findChainID(lst_pdbSingle, s.substring(0, 4));
            String chain2;
            if (ids[0].equalsIgnoreCase(s.substring(5, 6))) {
                chain2 = ids[1];
            } else {
                chain2 = ids[0];
            }
            String path = Configuration.Dir2PDB + s.substring(0, 4)  + StaticMethod.FindEndName(Configuration.Dir2PDB);
            prot.AdjustSecondIndex(path, chain2);
            MSA_FloatMatrix m = new MSA_FloatMatrix(Configuration.Dir2MSA, s.substring(0, 6));
            float Meff = m.Compute_M_eff();
            prot.ComputeEVcomplex(Meff);
            MyIO.WriteLstToFile(Configuration.Dir2EVcomplex + s + ".cmplx", prot.getLstScore());


        }
    }
}
