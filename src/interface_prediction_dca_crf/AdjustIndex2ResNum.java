/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import LinearAlgebra.FloatMatrix;
import Protein.NewProteinComplexSkeleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class AdjustIndex2ResNum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here

        // //single chain: change index start with 0 or 1 in Matlab to Residue Number in pdb
        /*
//        String ProtID = "3a0r";
//        String pdbFile = "Test2/PDB/"+ProtID+".pdb";
//        String chain = "B";
//        String path2plmdca = "Test2/plmdca/"+ProtID+"_"+chain+".plmdca";
        String Path2List = "Input/Zellner_Homodimer_5/List.txt";
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");

        String Dir2RawFile = "Input/Zellner_Homodimer_5/MSAMethod/HHblits/backup/";
        String Dir2PDB = "Input/Zellner_Homodimer_5/PDB/";
        String endfile = StaticMethod.FindEndName(Dir2RawFile);
        for (NewProteinComplexSkeleton c : lst) {
            String path2plmdca = Dir2RawFile + c.getProtPDBID()+endfile;
            String pdbFile = Dir2PDB + c.getProtPDBID() + ".pdb";
            
            ArrayList<ColPair_Score> lstPair = MyIO.read2ColPair(path2plmdca, ",");
            HashMap<Integer, String> Idx2ResNum = StaticMethod.getMapIdx2ResNum(pdbFile, c.getChainID1(), 1);

//        Iterator<ColPair_Score> iter = lstPair.iterator();
//        while(iter.hasNext()){
//            ColPair_Score col = iter.next();
//            int p1 = Integer.parseInt(col.getP1());
//            int p2 = Integer.parseInt(col.getP2());
//            String Res1 = Idx2ResNum.get(p1);
//            String Res2 = Idx2ResNum.get(p2);
//            col.setP1(Res1);
//            col.setP2(Res2);
//        }
            lstPair = processSingle(lstPair, Idx2ResNum);
            MyIO.WriteLstToFile("Input/Zellner_Homodimer_5/MSAMethod/HHblits/plmdca/" + c.getProtPDBID() + ".plmdca", lstPair);
        }
        //*/

        /////////////////////////////////////////////////////////////////////////////////////////
        
        // double chains: change index start with 0 or 1 in Matlab to Residue Number in pdb
        /*
         String ProtID = "3a0r";
         String chain1 = "A", chain2 = "B";
         int len1 = 334, len2 = 116;
         int StartIdx = 1;
        
         String pdbFile = "Test2/PDB/"+ProtID+".pdb";
         String path2Complexdca = "Test2/plmdca/3a0r.concatMSA.plmdca";
        
         ArrayList<ColPair_Score> lstPair = MyIO.read2ColPair(path2Complexdca, ",");
         HashMap<Integer, String> Idx2ResNum_Chain1 = StaticMethod.getMapIdx2ResNum(pdbFile, chain1, StartIdx);
         HashMap<Integer, String> Idx2ResNum_Chain2 = StaticMethod.getMapIdx2ResNum(pdbFile, chain2, StartIdx);
        
         ArrayList<ColPair_Score> lstC1 = new ArrayList<>();
         ArrayList<ColPair_Score> lstC2 = new ArrayList<>();
         ArrayList<ColPair_Score> lstComplex = new ArrayList<>();
        
        
         //        FloatMatrix ConcatMatrix = FloatMatrix.convert2Matrix(lstPair, len1, len2, StartIdx);
         FloatMatrix ConcatMatrix = new FloatMatrix("Test2/gremlin/3a0r.concatMSA.gremlin", len1+len2, len1+len2);
        
         FloatMatrix MxC1 = ConcatMatrix.getFloatMatrix(0, len1-1, 0, len1-1);
         FloatMatrix MxC2 = ConcatMatrix.getFloatMatrix(len1, len1+len2-1, len1, len1+len2-1);
         FloatMatrix MxComplex = ConcatMatrix.getFloatMatrix(0, len1-1, len1, len1+len2-1);
        
         lstC1 = MxC1.convert2ColPairsSqrtMx(StartIdx);
         lstC2 = MxC2.convert2ColPairsSqrtMx(StartIdx);
         lstComplex = MxComplex.convert2ColPairsRecMx(StartIdx);
        
         lstC1 = processSingle(lstC1,Idx2ResNum_Chain1);
         lstC2 = processSingle(lstC2, Idx2ResNum_Chain2);
         lstComplex = processDouble(lstComplex, Idx2ResNum_Chain1, Idx2ResNum_Chain2);
        
         //        MyIO.WriteLstToFile("Test2/Result/"+ProtID+"_"+chain1+".fromConcat.plmdca", lstC1);
         //        MyIO.WriteLstToFile("Test2/Result/"+ProtID+"_"+chain2+".fromConcat.plmdca", lstC2);
         //        MyIO.WriteLstToFile("Test2/Result/"+ProtID+".fromConcat.plmdca", lstComplex);
        
         MyIO.WriteLstToFile("Test2/gremlin/"+ProtID+"_"+chain1+".fromConcat.gremlin.score", lstC1);
         MyIO.WriteLstToFile("Test2/gremlin/"+ProtID+"_"+chain2+".fromConcat.gremlin.score", lstC2);
         MyIO.WriteLstToFile("Test2/gremlin/"+ProtID+".fromConcat.gremlin.score", lstComplex);
         */
        
        ////////////////////////////////////////////////////////////////////////////////
        
        // change from Debora format to my format
        String Path2List = "Input/Zellner_Homodimer_5/List.txt";
        String Dir2Debora_plmdca = "Input/Zellner_Homodimer_5/MSAMethod/HHblits/plmdca_Debora/";
        String Dir2PDB = "Input/Zellner_Homodimer_5/PDB/";
        String EndFileDebora = StaticMethod.FindEndName(Dir2Debora_plmdca);
        String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton c: lst){
            String ProtID = c.getProtPDBID();
            String chain1 = c.getChainID1();
            String Path2PDB = Dir2PDB + ProtID + EndFilePDB;
            ArrayList<String> lines = MyIO.ReadLines(Dir2Debora_plmdca+ProtID+EndFileDebora);
            HashMap<Integer, String> MapIdx2ResNum = 
                    StaticMethod.getMapIdx2ResNum(Path2PDB, chain1, 0);
            
            ArrayList<ColPair_Score> Arr = new ArrayList<>();
            int SeqLen = MapIdx2ResNum.size();
            int count = 0;
            for(int i=0; i<SeqLen-1; i++){
                for(int j=i+1; j<SeqLen; j++){
                    double score = Double.parseDouble(lines.get(count).split("\\s+")[5]);
                    count++;
                    String p1 = MapIdx2ResNum.get(i);
                    String p2 = MapIdx2ResNum.get(j);
                    Arr.add(new ColPair_Score(p1, p2, score));
                }
            }
            MyIO.WriteLstToFile(Dir2Debora_plmdca+ProtID+".plmdca", Arr);
        }
    }

    public static ArrayList<ColPair_Score> processSingle(ArrayList<ColPair_Score> lst, HashMap<Integer, String> map) {
        Iterator<ColPair_Score> iter = lst.iterator();
        while (iter.hasNext()) {
            ColPair_Score col = iter.next();
            int p1 = Integer.parseInt(col.getP1());
            int p2 = Integer.parseInt(col.getP2());
            String Res1 = map.get(p1);
            String Res2 = map.get(p2);
            col.setP1(Res1);
            col.setP2(Res2);
        }
        return lst;
    }

    public static ArrayList<ColPair_Score> processDouble(ArrayList<ColPair_Score> lst, HashMap<Integer, String> map1,
            HashMap<Integer, String> map2) {
        Iterator<ColPair_Score> iter = lst.iterator();
        while (iter.hasNext()) {
            ColPair_Score col = iter.next();
            int p1 = Integer.parseInt(col.getP1());
            int p2 = Integer.parseInt(col.getP2());
            String Res1 = map1.get(p1);
            String Res2 = map2.get(p2);
            col.setP1(Res1);
            col.setP2(Res2);
        }
        return lst;
    }
}
