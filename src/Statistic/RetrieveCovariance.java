/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic;

import Protein.Protein_PairwiseScore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math.linear.AbstractRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

/**
 *
 * @author t.dang
 */
public class RetrieveCovariance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String Dir23D = "Input/Magnus_DB/Magnus_3D/AdjustIndex/";
        String Dir2DCA = "Input/Magnus_DB/Magnus_DCA/AdjustIndex/";
        String Dir2NMI = "Input/Magnus_DB/Magnus_NMI/AdjustIndex/WithoutDSM/";
        int NeighborDistance = 4;
        List<String> lst = utils.Utils.dir2list(Dir23D);
        ArrayList<RealMatrix> container = new ArrayList<>();
        for(String s: lst){
            Protein_PairwiseScore Prot_3d = new Protein_PairwiseScore(Dir23D, s, NeighborDistance);
            Protein_PairwiseScore Prot_dca = new Protein_PairwiseScore(Dir2DCA, s, NeighborDistance);
            Protein_PairwiseScore Prot_nmi = new Protein_PairwiseScore(Dir2NMI, s, NeighborDistance);
            
            Prot_3d.EliminateNeighbor();
            Prot_dca.EliminateNeighbor();
            Prot_nmi.EliminateNeighbor();
            
            MyCorrelation mc = new MyCorrelation(Prot_3d.getLstScore(), 
                    Prot_dca.getLstScore(), Prot_nmi.getLstScore());
            container.add(mc.getCovariance());
        }
        RealMatrix m = container.get(0);
        for(int i=1; i<container.size(); i++){
            m = m.add(container.get(i));
        }
        double[][] A = m.getData();
        for(int i=0; i<m.getRowDimension();i++){
            for(int j=0; j<m.getColumnDimension(); j++){
                System.out.print(A[i][j]/container.size()+"\t");
            }
            System.out.println();
        }
    }
}
