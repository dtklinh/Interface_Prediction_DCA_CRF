/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import LinearAlgebra.FloatMatrix;
import Protein.NewProteinComplexSkeleton;
import StaticMethods.ProteinIO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class MyIO {

    public static void WriteToFile(String filename, ArrayList<String> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (String s : lst) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFileDouble(String filename, ArrayList<Double> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (Double s : lst) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, List<String> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
//        String tmp = "";
        for (String s : lst) {
            if (!s.trim().isEmpty()) {
                writer.write(s + "\n");
            }
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, HashSet<String> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        for (String s : lst) {
            if (!s.trim().isEmpty()) {
                writer.write(s + "\n");
            }
        }
        writer.flush();
        writer.close();
    }

    

    public static void WriteToFile(String filename, LinkedHashSet<String> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (String s : lst) {
            if (!s.trim().isEmpty()) {
                writer.write(s + "\n");
            }
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, double[] lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";

        for (double s : lst) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, double[][] arr) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);

//        for (double s : lst) {
//            writer.write(s + "\n");
//        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                writer.write(arr[i][j] + "\t");
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, float[][] arr) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);


        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                writer.write(arr[i][j] + "\t");
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, String[][] arr) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);


        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                writer.write(arr[i][j] + "\t");
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, double[][][][] arr) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);

//        for (double s : lst) {
//            writer.write(s + "\n");
//        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                for (int k = 0; k < arr[0][0].length; k++) {
                    for (int l = 0; l < arr[0][0][0].length; l++) {
                        writer.write(arr[i][j][k][l] + "\t");
                    }
                    writer.write("\n");
                }
            }

        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, int[][] arr) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);

//        for (double s : lst) {
//            writer.write(s + "\n");
//        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                writer.write(arr[i][j] + "\t");
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    public static void WriteToFile(String filename, int[] arr) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);

//        for (double s : lst) {
//            writer.write(s + "\n");
//        }
        for (int i = 0; i < arr.length; i++) {
            writer.write(arr[i] + "\t");
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    public static ArrayList<String> ReadLines(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            tmp = tmp.trim();
            if (!tmp.isEmpty()) {
                res.add(tmp);
            }
        }
        return res;
    }

    public static HashSet<String> ReadFile(String filename, String regex) throws FileNotFoundException, IOException {
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        HashSet<String> arr = new HashSet<String>();
        String tmp = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            tmp = tmp.trim();
            if (!tmp.isEmpty()) {
                res.add(tmp);
            }
        }
        for (String s : res) {
            String[] arr_str = s.split(regex);
            for (String ss : arr_str) {
                ss = ss.trim();
                if (!ss.isEmpty()) {
                    arr.add(ss);
//                    try {
//                        Integer.parseInt(ss);
//                    } catch (Exception e) {
////                        arr.add(ss.substring(0, ss.length()-1).trim());
//                    }

                }
            }
        }
        return arr;
    }

    public static ArrayList<Double> ReadLines2Double(String filename) throws FileNotFoundException, IOException {
        ArrayList<Double> res = new ArrayList<Double>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            tmp = tmp.trim();
            if (!tmp.isEmpty()) {
                res.add(Double.parseDouble(tmp));
            }
        }
        return res;
    }

    public static ArrayList<Integer> ReadLines2Integer(String filename) throws FileNotFoundException, IOException {
        ArrayList<Integer> res = new ArrayList<Integer>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            tmp = tmp.trim();
            if (!tmp.isEmpty()) {
                res.add(Integer.parseInt(tmp));
            }
        }
        return res;
    }

    public static ArrayList<String> ReadPDBFile(String PDBID) throws FileNotFoundException, IOException {
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream;

        String filepath = "PDB/";
        try {
            fstream = new FileInputStream(filepath + PDBID.toUpperCase() + ".pdb");
        } catch (Exception e) {
            fstream = new FileInputStream(filepath + "pdb" + PDBID.toLowerCase() + ".ent");
        }
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            if (!line.isEmpty()) {
                res.add(line);
            }
        }
        return res;
    }

    public static double[][] ReadDSM(String filename) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        double[][] tmp = new double[400][400];
        int row = 0, col = 0;
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.equalsIgnoreCase("")) {
                continue;
            }
            if (Character.isLetter(line.charAt(0))) {
                line = line.substring(7);
            }
            double v = Double.parseDouble(line.trim());
            tmp[row][col] = v;
            col++;
            if (col >= 400) {
                col = 0;
                row++;
            }
        }
        br.close();
        return tmp;
    }

//    public static void Write
//    public static ArrayList<ColPair_Score> Read2ColPair(String path2file){
//        
//    }
    public static HashSet<String> Read_GI_FromBLAST(String path2file) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(path2file);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        HashSet<String> res = new HashSet<>();
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.startsWith(("gi|")) || line.startsWith((">gi|"))) {
                String[] arr = line.split(Pattern.quote("|"));

                String gi = arr[1];
                int tmp = Integer.parseInt(gi);
                res.add(String.valueOf(tmp));
            }
        }
        return res;
    }
//    public static Pro ReadProtein_Pairwise(String path2file) throws FileNotFoundException{
//        FileInputStream fstream = new FileInputStream(path2file);
//        DataInputStream in = new DataInputStream(fstream);
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        String line = "";
//        while(true){
//            
//        }
//    }

//    public ArrayList<String> ReadUniprotIDFromMSA(String path2filename){
//        FastaSequence f = new FastaSequence(path2filename);
//        f.
//    }
    public static HashMap<String, ArrayList<String>> ReadMapUniprotEMBL(String filename) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";

        HashMap<String, ArrayList<String>> map = new HashMap<>();
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (!line.isEmpty()) {
                String[] arr = line.split("\\s+");
                if (arr[0].equalsIgnoreCase("from") && arr[1].equalsIgnoreCase("to")) {
                    continue;
                }
                if (!map.containsKey(arr[0])) {
                    ArrayList<String> vals = new ArrayList<>();
                    vals.add(arr[1]);
                    map.put(arr[0], vals);
                } else {
                    ArrayList<String> vals = map.get(arr[0]);
                    vals.add(arr[1]);
                    map.put(arr[0], vals);
                }
            }
        }
        br.close();
        return map;
    }

    public static void WriteFastaSequence2File(String filename, FastaSequence f) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String[] desc = f.getAllDescription();
        String[] seq = f.getSequences();
        if (desc.length != seq.length) {
            System.err.println("Length od desc and seq not equal");
            System.exit(1);
        }
        for (int i = 0; i < desc.length; i++) {
            String description;
            if (desc[i].startsWith(">")) {
                description = desc[i];
            } else {
                description = ">" + desc[i];
            }
            writer.write(description + "\n");
            writer.write(seq[i] + "\n");
        }
        writer.flush();
        writer.close();
    }

    
    public static ArrayList<ColPair_Score> read2ColPair(String path2file, 
            HashMap<Integer,String> MapIdx2ResNum, String regex) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(path2file);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        ArrayList<ColPair_Score> res = new ArrayList<>();
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (!line.isEmpty()) {
                String[] arr = line.split(regex);
                String p1 = MapIdx2ResNum.get(Integer.parseInt(arr[0]));
                String p2 = MapIdx2ResNum.get(Integer.parseInt(arr[1]));
                ColPair_Score p = new ColPair_Score(p1, p2, Double.parseDouble(arr[2]));
                res.add(p);
            }
        }
        br.close();
        return res;
    }

    public static ArrayList<NewProteinComplexSkeleton> readComplexSkeleton(String Path2File, String regrex) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(Path2File);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        ArrayList<NewProteinComplexSkeleton> res = new ArrayList<>();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] arr = line.split(regrex);
            if (arr[0].startsWith("//")) {
                continue;
            }
            res.add(new NewProteinComplexSkeleton(arr[0], arr[1], arr[2]));
        }
        return res;
    }

    

    public static HashSet<String> readInterfaceResidue(String Path2File, int chain, double thres, boolean isRealDistance) throws FileNotFoundException, IOException {
        HashSet<String> res = new HashSet<>();
        ArrayList<ColPair_Score> LstScore = ProteinIO.readColPairScore2ArrayList(Path2File, "\\s+");
        // chain is 0 or 1
        ArrayList<ColPair_Score> tmp = new ArrayList<>();
        for (ColPair_Score col : LstScore) {
            if (isRealDistance) {
                if (col.getScore() <= thres) {
                    tmp.add(col);
                }
            } else {
                if (col.getScore() >= thres) {
                    tmp.add(col);
                }
            }
        }
        for (ColPair_Score col : tmp) {
            if (chain == 0) {
                res.add(col.getP1());
            } else if (chain == 1) {
                res.add(col.getP2());
            }
        }
        return res;
    }

    public static HashSet<String> readPreditedInterfaceResidue(List<ColPair_Score> LstScore, int chain, int num, boolean isRealDistance) throws FileNotFoundException, IOException {
        HashSet<String> res = new HashSet<>();

        Collections.sort(LstScore);
        if (!isRealDistance) {
            Collections.reverse(LstScore);
        }
        List<ColPair_Score> tmp = LstScore.subList(0, num);
        // chain is 0 or 1
        for (ColPair_Score col : tmp) {
            if (chain == 0) {
                res.add(col.getP1());
            } else if (chain == 1) {
                res.add(col.getP2());
            }
        }
        return res;
    }

    public static double[][] readMatrix(String filename) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        ArrayList<ArrayList<Double>> tmp = new ArrayList<>();
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (!line.isEmpty()) {
                String[] arr = line.split("\\s+");
                ArrayList<Double> X = new ArrayList<>();
                for(int i=0; i<arr.length; i++){
                    X.add(Double.parseDouble(arr[i]));
                }
                tmp.add(X);
            }

        }
        double[][] A = new double[tmp.size()][tmp.get(0).size()];
        for(int i=0; i<A.length; i++){
            ArrayList<Double> X = tmp.get(i);
            for(int j=0; j<A[0].length; j++){
                A[i][j] = X.get(j);
            }
        }
        return A;
    }

    public static HashSet<ColPair_Score> readMat2ColLst(String filename,
            HashMap<Integer, String> MapIdx2ResNum) throws FileNotFoundException, IOException {
        HashSet<ColPair_Score> res = new HashSet<>();
        double[][] A = MyIO.readMatrix(filename);
        if (A.length != A[0].length) {
            System.err.println("Not square matrix");
            System.exit(1);
        }
        int N = A.length;
        for (int i = 0; i < N - 1; i++) {
            String p1 = MapIdx2ResNum.get(i);
            for (int j = i + 1; j < N; j++) {
                String p2 = MapIdx2ResNum.get(j);
                res.add(new ColPair_Score(p1, p2, A[i][j]));
            }
        }
        return res;
    }
    
}
