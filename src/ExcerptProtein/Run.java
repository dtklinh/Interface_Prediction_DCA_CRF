/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import StaticMethods.ProteinIO;
import java.io.IOException;
import java.util.List;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.ResidueNumber;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here
        String PDB_Path = "Input/SmallSet/PDB/1a70.pdb";
        String ProtID = "1A70";
        String ChainID = "A";
        String IdxCore = "57";
        ExcerptProt e = new ExcerptProt(IdxCore, ProtID, ChainID, PDB_Path);
        List<Group> lst = e.makeExcerpt(2);
        System.out.println("\n Total: "+ lst.size());
        
//        List<Group> Lst = ProteinIO.readGroups(PDB_Path, "A");
//        ResidueNumber r = Lst.get(0).getResidueNumber();
//        System.out.println("Int: "+ r.getSeqNum());
//        System.out.println("Char: "+ r.getInsCode());
//        System.out.println("res: "+ r.toString());
    }
}
