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
public class CalcAUCOneVertex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        String Path2MegaFile = "Input/SmallSet/Mega/1A70_CMI_plm_3D_Gremlin.txt";
        String Path2PDB = "Input/SmallSet/PDB/1a70.pdb";
        
        String Vertex = "58";
        List<ColPair_MegaScore> Lst = ProteinIO.readColPairMegaScore2List(Path2MegaFile, "\\s+",
                "mi", "dca", "three", "gremlin");

        double d3_thres = 4.0;
        List<Double> LstPos = new ArrayList<>();
        List<Double> LstNeg = new ArrayList<>();
        

//        List<ColPair_MegaScore> OneVertex = LookUpCollections.LookUpAtOneVertex(Lst, Vertex);
        List<ColPair_MegaScore> OneVertex = Lst;

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
        System.out.println("Node " + Vertex + ": Area: " + area);
        
    }
}
