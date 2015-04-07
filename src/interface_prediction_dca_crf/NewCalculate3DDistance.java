/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.MyIO;
import Protein.NewProteinChain;
import Protein.NewProteinComplex;
import java.io.IOException;
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
        String ProtID = "3a0r";
        String chain1 = "A", chain2 = "B";
        String path2PDbFile = "Test2/PDB/"+ProtID+".pdb";
        
        NewProteinChain C1 = new NewProteinChain(path2PDbFile, ProtID, chain1);
        NewProteinChain C2 = new NewProteinChain(path2PDbFile, ProtID, chain2);
        NewProteinComplex Cmpl = new NewProteinComplex(path2PDbFile, ProtID, chain1, chain2);
        
        MyIO.WriteLstToFile("Test2/3D/"+ProtID+"_"+chain1+".3d", C1.pairwiseDistance());
        MyIO.WriteLstToFile("Test2/3D/"+ProtID+"_"+chain2+".3d", C2.pairwiseDistance());
        MyIO.WriteLstToFile("Test2/3D/"+ProtID+".3d", Cmpl.pairwiseDistance());
    }
}
