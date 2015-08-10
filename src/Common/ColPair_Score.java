/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author t.dang
 */
public class ColPair_Score implements Comparable<ColPair_Score> {
//    private String ProteinChain;
    private String P1;
    private String P2;
    private double Score;
    public ColPair_Score(String p1, String p2, double s){
//        this.ProteinChain = str;
        this.P1 = p1;
        this.P2 = p2;
        this.Score = s;
    }

    /**
     * @return the P1
     */
    public String getP1() {
        return P1;
    }

    /**
     * @param P1 the P1 to set
     */
    public void setP1(String P1) {
        this.P1 = P1;
    }

    /**
     * @return the P2
     */
    public String getP2() {
        return P2;
    }

    /**
     * @param P2 the P2 to set
     */
    public void setP2(String P2) {
        this.P2 = P2;
    }

    /**
     * @return the Score
     */
    public double getScore() {
        return Score;
    }
    public String getInfo(){
        return P1+"\t"+P2+":\t"+Score;
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
    public boolean IsNeighbor(int n){
        int p1, p2;
        if(Character.isDigit(P1.charAt(P1.length()-1))){
         p1 = Integer.parseInt(P1);
        } else{
            p1 = Integer.parseInt(P1.substring(0, P1.length()-1));
        }
         if(Character.isDigit(P2.charAt(P2.length()-1))){
         p2 = Integer.parseInt(P2);
        } else{
            p2 = Integer.parseInt(P2.substring(0, P2.length()-1));
        }
        if(Math.abs(p1-p2)<=n){
            return true;
        }
        return false;
    }
    public boolean IsSameIndex(ColPair_Score other){
        if(other.P1.equalsIgnoreCase(P1)
             && other.P2.equalsIgnoreCase(P2))
            return true;
        return false;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 29).append(P1).append(P2).toHashCode();
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof ColPair_Score))
            return false;
        if (obj == this)
            return true;
        ColPair_Score rhs = (ColPair_Score) obj;
        return new EqualsBuilder().append(P1, rhs.P1).append(P2, rhs.P2).isEquals();
    }
    
}
