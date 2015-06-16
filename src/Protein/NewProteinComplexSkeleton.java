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
    
    public String getProteinChain(int i) { // i = 1 or 2
        String res = ProtPDBID + "_";
        if(i==1){
            res = res+ChainID1;
        }
        else if(i==2){
            res = res + ChainID2;
        }
        else{
            System.err.println("Chain must be 1 or 2");
            System.exit(1);
        }
        return res;
    }
    public String getProteinComplex(){
        return (ProtPDBID+"_"+ChainID1 + ChainID2);
    }
}
