/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import DCA.MSA_FloatMatrix;
import DCA.ThreadDCA;
import MultipleCore.MyThread;
import Common.Configuration;
import Common.FastaSequence;
import Common.StaticMethod;
import DCA.MSA_JamaMatrix;
import HomodimaProtein.Complex;
import LinearAlgebra.MyOwnFloatMatrix;
import MarkovCluster.Algorithm;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class TestAlgorithmDCA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        
        
        String DirMSAFile = Configuration.DirTest_MSA;
        String DirOut_DCAFile = Configuration.DirTest_DCA;
        
        // Run 1 MSA_Matrix
        ///*
        int num_cpu = 1;
        List<String> lst_tmp = utils.Utils.dir2list(DirMSAFile);
        ArrayList<String> lst_filename = new ArrayList<>();
        lst_filename.addAll(lst_tmp);
        
        ArrayList<MSA_FloatMatrix> lst_MSA = new ArrayList<MSA_FloatMatrix>();
        for(String s: lst_filename){
            MSA_FloatMatrix m = new MSA_FloatMatrix(DirMSAFile, s);
            m.setAlgnMx(Complex.refineMSA(m.getAlgnMx(), 10));
            lst_MSA.add(m);
        }
        ArrayList<ArrayList<MSA_FloatMatrix>> arr_MSA = StaticMethod.Divide(lst_MSA, num_cpu);
        for(int k=0; k<arr_MSA.size(); k++){
            ThreadDCA dca = new ThreadDCA(arr_MSA.get(k), DirOut_DCAFile);
            MyThread th = new MyThread(dca);
            th.start();
        } //*/
        // Run 1 MSA_FloatMatrix // end
        
        // Compare 2 Matrixes
//        String filename = "10MH_A.invC";
//        String file1 = Configuration.DirTest_MatFile + filename; 
//        String file2 = Configuration.DirTest_DCA + filename;
//        System.out.println("result: "+StaticMethod.CompareFiles(file1, file2));
        
        
        // hon hop nho nhat
//        FastaSequence f = new FastaSequence(Configuration.DirTest_MSA+"10MH_A.msa");
//        System.out.println(f.getAllSequence().size());
        
        // test MyOwnFloatMatrix
//        float[][] a = new float[][]{{1,1,1,1}, 
//            {1,1,0,1}, 
//            {1,0,1,0},
//            {1,1,0,1}};
//        MyOwnFloatMatrix m = new MyOwnFloatMatrix(a);
//        m = Algorithm.process(m);
//        m.print2Screen();
        
    }
}
