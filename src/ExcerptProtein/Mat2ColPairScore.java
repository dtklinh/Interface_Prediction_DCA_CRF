/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Common.MyIO;
import StaticMethods.ProteinCalc;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class Mat2ColPairScore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        String Path2File = "Input/SmallSet/dcaSoeding/1hix_A.out";
        String Path2PDB = "Input/SmallSet/PDB/1hix.pdb";
        HashMap<Integer,String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2PDB, "A", 0);
        double[][] mat = MyIO.readMatrix(Path2File);
        ArrayList<String> lst = new ArrayList<>();
        int size = mat.length;
        for(int i=0; i<size-1; i++){
            String P1 = MapIdx2ResNum.get(i);
            for(int j=i+1; j<size;j++){
                String P2 = MapIdx2ResNum.get(j);
                double score = mat[i][j];
                String str = P1 + "\t" + P2 + "\t" + score;
                lst.add(str);
            }
        }
        MyIO.WriteToFile("Input/SmallSet/dcaSoeding/1hix_A.dca", lst);
    }
}
