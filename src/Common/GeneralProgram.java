/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import MultipleCore.MyObject;
import Protein.Protein_PairwiseScore;
import StaticMethods.ProteinIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class GeneralProgram extends MyObject {

    private ArrayList<String> Lst_str;

    public GeneralProgram(ArrayList<String> lst) {
        this.Lst_str = lst;
    }

    public void run() throws IOException {
        // modify as u want
        // get only sequence from PDB file
        /*
         String Dir2File = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
         String Dir2Out =  "Input/Magnus_DB/Magnus_Sequence/";
        
         for(String s: Lst_str){
         String name = s.substring(0, 6);
         System.out.println("Thread "+Thread.currentThread().getName()+" run on "+s);
         Structure stru = (new PDBFileReader()).getStructure(Dir2File+s);
         Chain c = stru.getChain(0);
         String str = c.getAtomSequence();
         List<Group> g = c.getAtomGroups();
         int begin = g.get(0).getResidueNumber().getSeqNum();
         int end = g.get(g.size()-1).getResidueNumber().getSeqNum();
            
         ArrayList<String> Seq = new ArrayList<>();
         Seq.add(">"+name+"/"+begin+"-"+end);
         Seq.add(str);
         MyIO.WriteToFile(Dir2Out+name+".txt", Seq);
         */
        // end
        
        // Adjust index as Residue number in Score file.
        String Dir2PDB = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
        String Dir2NotAdjustFile = "Input/Magnus_DB/Magnus_NMI/NotAdjustIndex/";
        String Dir2AdjustFile = "Input/Magnus_DB/Magnus_NMI/AdjustIndex/WithDSM/";
        int distance = 4;
        for(String s: Lst_str){
            Protein_PairwiseScore p = new Protein_PairwiseScore(Dir2NotAdjustFile, s, distance,3);
            p.AdjustIndex(Dir2PDB);
            ProteinIO.writeColPairScore2File(Dir2AdjustFile+s, p.getLstScore());
        }
        //end
    }
}
