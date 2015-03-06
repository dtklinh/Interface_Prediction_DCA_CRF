/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import Common.StaticMethod;
import Common.ColPair_Score;
import Protein.Protein_PairwiseScore;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class MyResultTest {
    private String Dir2Test;
    private String Dir2Ref;
    private String Dir2PDB;
    private final int NeighborDistance = 4;
    private final int TopNum = 25;
    private int idx_score_ref;
    public MyResultTest(String d2test, String d2ref, String d2pdb, int idx){
        this.Dir2Test = d2test;
        this.Dir2Ref = d2ref;
        this.idx_score_ref = idx;
        this.Dir2PDB = d2pdb;
    }
    public GeneralResult Process() throws IOException{
        List<String> lst_Test = utils.Utils.dir2list(Dir2Test);
        List<String> lst_Ref = utils.Utils.dir2list(Dir2Ref);
        for(String t: lst_Test){
            Protein_PairwiseScore p = 
                    new Protein_PairwiseScore(Dir2Test, t, NeighborDistance, idx_score_ref);
            Protein_PairwiseScore ref = 
                    new Protein_PairwiseScore(Dir2Ref, 
                    t.substring(0, 6)+StaticMethod.FindEndName(Dir2Ref), 
                    NeighborDistance);
            p.EliminateNeighbor();
            p.Sort();
            p.AdjustIndex(Dir2PDB);
            List<ColPair_Score> scores = p.TopNumber(TopNum);
            
            ref.EliminateNeighbor();
            
        }
        return null;
    }
}
