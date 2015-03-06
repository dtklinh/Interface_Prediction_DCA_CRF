/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

//import MyJama.MyMatrix;
import Common.ColPair_Score;
import Common.FastaSequence;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class MyIO_DCA {
    // read MSA and return to matrix of number

    public static int[][] ReturnAlignment(String MSA_file) throws FileNotFoundException, IOException {
//        ArrayList<String> lst_str = MyIO_DCA.ReadMSA(MSA_file);
        ArrayList<String> lst_str = (new FastaSequence(MSA_file)).getAllSequence(20000);
//        ArrayList<String> filter = MyIO_DCA.Filter(lst_str);
//        System.err.println("Redundant in column: " + StaticMethod.CheckRedundantColumn(filter));
//        int[][] mx = MyIO_DCA.RefineMSA(filter);
        int[][] mx = MyIO_DCA.RefineMSA(lst_str);
        return mx;
    }
    // read MSA and return list of string of sequence protein,
    // probably consists of gap in the first line

    public static ArrayList<String> ReadMSA(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String tmp = "";
        String container = "";
        while (true) {
            tmp = br.readLine();
            if (tmp == null) {
                break;
            }
            if (tmp.isEmpty()) {
                continue;
            }
            if (tmp.indexOf(">") >= 0) {
                if (!container.isEmpty()) {
                    res.add(container);
                }
                container = "";
            } else {
                container = container + tmp.trim();
            }
        }
        br.close();
        return res;
    }
    // refine MSA, eliminate gaps in the first line and convert from String to number

    public static int[][] RefineMSA(ArrayList<String> lst) {
        int M = lst.size();
        int N = 0; // lenght of first sequence
        String s = lst.get(0);
        for (int i = 0; i < s.length(); i++) {
            if (!(s.substring(i, i + 1).equalsIgnoreCase("-"))) {
                N++;
            }
        }
        int[][] mtr = new int[M][N];
        int j = -1;
        for (int count = 0; count < s.length(); count++) {
            if (!(s.substring(count, count + 1).equalsIgnoreCase("-"))) {
                j++;
                for (int i = 0; i < M; i++) {
//                    mtr.set(i, j, MyIO.letter2Number(lst.get(i).substring(count, count+1)));
                    mtr[i][j] = MyIO_DCA.letter2Number(lst.get(i).substring(count, count + 1));
                }
            }
        }
        return mtr;
    }

    public static int letter2Number(String x) {
        switch (x) {
            case "-":
                return 0; //20
            case "A":
                return 1; // 0
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "G":
                return 6;
            case "H":
                return 7;
            case "I":
                return 8;
            case "K":
                return 9;
            case "L":
                return 10;
            case "M":
                return 11;
            case "N":
                return 12;
            case "P":
                return 13;
            case "Q":
                return 14;
            case "R":
                return 15;
            case "S":
                return 16;
            case "T":
                return 17;
            case "V":
                return 18;
            case "W":
                return 19;
            case "Y":
                return 20;
            default:
                return 0;
        }
    }

    public static void PrintMatrix(double[][] X) {
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[0].length; j++) {
                System.out.print(X[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void PrintVector(int[] X) {
        for (int i = 0; i < X.length; i++) {
            System.out.println(X[i]);
        }
    }

    // filter redundant in MSA
    public static ArrayList<String> Filter(ArrayList<String> lst) {
        ArrayList<String> res = new ArrayList<>();
        HashSet<String> hs = new HashSet<>(lst);
        res.addAll(hs);
        if (res.size() != lst.size()) {
            System.err.println("Original size: " + lst.size());
            System.err.println("Filter size: " + res.size());
        }
        return res;
    }

    public static ArrayList<String> ReadFile(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> res = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);
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
        br.close();
        return res;

    }
    public static void WriteToFile(String filename, ArrayList<String> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        String tmp = "";
        for (String s : lst) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();
    }
    public static ArrayList<ColPair_Score> ReadDCA_Score(String path2File) throws IOException{
        ArrayList<ColPair_Score> res = new ArrayList<ColPair_Score>();
        List<String> lst = utils.Utils.file2list(path2File);
        for(String s: lst){
            s = s.trim();
            if(s.isEmpty())
                continue;
            String[] arr = s.split("\t");
//            int p1 = (int)Double.parseDouble(arr[0]);
//            int p2 = (int)Double.parseDouble(arr[1]);
            
            double score = Double.parseDouble(arr[2]);
            ColPair_Score d = new ColPair_Score( arr[0], arr[1], score);
            res.add(d);
        }
        return res;
    }
    public static ArrayList<ColPair_Score> ReadDCA_Score(String path2File, int idx_score) throws IOException{
        ArrayList<ColPair_Score> res = new ArrayList<ColPair_Score>();
        List<String> lst = utils.Utils.file2list(path2File);
        for(String s: lst){
            s = s.trim();
            if(s.isEmpty())
                continue;
            String[] arr = s.split("\t");
//            int p1 = (int)Double.parseDouble(arr[0]);
//            int p2 = (int)Double.parseDouble(arr[1]);
            
            double score = Double.parseDouble(arr[idx_score]);
            ColPair_Score d = new ColPair_Score( arr[0], arr[1], score);
            res.add(d);
        }
        return res;
    }
}
