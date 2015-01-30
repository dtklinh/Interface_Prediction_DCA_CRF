/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import java.util.Comparator;

/**
 *
 * @author t.dang
 */
public class ColPair_Score implements Comparable<ColPair_Score> {
//    private String ProteinChain;
    private int P1;
    private int P2;
    private double Score;
    public ColPair_Score(int p1, int p2, double s){
//        this.ProteinChain = str;
        this.P1 = p1;
        this.P2 = p2;
        this.Score = s;
    }

    /**
     * @return the P1
     */
    public int getP1() {
        return P1;
    }

    /**
     * @param P1 the P1 to set
     */
    public void setP1(int P1) {
        this.P1 = P1;
    }

    /**
     * @return the P2
     */
    public int getP2() {
        return P2;
    }

    /**
     * @param P2 the P2 to set
     */
    public void setP2(int P2) {
        this.P2 = P2;
    }

    /**
     * @return the Score
     */
    public double getScore() {
        return Score;
    }

    /**
     * @param Score the Score to set
     */
    public void setScore(double Score) {
        this.Score = Score;
    }

    /**
     * @return the ProteinChain
     */
//    public String getProteinChain() {
//        return ProteinChain;
//    }
//
//    /**
//     * @param ProteinChain the ProteinChain to set
//     */
//    public void setProteinChain(String ProteinChain) {
//        this.ProteinChain = ProteinChain;
//    }
    
    
//    public boolean equals(DCA_Score other){
//        if(this.ProteinChain.equalsIgnoreCase(other.ProteinChain) && this.Score==other.Score){
//            return true;
//        }
//        return false;
//    }
    @Override
    public int compareTo(ColPair_Score other){
        if(this.Score>other.Score){
            return 1;
        }
        else if(this.Score < other.Score){
            return -1;
        }
        else{
            return 0;
        }
    }
    public void Print2Screen(){
        System.out.println(P1+"\t"+P2+":\t"+Score);
    }
    public boolean IsNeighbor(){
        if(Math.abs(P1-P2)<=2){
            return true;
        }
        return false;
    }
}
