/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Analysis.ThresholdClassifier;
import Common.ColPair_MegaScore;
import StaticMethods.LookUpCollections;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class CalcAUCMultipleVertex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here


        String Path2MegaFile = "Input/SmallSet/Mega/1A70_CMI_plm_3D_Gremlin.txt";
        String Path2PDB = "Input/SmallSet/PDB/1a70.pdb";
        HashMap<Integer, String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2PDB, "A", 0);
        String Vertex = null;
        List<ColPair_MegaScore> Lst = ProteinIO.readColPairMegaScore2List(Path2MegaFile, "\\s+",
                "mi", "dca", "three", "gremlin");

        double d3_thres = 4.0;
        List<Double> LstPos = new ArrayList<>();
        List<Double> LstNeg = new ArrayList<>();


        double AverageArea = 0;
        for (int i = 0; i < MapIdx2ResNum.size(); i++) {
            Vertex = MapIdx2ResNum.get(i);
            List<ColPair_MegaScore> OneVertex = LookUpCollections.LookUpAtOneVertex(Lst, Vertex);

            for (ColPair_MegaScore c : OneVertex) {
                if (c.getThreeDimDis() <= d3_thres) {
                LstPos.add(c.getCondMIScore());
//                LstPos.add(c.getPlmdcaScore());
//                    LstPos.add(c.getGremlinScore());
                } else {
                LstNeg.add(c.getCondMIScore());
//                LstNeg.add(c.getPlmdcaScore());
//                    LstNeg.add(c.getGremlinScore());
                }
            }
            ThresholdClassifier cl = new ThresholdClassifier(LstPos, LstNeg);
            double area = cl.calcAUC();
            AverageArea += area;
            System.out.println("Node " + Vertex + ": Area: " + area);
            LstNeg.clear();
            LstPos.clear();
        }
        System.out.println("Average area: "+ AverageArea/MapIdx2ResNum.size());













        /*
         String Path2MegaFile = "Input/SmallSet/Mega/1A70_CMI_plm_3D_Gremlin.txt";
         String Path2PDB = "Input/SmallSet/PDB/1a70.pdb";
         HashMap<Integer, String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2PDB, "A", 0);
         String Vertex = "28";
         List<ColPair_MegaScore> Lst = ProteinIO.readColPairMegaScore2List(Path2MegaFile, "\\s+",
         "mi","dca", "three", "gremlin");
        
         double d3_thres = 4.0;
         List<Double> LstPos = new ArrayList<>();
         List<Double> LstNeg = new ArrayList<>();
         double AverageArea = 0;
         for (int i = 0; i < MapIdx2ResNum.size(); i++) {
         Vertex = MapIdx2ResNum.get(i);
         List<ColPair_MegaScore> OneVertex = LookUpCollections.LookUpAtOneVertex(Lst, Vertex);
         //            List<ColPair_MegaScore> OneVertex = Lst;
         for (ColPair_MegaScore c : OneVertex) {
         if (c.getThreeDimDis() <= d3_thres) {
         LstPos.add(c.getCondMIScore());
         //                LstPos.add(c.getPlmdcaScore());
         //                LstPos.add(c.getGremlinScore());
         } else {
         LstNeg.add(c.getCondMIScore());
         //                LstNeg.add(c.getPlmdcaScore());
         //                LstNeg.add(c.getGremlinScore());
         }
         }
         ThresholdClassifier cl = new ThresholdClassifier(LstPos, LstNeg);
         double area = cl.calcAUC();
         System.out.println("Node "+Vertex+": Area: "+area);
         AverageArea += area;
         //            break;
         }
         System.out.println("Average Area: " + (AverageArea/97));
         */
    }
}
