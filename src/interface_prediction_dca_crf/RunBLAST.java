/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import BLAST.BLAST;
import MultipleCore.MyThread;
import Support.StaticMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class RunBLAST {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        System.out.println(Thread.currentThread().getName());
        String path2BLAST = "../../BLAST/ncbi-blast-2.2.30+/bin/psiblast";
        String db = "/home/t.dang/BLAST/ncbi-blastdb/nr";
        int num_iters = 3;
        String pathIn = "Input/Zellner_Sequence";
        String pathOut = "Output/Zellner_BLAST";
        
        List<String> lst = utils.Utils.file2list("Input/LstZellnerFile.txt");
        int cpu = 5;
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        int max = lst.size() / cpu;
        int i = 0;
        boolean mknew = true;
        ArrayList<String> tmp= new ArrayList<String>();
        for (String s : lst) {
            if (mknew) {
                tmp = new ArrayList<String>();
                tmp.add(s);
                mknew = false;
            } else {
                tmp.add(s);
                if(tmp.size()==max){
                    arr.add(tmp);
                    mknew = true;
                }
            }
        }
        
        for(int k=0; k<cpu; k++){
            BLAST b = new BLAST(path2BLAST, arr.get(k), db, pathOut, pathIn, num_iters);
            MyThread th = new MyThread(b);
            th.start();
        }
//        StaticMethod.WriteFileName("Input/Zellner_Sequence/", "Output/LstZellnerFile.txt");
    }
}
