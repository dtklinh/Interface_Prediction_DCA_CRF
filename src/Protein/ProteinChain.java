/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.StaticMethod;
import Common.ColPair_Score;
import StaticMethods.DistanceCalc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class ProteinChain {

    private String ProteinChainID;
    private String Sequence;
    private String Dir2PDB;
    private List<Group> LstAmino;
    private HashMap<Integer, String> Map_idx_ResNum;

    /**
     * @return the ProteinChainID
     */
    public String getProteinChainID() {
        return ProteinChainID;
    }

    /**
     * @param ProteinChainID the ProteinChainID to set
     */
    public void setProteinChainID(String ProteinChainID) {
        this.ProteinChainID = ProteinChainID;
    }

    /**
     * @return the Sequence
     */
    public String getSequence() {
        return Sequence;
    }

    /**
     * @param Sequence the Sequence to set
     */
    public void setSequence(String Sequence) {
        this.Sequence = Sequence;
    }

    /**
     * @return the Dir2PDB
     */
    public String getDir2PDB() {
        return Dir2PDB;
    }

    /**
     * @param Dir2PDB the Dir2PDB to set
     */
    public void setDir2PDB(String Dir2PDB) {
        this.Dir2PDB = Dir2PDB;
    }

    public ProteinChain(String Dir, String ID) throws IOException {
        this.Dir2PDB = Dir;
        String path = "";
        if (ID.indexOf(".") >= 0) {
            this.ProteinChainID = ID.substring(0, 6);
            path = Dir + ID;
        } else {
            this.ProteinChainID = ID;
            path = Dir + ID + StaticMethod.FindEndName(Dir);
        }
        Structure s = (new PDBFileReader()).getStructure(path);
        Chain c = s.getChain(0);
        this.Sequence = c.getAtomSequence();
        this.LstAmino = c.getAtomGroups();
        Iterator<Group> iter = LstAmino.iterator();
        while(iter.hasNext()){
            Group p = iter.next();
            if(!p.getType().equalsIgnoreCase("amino")){
                iter.remove();
            }
        }
        Map_idx_ResNum = new HashMap<>();
        for(int i=0; i<LstAmino.size(); i++){
            int key = i;
            String val = LstAmino.get(i).getResidueNumber().toString();
            Map_idx_ResNum.put(key, val);
        }
    }

    public ArrayList<ColPair_Score> DistancePairwise() throws StructureException {
        ArrayList<ColPair_Score> res = new ArrayList<>();
        int len = LstAmino.size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                AminoAcid a;
                Group g1 = LstAmino.get(i);
                Group g2 = LstAmino.get(j);
                double d = DistanceCalc.DistanceBtwGroups_CarbonAlpha(g1, g2);
                
                res.add(new ColPair_Score(g1.getResidueNumber().toString(), g2.getResidueNumber().toString(), d));

            }
        }
        return res;
    }

    /**
     * @return the Map_idx_ResNum
     */
    public HashMap<Integer, String> getMap_idx_ResNum() {
        return Map_idx_ResNum;
    }

    /**
     * @param Map_idx_ResNum the Map_idx_ResNum to set
     */
    public void setMap_idx_ResNum(HashMap<Integer, String> Map_idx_ResNum) {
        this.Map_idx_ResNum = Map_idx_ResNum;
    }
}
