/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.StaticMethod;
import Common.ColPairAndScores.ColPair_Score;
import StaticMethods.DistanceCalc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class ProteinComplex {

    private String ProteinID;
    private String Chain1_ID;
    private String Chain2_ID;
    private String Sequence1;
    private String Sequence2;
    private String Dir2PDB;
    private List<Group> LstAmino1;
    private List<Group> LstAmino2;

    /**
     * @return the ProteinChain1_ID
     */
    public String getChain1_ID() {
        return Chain1_ID;
    }

    /**
     * @param ProteinChain1_ID the ProteinChain1_ID to set
     */
    public void setChain1_ID(String ProteinChain1_ID) {
        this.Chain1_ID = ProteinChain1_ID;
    }

    /**
     * @return the ProteinChain2_ID
     */
    public String getChain2_ID() {
        return Chain2_ID;
    }

    /**
     * @param ProteinChain2_ID the ProteinChain2_ID to set
     */
    public void setChain2_ID(String ProteinChain2_ID) {
        this.Chain2_ID = ProteinChain2_ID;
    }

    /**
     * @return the Sequence1
     */
    public String getSequence1() {
        return Sequence1;
    }

    /**
     * @param Sequence1 the Sequence1 to set
     */
    public void setSequence1(String Sequence1) {
        this.Sequence1 = Sequence1;
    }

    /**
     * @return the Sequence2
     */
    public String getSequence2() {
        return Sequence2;
    }

    /**
     * @param Sequence2 the Sequence2 to set
     */
    public void setSequence2(String Sequence2) {
        this.Sequence2 = Sequence2;
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

    /**
     * @return the LstAmino1
     */
    public List<Group> getLstAmino1() {
        return LstAmino1;
    }

    /**
     * @param LstAmino1 the LstAmino1 to set
     */
    public void setLstAmino1(List<Group> LstAmino1) {
        this.LstAmino1 = LstAmino1;
    }

    /**
     * @return the LstAmino2
     */
    public List<Group> getLstAmino2() {
        return LstAmino2;
    }

    /**
     * @param LstAmino2 the LstAmino2 to set
     */
    public void setLstAmino2(List<Group> LstAmino2) {
        this.LstAmino2 = LstAmino2;
    }

    public ProteinComplex(String Prot, String Chain1, String Chain2, String dir2pdb) throws IOException, StructureException {
        this.Chain1_ID = Chain1;
        this.Chain2_ID = Chain2;
        this.Dir2PDB = dir2pdb;
        this.ProteinID = Prot.substring(0, 4);

        // read PDB file;
        String endfile = StaticMethod.FindEndName(dir2pdb);
        String path = dir2pdb + ProteinID + endfile;
        Structure s = (new PDBFileReader()).getStructure(path);
        
        Chain c1 = s.getChainByPDB(Chain1);
        Chain c2 = s.getChainByPDB(Chain2);
        this.Sequence1 = c1.getAtomSequence();
        this.LstAmino1 = c1.getAtomGroups();
        this.Sequence2 = c2.getAtomSequence();
        this.LstAmino2 = c2.getAtomGroups();
    }
    public ArrayList<ColPair_Score> DistancePairwise() throws StructureException{
        int len1 = LstAmino1.size();
        int len2 = LstAmino2.size();
        ArrayList<ColPair_Score> arr = new ArrayList<>();
        for(int i=0; i<len1;i++){
            for(int j=0; j<len2; j++){
                Group g1 = LstAmino1.get(i);
                Group g2 = LstAmino2.get(j);
                if(!g1.getType().equalsIgnoreCase("amino") || !g2.getType().equalsIgnoreCase("amino")){
                    continue;
                }
                double score = DistanceCalc.DistanceBtwGroups_CarbonAlpha(g1,g2);
                arr.add(new ColPair_Score(g1.getResidueNumber().toString(), g2.getResidueNumber().toString(), score));
                
            }
        }
        return arr;
    }

    /**
     * @return the ProteinID
     */
    public String getProteinID() {
        return ProteinID;
    }

    /**
     * @param ProteinID the ProteinID to set
     */
    public void setProteinID(String ProteinID) {
        this.ProteinID = ProteinID;
    }
}
