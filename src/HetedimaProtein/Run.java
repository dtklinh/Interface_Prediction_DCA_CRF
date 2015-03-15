/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HetedimaProtein;

import Common.FastaSequence;
import Common.MyIO;
import Common.StaticMethod;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author t.dang
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        // write concatenated MSA into file
        String Dir2MSA = "Test/Hetedima/MSA-Methods/HHblits/MSA/";
        String endfileMSA = StaticMethod.FindEndName(Dir2MSA);
        String Path2Map = "Test/Hetedima/MSA-Methods/HHblits/UniprotID_EMBL.txt";
        String Dir2ConcatMSA = "Test/Hetedima/MSA-Methods/HHblits/ConcatMSA/";
        
        String[] f1 = new String[]{"1i85_B", "1ory_A"};
        String[] f2 = new String[]{"1i85_D", "1ory_B"};
        
        for(int i=0; i<f1.length; i++){
            String path2msa1 = Dir2MSA + f1[i]+endfileMSA;
            String path2msa2 = Dir2MSA + f2[i] + endfileMSA;
            HetedimaMSA h = new HetedimaMSA(path2msa1, path2msa2, Path2Map);
            FastaSequence fasta = h.CreateConcatMSA();
            String filename = f1[i]+f2[i].substring(4, 6)+".msa";
            MyIO.WriteFastaSequence2File(Dir2ConcatMSA+filename, fasta);
        }
        // end write
    }
}
