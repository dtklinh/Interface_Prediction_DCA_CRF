/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Common.MyIO;
import StaticMethods.DistanceCalc;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class CalcThreeDim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here
        String Path2PDB = "Input/SmallSet/PDB/1a70.pdb";
        List<Group> Lst = ProteinIO.readGroups(Path2PDB, "A");
        ArrayList<String> Arr = new ArrayList<>();
//        HashMap<Integer,String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2PDB, "A", 0);
        for(int i=0; i<Lst.size()-1;i++){
            Group g1 = Lst.get(i);
            String P1 = g1.getResidueNumber().toString();
            for(int j=i+1; j<Lst.size();j++){
                Group g2 = Lst.get(j);
                String P2 = g2.getResidueNumber().toString();
                double dis = DistanceCalc.DistanceBtwGroups(g1, g2);
                String str = P1+"\t"+P2+"\t"+dis;
                Arr.add(str);
            }
        }
        MyIO.WriteToFile("Input/SmallSet/3D/1a70.3d_CA_Vdw", Arr);
    }
}
