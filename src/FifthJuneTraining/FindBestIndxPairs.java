/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FifthJuneTraining;

import Common.ColPair_Score;
import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class FindBestIndxPairs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        // we assume that we know complex of heterodimer, we pick up all residues belong to 
        // interface and select the best 5 pairs which have highest DCA score. 
        int num = 10;
        String Path2List = "Input/Zellner_Homodimer/LL.txt";
        String Dir2_plmdca = "Input/Zellner_Homodimer/MSAMethod/Jackhmmer/plm_dca/";
        String Endfile_plmdca = StaticMethod.FindEndName(Dir2_plmdca);
        String Dir2_3D = "Input/Zellner_Homodimer/3D/";
        String Endfile_3D = ".3d_Any_VdW";
        String Dir2Rasa = "Input/Zellner_Homodimer/Rasa/";
        String EndfileRasa = StaticMethod.FindEndName(Dir2Rasa);
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndfilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        Double InterfaceContact = 3.0;
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton s: lst){
            String ProtID = s.getProtPDBID();
            String chain1 = s.getChainID1();
            String chain2 = s.getChainID2();
            String Complex = s.getProteinComplex();
            String Path2_plmdca = Dir2_plmdca + Complex + Endfile_plmdca;
            
            String Path2_3D = Dir2_3D + Complex + Endfile_3D;
            String Path2Rasa1 = Dir2Rasa + ProtID +"_"+ s.getChainID1() + EndfileRasa;
            String Path2Rasa2 = Dir2Rasa + ProtID +"_" + s.getChainID2() + EndfileRasa;
            String Path2PDB = Dir2PDB + ProtID + EndfilePDB;
            
//            HashMap<String, String> MapC2ToC1 = StaticMethod.getMapChain1ToChain2(Path2PDB, chain2, chain1);
            
            ArrayList<ColPair_Score> Lst_Score = MyIO.read2ColPair(Path2_plmdca, "\\s+");
            ArrayList<ColPair_Score> Lst_3D = MyIO.read2ColPair(Path2_3D, "\\s+");
            HashMap<String, Double> MapRasa1 = MyIO.readRASAFile(Path2Rasa1);
            HashMap<String, Double> MapRasa2 = MyIO.readRASAFile(Path2Rasa2);
            HashMap<String, Integer> MapRes2IdxChain1 = StaticMethod.getMapResNum2Idx(Path2PDB, chain1, 1);
            HashMap<String, Integer> MapRes2IdxChain2 = StaticMethod.getMapResNum2Idx(Path2PDB, chain2, 1);
            
            ArrayList<ColPair_Score> tmp = filter(Lst_Score, Lst_3D, MapRasa1, MapRasa2,
                     InterfaceContact);
            Collections.sort(tmp);
            Collections.reverse(tmp);
            for(int i=0; i<num; i++){
                ColPair_Score col = tmp.get(i);
                System.out.println(ProtID + "\t" + tmp.get(i).getInfo());
                System.out.println(MapRes2IdxChain1.get(col.getP1())+ "\t"+ 
                        (MapRasa1.size() + MapRes2IdxChain2.get(col.getP2())));
                
            }
        }
    }
    public static ArrayList<ColPair_Score> filter(ArrayList<ColPair_Score> LstScore, 
            ArrayList<ColPair_Score> Lst3D, HashMap<String,Double> Rasa1,HashMap<String,Double> Rasa2,
            double InterfaceContact){
        
        ArrayList<ColPair_Score> tmp = new ArrayList<>();
        double Thres_rasa = Configuration.RASA_Thres;
        Iterator<ColPair_Score> iter = Lst3D.iterator();
        while(iter.hasNext()){
            ColPair_Score col = iter.next();
            if(col.getScore()<=InterfaceContact){
                String p1 = col.getP1();
                String p2 = col.getP2();
                double score = findScore(LstScore, p1, p2);
                if(score<-10){
                    continue;
                }
                if(Rasa1.get(p1) >=Thres_rasa && Rasa2.get(p2) >=Thres_rasa){
                    tmp.add(new ColPair_Score(p1, p2, score));
                }
            }
        }
        
        return tmp;
    }
    public static double findScore(ArrayList<ColPair_Score> lst, String p1, String p2){
        for(ColPair_Score col: lst){
            if(col.getP1().equalsIgnoreCase(p1) && col.getP2().equalsIgnoreCase(p2)){
                return col.getScore();
            }
            else if(col.getP1().equalsIgnoreCase(p2) && col.getP2().equalsIgnoreCase(p1)){
                return col.getScore();
            }
        }
        System.err.println("Could not find");
        System.err.println(p1 + "\t" + p2);
        return -100;
    }
}
