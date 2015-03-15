/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLAST;

import Common.Configuration;
import Common.FastaSequence;
import Common.MyIO;
import Common.StaticMethod;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class MappingUniProtService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        // TODO code application logic here
        // get all GIs number in BLAST output file
        ///*
        List<String> lst = utils.Utils.dir2list(Configuration.DirTest_MSA);
        HashSet<String> Lst_GI = new HashSet<>();
        String endfileBLAST = StaticMethod.FindEndName(Configuration.DirTest_MSA);
        for (String s : lst) {
            String path2file = Configuration.DirTest_MSA+ s;
            FastaSequence f = new FastaSequence(path2file);
            HashSet<String> uniprot = f.getAllUniprotID2Set();
            Lst_GI.addAll(uniprot);
        }
        
        MyIO.WriteToFile("Test/Hetedima/MSA-Methods/HHblits/Uniprot.txt", Lst_GI);
//        ArrayList<ArrayList<String>> lst_arr = StaticMethod.Divide(tmp, 32);
//        for (int i = 0; i < 32; i++) {
//            MyIO.WriteToFile(Configuration.Dir2DataBase + Configuration.MethodMakingMSA + "GI/GIs_"+i+".txt", lst_arr.get(i));
//        }
        //*/
        // end

//        String Path2File = Configuration.Dir2DataBase + Configuration.MethodMakingMSA + "GIs.txt";
//        List<String> content = utils.Utils.file2list(Path2File);
//        utils.Utils.chkdir(Configuration.TmpDir+"GI/");
//        utils.Utils.chkdir(Configuration.TmpDir+"MAP/");
//        int n = 226;
//        ArrayList<ArrayList<String>> arr = StaticMethod.Divide(content, n);
//        for(int i=0; i<n; i++){
//            String filename = Configuration.TmpDir+"GI/"+i+".txt";
//            MyIO.WriteToFile(filename, arr.get(i));
//            Thread.sleep(1000);
//            String fullpath2GI = Configuration.HomeProject+filename;
//            String fullpath2Map = Configuration.HomeProject + Configuration.TmpDir + "MAP/"+i + ".map";
//            String cmd = "uniprot map P_GI ID "+fullpath2GI + fullpath2Map;
//            utils.Utils.tic();
//            Runtime rt = Runtime.getRuntime();
//            Process pr = rt.exec(cmd);
//            pr.waitFor();
//            pr.destroy();
//            rt.freeMemory();
//            utils.Utils.tac();
//        }
//        List<String> lst_gi = utils.Utils.dir2list("Tmp/GI/");
//        List<String> Done = utils.Utils.dir2list("Tmp/MAP/");
//        for (int i = lst_gi.size() - 1; i >= 0; i--) {
//            String s = lst_gi.get(i);
//            if (Done.contains(s)) {
//                lst_gi.remove(i);
//            }
//        }
//        
//        for(String s: lst_gi){
//            String fullpath2GI = Configuration.HomeProject+"Tmp/GI/"+s;
//            String fullpath2MAP = Configuration.HomeProject+"Tmp/MAP/"+s;
//            String cmd = "uniprot map P_GI ID "+fullpath2GI +" "+ fullpath2MAP;
//            utils.Utils.tic();
//            Runtime rt = Runtime.getRuntime();
//            Process pr = rt.exec(cmd);
//            pr.waitFor();
//            System.out.println("Finish "+s);
//            pr.destroy();
//            rt.freeMemory();
//            utils.Utils.tac();
//        }
//        List<String> lst = utils.Utils.dir2list(Configuration.TmpDir+"MAP/");
//        ArrayList<String> lines = new ArrayList<>();
//        for(String s: lst){
//            List<String> f = utils.Utils.file2list(Configuration.TmpDir+"MAP/"+s);
//            f.remove(0);
//            lines.addAll(f);
//        }
//        MyIO.WriteToFile(Configuration.Dir2DataBase+Configuration.MethodMakingMSA+"GI_Uniprot.map", lines);
        
    }
}
