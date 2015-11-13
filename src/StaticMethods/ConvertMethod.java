/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticMethods;

import Common.ColPairAndScores.ColPair_Score;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.biojava.bio.structure.ResidueNumber;

/**
 *
 * @author t.dang
 */
public class ConvertMethod {
    public static double[][] convertFromColPair2DoubleArray(List<ColPair_Score> Lst, 
            HashMap<String,Integer> MapResNum2Idx){
        int N = MapResNum2Idx.size();
        double[][] res = new double[N][N];
        for(ColPair_Score c: Lst){
            int idx1 = MapResNum2Idx.get(c.getP1());
            int idx2 = MapResNum2Idx.get(c.getP2());
            res[idx1][idx2] = c.getScore();
            res[idx2][idx1] = c.getScore();
        }
        return res;
    }
    public static List<ColPair_Score> convertDoubleArray2ColPair(double[][] A, 
            HashMap<Integer,String> MapIdx2ResNum){
        int nRows = A.length;
        int nCols = A[0].length;
        
        if(nRows!=nCols){
            System.err.println("Not symmetric matrix");
            System.exit(1);
            return null;
        }
        List<ColPair_Score> res = new ArrayList<>();
        for(int i=0; i<(nRows-1);i++){
            String P1 = MapIdx2ResNum.get(i);
//            ResidueNumber r1 = String2ResidueNum("A", P1);
            for(int j=i+1; j<nRows; j++){
                String P2 = MapIdx2ResNum.get(j);
                ColPair_Score col = new ColPair_Score(P1, P2, A[i][j]);
                res.add(col);
            }
        }
        return res;
    }
    public static ResidueNumber String2ResidueNum(String ChainID, String ResNum){
        if(Character.isDigit(ResNum.charAt(ResNum.length()-1))){
            return new ResidueNumber(ChainID, Integer.parseInt(ResNum), null);
        }
        else{
            String p1 = ResNum.substring(0, ResNum.length()-1);
            Character c = ResNum.charAt(ResNum.length()-1);
            return new ResidueNumber(ChainID, Integer.parseInt(p1), c);
        }
    }
}
