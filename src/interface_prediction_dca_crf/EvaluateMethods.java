/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Analysis.GeneralResult;
import Common.ColPair_Score;
import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import DCA.MyIO_DCA;
import Protein.NewProteinComplexSkeleton;
import Protein.NewProtein_PairwiseScore;
import Protein.NewProtein_Pairwise_ScoreRef;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class EvaluateMethods {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        
        String Path2List = "Input/Zellner_Homodimer/LL.txt";
        String Dir2_3D = "Input/Zellner_Homodimer/3D/";
        String Endfile3D = ".3d_Any_VdW";
        String Dir2RASA = "Input/Zellner_Homodimer/Rasa/";
        String EndfileRASA = StaticMethod.FindEndName(Dir2RASA);
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndfilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        String Dir2_NMFA_Score = "Input/Zellner_Homodimer/MSAMethod/HHblits/DCA/";
        String Endfile_NMFA = ".dca";
        String Dir2_NMFA_NMI = "Input/Zellner_Homodimer/MSAMethod/HHblits/NormalizedDCA/";
        String Endfile_NMFA_NMI = StaticMethod.FindEndName(Dir2_NMFA_NMI);
        String Dir2_plmdca = "Input/Zellner_Homodimer/MSAMethod/HHblits/plmdca/";
        String Endfile_plmdca = StaticMethod.FindEndName(Dir2_plmdca);
        String Dir2Gremlin = "Input/Zellner_Homodimer/MSAMethod/HHblits/Gremlin/";
        String Endfile_Gremlin = StaticMethod.FindEndName(Dir2Gremlin);


        ArrayList<NewProteinComplexSkeleton> LstSkeleton = MyIO.readComplexSkeleton(Path2List, "\\s+");


        
        int neighbor = 7;
        double ResContact = 4.5;
        double NMFA_thres = 0.2;
        double NMFA_NMI_thres = 0.1;
        double PLM_thres = 0.15;
        double PLM_NMI_thres = 0.1;
        double Gremlin_thres = 0.04;
        double InterfaceContact = 5.0;

        //
        GeneralResult Result_NMFA = new GeneralResult();
        GeneralResult Result_NMFA_NMI = new GeneralResult();
        GeneralResult Result_plmdca = new GeneralResult();
        GeneralResult Result_Random = new GeneralResult();
        
        // evaluate different methods for residues contact in 1 protein chain (contact map)
        // evaluate the performance of NMFA_DCA and plm_DCA (with/out NMI) for residue contact in one chain
        /*
        for (NewProteinComplexSkeleton s : LstSkeleton) {
            String ProtID = s.getProtPDBID();
            String chain = s.getChainID1();
            String Path2_3D = Dir2_3D + ProtID + "_" + chain + Endfile3D;
            String Path2_NMFA = Dir2_NMFA_Score + ProtID + Endfile_NMFA;
            String Path2_NMFA_NMI = Dir2_NMFA_NMI + ProtID + Endfile_NMFA_NMI;
            String Path2_plmdca = Dir2_plmdca + ProtID + Endfile_plmdca;

            ArrayList<ColPair_Score> Lst3D = MyIO.readColPairScore2ArrayList(Path2_3D, "\\s+");
            ArrayList<ColPair_Score> LstRealContact = StaticMethod.getTopCol(Lst3D, ResContact, neighbor, true);

            ArrayList<ColPair_Score> Lst_NMFA_Score = MyIO.readColPairScore2ArrayList(Path2_NMFA, "\\s+");
            ArrayList<ColPair_Score> Lst_NMFA_Candidate = StaticMethod.getTopCol(Lst_NMFA_Score,
                    NMFA_thres, neighbor, false);

            ArrayList<ColPair_Score> Lst_NMFA_NMI_Score = MyIO.readColPairScore2ArrayList(Path2_NMFA_NMI, "\\s+");
            ArrayList<ColPair_Score> Lst_NMFA_NMI_Candidate = StaticMethod.getTopCol(Lst_NMFA_NMI_Score,
                    NMFA_NMI_thres, neighbor, false);
            
            ArrayList<ColPair_Score> Lst_plmdca_Score = MyIO.readColPairScore2ArrayList(Path2_plmdca, "\\s+");
            ArrayList<ColPair_Score> Lst_plmdca_Candidate = StaticMethod.getTopCol(Lst_plmdca_Score,
                    PLM_thres, neighbor, false);

            HashSet<String> All = StaticMethod.fromColPair2ColSet(Lst3D);
            HashSet<String> RealContact = StaticMethod.fromColPair2ColSet(LstRealContact);
            HashSet<String> NMFA_Prediction = StaticMethod.fromColPair2ColSet(Lst_NMFA_Candidate);
//            System.err.println("Number of predicted Res contact by NMFA: " + NMFA_Prediction.size());
            HashSet<String> NMFA_NMI_Prediction = StaticMethod.fromColPair2ColSet(Lst_NMFA_NMI_Candidate);
//            System.err.println("Number of predicted Res contact by NMFA-NMI: "+NMFA_NMI_Prediction.size());
            HashSet<String> plmdca_Prediction = StaticMethod.fromColPair2ColSet(Lst_plmdca_Candidate);
            System.err.println("Number of predicted Res contact by plmdca: " + plmdca_Prediction.size());


            GeneralResult res = NewProtein_Pairwise_ScoreRef.processResult(All, RealContact, NMFA_Prediction);
            Result_NMFA.addResult(res);

            res = NewProtein_Pairwise_ScoreRef.processResult(All, RealContact, NMFA_NMI_Prediction);
            Result_NMFA_NMI.addResult(res);
            
            res = NewProtein_Pairwise_ScoreRef.processResult(All, RealContact, plmdca_Prediction);
            Result_plmdca.addResult(res);
        }
        Result_NMFA.PrintResult("NMFA");
        Result_NMFA_NMI.PrintResult("NMFA_NMI");
        Result_plmdca.PrintResult("plmdca");
        //*/ 
        
        /// Predict interface (pair of residue on interface)
        /*
        for(NewProteinComplexSkeleton s: LstSkeleton){
            String ProtID = s.getProtPDBID();
            String chain1 = s.getChainID1();
            String chain2 = s.getChainID2();
            String ProtComplx = s.getProteinComplex();
            String Path2_3D = Dir2_3D + ProtComplx + Endfile3D;
            String Path2_plmdca = Dir2_plmdca + ProtID + Endfile_plmdca;
            String Path2PDB = Dir2PDB + ProtID + EndfilePDB;
            String Path2RASA = Dir2RASA + ProtID + "_" + chain1 + EndfileRASA;
            
            ArrayList<ColPair_Score> Lst3D = MyIO.readColPairScore2ArrayList(Path2_3D, "\\s+");
            ArrayList<ColPair_Score> LstInterface = StaticMethod.getTopCol(Lst3D, InterfaceContact, neighbor, true);
            
            ArrayList<ColPair_Score> Lst_plmdcaScore = MyIO.readColPairScore2ArrayList(Path2_plmdca, "\\s+");
            HashMap<String,Integer> MapFromResNum2IdxChain1 = StaticMethod.getMapResNum2Idx(Path2PDB, chain1, 0);
            HashMap<Integer, String> MapFromIdx2ResNumChain2 = StaticMethod.getMapIdx2ResNum(Path2PDB, chain2, 0);
            HashMap<String, Double> Map2RASA = MyIO.readRASAFile(Path2RASA);
            ArrayList<ColPair_Score> Lst_Candidate = processFromOneChain2Complex(Lst_plmdcaScore,
                    Map2RASA, MapFromResNum2IdxChain1, MapFromIdx2ResNumChain2, PLM_thres);
            
            HashSet<String> All = StaticMethod.fromColPair2ColSet(Lst3D, MapFromResNum2IdxChain1, MapFromIdx2ResNumChain2);
            HashSet<String> Interface = StaticMethod.fromColPair2ColSet(LstInterface, MapFromResNum2IdxChain1, MapFromIdx2ResNumChain2);
            HashSet<String> Prediction = StaticMethod.fromColPair2ColSet(Lst_Candidate);
            
            GeneralResult tmp = NewProtein_Pairwise_ScoreRef.processResult(All, Interface, Prediction);
            Result_plmdca.addResult(tmp);
        }
        Result_plmdca.PrintResult("plmdca");
        //*/
        
        
        // evaluate interface prediction based on correlation pair on the surface
        /*
        for(NewProteinComplexSkeleton s: LstSkeleton){
            String ProtID = s.getProtPDBID();
            String chain1 = s.getChainID1();
            String Path2_3D = Dir2_3D + s.getProteinComplex() + Endfile3D;
            String Path2_plmdca = Dir2_plmdca + ProtID + Endfile_plmdca;
            String Path2RASA = Dir2RASA + ProtID + "_" + chain1 + EndfileRASA;
            
            // load reference
            ArrayList<ColPair_Score> Lst3D = MyIO.readColPairScore2ArrayList(Path2_3D, "\\s+");
            HashSet<String> All = StaticMethod.getInterfaceRes(Lst3D, 1, 10000);
            HashSet<String> Interfaces = StaticMethod.getInterfaceRes(Lst3D, 1, InterfaceContact);
            HashMap<String, Double> Map2RASA = MyIO.readRASAFile(Path2RASA);
            
            ArrayList<ColPair_Score> Lst_plmdca_Score = MyIO.readColPairScore2ArrayList(Path2_plmdca, "\\s+");
            HashSet<String> Predictions = StaticMethod.getInterfaceRes(Lst_plmdca_Score, PLM_thres, Map2RASA, neighbor);
            System.err.println("Num predicted interface: "+ Predictions.size());
            HashSet<String> RndPredictions = StaticMethod.randomPickUp(All, Predictions.size(), Map2RASA);
            
            GeneralResult tmp = NewProtein_Pairwise_ScoreRef.processResult(All, Interfaces, Predictions);
//            tmp.PrintResult(ProtID);
            Result_plmdca.addResult(tmp);
            
            tmp = NewProtein_Pairwise_ScoreRef.processResult(All, Interfaces, RndPredictions);
            Result_Random.addResult(tmp);
        }
        Result_plmdca.PrintResult("plmdca");
        Result_Random.PrintResult("Random");
        //*/
        
        
        
        for (NewProteinComplexSkeleton s : LstSkeleton) {
            String ProtID = s.getProtPDBID();
            String ProtCmplx = s.getProteinComplex();
            String Path2_3D = Dir2_3D + ProtCmplx + Endfile3D;
            String Path2Gremlin = Dir2Gremlin + ProtCmplx + Endfile_Gremlin;
            
            
            // load reference
            ArrayList<ColPair_Score> Lst3D = ProteinIO.readColPairScore2ArrayList(Path2_3D, "\\s+");
            HashSet<String> All = ProteinCalc.getInterfaceRes(Lst3D, 1, 10000, true);
            HashSet<String> Interfaces = ProteinCalc.getInterfaceRes(Lst3D, 1, InterfaceContact, true);
//            HashMap<String, Double> Map2RASA = MyIO.readRASAFile(Path2RASA);
            
            ArrayList<ColPair_Score> Lst_gremlin_Score = ProteinIO.readColPairScore2ArrayList(Path2Gremlin, "\\s+");
            HashSet<String> Predictions = ProteinCalc.getInterfaceRes(Lst_gremlin_Score, 1, Gremlin_thres, false);
            
            GeneralResult tmp = NewProtein_Pairwise_ScoreRef.processResult(All, Interfaces, Predictions);
            tmp.PrintResult("Gremlin "+ ProtID);
        }
        
    }
    public static ArrayList<ColPair_Score> processFromOneChain2Complex(ArrayList<ColPair_Score> lst,
           HashMap<String, Double> Map2RASA, HashMap<String,Integer> MapFromChain1, 
           HashMap<Integer, String> Map2Chain2, double Score_thres){
        
        ArrayList<ColPair_Score> res = new ArrayList<>();
        double RASA_Thres = Configuration.RASA_Thres;
        Iterator<ColPair_Score> iter = lst.iterator();
        while(iter.hasNext()){
            ColPair_Score col = iter.next();
            String p1 = col.getP1();
            String p2 = col.getP2();
            double score = col.getScore();
            if(Map2RASA.get(p1)<RASA_Thres || Map2RASA.get(p2)<RASA_Thres){
                iter.remove();
            }
            else if (score>=Score_thres){
                //
                String C1 = p1;
                String C2 = Map2Chain2.get(MapFromChain1.get(p2));
                res.add(new ColPair_Score(C1, C2, score));
                C1 = p2;
                C2 = Map2Chain2.get(MapFromChain1.get(p1));
                res.add(new ColPair_Score(C1, C2, score));
            }
        }
        return res;
    }
}
