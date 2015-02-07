/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import DCA.ColPair_Score;
import DCA.Protein_DCAScore;
import Drawing.ChartPanel;
import Drawing.MyDraw;
import Common.StaticMethod;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.commons.math.stat.StatUtils;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        // test for divide a list into several sublist
//        List<String> lst = utils.Utils.file2list("Input/LstZellnerFile.txt");
//        ArrayList<String> arr = new ArrayList<>();
//        arr.addAll(lst);
//        int n = 6;
//        ArrayList<ArrayList<String>> lst_arr = StaticMethod.Divide(arr, n);
//        for (ArrayList<String> s : lst_arr) {
//            for (String ss : s) {
//                System.out.print(ss + " ");
//            }
//            System.out.println(s.size());
//        }
        // test for divide a list into several sublist // end
        
        // test for DCA
//        String Path2Folder = "Output/Zellner_BLAST/DCAFile/";
//        String filename = "1a4i_A";
//        Protein_DCAScore prot = new Protein_DCAScore(Path2Folder, filename);
//        prot.EliminateNeighbor();
//        prot.Sort();
//        prot.AdjustIndex("Input/Zellner_PDB/");
//        List<ColPair_Score> lst = prot.TopPercent(0.01);
//        for(ColPair_Score s: lst){
//            s.Print2Screen();
//        }
        // test for DCA // end
        
        // test Common Math
        
//        double[] a = new double[]{1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d};
//        ArrayList<Double> lst = new ArrayList<>();
//        Random r = new Random();
//        for(int i=0; i<100;i++){ 
//            lst.add(r.nextDouble());
//        }
//        Collections.sort(lst);
//        MyDraw dr = new MyDraw("My Draw", lst);
//        dr.PaintJFrame();
        // test Common Math // end
        
//        String Dir = "Input/Magnus_DB/Magnus_PDB_SingleChain/";
//        List<String> lst = utils.Utils.dir2list(Dir);
//        for(String s: lst){
//            Structure struc = (new PDBFileReader()).getStructure(Dir+s);
//            Chain c = struc.getChain(0);
//            System.out.println(c.getAtomSequence());
//        }
    }
}
