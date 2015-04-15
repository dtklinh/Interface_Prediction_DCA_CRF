/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Common.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import Protein.NewProtein_PairwiseScore;
import Protein.NewProtein_Pairwise_ScoreRef;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class RunHonHop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here

        // Test high plmdca score of long range pair if they are correlated?
        ///*
        String Dir2ThreeDim = "Input/Zellner_Homodimer_5/3D/";
        String Dir2DCAScore = "Input/Zellner_Homodimer_5/MSAMethod/HHblits/DCA/";
        String Dir2RASA = "Input/Zellner_Homodimer_5/Rasa/";
        String Dir2PDB = "Input/Zellner_Homodimer_5/PDB/";

        String Path2List = "Input/Zellner_Homodimer_5/List.txt";
        String EndFile3D = StaticMethod.FindEndName(Dir2ThreeDim);
        String EndFileDCAScore = StaticMethod.FindEndName(Dir2DCAScore);
        String EndFileRasa = StaticMethod.FindEndName(Dir2RASA);
        String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);

        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        int P = 0, TP = 0, PP = 0, PredTP=0;
        int num = 60;
        int idx = 0;
        int neighbor = 11;
        for (NewProteinComplexSkeleton p : lst.subList(idx, idx + 5)) {
            String ProtID = p.getProtPDBID();
            String chain1 = p.getChainID1();
            String chain2 = p.getChainID2();
            String Path2ThreeDimFile = Dir2ThreeDim + ProtID + "_" + chain1 + chain2 + EndFile3D;
            String Path2DCAScoreFile = Dir2DCAScore + ProtID + EndFileDCAScore;
            String Path2RASAFile = Dir2RASA + ProtID + "_" + chain1 + EndFileRasa;
            String Path2PDBFile = Dir2PDB + ProtID + EndFilePDB;

            NewProtein_Pairwise_ScoreRef ref =
                    new NewProtein_Pairwise_ScoreRef(Path2ThreeDimFile, Path2RASAFile, 8.0);
            NewProtein_PairwiseScore prot = new NewProtein_PairwiseScore(neighbor, ProtID, chain1);
            ArrayList<ColPair_Score> LstPairScore = MyIO.read2ColPair(Path2DCAScoreFile, "\\s+");

            List<ColPair_Score> LstCandidate = prot.topNumber(ref, LstPairScore, true, true, num);
//            List<ColPair_Score> LstCandidate = prot.randomPickUp(LstPairScore, num, true, ref);

            //            LstCandidate = ref.filterCandidateSS(LstCandidate);

            //            ref.printPairPosition(LstCandidate);
            P += ref.countFirstColumnContact();
//            PP += LstCandidate.size();
            PP += NewProtein_Pairwise_ScoreRef.countNumResinPair(LstCandidate);

            TP += ref.countTPwithConnDeg(LstCandidate);
            int PredictedTPNum = NewProtein_Pairwise_ScoreRef.countPredictedTPNumber(LstCandidate);
            
            PredTP += ref.countTPwithBestRASA(Path2PDBFile, chain1,PredictedTPNum);
            
//            for (ColPair_Score col : LstCandidate) {
//                if (ref.isOneResidueOnInterface(col)) {
//                    TP++;
//                }
//            }

        }
        System.out.println();
        System.out.println("Recall: " + (double) TP / P);
        System.out.println("Recall(best RASA): " + (double) PredTP / P);
        System.out.println("Precision: " + (double) TP / PP);
        //*/
        ////////////////////////////////////////////////////////////////

        // Test Contact residue pairs in one protein chain.
        /*
         String Dir2ThreeDim = "Input/Zellner_Homodimer_5/3D/";
         String Dir2DCAScore = "Input/Zellner_Homodimer_5/MSAMethod/HHblits/DCA/";
         String Dir2RASA = "Input/Zellner_Homodimer_5/Rasa/";

         String Path2List = "Input/Zellner_Homodimer_5/List.txt";
         String EndFile3D = StaticMethod.FindEndName(Dir2ThreeDim);
         String EndFileDCAScore = StaticMethod.FindEndName(Dir2DCAScore);
         String EndFileRasa = StaticMethod.FindEndName(Dir2RASA);

         ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
         int P = 0, PP = 0, TP = 0;
         int num = 400, neighbor = 11;
         for (NewProteinComplexSkeleton p : lst) {

         String ProtID = p.getProtPDBID();
         String chain1 = p.getChainID1();
         String Path2ThreeDimFile = Dir2ThreeDim + ProtID + "_" + chain1 + EndFile3D;
         String Path2DCAFile = Dir2DCAScore + ProtID + EndFileDCAScore;
         String Path2RasaFile = Dir2RASA + ProtID + "_" + chain1 + EndFileRasa;
            
         //            NewProtein_Pairwise_ScoreRef ref = new NewProtein_Pairwise_ScoreRef(Path2ThreeDimFile, 8.5);
         NewProtein_Pairwise_ScoreRef ref = new NewProtein_Pairwise_ScoreRef(Path2ThreeDimFile, Path2RasaFile, 8.5);
            
         NewProtein_PairwiseScore prot = new NewProtein_PairwiseScore(neighbor, ProtID, chain1);
         ArrayList<ColPair_Score> LstScore = MyIO.read2ColPair(Path2DCAFile, "\\s+");

         P += ref.countPairContact(neighbor, true);
         num = ref.countPairContact(neighbor, true);
         PP += num;


         List<ColPair_Score> LstCandidate = prot.topNumber(ref, LstScore, true, true, num);
         //            List<ColPair_Score> LstCandidate = prot.randomPickUp(LstScore, num, true, ref);


         for (ColPair_Score col : LstCandidate) {
         if (ref.isPairOnInterfaceOrContact(col)) {
         TP++;
         }
         }
         }
         System.out.println("Recall: " + (double) TP / P);
         System.out.println("Precision: " + (double) TP / PP);
         //*/
    }
}
