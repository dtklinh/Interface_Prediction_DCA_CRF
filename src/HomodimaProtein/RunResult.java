/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HomodimaProtein;

import Common.Configuration;
import Protein.Protein_PairwiseScore;
import Protein.Protein_Pairwise_Reference;
import java.io.IOException;
import java.util.List;
import utils.Utils;

/**
 *
 * @author t.dang
 */
public class RunResult {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // 
        // Two chains of protein are identical, and we suppose that 
        // two MSAs of these two protein chains are the same as well.
        // Then the DCA score across proteins is the same as intra protein itself.
        // E.g. DCA(A1, B5) = DCA(A1, A5).
        
        String Dir23D = Configuration.Dir23DComplex;
        String Dir2EVcomplex = Configuration.Dir2EVcomplex;
        int neighbor =7;
        List<String> lst = Utils.dir2list(Configuration.Dir2EVcomplex);
        int TP = 0;
        int top = 30;
        for(String s: lst){
            // load reference
            Protein.Protein_Pairwise_Reference ref = new Protein_Pairwise_Reference(Dir23D, s, neighbor, 8.5);
            Protein_PairwiseScore ev = new Protein_PairwiseScore(Dir2EVcomplex, s, neighbor);
            ev.EliminateNeighbor();
            ev.Sort();
            
            TP += ref.CountTP(ev.TopNumber(top));
        }
        
        System.out.println("TP: "+ TP);
    }
}
