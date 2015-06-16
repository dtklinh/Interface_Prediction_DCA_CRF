/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import Protein.NewProteinComplexSkeleton;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class Scripts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        // TODO code application logic here

        // Create a3m files from HHblits
        /*
        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String Dir2Sequence = "Input/Zellner_Homodimer/Sequence/";
        String Dir2MSA = "Input/Zellner_Homodimer/MSAMethod/HHblits/MSA/";
        String Dir2Tmp = "/home/t.dang/BioTools/HHsuite/Tmp/";
        String EndFileSequence = StaticMethod.FindEndName(Dir2Sequence);

        int cpu = 4;
        String Dir2Program = "../../BioInfoTools/hhsuite-2.0.16-linux-x86_64/bin/";
        String Path2Script = "/home/t.dang/BioTools/HHsuite/hhsuite-2.0.16-linux-x86_64/scripts/reformat.pl";
        String Path2Database = "/home/t.dang/BioTools/HHsuite/uniprot20_2013_03/uniprot20_2013_03";
        String Program = "hhblits";
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for (NewProteinComplexSkeleton prot : lst) {
            String ProtID = prot.getProtPDBID();
            String Path2Query = Dir2Sequence + ProtID + EndFileSequence;
            String cmd;

            cmd = Dir2Program;
            cmd = cmd + Program ;
            cmd = cmd + " -i " + Path2Query;
            cmd = cmd + " -cpu " + cpu;
            cmd = cmd + " -d " + Path2Database;
            cmd = cmd + " -n 8 -e 1E-20 -maxfilt 1000000 -neffmax 20 -nodiff -realign_max 1000000";
            cmd = cmd + " -oa3m " + Dir2Tmp + ProtID + ".a3m";

            System.err.println(cmd);
            utils.Utils.tic();
//            Runtime rt = Runtime.getRuntime();
//            Process pr = rt.exec(cmd);
//            pr.waitFor();
//            pr.destroy();
//            rt.freeMemory();

            final Process p = Runtime.getRuntime().exec(cmd);

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
            utils.Utils.tac();
        }

        //*/
        ////////////////////////////////////////////////////////////
        
        // run hhfilter
        /*
        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String Dir2a3m = "/home/t.dang/BioTools/HHsuite/Tmp/";
        String Path2Program = "../../BioInfoTools/hhsuite-2.0.16-linux-x86_64/bin/hhfilter";
        String EndFilea3m = ".a3m";
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String protID = prot.getProtPDBID();
            String Path2File = Dir2a3m + protID + EndFilea3m;
            String Path2OutFile = Dir2a3m + protID + ".filter.a3m";
            String cmd = Path2Program ;
            cmd = cmd + " -i "+ Path2File;
            cmd = cmd + " -o "+ Path2OutFile;
            cmd = cmd + " -id 100 -cov 75";
            
            System.err.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
            System.out.println("Exit value: "+ p.exitValue());
            utils.Utils.tac();
        }
        //*/
        ///////////////////////////////////////////////////////////////
        
        // reformat from a3m to Fasta
        /*
        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String Dir2a3m = "/home/t.dang/BioTools/HHsuite/Tmp/";
        String Path2Program = "../../BioInfoTools/hhsuite-2.0.16-linux-x86_64/scripts/reformat.pl";
        String EndFilea3m = ".filter.a3m";
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String Path2File = Dir2a3m + ProtID + EndFilea3m;
            String Path2OutFile = Dir2a3m + ProtID + ".fas";
            
            String cmd = "perl "+Path2Program;
            cmd = cmd + " "+ Path2File;
            cmd = cmd + " " + Path2OutFile;
            
            System.err.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
            System.out.println("Exit value: "+ p.exitValue());
            utils.Utils.tac();
        }
        //*/
        ///////////////////////////////////////////////////////////////////////
        
        // edit MSA file: filter dot columns
        /*
        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String Dir2Tmp = "/home/t.dang/BioTools/HHsuite/Tmp/";
        String Dir2Program = Configuration.HomeProject+"DCA_Matlab/dca_DeboraSever/calculate_evolutionary_constraints_plmdca_v1.2/";
        String EndFileTmp = ".fas";
        String Program = "filter_dot_columns_from_a2m.pl";
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String Path2File = Dir2Tmp + ProtID + EndFileTmp;
            String Path2Out = Dir2Tmp + ProtID + ".nogaps";
            
            String cmd = "perl " + Dir2Program + Program;
            cmd = cmd + " "+Path2File + " 80 > " + Path2Out;
            
            System.err.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);
            

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
            System.out.println("Exit value: "+p.exitValue());
            utils.Utils.tac();
        }
        //*/
        //////////////////////////////////////////////////////////
        
        // edit MSA file: filter duplicate sequence
        /*
        String Path2List = "Input/Zellner_Homodimer/List_5_2.txt";
        String Dir2Tmp = "/home/t.dang/BioTools/HHsuite/Tmp/";
        String Dir2Program = Configuration.HomeProject+"DCA_Matlab/dca_DeboraSever/calculate_evolutionary_constraints_plmdca_v1.2/";
        String EndFileTmp = ".fas";
        String Program = "filter_ambiguous_residues_and_duplicates.py";
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String Path2File = Dir2Tmp + ProtID + EndFileTmp;
            String Path2Out = Dir2Tmp + ProtID + ".nodups";
            
            String cmd = "python " + Dir2Program + Program;
            cmd = cmd + " -m \"suppress_member\" ";
            cmd = cmd + " -u filter_perfect_match ";
            cmd = cmd + " -g 70 ";
            cmd = cmd + " "+Path2File + " > " + Path2Out;
            
            System.err.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);
            

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
            System.out.println("Exit value: "+p.exitValue());
            utils.Utils.tac();
        }
        //*/
        //////////////////////////////////////////////////////////
        
        // run rasa script
        ///*
        String Path2List = "Input/Zellner_Homodimer/LL.txt";
        String Path2Script = "java -jar ./scripts/protein-pdb-asa.jar";
        String Dir2PDB = "Input/Zellner_Homodimer/PDB/";
        String Dir2Rasa = "Input/Zellner_Homodimer/Rasa/";
        String EndFilePDB = StaticMethod.FindEndName(Dir2PDB);
        
        ArrayList<NewProteinComplexSkeleton> lst = MyIO.readComplexSkeleton(Path2List, "\\s+");
        for(NewProteinComplexSkeleton prot: lst){
            String ProtID = prot.getProtPDBID();
            String chain2 = prot.getChainID2();
            String Path2FilePDB = Dir2PDB + ProtID + EndFilePDB;
            String Path2Out = Dir2Rasa + ProtID + "_" + chain2 + ".rasa";
            String cmd = Path2Script;
            cmd = cmd + " -pdbfile " + Path2FilePDB;
            cmd = cmd + " -pdb " + ProtID;
            cmd = cmd + " -chain " + chain2;
            cmd = cmd + " -rasafile " + Path2Out;
            System.err.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);
            

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;

                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            p.waitFor();
            System.out.println("Exit value: "+p.exitValue());
            utils.Utils.tac();
        }
        //*/ 
    }
}
