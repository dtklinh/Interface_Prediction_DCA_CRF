/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

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
        String tmp = "";
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
}
