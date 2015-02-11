/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import DCA.ColPair_Score;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class Protein_Pairwise_Reference extends Protein_PairwiseScore{
    private double ContactDistance;
    public Protein_Pairwise_Reference(String path, String ID, int num) throws IOException{
        super(path,ID,num);
    }
    public Protein_Pairwise_Reference(String path, String ID, int num, double dis) throws IOException{
        super(path,ID,num);
        this.ContactDistance = dis;
    }
    public int CountTP(ArrayList<ColPair_Score> lst){
        int sum = 0;
        for(ColPair_Score c: lst){
            if(getScoreByIndexes(c.getP1(), c.getP2())<=ContactDistance){
                sum++;
            }
        }
        return sum;
    }
     public int CountTP(List<ColPair_Score> lst){
        int sum = 0;
        for(ColPair_Score c: lst){
            if(getScoreByIndexes(c.getP1(), c.getP2())<=ContactDistance){
                sum++;
            }
        }
        return sum;
    }
     public int CountNotNeighborProximity(){
         int sum = 0;
         for(ColPair_Score s: this.getLstScore()){
             if(!s.IsNeighbor(this.getNeighborDistance()) && s.getScore()<=ContactDistance){
                 sum++;
             }
         }
         return sum;
     }
}
