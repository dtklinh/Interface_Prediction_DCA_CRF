/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.ColPairAndScores.ColPair_Score;
import Common.Configuration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class NewProtein_PairwiseScore {

    private String ProtPDBID;
    private String ChainID;
    private int NeighborDistance;

    /**
     * @return the ProtPDBID
     */
    public String getProtPDBID() {
        return ProtPDBID;
    }

    /**
     * @param ProtPDBID the ProtPDBID to set
     */
    public void setProtPDBID(String ProtPDBID) {
        this.ProtPDBID = ProtPDBID;
    }

    public NewProtein_PairwiseScore(int dis, String ProtID, String c) {
        this.NeighborDistance = dis;
        this.ProtPDBID = ProtID;
        this.ChainID = c;
    }

    public List<ColPair_Score> topPercent(ArrayList<ColPair_Score> Lst_PairScore,
            boolean UseNeighborDef, double p) {
        ArrayList<ColPair_Score> LstScore = new ArrayList<>();
        LstScore.addAll(Lst_PairScore);
        if (UseNeighborDef) {
            for (int i = LstScore.size() - 1; i >= 0; i--) {
                ColPair_Score c = LstScore.get(i);
                if (c.isNeighbor(this.NeighborDistance)) {
                    LstScore.remove(i);
                }
            }
        }
        Collections.sort(LstScore);
        return LstScore.subList((int) (LstScore.size() * (1.0 - p)), LstScore.size());
    }

    public List<ColPair_Score> topNumber(NewProtein_Pairwise_ScoreRef ref, ArrayList<ColPair_Score> Lst_PairScore,
            boolean UseNeighborDef, boolean UseSARAScore, int N) {
        ArrayList<ColPair_Score> LstScore = new ArrayList<>();
        LstScore.addAll(Lst_PairScore);
        if (UseNeighborDef) {
            for (int i = LstScore.size() - 1; i >= 0; i--) {
                ColPair_Score c = LstScore.get(i);
                if (UseSARAScore) {
                    if (!ref.isPairOnSurface(c)) {
                        LstScore.remove(i);
                        continue;
                    }
                }
//                if(ref.getPairPosition(c).equalsIgnoreCase("Core-Core")){
//                    LstScore.remove(i);
//                    continue;
//                }
                if (c.isNeighbor(this.NeighborDistance)) {
                    LstScore.remove(i);
                }
            }
        }
        Collections.sort(LstScore);
        return LstScore.subList((LstScore.size() - N), LstScore.size());

    }
    public List<ColPair_Score> topAll(NewProtein_Pairwise_ScoreRef ref, ArrayList<ColPair_Score> Lst_PairScore,
            boolean UseNeighborDef, boolean UseSARAScore) {
        ArrayList<ColPair_Score> LstScore = new ArrayList<>();
        LstScore.addAll(Lst_PairScore);
        if (UseNeighborDef) {
            for (int i = LstScore.size() - 1; i >= 0; i--) {
                ColPair_Score c = LstScore.get(i);
                if (UseSARAScore) {
                    if (!ref.isPairOnSurface(c)) {
                        LstScore.remove(i);
                        continue;
                    }
                }
//                if(ref.getPairPosition(c).equalsIgnoreCase("Core-Core")){
//                    LstScore.remove(i);
//                    continue;
//                }
                if (c.isNeighbor(this.NeighborDistance)) {
                    LstScore.remove(i);
                }
            }
        }
        Collections.sort(LstScore);
        return LstScore;

    }
    public List<ColPair_Score> topOverThreshold(ArrayList<ColPair_Score> Lst_PairScore, double thres,
            boolean IncludeNeighbor){
        ArrayList<ColPair_Score> res = new ArrayList<>();
        for(ColPair_Score col: Lst_PairScore){
            if(col.getScore()>=thres ){
                if(IncludeNeighbor){
                res.add(col);
                }
                else{
                    if(!col.isNeighbor(Configuration.Neighbor)){
                        res.add(col);
                    }
                }
            }
        }
        return res;
    }

    public List<ColPair_Score> randomPickUp(ArrayList<ColPair_Score> Lst_PairScore, int num, boolean OnSurface,
            NewProtein_Pairwise_ScoreRef ref) {

        ArrayList<ColPair_Score> LstScore = new ArrayList<>();
        LstScore.addAll(Lst_PairScore);
        if (OnSurface) {
            for (int i = LstScore.size() - 1; i >= 0; i--) {
                ColPair_Score col = LstScore.get(i);
                if (!ref.isPairOnSurface(col)) {
                    LstScore.remove(i);
                }
            }
        }

        Collections.shuffle(LstScore);
        return LstScore.subList(0, num);
    }
}
