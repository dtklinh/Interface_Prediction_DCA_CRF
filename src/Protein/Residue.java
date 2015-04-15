/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

/**
 *
 * @author t.dang
 */
public class Residue implements Comparable<Residue>{
    private String ResidueNumber;
    private int ConnectDegree;
    @Override
    public int compareTo(Residue other){
        if(this.getConnectDegree()>other.getConnectDegree()){
            return 1;
        }
        else if(this.getConnectDegree() < other.getConnectDegree()){
            return -1;
        }
        else{
            return 0;
        }
    }
    public Residue(String ResNum, int deg){
        ResidueNumber = ResNum;
        ConnectDegree = deg;
    }
    public Residue(String ResNum){
        ResidueNumber = ResNum;
    }
    public void plusConnectDegree(int n){
        setConnectDegree(getConnectDegree() + n);
    }

    /**
     * @return the ResidueNumber
     */
    public String getResidueNumber() {
        return ResidueNumber;
    }

    /**
     * @param ResidueNumber the ResidueNumber to set
     */
    public void setResidueNumber(String ResidueNumber) {
        this.ResidueNumber = ResidueNumber;
    }

    /**
     * @return the ConnectDegree
     */
    public int getConnectDegree() {
        return ConnectDegree;
    }

    /**
     * @param ConnectDegree the ConnectDegree to set
     */
    public void setConnectDegree(int ConnectDegree) {
        this.ConnectDegree = ConnectDegree;
    }
}
