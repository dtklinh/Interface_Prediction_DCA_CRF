/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author t.dang
 */
public class Configuration {
    public static String DirTest_MSA = "Test/Hetedima/MSA-Methods/HHblits/MSA/";
    public static String DirTest_DCA = "Test/DCA/";
    public static String DirTest_MatFile = "TestDCA/FileFromMatlab/";
    public static String TmpDir = "Tmp/";
    
    public static String HomeProject = "/afs/informatik.uni-goettingen.de/user/t/t.dang/NetBeansProjects/Interface_Prediction_DCA_CRF/";
    
    public static final String Dir2DataBase = "Test/Hetedima/";
    public static final String MethodMakingMSA = "MSA-Methods/HHblits/";
    
    public static final String Dir2PDBSingleChain = Dir2DataBase + "PDBSingleChain/";
    public static final String Dir2PDB = Dir2DataBase + "PDBComplex/";
    public static final String Dir2MSA = Dir2DataBase+MethodMakingMSA+"MSA/";
    public static final String Dir2BLAST = Dir2DataBase+MethodMakingMSA+"BLAST/";
    public static final String Dir2DCA = Dir2DataBase+MethodMakingMSA+"DCA/";
    public static final String Dir2Sequence = Dir2DataBase + "Sequence/";
    public static final String Dir2EVcomplex = Dir2DataBase+MethodMakingMSA + "ConcatResultAdjustIdx/";
    public static final String Dir23DComplex = Dir2DataBase + "3DComplex/";
    
    public static final double Percent = 0.75;
    // Van der Waals radius
    public static final double VdW_H = 1.2;
    public static final double VdW_C = 1.7;
    public static final double VdW_N = 1.55;
    public static final double VdW_O = 1.52;
    public static final double VdW_F = 1.47;
    public static final double VdW_P = 1.8;
    public static final double VdW_S = 1.8;
    public static final double VdW_Cl = 1.75;
    
    public static final boolean UseVanDerWaals = true;
    public static final boolean UseCarbonAlpha = true;
    public static final int Neighbor = 7;
    public static final double RASA_Thres = 0.15;
    // defined distance of neighbor amino acids in one chain (include Van der Vaal radius) with any atom
    public static final double AA_Neighbor_Vdw = 4.0;
}
