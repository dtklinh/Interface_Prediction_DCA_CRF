/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.StaticMethod;
import DCA.ColPair_Score;
import DCA.MyIO_DCA;
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
public class Protein_PairwiseScore {

    private String Path2File;
    private String ProteinChain;
    private int NeighborDistance;
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
        this.setLstScore(LstScore);
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

    public Protein_PairwiseScore(String path, String name, int distance) throws IOException {
        // make code more roburst
        String endfile = StaticMethod.FindEndName(path);

        String Path2OpenFile = "";
        this.NeighborDistance = distance;
        this.Path2File = path;
        Path2OpenFile = path + name.substring(0, 6) + endfile;
        this.ProteinChain = name.substring(0, 6);
//        if (name.length() == 6) {
//            this.ProteinChain = name;
//            Path2OpenFile = path + name + endfile;
//        } else {
//            this.ProteinChain = name.substring(0, 6);
//            Path2OpenFile = path + name;
//        }
        this.LstScore = MyIO_DCA.ReadDCA_Score(Path2OpenFile);
    }

    public Protein_PairwiseScore(String path, String name, int distance, int idx_score) throws IOException {
        // make code more roburst
        String endfile = StaticMethod.FindEndName(path);

        String Path2OpenFile = "";
        this.NeighborDistance = distance;
        this.Path2File = path;
        this.ProteinChain = name.substring(0, 6);
        Path2OpenFile = path+ProteinChain+endfile;
//        if (name.length() == 6) {
//            this.ProteinChain = name;
//            Path2OpenFile = path + name + endfile;
//        } else {
//            this.ProteinChain = name.substring(0, 6);
//            Path2OpenFile = path + name;
//        }
        this.LstScore = MyIO_DCA.ReadDCA_Score(Path2OpenFile, idx_score);
    }

    public void Sort() {
        if (this.getLstScore() != null) {
            Collections.sort(getLstScore());
        }
    }

    public void EliminateNeighbor() {
        int len = this.getLstScore().size();
        for (int i = len - 1; i >= 0; i--) {
            if (getLstScore().get(i).IsNeighbor(getNeighborDistance())) {
                getLstScore().remove(i);
            }
        }
    }

    public List<ColPair_Score> TopPercent(double percent) {
        return this.getLstScore().subList((int) (getLstScore().size() * (1.0 - percent)), getLstScore().size() - 1);
    }

    public List<ColPair_Score> TopNumber(int n) {
        int size = getLstScore().size();
        return getLstScore().subList(size - n, size);
    }

    public void AdjustIndex(String Path2Folder) throws IOException {
        // make code more roburst
//        String tmp = utils.Utils.dir2list(Path2Folder).get(0);
//        tmp = tmp.trim();
//        String endfile = "";
//        if(tmp!=null){
//            int idx = tmp.indexOf(".");
//            endfile = tmp.substring(idx);
//        }
        String endfile = StaticMethod.FindEndName(Path2Folder);

        Structure s = (new PDBFileReader()).getStructure(Path2Folder + ProteinChain + endfile);
        Chain c = s.getChain(0);
        List<Group> lst_group = c.getAtomGroups();
        ArrayList<Integer> ResidualNum = new ArrayList<>();
        for (int i = 0; i < lst_group.size(); i++) {
            ResidualNum.add(lst_group.get(i).getResidueNumber().getSeqNum());
        }
        for (ColPair_Score d : getLstScore()) {
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

//    public boolean CheckPairProximity(ColPair_Score p, double def_proximity){
//        int P1 = p.getP1();
//        int P2 = p.getP2();
//        double dis = getScore(P1, P2);
//        if()
//    }
    public double getScore(int p1, int p2) {
        double res = -10;
        for (ColPair_Score col : getLstScore()) {
            if (col.getP1() == p1 && col.getP2() == p2) {
                return col.getScore();

            }
        }
        return res;
    }

    /**
     * @param LstScore the LstScore to set
     */
//    public void setLstScore(ArrayList<ColPair_Score> LstScore) {
//        this.LstScore = LstScore;
//    }
//    public int CountTP(ArrayList<ColPair_Score> lst){
//        
//    }
    public double getScoreByIndexes(int P1, int P2) {
        for (ColPair_Score s : LstScore) {
            if (s.getP1() == P1 && s.getP2() == P2) {
                return s.getScore();
            }
        }
        System.err.println("Position 1 or 2 is not found");
        return -10;
    }

    public int CountOverlap(Protein_PairwiseScore other) {
        int sum = 0;
        for (ColPair_Score s : other.getLstScore()) {
            for (ColPair_Score self : LstScore) {
                if (self.IsSameIndex(s)) {
                    sum++;
                    break;
                }
            }
        }
        return sum;
    }

    /**
     * @return the NeighborDistance
     */
    public int getNeighborDistance() {
        return NeighborDistance;
    }

    /**
     * @param NeighborDistance the NeighborDistance to set
     */
    public void setNeighborDistance(int NeighborDistance) {
        this.NeighborDistance = NeighborDistance;
    }
    
    public static int CountOverlapIdx(List<ColPair_Score> A, List<ColPair_Score> B){
        int sum = 0;
        for(ColPair_Score a:A){
            for(ColPair_Score b:B){
                if(a.getP1()==b.getP1() && a.getP2()==b.getP2()){
                    sum++;
                    break;
                }
            }
        }
        return sum;
    }
}
