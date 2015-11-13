/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Analysis.GeneralResult;
import Analysis.ReferenceResult;
import Common.ColPairAndScores.ColPair_Score;
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
import java.util.HashSet;
import java.util.List;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class CompareMethods {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        // compare two methods of calculate the contact map
        // Input: two contact maps
        // Output: the results of each method
        String Path2List = "Input/SmallSet/List.txt";
        String Dir2PDB = "Input/SmallSet/PDB/";
        String Endfile_PDB = StaticMethod.FindEndName(Dir2PDB);
        String Dir2Old = "Input/SmallSet/Gremlin/";
        String Endfile_Old = StaticMethod.FindEndName(Dir2Old);
        String Dir2New = "Input/SmallSet/New/";
        String Endfile_New = StaticMethod.FindEndName(Dir2New);
        String Dir2_3D = "Input/SmallSet/3D/";
        String Endfile_3D = StaticMethod.FindEndName(Dir2_3D);
        String Dir2PLM = "Input/SmallSet/loop2/";
        String Endfile_PLM = StaticMethod.FindEndName(Dir2PLM);
        
        GeneralResult Result_Old = new GeneralResult();
        GeneralResult Result_New = new GeneralResult();
        GeneralResult Result_plmdca = new GeneralResult();
        double ContactDef = 3.5;
        double factor = 1.5;
        
        ArrayList<NewProteinComplexSkeleton> Lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton p: Lst){
            String ProtID = p.getProtPDBID();
            String chain = p.getChainID1();
            
            String Path2PDB = Dir2PDB + ProtID  + Endfile_PDB;
            HashMap<Integer,String> MapIdx2ResNum = ProteinCalc.getMapIdx2ResNum(Path2PDB, chain, 0);
            int len = (int)(MapIdx2ResNum.size()*factor);
            String Path2Old = Dir2Old + ProtID + "_"+chain + Endfile_Old;
            String Path2New = Dir2New + ProtID + "_"+chain + Endfile_New;
            String Path2_3D = Dir2_3D + ProtID + "_"+chain + Endfile_3D;
            String Path2_PLM = Dir2PLM + ProtID + "_"+chain + Endfile_PLM;
            
            // read PLM
            HashMap<Integer, String> MapIdx2ResMatlab = ProteinCalc.getMapIdx2ResNum(Path2PDB, chain, 1);
            ArrayList<ColPair_Score> PLM = MyIO.read2ColPair(Path2_PLM, MapIdx2ResMatlab, ",");
            HashSet<ColPair_Score> LstPLM = new HashSet<>();
            LstPLM.addAll(PLM);
            LstPLM = getTop(LstPLM, len);
            
            ////////////////////////////////////
            
//            ArrayList<ColPair_Score> LstAllPair = MyIO.readColPairScore2ArrayList(Path2_3D, "\\s+");
            // load all pair
            HashSet<ColPair_Score> LstAllPair = new HashSet<>();
            LstAllPair.addAll(ProteinIO.readColPairScore2ArrayList(Path2_3D, "\\s+"));
            System.out.println("Read all pairs");
            // Load gremlin
            HashSet<ColPair_Score> LstMyOld = MyIO.readMat2ColLst(Path2Old, MapIdx2ResNum);
            System.out.println("Read  pairs old");
            LstMyOld = getTop(LstMyOld, len);
            
            // load new 
            HashSet<ColPair_Score> LstMyNew = MyIO.readMat2ColLst(Path2New, MapIdx2ResNum);
            System.out.println("Read  pairs new");
            LstMyNew = getTop(LstMyNew, len);
            
            ReferenceResult Ref = new ReferenceResult(LstAllPair, ContactDef);
            Result_Old = Ref.evaluate(LstMyOld);
            System.out.println("Finish evaluate  pairs old");
            Result_New = Ref.evaluate(LstMyNew);
            System.out.println("Finish evaluate  pairs new");
            Result_plmdca = Ref.evaluate(LstPLM);
            System.out.println("Old: Sen, Spec, Precision: "+ Result_Old.getSensitivity()+"\t"+ Result_Old.getSpecficity()+"\t"+Result_Old.getPrecision());
            System.out.println("New: Sen, Spec, Precision: "+ Result_New.getSensitivity() +"\t"+ Result_New.getSpecficity()+ "\t"+ Result_New.getPrecision());
            System.out.println("plm: Sen, Spec, Precision: "+ Result_plmdca.getSensitivity()+"\t"+ Result_plmdca.getSpecficity()+"\t"+Result_plmdca.getPrecision());
        }
    }
    public static HashSet<ColPair_Score> getTop(HashSet<ColPair_Score> lst, int len){
        ArrayList<ColPair_Score> tmp = new ArrayList<>();
        tmp.addAll(lst);
        Collections.sort(tmp);
        Collections.reverse(tmp);
        List<ColPair_Score> tt = tmp.subList(0, len);
        HashSet<ColPair_Score> res = new HashSet<>();
        res.addAll(tt);
        return res;
    }
}
