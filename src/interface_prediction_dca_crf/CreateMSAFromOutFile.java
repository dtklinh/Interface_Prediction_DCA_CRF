/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import BLAST.CreateMSAfromBLAST;
import Common.MyIO;
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
        String dirMSA = "Input/Magnus_DB/Magnus_MSA/";
        
        String dirOutFile = "Input/Magnus_DB/Magnus_BLAST/";
        String dirPDB = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
        List<String> lst = utils.Utils.dir2list(dirOutFile);
        for(String s: lst){
            s = s.trim();
            Structure struc = (new PDBFileReader()).getStructure(dirPDB + s.substring(0, 6)+".msa");
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
