/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Common.ColPairAndScores.ColPair_Score;
import Common.Configuration;
import Common.FastaSequence;
import Common.MyIO;
import Common.StaticMethod;
import StaticMethods.DistanceCalc;
import StaticMethods.ProteinIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class ExcerptProt {

    private String IdxCoreResdidue;
    private String ProtID;
    private String ChainID;
    private String PDBPath;

    /**
     * @return the IdxCoreResdidue
     */
    public String getIdxCoreResdidue() {
        return IdxCoreResdidue;
    }

    /**
     * @param IdxCoreResdidue the IdxCoreResdidue to set
     */
    public void setIdxCoreResdidue(String IdxCoreResdidue) {
        this.IdxCoreResdidue = IdxCoreResdidue;
    }

    /**
     * @return the ProtID
     */
    public String getProtID() {
        return ProtID;
    }

    /**
     * @param ProtID the ProtID to set
     */
    public void setProtID(String ProtID) {
        this.ProtID = ProtID;
    }

    /**
     * @return the ChainID
     */
    public String getChainID() {
        return ChainID;
    }

    /**
     * @param ChainID the ChainID to set
     */
    public void setChainID(String ChainID) {
        this.ChainID = ChainID;
    }

    /**
     * @return the PDBPath
     */
    public String getPDBPath() {
        return PDBPath;
    }

    /**
     * @param PDBPath the PDBPath to set
     */
    public void setPDBPath(String PDBPath) {
        this.PDBPath = PDBPath;
    }

    ///////////////////////////////////////////
    public ExcerptProt(String CoreIdx, String Prot, String Chain, String path) {
        this.ChainID = Chain;
        this.IdxCoreResdidue = CoreIdx;
        this.PDBPath = path;
        this.ProtID = Prot;
    }

    public List<Group> makeExcerpt(int NumLayer) throws IOException, StructureException {
        List<Group> groups = ProteinIO.readGroups(PDBPath, ChainID);
        List<Group> Excerpt = new ArrayList<>();
        Group center = null;
        for (Group g : groups) {
            if (g.getResidueNumber().toString().equalsIgnoreCase(this.IdxCoreResdidue)) {
                center = g;
                groups.remove(g);
                break;
                
            }
        }
        if (center == null) {
            System.out.println("Could not find center amino acid");
            System.exit(1);
            return null;
        }
        
        Excerpt.add(center);
        System.out.println("Center: "+ center.getResidueNumber());
        for (int i = 0; i < NumLayer; i++) {
            
            System.out.print("\nRound "+i+": ");
            int len_exceprt = Excerpt.size();
            for(int k=0;k<len_exceprt;k++){
                Group g1 = Excerpt.get(k);
                int len_rest = groups.size();
                for(int l=len_rest-1; l>=0;l--){
                    Group g2 = groups.get(l);
                    double dis = DistanceCalc.DistanceBtwGroups(g1, g2);
                    if(dis<Configuration.AA_Neighbor_Vdw){
                        System.out.print(g2.getResidueNumber()+" ");
                        Excerpt.add(g2);
                        groups.remove(l);
                    }
                }
            }
        }
        return Excerpt;
    }
    
}
