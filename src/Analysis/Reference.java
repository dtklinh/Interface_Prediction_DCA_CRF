/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import Protein.Protein_PairwiseScore;

/**
 *
 * @author t.dang
 */
public class Reference {

    private String ProteinChainID;
//    private String Dir2PDB;
    private double ContactDistance;
    private int NeighborDistance;
    private Protein_PairwiseScore PairScores;

    public Reference(String ProtChainID, double distance, int neighbor) {
        ProteinChainID = ProtChainID;
//        Dir2PDB = d2pdb;
        ContactDistance = distance;
        NeighborDistance = neighbor;
//        PairScores = new Protein_PairwiseScore
    }
    
}
