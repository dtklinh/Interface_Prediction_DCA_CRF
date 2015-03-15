/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.RefineProteinChain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class CreateSinglePDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String Dir2PDB_Org = "Test/Hetedima/PDBComplex/";
        String Dir2PDB_Single = "Test/Hetedima/PDBSingleChain/";
        List<String> lst = utils.Utils.dir2list(Dir2PDB_Org);

        for (String s : lst) {
            s = s.trim();
            String ProteinID = s.substring(0, 4);
            Structure struc = (new PDBFileReader()).getStructure(Dir2PDB_Org + s);
            String ChainID = null;

            Chain c = null;
            for (int i = 0; i < struc.getChains().size(); i++) {
                c = struc.getChain(i);
                if ((c.getAtomGroup(0).getType()).equalsIgnoreCase("amino")) {
                    ChainID = c.getChainID();
//                    break;
                }
                if (ProteinID != null && ChainID != null) {
                RefineProteinChain rf = new RefineProteinChain(ProteinID, ChainID);
                rf.PrintFileOffLine(Dir2PDB_Org+s, Dir2PDB_Single+ProteinID+"_"+ChainID+".pdb");
            }
            }
//            if (ProteinID != null && ChainID != null) {
//                RefineProteinChain rf = new RefineProteinChain(ProteinID, ChainID);
//                rf.PrintFileOffLine(Dir2PDB_Org+s, Dir2PDB_Single+ProteinID+"_"+ChainID+".pdb");
//            }
        }
    }
}
