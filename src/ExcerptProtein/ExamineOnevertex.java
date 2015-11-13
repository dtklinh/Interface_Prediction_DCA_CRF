/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Common.ColPairAndScores.ColPair_MegaScore;
import StaticMethods.LookUpCollections;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class ExamineOnevertex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        //foo("My", "love", "is");
        String Path2MegaFile = "Input/SmallSet/Excerpt/1A70_CMI_plm_3D_Gremlin.txt";
        String Vertex = "18";
        List<ColPair_MegaScore> Lst = ProteinIO.readColPairMegaScore2List(Path2MegaFile, "\\s+", 
                "mi","dca","three","gremlin");
        List<ColPair_MegaScore> OneVertex = LookUpCollections.LookUpAtOneVertex(Lst, Vertex);
        for(ColPair_MegaScore c:OneVertex){
            if(c.getP1().equalsIgnoreCase(Vertex)){
                System.out.println(c.getP1()+"\t"+c.getP2()+"\t"+c.getThreeDimDis()+"\t"+c.getPlmdcaScore());
            }
            else{
                System.out.println(c.getP2()+"\t"+c.getP1()+"\t"+c.getThreeDimDis()+"\t"+c.getPlmdcaScore());
            }
        }
    }
    
    public static void foo(String str, String... lst){
        for(String s:lst){
            System.out.println(str +" : "+s);
        }
    }
}
