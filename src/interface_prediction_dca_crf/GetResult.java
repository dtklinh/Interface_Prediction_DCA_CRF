/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Analysis.Reference;
import Common.Configuration;
import Protein.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class GetResult {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        String Dir23D = Configuration.Dir23DComplex;
        String Dir2DCA = Configuration.Dir2EVcomplex;
//        String Dir2NMI1 = "Input/Magnus_DB/Magnus_NMI/AdjustIndex/WithoutDSM/";
//        String Dir2NMI2 = "Input/Magnus_DB/Magnus_NMI/AdjustIndex/WithDSM/";
        int neighbor = 4;
        int top = 100;
//        int bottom =50;
        List<String> lst = utils.Utils.dir2list(Dir23D);
        int TP_DCA =0, TP_NMI1=0, TP_NMI2=0, Overlap_NMI1 =0, Overlap_NMI2 =0, total=0;
        for(String s: lst){
            // make reference
            Protein_Pairwise_Reference reference = new Protein_Pairwise_Reference(Dir23D, s, neighbor, 8.5);
            top = reference.CountNotNeighborProximity();
//            bottom = reference.CountNotNeighborNOTProximity();
//            bottom = reference.getLstScore().size()/2;
            total += reference.CountNotNeighborProximity();
            
            // make for DCA
            Protein_PairwiseScore dca = new Protein_PairwiseScore(Dir2DCA, s, neighbor);
            dca.EliminateNeighbor();
            dca.Sort();
            
            // make for NMI1 // withoutDSM
//            Protein_PairwiseScore nmi1 = new Protein_PairwiseScore(Dir2NMI1, s, neighbor);
//            nmi1.EliminateNeighbor();
//            nmi1.Sort();
            // make for NMI2 // withDSM
//            Protein_PairwiseScore nmi2 = new Protein_PairwiseScore(Dir2NMI2, s, neighbor);
//            nmi2.EliminateNeighbor();
//            nmi2.Sort();
            
            TP_DCA += reference.CountTP(dca.TopNumber(top));
//            TP_NMI1 += reference.CountTP(nmi1.TopNumber(top));
//            TP_NMI2 += reference.CountTP(nmi2.TopNumber(top));
            
//            TP_DCA += reference.CountTN(dca.TopNumber(bottom));
//            TP_NMI1 += reference.CountTN(nmi1.TopNumber(bottom));
//            TP_NMI2 += reference.CountTN(nmi2.TopNumber(bottom));
            
//            Overlap_NMI1 += Protein_PairwiseScore.CountOverlapIdx(dca.TopNumber(top), nmi1.TopNumber(top));
//            Overlap_NMI2 += Protein_PairwiseScore.CountOverlapIdx(dca.TopNumber(top), nmi2.TopNumber(top));
//            Overlap_NMI2 = dca.CountOverlap(nmi2);
        }
        System.out.println("TP_DCA: "+TP_DCA);
//        System.out.println("TP_NMI1: "+TP_NMI1);
//        System.out.println("TP_NMI2: "+TP_NMI2);
//        System.out.println("Overlap_NMI1: "+Overlap_NMI1);
//        System.out.println("Over_NMI2: "+Overlap_NMI2);
        System.out.println("TP_Total: "+total);
    }
}
