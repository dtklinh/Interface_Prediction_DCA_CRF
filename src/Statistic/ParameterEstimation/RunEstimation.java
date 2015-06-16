/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic.ParameterEstimation;

import Common.ColPair_Score;
import Common.MyIO;
import Common.StaticMethod;
import DCA.MyIO_DCA;
import Protein.NewProteinComplexSkeleton;
import interface_prediction_dca_crf.AdjustIndex2ResNum;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class RunEstimation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here
        // estimate covariance L x L (L: length of amino acids) using all the data
        String Path2List = "Input/Zellner_Homodimer/List.txt";
        String Dir2MSA = "Input/Zellner_Homodimer/MSAMethod/HHblits/MSA/";
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String EndfileMSA = StaticMethod.FindEndName(Dir2MSA);
        String EndfilePDB = StaticMethod.FindEndName(Dir2PDB);
        String Dir2Out = "Input/Zellner_Homodimer/MSAMethod/HHblits/nML/";
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String chain = prot.getChainID1();
            String Path2FileMSA = Dir2MSA + ProtID + EndfileMSA;
            String Path2FilePDB = Dir2PDB + ProtID + EndfilePDB;
            int[][] A = MyIO_DCA.ReturnAlignment(Path2FileMSA);
            int dim = A[0].length;
            List<RealVector> Vecs = new ArrayList<>();
            for(int i=0; i<A.length; i++){
                double[] val = new double[dim];
                for(int j=0; j<dim; j++){
                    val[j] = A[i][j];
                }
                
                RealVector v = new ArrayRealVector(val);
                Vecs.add(v);
            }
            MLGaussian ml = new MLGaussian(Vecs);
//            RealMatrix mx = ml.estimateCovariance();
            RealMatrix mx = ml.estimateCorrelation();
            ArrayList<ColPair_Score> Lst_ColPair = StaticMethod.getLstColPairScore(mx.getData());
            HashMap<Integer,String> MapIdx2ResNum = StaticMethod.getMapIdx2ResNum(Path2FilePDB, chain, 0);
            Lst_ColPair = AdjustIndex2ResNum.processSingle(Lst_ColPair, MapIdx2ResNum);
            Collections.sort(Lst_ColPair);
            Collections.reverse(Lst_ColPair);
            String Path2OutFile = Dir2Out + ProtID + "_" + chain + ".nML";
            MyIO.WriteLstToFile(Path2OutFile, Lst_ColPair);
        }
    }
}
