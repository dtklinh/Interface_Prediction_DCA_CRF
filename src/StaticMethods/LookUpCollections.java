/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticMethods;

import Common.ColPair_MegaScore;
import Common.ColPair_Score;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class LookUpCollections {

    public static double LookUpScoreInLstColPair_Score(ArrayList<ColPair_Score> lst, String P1, String P2) {
        for (ColPair_Score c : lst) {
            if (c.isSameIndex(new ColPair_Score(P1, P2, 0))) {
                return c.getScore();
            }
        }
        System.err.println("Could not find ColPair in list");
        System.exit(1);
        return -1;
    }

    public static List<ColPair_MegaScore> LookUpAtOneVertex(List<ColPair_MegaScore> lst, String vertex) {
        List<ColPair_MegaScore> res = new ArrayList<>();
        for (ColPair_MegaScore c : lst) {
            if(c.getP1().equalsIgnoreCase(vertex) || c.getP2().equalsIgnoreCase(vertex)){
                res.add(c);
            }
        }
        return res;
    }
}
