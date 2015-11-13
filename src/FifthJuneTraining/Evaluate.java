/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FifthJuneTraining;

import Analysis.GeneralResult;
import Common.ColPairAndScores.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.biojava.bio.structure.StructureException;
import InformationTheory.ITUtils;
import Protein.NewProtein_Pairwise_ScoreRef;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author t.dang
 */
public class Evaluate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndfilePDB = StaticMethod.FindEndName(Dir2PDB);
        String Dir2_3D = "Input/Zellner_Homodimer/3D/";
        String Endfile3D = ".3d_Any_VdW";
        String Dir2Rasa = "Input/Zellner_Homodimer/Rasa/";
        String EndfileRasa = StaticMethod.FindEndName(Dir2Rasa);
        
        double thres_Interface = 4.5;

        ArrayList<NewProteinComplexSkeleton> LstTrain = new ArrayList<>();
        ArrayList<NewProteinComplexSkeleton> LstTest = new ArrayList<>();
        LstTrain.add(new NewProteinComplexSkeleton("1eq9", "A", "B"));
        LstTrain.add(new NewProteinComplexSkeleton("1g2q", "A", "B"));
        LstTest.add(new NewProteinComplexSkeleton("1jxh", "A", "B"));

        String Dir2DirDis = "Input/Zellner_Homodimer/DirDis/";
        String EndfileDirDis = StaticMethod.FindEndName(Dir2DirDis);

        ArrayList<DirectDistribution> Train = new ArrayList<>();
        ArrayList<DirectDistribution> Test = new ArrayList<>();
        for (NewProteinComplexSkeleton s : LstTrain) {
            String Path2PDB = Dir2PDB + s.getProtPDBID() + EndfilePDB;
            HashMap<Integer, String> MapIdx2Res = ProteinCalc.getMapIdx2ResNum(Path2PDB, s.getChainID1(), 1);

            String Path2DirDis = Dir2DirDis + s.getProtPDBID() + EndfileDirDis;
            ArrayList<String> arr = MyIO.ReadLines(Path2DirDis);
            Train.addAll(getDirDis(MapIdx2Res, arr));
        }

        for (NewProteinComplexSkeleton s : LstTest) {
            Test.clear();
            String Path2PDB = Dir2PDB + s.getProtPDBID() + EndfilePDB;
            String Path2_3D = Dir2_3D + s.getProteinComplex() + Endfile3D;
            String Path2Rasa = Dir2Rasa + s.getProteinChain(1) + EndfileRasa;
            HashMap<String, Double> MapRasaChain1 = ProteinIO.readRASAFile(Path2Rasa);
            HashMap<Integer, String> MapIdx2Res = ProteinCalc.getMapIdx2ResNum(Path2PDB, s.getChainID1(), 1);

            String Path2DirDis = Dir2DirDis + s.getProtPDBID() + EndfileDirDis;
            ArrayList<String> arr = MyIO.ReadLines(Path2DirDis);
            Test.addAll(getDirDis(MapIdx2Res, arr));

            List<ColPair_Score> lst = getTop(Train, Test, 50);
            for (int i = 0; i < lst.size(); i++) {
                System.out.println(s.getProtPDBID()+" : "+lst.get(i).getP1() + "\t" + lst.get(i).getP2() + "\t" + lst.get(i).getScore());
            }
            // evaluate
            HashSet<String> All = MyIO.readInterfaceResidue(Path2_3D, 0, 10000, true);
            HashSet<String> Interfaces = MyIO.readInterfaceResidue(Path2_3D, 0, thres_Interface, true);
            HashSet<String> Pred = MyIO.readPreditedInterfaceResidue(lst, 0, 10, true);
            HashSet<String> Rnd = StaticMethod.randomPickUp(All, Pred.size(), MapRasaChain1);
            GeneralResult r = NewProtein_Pairwise_ScoreRef.processResult(All, Interfaces, Rnd);
            r.PrintResult(s.getProtPDBID());
        }

    }

    public static ArrayList<DirectDistribution> getDirDis(HashMap<Integer, String> map, ArrayList<String> lines) {
        ArrayList<DirectDistribution> res = new ArrayList<>();
        for (String s : lines) {
            s = s.trim();
            String[] arr = s.split(",");
            String p1 = map.get(Integer.parseInt(arr[0]));
            String p2 = map.get(Integer.parseInt(arr[1]));
            double[] value = new double[arr.length - 2];
            for (int i = 0; i < value.length; i++) {
                value[i] = Double.parseDouble(arr[i + 2]);
            }
            res.add(new DirectDistribution(p1, p2, value));
        }
        return res;
    }

    public static double getClosestDis(ArrayList<DirectDistribution> lst, DirectDistribution d) {
        double min = 100000;
        for (DirectDistribution other : lst) {
            double distance = InformationTheory.ITUtils.jensenShannonDivergence(d.getValue(), other.getValue());
            if (distance < min) {
                min = distance;
            }
        }
        return min;
    }

    public static List<ColPair_Score> getTop(ArrayList<DirectDistribution> LstTrain,
            ArrayList<DirectDistribution> LstTest, int num) {

        ArrayList<ColPair_Score> res = new ArrayList<>();
        for (DirectDistribution d : LstTest) {
            double score = getClosestDis(LstTrain, d);
//            System.out.println(d.getP1()+"\t"+ d.getP2()+"\t:"+ score);
            res.add(new ColPair_Score(d.getP1(), d.getP2(), score));
        }
        Collections.sort(res);
        return res.subList(0, num);
    }
}
