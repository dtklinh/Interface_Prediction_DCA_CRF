/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.ColPairAndScores.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import DCA.DCA;
import DCA.MyIO_DCA;
import DCA.StaticMethod_DCA;
import Protein.NewProteinComplexSkeleton;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class NewCalculateDCA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        String Path2List = "Input/Zellner_Homodimer/List.txt";
        String Dir2MSA = "Input/Zellner_Homodimer/MSAMethod/HHblits/MSA/";
        String Dir2DCAOut = "Input/Zellner_Homodimer/MSAMethod/HHblits/NormalizedDCA/";
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndFileMSA = StaticMethod.FindEndName(Dir2MSA);
        String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton p:lst){
            String protID = p.getProtPDBID();
            String chain1 = p.getChainID1();
            String chain2 = p.getChainID2();
            String Path2MSAFile = Dir2MSA + protID + EndFileMSA;
            String Path2ThreeDimFile = Dir2PDB + protID + EndFilePDB;
            int[][] MxA = MyIO_DCA.ReturnAlignment(Path2MSAFile);
            DCA d = new DCA(MxA);
            HashMap<Integer, String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2ThreeDimFile, chain1, 0);
            ArrayList<ColPair_Score> lstScore = d.GetResult(MapIdx2ResNum);
            ProteinIO.writeColPairScore2File(Dir2DCAOut+protID+".Normalized_dca", lstScore);
            System.out.println("Finish "+protID);
        }
    }
}
