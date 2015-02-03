/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class StaticMethod {

    public static void WriteFileName(String dir, String fileout) throws IOException {
        List<String> lst = utils.Utils.dir2list(dir);
        ArrayList<String> ProtName = new ArrayList<String>();
        ArrayList<String> ProtNameChain = new ArrayList<String>();
        for (String s : lst) {
            s = s.trim();
            String name = s.substring(0, 4);
            if (!ProtName.contains(name)) {
                ProtName.add(name);
                ProtNameChain.add(s);
            }
        }
        MyIO.WriteToFile(fileout, ProtNameChain);
    }

    public static <T> ArrayList<ArrayList<T>> Divide(ArrayList<T> lst, int n) {
        ArrayList<ArrayList<T>> res = new ArrayList<>();

        int max = (int) Math.ceil((double) lst.size() / n);
//        int max = lst.size()/n;

        boolean mknew = true;
        for (int i = 0; i < n - 1; i++) {
            List<T> tmp = lst.subList(max*i, max*(i+1));
            
            res.add(new ArrayList<>(tmp));
        }
        List<T> tmp = lst.subList(max*(n-1), lst.size());
        res.add(new ArrayList<>(tmp));

//        for (T s : lst) {
//            if (mknew) {
//                tmp = new ArrayList<T>();
//                tmp.add(s);
//                mknew = false;
//                if(tmp.size()==max){
//                    mknew = true;
//                }
//            } else {
//                tmp.add(s);
////                if(tmp.size()==max || lst.indexOf(s)==(lst.size()-1)){
//                if(tmp.size()==max){
//                    res.add(tmp);
//                    mknew = true;
//                }
//            }
//            if(!mknew && lst.indexOf(s)==(lst.size()-1)){
//                res.add(tmp);
//            }
//        }

        return res;
    }
    public static boolean CompareFiles(String file1, String file2) throws FileNotFoundException, IOException{
        // Reader 1
        FileInputStream fstream1 = new FileInputStream(file1);
        DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
        
        // reader 2
        FileInputStream fstream2 = new FileInputStream(file2);
        DataInputStream in2 = new DataInputStream(fstream2);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
        
        int idx=0;
        double epsilon = 0.0025;
        boolean res = true;
        while(true){
            String str1 = br1.readLine();
            String str2 = br2.readLine();
            if(str1==null || str2==null){
                break;
            }
            str1 = str1.trim(); str2 = str2.trim();
            
            // parse str1, str2
            double[] d1 = ParseVector(str1);
            double[] d2 = ParseVector(str2);
            
            // compare
            if(d1.length!=d2.length){
                System.err.println("Different lenght in vector");
                System.exit(1);
            }
            for(int i=0; i<d1.length; i++){
                if(Math.abs(d1[i]-d2[i])>epsilon){
                    System.err.println("Does not fit. i: "+idx+"\tj: "+i
                            +"\n Value1: "+d1[i]+"\tValue2: "+d2[i]);
                    res = false;
                }
            }
            idx++;
        }
        br1.close();
        br2.close();
        return res;
    }
    private static double[] ParseVector(String str){
        String[] v = str.split("\\s+");
        int len = v.length;
        double[] res = new double[len];
        for(int i=0; i<len; i++){
            res[i] = Double.parseDouble(v[i]);
        }
        return res;
    }
}
