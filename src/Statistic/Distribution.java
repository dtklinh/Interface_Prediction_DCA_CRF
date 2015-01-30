/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic;

import java.util.ArrayList;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 *
 * @author t.dang
 */
public class Distribution {
    private String Name;
    private ArrayList<Double> DValue;

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the DValue
     */
    public ArrayList<Double> getDValue() {
        return DValue;
    }

    /**
     * @param DValue the DValue to set
     */
    public void setDValue(ArrayList<Double> DValue) {
        this.DValue = DValue;
    }
    
    public Distribution(String name, ArrayList<Double> lst){
        this.Name = name;
        this.DValue = lst;
    }
    
    // draw histogram of this distribution
    public void DrawHistogram(int numBin){
        
    }
    
}
