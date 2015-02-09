/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class Protein_DCAScore {
    private String Path2File;
    private String ProteinChain;
    private ArrayList<ColPair_Score> LstScore;

    /**
     * @return the ProteinChain
     */
    public String getProteinChain() {
        return ProteinChain;
    }

    /**
     * @param ProteinChain the ProteinChain to set
     */
    public void setProteinChain(String ProteinChain) {
        this.ProteinChain = ProteinChain;
    }

    /**
     * @return the LstScore
     */
    public ArrayList<ColPair_Score> getLstScore() {
        return LstScore;
    }

    /**
     * @param LstScore the LstScore to set
     */
    public void setLstScore(ArrayList<ColPair_Score> LstScore) {
        this.LstScore = LstScore;
    }

    /**
     * @return the Path2File
     */
    public String getPath2File() {
        return Path2File;
    }

    /**
     * @param Path2File the Path2File to set
     */
    public void setPath2File(String Path2File) {
        this.Path2File = Path2File;
    }
    
    public Protein_DCAScore(String path, String name) throws IOException{
        String Path2OpenFile="";
        this.Path2File = path;
        if(name.length()==6){
            this.ProteinChain = name;
            Path2OpenFile = path + name+".dca";
        }
        else{
            this.ProteinChain = name.substring(0, 6);
            Path2OpenFile = path + name;
        }
        this.LstScore = MyIO_DCA.ReadDCA_Score(Path2OpenFile);
    }
    public void Sort(){
        if(this.LstScore!=null){
            Collections.sort(LstScore);
        }
    }
    public void EliminateNeighbor(){
        int len = this.LstScore.size();
        for(int i=len-1; i>=0; i--){
            if(LstScore.get(i).IsNeighbor()){
                LstScore.remove(i);
            }
        }
    }
    public List<ColPair_Score> TopPercent(double percent){
        return this.LstScore.subList((int)(LstScore.size()*(1.0-percent)), LstScore.size()-1);
    }
    public void AdjustIndex(String Path2Folder) throws IOException{
        Structure s = (new PDBFileReader()).getStructure(Path2Folder+ProteinChain+".txt");
        Chain c = s.getChain(0);
        List<Group> lst_group = c.getAtomGroups();
        ArrayList<Integer> ResidualNum = new ArrayList<>();
        for(int i=0; i<lst_group.size(); i++){
            ResidualNum.add(lst_group.get(i).getResidueNumber().getSeqNum());
        }
        for(ColPair_Score d: LstScore){
//            int p1_idx = ResidualNum.indexOf(d.getP1());
//            int p2_idx = ResidualNum.indexOf(d.getP2());
//            if(p1_idx<0 || p2_idx<0){
//                System.err.println("P1: "+ d.getP1());
//                System.err.println("P2: "+d.getP2());
//                System.err.println("Res len: "+ ResidualNum.size());
//                System.exit(1);
//            }
            d.setP1(ResidualNum.get(d.getP1()));
            d.setP2(ResidualNum.get(d.getP2()));
        }
        
    }
//    public void Print2Screen(){
//        
//    }
   
   
}
