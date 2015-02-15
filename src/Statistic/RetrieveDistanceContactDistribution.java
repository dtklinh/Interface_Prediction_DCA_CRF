/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic;

import Protein.Protein_Pairwise_Reference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class RetrieveDistanceContactDistribution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String Dir23D = "Input/Magnus_DB/Magnus_3D/AdjustIndex/";
        List<String> lst = utils.Utils.dir2list(Dir23D);
        ArrayList<Integer> res = new ArrayList<>();
        for(String s: lst){
            Protein_Pairwise_Reference ref = new Protein_Pairwise_Reference(Dir23D, s, 2, 5.5);
            res.addAll(ref.getNumDistanceContact());
        }
        HashSet<Integer> hash = new HashSet<>();
        hash.addAll(res);
        for(Integer i: hash){
            int count = Collections.frequency(res, i);
            System.out.println(i + "\t"+count);
        }
    }
}
