/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Analysis.GeneralResult;
import Common.ColPair;
import Common.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import Protein.NewProtein_PairwiseScore;
import Protein.NewProtein_Pairwise_ScoreRef;
import Protein.Residue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        /*
         String Dir2ThreeDim = "Input/Zellner_Homodimer/3D/";
         String Dir2DCAScore = "Input/Zellner_Homodimer/MSAMethod/HHblits/DCA/";
         String Dir2RASA = "Input/Zellner_Homodimer/Rasa/";
         String Dir2PDB = "Input/Zellner_Homodimer/PDB/";

         String Path2List = "Input/Zellner_Homodimer/List.txt";
         String EndFile3D = StaticMethod.FindEndName(Dir2ThreeDim);
         String EndFileDCAScore = StaticMethod.FindEndName(Dir2DCAScore);
         String EndFileRasa = StaticMethod.FindEndName(Dir2RASA);
         String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);

         ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
         double MySen = 0, RasaSen = 0, MySpec = 0, RasaSpec = 0, MyMCC = 0, RasaMCC = 0;
         int num = 60;
         int idx = 0, PredTP=0, TP=0;
         int neighbor = 11;
         GeneralResult MyGen = new GeneralResult(0, 0, 0, 0);
         GeneralResult RasaGen = new GeneralResult(0, 0, 0, 0);
         for (NewProteinComplexSkeleton p : lst) {
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
         //                     List<ColPair_Score> LstCandidate = prot.randomPickUp(LstPairScore, num, true, ref);

         //            LstCandidate = ref.filterCandidateSS(LstCandidate);

         //            ref.printPairPosition(LstCandidate);
         //            P += ref.countFirstColumnContact();
         //            PP += LstCandidate.size();
         //            PP += NewProtein_Pairwise_ScoreRef.countNumResinPair(LstCandidate);

         //            TP += ref.countTPwithConnDeg(LstCandidate);
         int PredictedTPNum = NewProtein_Pairwise_ScoreRef.countPredictedTPNumber(LstCandidate);
         GeneralResult dca = ref.getResultWithConnDeg(LstCandidate);
         GeneralResult rasa = ref.getResultWithBestRASA(Path2PDBFile, chain1, PredictedTPNum);

         MyGen.addResult(dca);
         RasaGen.addResult(rasa);
         //                     PredTP += ref.countTPwithBestRASA(Path2PDBFile, chain1,PredictedTPNum);

         for (ColPair_Score col : LstCandidate) {
         if (ref.isOneResidueOnInterface(col)) {
         TP++;
         }
         }

         MySen += dca.getSensitivity();
         MySpec += dca.getSpecficity();
         RasaSen += rasa.getSensitivity();
         RasaSpec += rasa.getSpecficity();
         MyMCC += dca.getMCC();
         RasaMCC += rasa.getMCC();
         System.out.println();
         System.out.println(ProtID+" Myre TP TN FP FN: " + dca.getTP() + "\t" + dca.getTN() + "\t" + dca.getFP() + "\t" + dca.getFN());
         System.out.println(ProtID +" Rasa TP TN FP FN: " + rasa.getTP() + "\t" + rasa.getTN() + "\t" + rasa.getFP() + "\t" + rasa.getFN());
         //            System.out.println("My Sen / Specf / MCC: " + dca.getSensitivity() + "\t" + dca.getSpecficity()+"\t"+dca.getMCC());
         //            System.out.println("Rasa Sen / Specf / MCC: " + rasa.getSensitivity() + "\t" + rasa.getSpecficity()+"\t"+rasa.getMCC());
         }

         int size = lst.size();
         System.out.println();
         System.out.println("MyRe Total TP TN FP FN: " + MyGen.getTP() + "\t" + MyGen.getTN() + "\t" + MyGen.getFP() + "\t" + MyGen.getFN());
         System.out.println("Rasa Total TP TN FP FN: " + RasaGen.getTP() + "\t" + RasaGen.getTN() + "\t" + RasaGen.getFP() + "\t" + RasaGen.getFN());
         //        System.out.println("My Total Sen / Specf / MCC: " + MySen/size + "\t" + MySpec/size+"\t"+MyMCC/size);
         //        System.out.println("Rasa Total Sen / Specf / MCC: " + RasaSen/size + "\t" + RasaSpec/size+"\t"+RasaMCC/size);
         System.out.println("TP: " + TP);
         //*/
        ////////////////////////////////////////////////////////////////

        // Test Contact residue pairs in one protein chain.
        /*
         String Dir2ThreeDim = "Input/Zellner_Homodimer/3D/";
         String Dir2DCAScore = "Input/Zellner_Homodimer/MSAMethod/HHblits/nML/";
         String Dir2RASA = "Input/Zellner_Homodimer/Rasa/";

         String Path2List = "Input/Zellner_Homodimer/List.txt";
         String EndFile3D = StaticMethod.FindEndName(Dir2ThreeDim);
         String EndFileDCAScore = StaticMethod.FindEndName(Dir2DCAScore);
         String EndFileRasa = StaticMethod.FindEndName(Dir2RASA);

         ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
         int P = 0, PP = 0, TP = 0;
         int num = 40, neighbor = 7;
         for (NewProteinComplexSkeleton p : lst) {

         String ProtID = p.getProtPDBID();
         String chain1 = p.getChainID1();
         String Path2ThreeDimFile = Dir2ThreeDim + ProtID + "_" + chain1 + EndFile3D;
         String Path2DCAFile = Dir2DCAScore + ProtID+"_"+chain1 + EndFileDCAScore;
         String Path2RasaFile = Dir2RASA + ProtID + "_" + chain1 + EndFileRasa;

         //            NewProtein_Pairwise_ScoreRef ref = new NewProtein_Pairwise_ScoreRef(Path2ThreeDimFile, 8.5);
         NewProtein_Pairwise_ScoreRef ref = new NewProtein_Pairwise_ScoreRef(Path2ThreeDimFile, Path2RasaFile, 8.5);

         NewProtein_PairwiseScore prot = new NewProtein_PairwiseScore(neighbor, ProtID, chain1);
         ArrayList<ColPair_Score> LstScore = MyIO.read2ColPair(Path2DCAFile, "\\s+");

         P += ref.countPairContact(neighbor, true);
         num = ref.countPairContact(neighbor, true);
         PP += num;


                     List<ColPair_Score> LstCandidate = prot.topNumber(ref, LstScore, true, true, num);
//         List<ColPair_Score> LstCandidate = prot.randomPickUp(LstScore, num, true, ref);


         for (ColPair_Score col : LstCandidate) {
         if (ref.isPairOnInterfaceOrContact(col)) {
         TP++;
         }
         }
         }
         System.out.println("Recall: " + (double) TP / P);
         System.out.println("Precision: " + (double) TP / PP);
         //*/

        ////////////////////////////////////////////////////////////////////////////////////

        // sorted distances among amino acids from 2 protein chains
        /*
         String Dir2ThreeDim = "Input/Zellner_Homodimer_5/3D/";
         String Dir2SortedDistance = "Input/Zellner_Homodimer_5/SortedDistance/";
         String Path2List = "Input/Zellner_Homodimer_5/List.txt";
         String EndFile3D = StaticMethod.FindEndName(Dir2ThreeDim);
        
         ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
         for(NewProteinComplexSkeleton p: lst){
         String Path2ThreeDimFile = Dir2ThreeDim + p.getProteinComplex()+EndFile3D;
         ArrayList<ColPair_Score> LstScore = MyIO.read2ColPair(Path2ThreeDimFile, "\\s+");
         Collections.sort(LstScore);
         MyIO.WriteLstToFile(Dir2SortedDistance+p.getProteinComplex()+".3d", LstScore);
         }
         //*/

        //////////////////////////////////////////////////////////////////

        // find the leading total score residue
         /*
        String Dir2ThreeDim = "Input/Zellner_Homodimer/3D/";
        String Dir2DCAScore = "Input/Zellner_Homodimer/MSAMethod/HHblits/DCA/";
        String Dir2RASA = "Input/Zellner_Homodimer/Rasa/";
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";

        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String EndFile3D = StaticMethod.FindEndName(Dir2ThreeDim);
        String EndFileDCAScore = StaticMethod.FindEndName(Dir2DCAScore);
        String EndFileRasa = StaticMethod.FindEndName(Dir2RASA);
        String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);

        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        int neighbor = 11;
        for (NewProteinComplexSkeleton p : lst) {
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

            List<ColPair_Score> LstCandidate = prot.topAll(ref, LstPairScore, true, true);
            ArrayList<Residue> Lst_Res = StaticMethod.toLstResidue(LstCandidate);
            Collections.sort(Lst_Res);
            Residue Best = Lst_Res.get(Lst_Res.size()-1);
            System.out.println(ProtID + ":\t"+Best.getResidueNumber()+"\t"+ ref.isOnInterface(Best));
            
        }
        //*/ 
        
        HashSet<ColPair> Set1 = new HashSet<>();
        HashSet<ColPair> Set2 = new HashSet<>();
        for(int i=1; i<5; i++){
            for(int j=i+1; j<6; j++){
                Set1.add(new ColPair(String.valueOf(i), String.valueOf(j)));
//                System.out.println("Add to Set1: "+ i + "\t"+ j);
            }
        }
        
       
        for(int i=0; i<7; i++){
            for(int j=i+1; j<8; j++){
                Set2.add(new ColPair(String.valueOf(i), String.valueOf(j)));
//                System.out.println("Add to Set2: "+ i + "\t"+ j);
            }
        }
        System.out.println("Set1: "+ Set1.size());
        System.out.println("Set2: "+ Set2.size());
        Set1.retainAll(Set2);
        System.out.println("Intersection: "+ Set1.size());
        
    }
}
