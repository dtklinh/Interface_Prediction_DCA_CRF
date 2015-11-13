/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FifthJuneTraining;

import Common.ColPairAndScores.ColPair_Score;
import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import StaticMethods.ProteinCalc;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class CalculateBestPairOnSurface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        // Calculate all pairs on surface and pickup the best 50.
        int num = 5;
        String Path2List = "Input/Zellner_Homodimer/LL.txt";
        String Dir2_plmdca = "Input/Zellner_Homodimer/MSAMethod/Jackhmmer/mat/";
        String Endfile_plmdca = StaticMethod.FindEndName(Dir2_plmdca);
        String Dir2_3D = "Input/Zellner_Homodimer/3D/";
        String Endfile_3D = ".3d_Any_VdW";
        String Dir2Rasa = "Input/Zellner_Homodimer/Rasa/";
        String EndfileRasa = StaticMethod.FindEndName(Dir2Rasa);
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndfilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton s: lst){
            String ProtID = s.getProtPDBID();
            String chain1 = s.getChainID1();
            String Path2_plmdca = Dir2_plmdca + ProtID + Endfile_plmdca;
            String Path2Rasa = Dir2Rasa + ProtID + "_" + chain1 + EndfileRasa;
            String Path2PDB = Dir2PDB + ProtID + EndfilePDB;
            
            HashMap<String, Double> MapRasa = ProteinIO.readRASAFile(Path2Rasa);
            ArrayList<ColPair_Score> Lst_Score = ProteinIO.readColPairScore2ArrayList(Path2_plmdca, "\\s+");
            HashMap<String, Integer> MapFromRes2Idx = ProteinCalc.getMapResNum2Idx(Path2PDB, chain1, 1);
            
            double Rasa_thres = Configuration.RASA_Thres;
            Iterator<ColPair_Score> iter = Lst_Score.iterator();
            while(iter.hasNext()){
                ColPair_Score c = iter.next();
                String p1 = c.getP1();
                String p2 = c.getP2();
                if(MapRasa.get(p1)<Rasa_thres || MapRasa.get(p2)<Rasa_thres){
                    iter.remove();
                }
            }
            Collections.sort(Lst_Score);
            Collections.reverse(Lst_Score);
            System.out.println(ProtID);
            for(int i = 0; i<num; i++){
                ColPair_Score c = Lst_Score.get(i);
                System.out.println(MapFromRes2Idx.get(c.getP1())+"\t"+MapFromRes2Idx.get(c.getP2()));
            }
        }
    }
}
