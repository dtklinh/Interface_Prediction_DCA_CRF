/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HetedimaProtein;

import Common.ColPairAndScores.ColPair_Score;
import Common.Configuration;
import Common.FastaSequence;
import Common.MyIO;
import Common.StaticMethod;
import Protein.ProteinChain;
import StaticMethods.ProteinIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
        /*
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
        */ 
        // end write
        
        // adjust index of file from Matlab
        String[] ProteinChainID1 = new String[]{"1i85_B", "1ory_A"};
        String[] ProteinChainID2 = new String[]{"1i85_D", "1ory_B"};
        String endfilePDBSingle = StaticMethod.FindEndName(Configuration.Dir2PDBSingleChain);
        String endfilePlmdca = StaticMethod.FindEndName("Test/Hetedima/MSA-Methods/HHblits/ConcatResult/");
        String Dir2Out = "Test/Hetedima/MSA-Methods/HHblits/ConcatResultAdjustIdx/";
        for(int i=0; i<ProteinChainID1.length; i++){
            ArrayList<ColPair_Score> Lst_filter = new ArrayList<>();
            
            String filenamePlmdca = ProteinChainID1[i]+ProteinChainID2[i].substring(4, 6);
            String path2file = "Test/Hetedima/MSA-Methods/HHblits/ConcatResult/"+filenamePlmdca + endfilePlmdca;
            ArrayList<ColPair_Score> arr = ProteinIO.readColPairScore2ArrayList(path2file, ",");
            ProteinChain chain1 = new ProteinChain(Configuration.Dir2PDBSingleChain, ProteinChainID1[i]);
            ProteinChain chain2 = new ProteinChain(Configuration.Dir2PDBSingleChain, ProteinChainID2[i]);
            int len1 = chain1.getSequence().trim().length();
            int len2 = chain2.getSequence().trim().length();
            System.out.println("len 1: "+ len1);
            System.out.println("len 2: "+ len2);
            for(ColPair_Score p : arr){
                // adjust from matlab convention
                int position1 = (Integer.parseInt(p.getP1())-1);
                int position2 = (Integer.parseInt(p.getP2())-1)-len1;
                String ResiNum1 = chain1.getMap_idx_ResNum().get(position1);
                String ResiNum2 = chain2.getMap_idx_ResNum().get(position2);
                if(ResiNum1!=null && ResiNum2!=null){
                    Lst_filter.add(new ColPair_Score(ResiNum1, ResiNum2, p.getScore()));
                    System.out.println("("+position1 + ":"+ position2+") -> ("+ResiNum1+":"+ResiNum2+")");
                }
                else{
                    if(position1>=0 && position2>=0){
                        System.err.println("("+position1 + ":"+ position2+") -> ("+ResiNum1+":"+ResiNum2+")");
                        
                    }
                }
            }
            ProteinIO.writeColPairScore2File(Dir2Out+filenamePlmdca+".plmdca", Lst_filter);
        }
        // end adjust
    }
}
