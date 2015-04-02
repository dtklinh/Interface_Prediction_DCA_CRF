/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.ColPair_Score;
import Common.StaticMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class NewProteinChain {
    private String ProteinPDBID;
    private String ChainID;
    private String Sequence;
    private List<Group> LstAminoAcid;

    /**
     * @return the ProteinPDBID
     */
    public String getProteinPDBID() {
        return ProteinPDBID;
    }

    /**
     * @param ProteinPDBID the ProteinPDBID to set
     */
    public void setProteinPDBID(String ProteinPDBID) {
        this.ProteinPDBID = ProteinPDBID;
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
    
    public NewProteinChain(String path2PDBFile,String ProtID, String chainID) throws IOException, StructureException{
        this.ProteinPDBID = ProtID;
        this.ChainID = chainID;
        Chain C = (new PDBFileReader()).getStructure(path2PDBFile).getChainByPDB(chainID);
        this.Sequence = C.getAtomSequence();
        List<Group> lst = C.getAtomGroups();
        Iterator<Group> iter = lst.iterator();
        while(iter.hasNext()){
            Group g = iter.next();
            if(!g.getType().equalsIgnoreCase("amino")){
                iter.remove();
            }
        }
        this.LstAminoAcid = lst;
    }
    public ArrayList<ColPair_Score> pairwiseDistance() throws StructureException{
        ArrayList<ColPair_Score> res = new ArrayList<>();
        for(int i=0; i<getLstAminoAcid().size()-1; i++){
            Group g1 = getLstAminoAcid().get(i);
            for(int j = i+1; j<getLstAminoAcid().size(); j++){
                Group g2 = getLstAminoAcid().get(j);
                double score = StaticMethod.DistanceBtwGroups(g1, g2);
                ColPair_Score col = new ColPair_Score(g1.getResidueNumber().toString(), g2.getResidueNumber().toString(), score);
                res.add(col);
            }
        }
        return res;
    }

    /**
     * @return the LstAminoAcid
     */
    public List<Group> getLstAminoAcid() {
        return LstAminoAcid;
    }

    /**
     * @param LstAminoAcid the LstAminoAcid to set
     */
    public void setLstAminoAcid(List<Group> LstAminoAcid) {
        this.LstAminoAcid = LstAminoAcid;
    }
}
