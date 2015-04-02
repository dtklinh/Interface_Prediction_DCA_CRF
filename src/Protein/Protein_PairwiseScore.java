/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.StaticMethod;
import Common.ColPair_Score;
import Common.Configuration;
import DCA.MyIO_DCA;
import LinearAlgebra.MyOwnFloatMatrix;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.ResidueNumber;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
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
    private ArrayList<String> LstResidueNum;
    
    public Protein_PairwiseScore(String p2f, String protChain, int nd, ArrayList<ColPair_Score> lst){
        Path2File = p2f;
        ProteinChain = protChain;
        NeighborDistance = nd;
        LstScore = lst;
    }

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
//        this.ProteinChain = name.substring(0, 6);
        if(name.indexOf(".")<0){
            ProteinChain = name;
        }
        else{
            int idx = name.indexOf(".");
            ProteinChain = name.substring(0,idx);
        }
        Path2OpenFile = path + ProteinChain + endfile;
        
//        if (name.length() == 6) {
//            this.ProteinChain = name;
//            Path2OpenFile = path + name + endfile;
//        } else {
//            this.ProteinChain = name.substring(0, 6);
//            Path2OpenFile = path + name;
//        }
        this.LstScore = MyIO_DCA.ReadDCA_Score(Path2OpenFile);
        // ResidueNumber
        /*
        Path2OpenFile = Configuration.Dir2PDBSingleChain+ProteinChain+StaticMethod.FindEndName(Configuration.Dir2PDBSingleChain);
        Structure s = (new PDBFileReader()).getStructure(Path2OpenFile);
        Chain c = s.getChain(0);
        List<Group> lst_groups = c.getAtomGroups();
        LstResidueNum = new ArrayList<>();
        for(int i=0;i<lst_groups.size();i++){
            LstResidueNum.add(lst_groups.get(i).getResidueNumber().toString());
        }
        */ 
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

    public Protein_PairwiseScore(String path, String name, int distance, MyOwnFloatMatrix m){
        this.Path2File = path;
        this.ProteinChain = name.substring(0, 6);
        this.NeighborDistance = distance;
        this.LstScore = new ArrayList<>();
        float[][] A = m.getArrayCopy();
        for(int i=0; i<A.length-1;i++){
            for(int j=i+1;j<A[0].length;j++){
                ColPair_Score col = new ColPair_Score(String.valueOf(i), String.valueOf(j), (A[i][j]+A[j][i])/2);
//                ColPair_Score col = new ColPair_Score(i, j, Math.min(A[i][j], A[j][i]));
                LstScore.add(col);
            }
        }
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
    public List<ColPair_Score> BottomNumber(int n){
//        int size = getLstScore().size();
        return getLstScore().subList(0, n);
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
        ArrayList<String> ResidualNum = new ArrayList<>();
        for (int i = 0; i < lst_group.size(); i++) {
            ResidualNum.add(lst_group.get(i).getResidueNumber().toString());
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
            d.setP1(ResidualNum.get(Integer.parseInt(d.getP1())));
            d.setP2(ResidualNum.get(Integer.parseInt(d.getP2())));
        }

    }
    public void AdjustSecondIndex(String Path2PDBFile, String chain2) throws IOException, StructureException {
        Structure s = (new PDBFileReader()).getStructure(Path2PDBFile);
        Chain c = s.getChainByPDB(chain2);
        List<Group> groups = c.getAtomGroups();
        Iterator<Group> iterGroup = groups.iterator();
        while(iterGroup.hasNext()){
            Group g = iterGroup.next();
            if(!g.hasAminoAtoms()){
                iterGroup.remove();
            }
        }
        System.out.println("Chain 2: "+chain2 +" : length: "+groups.size());
        Iterator<ColPair_Score> iter = LstScore.iterator();
        while(iter.hasNext()){
            ColPair_Score col = iter.next();
            String Res2 = col.getP2();
            int idx2 = LstResidueNum.indexOf(Res2);
            String newidx2 = groups.get(idx2).getResidueNumber().toString();
            col.setP2(newidx2);
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
    public double getScore(String p1, String p2) {
        double res = -10;
        for (ColPair_Score col : getLstScore()) {
            if (col.getP1().equals(p1)  && col.getP2().equals(p2) ) {
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
    public double getScoreByIndexes(String P1, String P2) {
        for (ColPair_Score s : LstScore) {
            if (s.getP1().equals(P1)  && s.getP2().equals(P2) ) {
                return s.getScore();
            }
        }
        System.err.println("Position 1 or 2 is not found");
        return 10;
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
    public int getSequenceLength(){
        return (int)(1+ Math.sqrt(8*LstScore.size()+1))/2;
    }
    
    @Override
    public Protein_PairwiseScore clone(){
        return new Protein_PairwiseScore(Path2File, ProteinChain, NeighborDistance, LstScore);
    }
    public void ComputeEVcomplex(float M_eff){
        int L = getSequenceLength();
//        Protein_PairwiseScore p = this.clone();
//        ArrayList<ColPair_Score> lst_score = p.getLstScore();
        Iterator<ColPair_Score> iter = LstScore.iterator(); 
        double min = Collections.min(LstScore).getScore();
        while(iter.hasNext()){
            ColPair_Score c = iter.next();
            double score = c.getScore();
            score = (score/min);
            score = score/(1+1/(Math.sqrt(M_eff/L)));
            c.setScore(score);
        }
//        p.setLstScore(lst_score);
        
    }
    
}
