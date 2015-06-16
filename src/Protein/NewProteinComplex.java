/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.ColPair_Score;
import Common.Configuration;
import Common.StaticMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class NewProteinComplex {
    private String ProteinPDBID;
    private String ChainID1, ChainID2;
    private NewProteinChain ProteinChain1, ProteinChain2;
    
    public NewProteinComplex(String path2PDbFile, String ProtID, String chain1, String chain2) throws IOException, StructureException{
        this.ChainID1 = chain1;
        this.ChainID2 = chain2;
        this.ProteinPDBID = ProtID;
        this.ProteinChain1 = new NewProteinChain(path2PDbFile, ProtID, chain1);
        this.ProteinChain2 = new NewProteinChain(path2PDbFile, ProtID, chain2);
    }
    public NewProteinComplex(NewProteinChain C1, NewProteinChain C2){
        if(!C1.getProteinPDBID().equalsIgnoreCase(C2.getProteinPDBID())){
            System.err.println("Proteins "+C1.getProteinPDBID()+" , "+C2.getProteinPDBID()+" not in same complex");
            System.exit(1);
        }
        this.ProteinPDBID = C1.getProteinPDBID();
        this.ChainID1 = C1.getChainID();
        this.ChainID2 = C2.getChainID();
        
    }
    public ArrayList<ColPair_Score> pairwiseDistance() throws StructureException{
        ArrayList<ColPair_Score> res = new ArrayList<>();
        List<Group> lstG1 = ProteinChain1.getLstAminoAcid();
        List<Group> lstG2 = ProteinChain2.getLstAminoAcid();
        for(int i=0; i<lstG1.size(); i++){
            Group g1 = lstG1.get(i);
            for(int j=0; j<lstG2.size(); j++){
                Group g2 = lstG2.get(j);
                double score = 0;
                if(Configuration.UseCarbonAlpha){
                 score = StaticMethod.DistanceBtwGroups(g1, g2);
                }
                else{
                    score = StaticMethod.DistanceBtwGroupsAnyAtom(g1, g2);
                }
                ColPair_Score col = new ColPair_Score(g1.getResidueNumber().toString(), g2.getResidueNumber().toString(), score);
                res.add(col);
            }
        }
        Collections.sort(res);
        return res;
    }
}
