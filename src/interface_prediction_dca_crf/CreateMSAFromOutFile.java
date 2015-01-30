/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import BLAST.CreateMSAfromBLAST;
import Support.MyIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class CreateMSAFromOutFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here
        String dirMSA = "Output/Zellner_BLAST/MSAFile/";
        
        String dirOutFile = "Output/Zellner_BLAST/OutFile/";
        String dirPDB = "Input/Zellner_PDB/";
        List<String> lst = utils.Utils.dir2list(dirOutFile);
        for(String s: lst){
            s = s.trim();
            Structure struc = (new PDBFileReader()).getStructure(dirPDB + s.substring(0, 6)+".txt");
            Chain c = struc.getChain(0);
            String seq = c.getAtomSequence();
            CreateMSAfromBLAST b = new CreateMSAfromBLAST(dirOutFile, seq, s);
            b.BuildAlignedBlock();
            ArrayList<String> msa = new ArrayList<String>();
            msa = b.CreateMSA();
            MyIO.WriteToFile(dirMSA+s.substring(0, 6)+".msa", msa);
        }
    }
}
