/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Common.ColPairAndScores.ColPair_MegaScore;
import Common.ColPairAndScores.ColPair_Score;
import StaticMethods.ConvertMethod;
import StaticMethods.CorrectionMethod;
import StaticMethods.LookUpCollections;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class MakeAPC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        String Path2PDB = "Input/SmallSet/PDB/1a70.pdb";
        String Path2CMI = "Input/SmallSet/CMI/1A70_apc.cmi";
        HashMap<Integer,String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2PDB, "A", 0);
        HashMap<String,Integer> MapResNum2Idx = ProteinCalc.getMapResNum2Idx(Path2PDB, "A", 0);
        String Path2File = "Input/SmallSet/Mega/1A70_CMI_plm_3D_Gremlin.txt";
        List<ColPair_MegaScore> Lst = ProteinIO.readColPairMegaScore2List(Path2File, "\\s+", "mi","dca","three","gremlin");
        List<ColPair_Score> LstCMI = new ArrayList<>();
        for(ColPair_MegaScore c: Lst){
            LstCMI.add(c.convert2ColPair_Score("mi"));
        }
        double[][] A = ConvertMethod.convertFromColPair2DoubleArray(LstCMI, MapResNum2Idx);
        A = CorrectionMethod.APC(A);
        LstCMI = ConvertMethod.convertDoubleArray2ColPair(A, MapIdx2ResNum);
        ProteinIO.writeColPairScore2File(Path2CMI, LstCMI);
    }
}
