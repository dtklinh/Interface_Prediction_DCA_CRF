/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class Interface_Prediction_DCA_CRF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        
        // write protein sequence to file
        /*
        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String Dir2Sequence = "Input/Zellner_Homodimer/Sequence/";
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton p: lst){
            String ProtID = p.getProtPDBID();
            String chain1 = p.getChainID1();
            String Path2PDBFile = Dir2PDB + ProtID + EndFilePDB;
            Chain C = (new PDBFileReader()).getStructure(Path2PDBFile).getChainByPDB(chain1);
            String Seq = C.getAtomSequence();
            HashMap<Integer, String> map = StaticMethod.getMapIdx2ResNum(Path2PDBFile, chain1, 0);
            String Des = ">"+ProtID+"/"+map.get(0) + "-"+map.get(map.size()-1);
            ArrayList<String> arr = new ArrayList<>();
            arr.add(Des);
            arr.add(Seq);
            MyIO.WriteToFile(Dir2Sequence+ProtID+".txt", arr);
        }
        */
        
        // sort score of ColPair-Score
        String Path2List = "Input/Zellner_Homodimer/LL.txt";
        String Dir2ScoreFile = "Input/Zellner_Homodimer/MSAMethod/HHblits/plmdca_complex/";
        String EndFileScore = StaticMethod.FindEndName(Dir2ScoreFile);
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String Path2ScoreFile = Dir2ScoreFile + ProtID + EndFileScore;
            ArrayList<ColPair_Score> Scores = MyIO.read2ColPair(Path2ScoreFile, "\\s+");
            Collections.sort(Scores);
            Collections.reverse(Scores);
            MyIO.WriteLstToFile(Path2ScoreFile, Scores);
        }
    }
}
