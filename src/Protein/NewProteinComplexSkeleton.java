/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

/**
 *
 * @author t.dang
 */
public class NewProteinComplexSkeleton {
    private String ProtPDBID;
    private String ChainID1;
    private String ChainID2;

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

    /**
     * @return the ChainID1
     */
    public String getChainID1() {
        return ChainID1;
    }

    /**
     * @param ChainID1 the ChainID1 to set
     */
    public void setChainID1(String ChainID1) {
        this.ChainID1 = ChainID1;
    }

    /**
     * @return the ChainID2
     */
    public String getChainID2() {
        return ChainID2;
    }

    /**
     * @param ChainID2 the ChainID2 to set
     */
    public void setChainID2(String ChainID2) {
        this.ChainID2 = ChainID2;
    }
    
    public NewProteinComplexSkeleton(String prot, String c1, String c2){
        this.ProtPDBID = prot;
        this.ChainID1 = c1;
        this.ChainID2 = c2;
    }
}
