/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.ColPair_Score;
import Common.Configuration;
import Protein.Protein_PairwiseScore;
import Drawing.ChartPanel;
import Drawing.MyDraw;
import Common.StaticMethod;
import LinearAlgebra.MyOwnFloatMatrix;
import LinearAlgebra.MyOwnMatrix;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math.stat.StatUtils;
import org.apache.commons.math.stat.correlation.Covariance;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;
import utils.Utils;

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
//        String Path2Folder = "Input/Magnus_DB/Magnus_DCA/";
//        String filename = "1A70_A";
//        Protein_PairwiseScore prot = new Protein_PairwiseScore(Path2Folder, filename, 4);
//        prot.EliminateNeighbor();
//        prot.Sort();
//        prot.AdjustIndex("Input/Magnus_DB/Magnus_PDB_SingleChain/");
//        List<ColPair_Score> lst = prot.TopNumber(20);
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
        
        
        /// Randomly pick up 20 protein chains in Homo Dilema (Zellner data set)
//        String Dir2Seq = "Input/Zellner_DB55/Zellner_Sequence/";
//        String Dir2PDB = "Input/Zellner_DB55/Zellner_PDB/";
//        String Dir2Seq_out = "Input/Zellner_DB20/Zellner_Sequence/";
//        String Dir2PDB_out = "Input/Zellner_DB20/Zellner_PDB/";
//        int num = 20;
//        String endfile_Seq = StaticMethod.FindEndName(Dir2Seq);
//        String endfile_PDB = StaticMethod.FindEndName(Dir2PDB);
//        
//        List<String> lst_tmp = utils.Utils.dir2list(Dir2PDB);
//        ArrayList<String> lst = new ArrayList<>();
//        boolean had = false;
//        for(String s: lst_tmp){
//            for(String str: lst){
//                if(str.indexOf(s.substring(0, 4))>=0){
//                    had = true;
//                    break;
//                }
//            }
//            if(!had){
//                lst.add(s);
//                
//            }
//            had = false;
//        }
//        Iterator<String> iter = lst.iterator();
//        while(iter.hasNext()){
//            String v = iter.next();
//            Structure str = (new PDBFileReader()).getStructure(Dir2PDB+v);
//            Chain c = str.getChain(0);
//            if(c.getAtomSequence().length()>400){
//                iter.remove();
//            }
//        }
//        Collections.shuffle(lst); Collections.shuffle(lst);
//        for(int i=0; i<num;i++){
//            String name = lst.get(i).substring(0, 6);
//            File source_seq = new File(Dir2Seq+name+endfile_Seq);
//            File source_pdb = new File(Dir2PDB+name+endfile_PDB);
//            File des_seq = new File(Dir2Seq_out+name+".txt");
//            File des_pdb = new File(Dir2PDB_out+name+".pdb");
//            
//            FileUtils.copyFile(source_seq, des_seq);
//            FileUtils.copyFile(source_pdb, des_pdb);
//        }
        
        //end
//        int len = 1000*1000*21*21;
//        double[] a = new double[len];
//        double[][][][] d = new double[1000][1000][21][21];
//        double[][] A = new double[][]{{1,2,3},{4,5,6},{7,8,14}};
//        MyOwnMatrix m = new MyOwnMatrix(A);
//        m = m.inverseMyOwnMatrix();
//        m.print2Screen();
        
//        List<String> lst = Utils.dir2list(Configuration.Dir2PDB);
//        for(String s: lst){
//            File f = new File(Configuration.Dir2PDB+s);
//            File f2 = new File(Configuration.Dir2PDB+s.toLowerCase());
//            if(f2.exists()) throw new java.io.IOException("file exists");
//            f.renameTo(f2);
//        }
        
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> vals = new ArrayList<>();
        vals.add("one"); vals.add("ein");
        map.put("mot", vals);
        System.out.println(map);
        vals = map.get("mot");
        vals.add("mot");
        
        System.out.println(map);
    }
}
