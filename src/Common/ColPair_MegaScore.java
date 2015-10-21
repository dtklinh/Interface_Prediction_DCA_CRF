/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author t.dang
 */
public class ColPair_MegaScore extends ColPair{
//    private String P1;
//    private String P2;
    private double ThreeDimDis;
    private double PlmdcaScore;
    private double GremlinScore;
    private double CondMIScore;
    
    public ColPair_MegaScore(String p1, String p2, double d3, double dca, double gremlin, double cmi){
//        this.P1 = p1;
//        this.P2 = p2;
        super(p1,p2);
        this.ThreeDimDis = d3;
        this.PlmdcaScore = dca;
        this.GremlinScore = gremlin;
        this.CondMIScore = cmi;
    }
    
//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(13, 29).append(getP1()).append(getP2()).toHashCode();
//    }
//    @Override
//    public boolean equals(Object obj){
//        if (!(obj instanceof ColPair_MegaScore))
//            return false;
//        if (obj == this)
//            return true;
//        ColPair_MegaScore rhs = (ColPair_MegaScore) obj;
//        return new EqualsBuilder().append(getP1(), rhs.getP1()).append(getP2(), rhs.getP2()).isEquals();
//    }
//    public boolean isNeighbor(int n){
//        int p1, p2;
//        if(Character.isDigit(getP1().charAt(getP1().length()-1))){
//         p1 = Integer.parseInt(getP1());
//        } else{
//            p1 = Integer.parseInt(getP1().substring(0, getP1().length()-1));
//        }
//         if(Character.isDigit(getP2().charAt(getP2().length()-1))){
//         p2 = Integer.parseInt(getP2());
//        } else{
//            p2 = Integer.parseInt(getP2().substring(0, getP2().length()-1));
//        }
//        if(Math.abs(p1-p2)<=n){
//            return true;
//        }
//        return false;
//    }
    public ColPair_Score convert2ColPair_Score(String str){
        str = str.toLowerCase();
        if(str.indexOf("three")>=0){
            return new ColPair_Score(getP1(), getP2(), getThreeDimDis());
        }
        else if(str.indexOf("dca")>=0){
            return new ColPair_Score(getP1(), getP2(), getPlmdcaScore());
        }
        else if(str.indexOf("gremlin")>=0){
            return new ColPair_Score(getP1(), getP2(), getGremlinScore());
        }
        else if(str.indexOf("mi")>=0){
            return new ColPair_Score(getP1(), getP2(), getCondMIScore());
        }
        else{
            System.err.println("Do not know format");
            return null;
        }
    }
//    public boolean isSameIdx(ColPair_MegaScore other){
//        if(this.P1.equalsIgnoreCase(other.getP1()) && this.P2.equalsIgnoreCase(other.getP2())){
//            return true;
//        }
//        if(this.P1.equalsIgnoreCase(other.getP2()) && this.P2.equalsIgnoreCase(other.getP1())){
//            return true;
//        }
//        return false;
//    }

    /**
     * @return the P1
     */
//    public String getP1() {
//        return P1;
//    }

    /**
     * @param P1 the P1 to set
     */
//    public void setP1(String P1) {
//        this.P1 = P1;
//    }

    /**
     * @return the P2
     */
//    public String getP2() {
//        return P2;
//    }

    /**
     * @param P2 the P2 to set
     */
//    public void setP2(String P2) {
//        this.P2 = P2;
//    }

    /**
     * @return the ThreeDimDis
     */
    public double getThreeDimDis() {
        return ThreeDimDis;
    }

    /**
     * @param ThreeDimDis the ThreeDimDis to set
     */
    public void setThreeDimDis(double ThreeDimDis) {
        this.ThreeDimDis = ThreeDimDis;
    }

    /**
     * @return the PlmdcaScore
     */
    public double getPlmdcaScore() {
        return PlmdcaScore;
    }

    /**
     * @param PlmdcaScore the PlmdcaScore to set
     */
    public void setPlmdcaScore(double PlmdcaScore) {
        this.PlmdcaScore = PlmdcaScore;
    }

    /**
     * @return the GremlinScore
     */
    public double getGremlinScore() {
        return GremlinScore;
    }

    /**
     * @param GremlinScore the GremlinScore to set
     */
    public void setGremlinScore(double GremlinScore) {
        this.GremlinScore = GremlinScore;
    }

    /**
     * @return the CondMIScore
     */
    public double getCondMIScore() {
        return CondMIScore;
    }

    /**
     * @param CondMIScore the CondMIScore to set
     */
    public void setCondMIScore(double CondMIScore) {
        this.CondMIScore = CondMIScore;
    }
}
