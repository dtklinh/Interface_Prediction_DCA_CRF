/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.MyIO;
import Protein.NewProteinChain;
import Protein.NewProteinComplex;
import Protein.NewProteinComplexSkeleton;
import java.io.IOException;
import java.util.ArrayList;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class NewCalculate3DDistance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here

        // calculate 3d distance for single chain and complex
        String Path2List = "Input/SmallSet/List.txt";
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for (NewProteinComplexSkeleton c : lst) {
//        String ProtID = "3a0r";
//        String chain1 = "A", chain2 = "B";
            String ProtID = c.getProtPDBID();
            String chain1 = c.getChainID1(), chain2 = c.getChainID2();
            String path2PDbFile = "Input/SmallSet/PDB/" + ProtID + ".pdb";

            NewProteinChain C1 = new NewProteinChain(path2PDbFile, ProtID, chain1);
//            NewProteinChain C2 = new NewProteinChain(path2PDbFile, ProtID, chain2);
//            NewProteinComplex Cmpl = new NewProteinComplex(path2PDbFile, ProtID, chain1, chain2);

            MyIO.WriteLstToFile("Input/SmallSet/3D/" + ProtID + "_" + chain1 + ".3d_Any_VdW", C1.pairwiseDistance());
//            MyIO.WriteLstToFile("Input/Zellner_Homodimer/3D/" + ProtID + "_" + chain2 + ".3d_Any_VdW", C2.pairwiseDistance());
//            MyIO.WriteLstToFile("Input/Zellner_Homodimer/3D/" + ProtID +"_" + chain1+chain2+ ".3d_Any_VdW", Cmpl.pairwiseDistance());
        }
    }
}
