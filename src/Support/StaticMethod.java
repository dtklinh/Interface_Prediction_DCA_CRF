/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import java.io.IOException;
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
}
