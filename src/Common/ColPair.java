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
public class ColPair {
    protected String P1;
    protected String P2;

    /**
     * @return the P1
     */
    public ColPair(String s1, String s2){
        P1 = s1;
        P2 = s2;
    }
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
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 29).append(P1).append(P2).toHashCode();
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof ColPair))
            return false;
        if (obj == this)
            return true;
        ColPair rhs = (ColPair) obj;
        return isSameIndex(rhs);
//        return new EqualsBuilder().append(P1, rhs.P1).append(P2, rhs.P2).isEquals();
    }
    public boolean isSameIndex(ColPair other){
        if(this.P1.equalsIgnoreCase(other.getP1()) && this.P2.equalsIgnoreCase(other.getP2())){
            return true;
        }
        if(this.P1.equalsIgnoreCase(other.getP2()) && this.P2.equalsIgnoreCase(other.getP1())){
            return true;
        }
        return false;
    }
    public boolean isNeighbor(int n){
        int p1, p2;
        if(Character.isDigit(P1.charAt(P1.length()-1))){
         p1 = Integer.parseInt(P1);
        } else{
            p1 = Integer.parseInt(P1.substring(0, getP1().length()-1));
        }
         if(Character.isDigit(P2.charAt(P2.length()-1))){
         p2 = Integer.parseInt(getP2());
        } else{
            p2 = Integer.parseInt(P2.substring(0, P2.length()-1));
        }
        if(Math.abs(p1-p2)<=n){
            return true;
        }
        return false;
    }
}
