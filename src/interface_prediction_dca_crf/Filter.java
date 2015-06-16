/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.FastaSequence;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class Filter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        // Filter a sequence which is very similar to another sequence in MSA
        String Path2List = "Input/Zellner_Homodimer/List.txt";
        String Dir2MSA = "Input/Zellner_Homodimer/MSAMethod/HHblits/MSA/";
        String EndFileMSA = StaticMethod.FindEndName(Dir2MSA);
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String Path2FileMSA = Dir2MSA + ProtID + EndFileMSA;
            FastaSequence fas = new FastaSequence(Path2FileMSA);
            fas = StaticMethod.filterMSA(fas, 0.1, 0.8);
        }
    }
}
