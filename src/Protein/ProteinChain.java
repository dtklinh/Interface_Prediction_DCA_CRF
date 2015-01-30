/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import DCA.ColPair_Score;
import java.util.ArrayList;
import org.biojava.bio.structure.Chain;

/**
 *
 * @author t.dang
 */
public class ProteinChain {
    private String ProteinChainName;
    private Chain ProtChain;
    private ArrayList<ColPair_Score> LstPair;

    /**
     * @return the ProteinChainName
     */
    public String getProteinChainName() {
        return ProteinChainName;
    }

    /**
     * @param ProteinChainName the ProteinChainName to set
     */
    public void setProteinChainName(String ProteinChainName) {
        this.ProteinChainName = ProteinChainName;
    }

    /**
     * @return the LstPair
     */
    public ArrayList<ColPair_Score> getLstPair() {
        return LstPair;
    }

    /**
     * @param LstPair the LstPair to set
     */
    public void setLstPair(ArrayList<ColPair_Score> LstPair) {
        this.LstPair = LstPair;
    }
    
    public ProteinChain(String path, String name){
        this.ProteinChainName = name;
        
    }

    /**
     * @return the ProtChain
     */
    public Chain getProtChain() {
        return ProtChain;
    }

    /**
     * @param ProtChain the ProtChain to set
     */
    public void setProtChain(Chain ProtChain) {
        this.ProtChain = ProtChain;
    }
    
    public ProteinChain(String name, Chain c){
        this.ProteinChainName = name;
        this.ProtChain = c;
    }
}
