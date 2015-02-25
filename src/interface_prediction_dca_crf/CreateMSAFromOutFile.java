/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import BLAST.CreateMSAfromBLAST;
import Common.MyIO;
import Common.StaticMethod;
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
        String dirMSA = "Input/Zellner_DB20/Zellner_MSA/";
        
        String dirOutFile = "Input/Zellner_DB20/Zellner_BLAST/";
        String dirPDB = "Input/Zellner_DB20/Zellner_PDB/";
        List<String> lst = utils.Utils.dir2list(dirPDB);
        String endfile = StaticMethod.FindEndName(dirPDB);
        for(String s: lst){
            s = s.trim();
            Structure struc = (new PDBFileReader()).getStructure(dirPDB + s.substring(0, 6)+endfile);
            Chain c = struc.getChain(0);
            String seq = c.getAtomSequence();
            CreateMSAfromBLAST b = new CreateMSAfromBLAST(dirOutFile, seq, s.substring(0, 6) +StaticMethod.FindEndName(dirOutFile));
            b.BuildAlignedBlock();
            ArrayList<String> msa = new ArrayList<String>();
            msa = b.CreateMSA(dirPDB);
            MyIO.WriteToFile(dirMSA+s.substring(0, 6)+".msa", msa);
        }
    }
}
