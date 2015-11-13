/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic.ParameterEstimation;

import Common.ColPairAndScores.ColPair_Score;
import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import Protein.*;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class Evaluate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        // evaluate the naive Maximum Likelihood method
        String Path2List = "Input/Zellner_Homodimer/List.txt";
        String Dir2nML = "Input/Zellner_Homodimer/MSAMethod/HHblits/nML/";
        String Dir2DCA = "Input/Zellner_Homodimer/MSAMethod/HHblits/plmdca/";
        String Dir2_3D = "Input/Zellner_Homodimer/3D/";
        String Endfile_nML = StaticMethod.FindEndName(Dir2nML);
        String Endfile_3D = ".3d_Any_VdW";
        String EndfileDCA = StaticMethod.FindEndName(Dir2DCA);
        int neighbor = Configuration.Neighbor;
        double contact = 4.5;
        int Num = 50;
        int TP=0, PredTP =0, RndTP=0;
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot:lst){
            String ProtID = prot.getProtPDBID();
            String chain = prot.getChainID1();
            String Path2_nML_ScoreFile = Dir2nML + ProtID + "_" + chain + Endfile_nML;
            String Path2DCAScore = Dir2DCA + ProtID + EndfileDCA;
            String Path2_3DFile = Dir2_3D + ProtID + "_" + chain + Endfile_3D;
            NewProtein_PairwiseScore p = new NewProtein_PairwiseScore(neighbor, ProtID, chain);
            
//            ArrayList<ColPair_Score> Lst_Col = MyIO.readColPairScore2ArrayList(Path2_nML_ScoreFile, "\\s+");
            ArrayList<ColPair_Score> Lst_Col = ProteinIO.readColPairScore2ArrayList(Path2DCAScore, "\\s+");
            
            List<ColPair_Score> Candidates = p.topOverThreshold(Lst_Col, 0.3, false);
            List<ColPair_Score> RndCandidates = p.randomPickUp(Lst_Col,Candidates.size() , false, null);
            
//            List<ColPair_Score> Candidates = p.top;
            PredTP += Candidates.size();
            
            NewProtein_Pairwise_ScoreRef ref = new NewProtein_Pairwise_ScoreRef(Path2_3DFile, contact);
            TP += ref.countTPContactPair(Candidates);
            RndTP += ref.countTPContactPair(RndCandidates);
        }
        System.out.println("Recall,  TP / PredTP: "+ (double)TP/PredTP+"\t"+TP+"\t"+PredTP);
        System.out.println("Recall of Random: TP / PredTP "+ (double)RndTP/PredTP +"\t"+RndTP+"\t"+PredTP);
    }
}
