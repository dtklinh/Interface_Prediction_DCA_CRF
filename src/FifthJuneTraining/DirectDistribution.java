/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FifthJuneTraining;

import Common.MyIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author t.dang
 */
public class DirectDistribution {
    private String P1;
    private String P2;
    private double[] Value;

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
     * @return the Value
     */
    public double[] getValue() {
        return Value;
    }

    /**
     * @param Value the Value to set
     */
    public void setValue(double[] Value) {
        this.Value = Value;
    }
    
    public DirectDistribution(String p1, String p2, double[]v){
        P1 = p1;
        P2 = p2;
        Value = new double[v.length];
        System.arraycopy(v, 0, Value, 0, v.length);
    }
    
}
