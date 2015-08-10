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
    private String P1;
    private String P2;

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
        return new EqualsBuilder().append(P1, rhs.P1).append(P2, rhs.P2).isEquals();
    }
}
