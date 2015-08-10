/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Analysis.GeneralResult;
import Protein.Residue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;
import Common.Configuration;

/**
 *
 * @author t.dang
 */
public class StaticMethod {

    public static void WriteFileName(String dir, String fileout) throws IOException {
        List<String> lst = utils.Utils.dir2list(dir);
        ArrayList<String> ProtName = new ArrayList<String>();
        ArrayList<String> ProtNameChain = new ArrayList<String>();
        for (String s : lst) {
            s = s.trim();
            String name = s.substring(0, 4);
            if (!ProtName.contains(name)) {
                ProtName.add(name);
                ProtNameChain.add(s);
            }
        }
        MyIO.WriteToFile(fileout, ProtNameChain);
    }

    public static <T> ArrayList<ArrayList<T>> Divide(ArrayList<T> lst, int n) {
        ArrayList<ArrayList<T>> res = new ArrayList<>();

        int max = (int) Math.ceil((double) lst.size() / n);
//        int max = lst.size()/n;

        boolean mknew = true;
        for (int i = 0; i < n - 1; i++) {
            List<T> tmp = lst.subList(max * i, max * (i + 1));

            res.add(new ArrayList<>(tmp));
        }
        List<T> tmp = lst.subList(max * (n - 1), lst.size());
        res.add(new ArrayList<>(tmp));

//        for (T s : lst) {
//            if (mknew) {
//                tmp = new ArrayList<T>();
//                tmp.add(s);
//                mknew = false;
//                if(tmp.size()==max){
//                    mknew = true;
//                }
//            } else {
//                tmp.add(s);
////                if(tmp.size()==max || lst.indexOf(s)==(lst.size()-1)){
//                if(tmp.size()==max){
//                    res.add(tmp);
//                    mknew = true;
//                }
//            }
//            if(!mknew && lst.indexOf(s)==(lst.size()-1)){
//                res.add(tmp);
//            }
//        }

        return res;
    }

    public static <T> ArrayList<ArrayList<T>> Divide(List<T> lst, int n) {
        ArrayList<ArrayList<T>> res = new ArrayList<>();

        int max = (int) Math.ceil((double) lst.size() / n);
//        int max = lst.size()/n;

        boolean mknew = true;
        for (int i = 0; i < n - 1; i++) {
            List<T> tmp = lst.subList(max * i, max * (i + 1));

            res.add(new ArrayList<>(tmp));
        }
        List<T> tmp = lst.subList(max * (n - 1), lst.size());
        res.add(new ArrayList<>(tmp));

        return res;
    }

    public static boolean CompareFiles(String file1, String file2) throws FileNotFoundException, IOException {
        // Reader 1
        FileInputStream fstream1 = new FileInputStream(file1);
        DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));

        // reader 2
        FileInputStream fstream2 = new FileInputStream(file2);
        DataInputStream in2 = new DataInputStream(fstream2);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));

        int idx = 0;
        double epsilon = 0.0025;
        boolean res = true;
        while (true) {
            String str1 = br1.readLine();
            String str2 = br2.readLine();
            if (str1 == null || str2 == null) {
                break;
            }
            str1 = str1.trim();
            str2 = str2.trim();

            // parse str1, str2
            double[] d1 = ParseVector(str1);
            double[] d2 = ParseVector(str2);

            // compare
            if (d1.length != d2.length) {
                System.err.println("Different lenght in vector");
                System.exit(1);
            }
            for (int i = 0; i < d1.length; i++) {
                if (Math.abs(d1[i] - d2[i]) > epsilon) {
                    System.err.println("Does not fit. i: " + idx + "\tj: " + i
                            + "\n Value1: " + d1[i] + "\tValue2: " + d2[i]);
                    res = false;
                }
            }
            idx++;
        }
        br1.close();
        br2.close();
        return res;
    }

    private static double[] ParseVector(String str) {
        String[] v = str.split("\\s+");
        int len = v.length;
        double[] res = new double[len];
        for (int i = 0; i < len; i++) {
            res[i] = Double.parseDouble(v[i]);
        }
        return res;
    }

    // suppose all files in this folder share the same extend nameA such as *.txt, 
    public static String FindEndName(String Dir) {
        if (utils.Utils.dir2list(Dir).size() == 0) {
            return "";
        }
        String str = utils.Utils.dir2list(Dir).get(0);
        str = str.trim();
        int idx = str.indexOf(".");
        if (idx >= 0) {
            return str.substring(idx);
        }
        return "";
    }

    public static double DistanceBtwGroupsAnyAtom(Group A, Group B) {
        List<Atom> lst_Atom_A = A.getAtoms();
        List<Atom> lst_Atom_B = B.getAtoms();
        double distance = 100.0;

        for (Atom a : lst_Atom_A) {
            double Radius_A = findVanDerWaalsRadius(a);
            for (Atom b : lst_Atom_B) {
                double Radius_B = findVanDerWaalsRadius(b);
                double dis = DistanceBtwAtoms(a, b);
                if (Configuration.UseVanDerWaals) {
                    dis = dis - Radius_A - Radius_B;
                }
                if (dis < distance) {
                    distance = dis;
                }
            }
        }
        return distance;
    }

    private static double findVanDerWaalsRadius(Atom a) {
        double Radius_C = Configuration.VdW_C;
        double Radius_N = Configuration.VdW_N;
        double Radius_O = Configuration.VdW_O;
        double Radius_S = Configuration.VdW_S;
        double Radius_P = Configuration.VdW_P;
        double Radius_H = Configuration.VdW_H;

        String nameA = a.getName().substring(0, 1);
        double Radius1 = 0;
        if (nameA.equalsIgnoreCase("C")) {
            Radius1 = Radius_C;
        } else if (nameA.equalsIgnoreCase("N")) {
            Radius1 = Radius_N;
        } else if (nameA.equalsIgnoreCase("O")) {
            Radius1 = Radius_O;
        } else if (nameA.equalsIgnoreCase("S")) {
            Radius1 = Radius_S;
        } else if (nameA.equalsIgnoreCase("P")) {
            Radius1 = Radius_P;
        } else if (Character.isDigit(nameA.charAt(0)) || nameA.equalsIgnoreCase("H")) {
            Radius1 = Radius_H;
        } else {
            System.err.println("Not Amino acids :" + a.getFullName());
            System.exit(1);
        }
        return Radius1;
    }

    public static double DistanceBtwGroups(Group A, Group B) throws StructureException {
        List<Atom> lst_Atom_A = A.getAtoms();
        List<Atom> lst_Atom_B = B.getAtoms();
        double tmp = -1.0;
        Atom A_CA = ((AminoAcid) A).getCA();
        Atom B_CA = ((AminoAcid) B).getCA();

//        for(Atom a: lst_Atom_A){
//            if(a.getName().equalsIgnoreCase("CA")){
//                A_CA = a;
//            }
//        }
//        for(Atom b: lst_Atom_B){
//            if(b.getName().equalsIgnoreCase("CA")){
//                B_CA = b;
//            }
//        }
        if (A_CA != null && B_CA != null) {
            if (!Configuration.UseVanDerWaals) {
                return DistanceBtwAtoms(A_CA, B_CA);
            } else {
                return (DistanceBtwAtoms(A_CA, B_CA) - findVanDerWaalsRadius(A_CA) - findVanDerWaalsRadius(B_CA));
            }
        } else {
            System.err.println("Wrong in calculate dis btw two amino acids");
            System.exit(1);
            return -1.0d;
        }
//        for (Atom a : lst_Atom_A) {
//            if (A.getType().equalsIgnoreCase("amino")) {
//                    if (!a.getName().equalsIgnoreCase("CA")) {
//                        continue;
//                    }
//            }
//            for (Atom b : lst_Atom_B) {
//                
//                if (B.getType().equalsIgnoreCase("amino")) {
//                    if (b.getName().equalsIgnoreCase("CA")) {
//                        continue;
//                    }
//                }
//                tmp = DistanceBtwAtoms(a, b);
//                break;
//
//            }
//        }
//        return tmp;
    }

    private static double DistanceBtwAtoms(Atom A, Atom B) {
        double dis = 0.0;
        double X1 = A.getX(); //System.out.println("X1: " + X1);
        double Y1 = A.getY(); //System.out.println("Y1: " + Y1);
        double Z1 = A.getZ(); //System.out.println("Z1: " + Z1);
        double X2 = B.getX(); //System.out.println("X2: " + X2);
        double Y2 = B.getY(); //System.out.println("Y2: " + Y2);
        double Z2 = B.getZ(); //System.out.println("Z2: " + Z2);
        dis = Math.pow(X1 - X2, 2) + Math.pow(Y1 - Y2, 2) + Math.pow(Z1 - Z2, 2);
        dis = Math.sqrt(dis);
        //System.out.println("Dis: "+ dis);
        //System.exit(0);
        return dis;
    }

    public static int[] getResidueNum(String Dir2PDB, String ProtName) throws IOException {
        String endfile = FindEndName(Dir2PDB);
        Structure s = (new PDBFileReader()).getStructure(Dir2PDB + ProtName.substring(0, 6) + endfile);
        Chain c = s.getChain(0);
        List<Group> lst = c.getAtomGroups();
        int[] res = new int[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            res[i] = lst.get(i).getResidueNumber().getSeqNum();
        }
        return res;
    }

    public static double[][] AdjustIndex(double[][] d, String path2PDBFile) throws IOException {
        double[][] res = new double[d.length][d[0].length];
        Structure s = (new PDBFileReader()).getStructure(path2PDBFile);
        Chain c = s.getChain(0);
        List<Group> lst_group = c.getAtomGroups();
        ArrayList<Integer> ResidualNum = new ArrayList<>();
        for (int i = 0; i < lst_group.size(); i++) {
            ResidualNum.add(lst_group.get(i).getResidueNumber().getSeqNum());
        }
        for (int i = 0; i < d.length; i++) {
            res[i][0] = ResidualNum.get((int) d[i][0]);
            res[i][1] = ResidualNum.get((int) d[i][1]);
            res[i][2] = d[i][2];
        }
        return res;
    }

    public static int[] getResidueNum(String path2PDBFile) throws IOException {
        Structure s = (new PDBFileReader()).getStructure(path2PDBFile);
        Chain c = s.getChain(0);
        List<Group> lst_group = c.getAtomGroups();
        int[] ResidueNum = new int[lst_group.size()];
        for (int i = 0; i < lst_group.size(); i++) {
            ResidueNum[i] = lst_group.get(i).getResidueNumber().getSeqNum();
        }
        return ResidueNum;
    }

    public static String[] findChainID(List<String> lst, String ProteinID) {
        String[] res = new String[2];
        int count = 0;
        for (String s : lst) {
            if (s.startsWith(ProteinID)) {
                res[count] = s.substring(5, 6);
                count++;
            }
        }
        return res;
    }

    // a hashmap from index number to Residue Number
    public static HashMap<Integer, String> getMapIdx2ResNum(String path2pdbfile, String chain, int startIdx) throws StructureException, IOException {
        // startIdx = 0 in java, =1 in Matlab
        HashMap<Integer, String> map = new HashMap<>();
        Chain C = (new PDBFileReader()).getStructure(path2pdbfile).getChainByPDB(chain);
        Iterator<Group> iter = C.getAtomGroups().iterator();
        int count = startIdx;
        while (iter.hasNext()) {
            Group g = iter.next();
            if (g.getType().equalsIgnoreCase("amino")) {
                map.put(count, g.getResidueNumber().toString());
                count++;
            }
        }
        return map;
    }

    public static HashMap<String, Integer> getMapResNum2Idx(String path2pdbfile, String chain, int startIdx) throws StructureException, IOException {
        HashMap<String, Integer> map = new HashMap<>();
        Chain C = (new PDBFileReader()).getStructure(path2pdbfile).getChainByPDB(chain);
        Iterator<Group> iter = C.getAtomGroups().iterator();
        int count = startIdx;
        while (iter.hasNext()) {
            Group g = iter.next();
            if (g.getType().equalsIgnoreCase("amino")) {
                map.put(g.getResidueNumber().toString(), count);
                count++;
            }
        }
        return map;
    }

    public static int getNumResidue(List<ColPair_Score> lst) {
        HashSet<String> MySet = new HashSet<>();
        for (ColPair_Score col : lst) {
            MySet.add(col.getP1());
            MySet.add(col.getP2());
        }
        return MySet.size();
    }

    public static ArrayList<Residue> toLstResidue(List<ColPair_Score> lst) {

        ArrayList<Residue> res = new ArrayList<>();

        ArrayList<String> MyList = new ArrayList<>();
        HashSet<String> MySet = new HashSet<>();
        HashMap<String, Double> MapRes2TotalScore = new HashMap<>();
        for (ColPair_Score col : lst) {
            String p1 = col.getP1();
            String p2 = col.getP2();
            MapRes2TotalScore.put(p1, 0.0);
            MapRes2TotalScore.put(p2, 0.0);
        }
        for (ColPair_Score col : lst) {
            String p1 = col.getP1();
            String p2 = col.getP2();

            double score = col.getScore();
            double s1 = MapRes2TotalScore.get(p1) + score;
            double s2 = MapRes2TotalScore.get(p2) + score;
            MapRes2TotalScore.put(p1, s1);
            MapRes2TotalScore.put(p2, s2);

            MyList.add(p1);
            MyList.add(p2);
            MySet.add(p1);
            MySet.add(p2);
        }
        for (String s : MySet) {
            int freq = Collections.frequency(MyList, s);
            res.add(new Residue(s, freq, MapRes2TotalScore.get(s) / freq));
        }
        return res;
    }

    public static FastaSequence filterMSA(FastaSequence fas, double LowerBound, double UpperBound) {
        ArrayList<String> Sequence = fas.getAllSequence();
        ArrayList<String> Description = fas.getAllDescription2List();
        String Query = Sequence.get(0);
        int remove = 0;
        for (int i = Sequence.size() - 1; i >= 1; i--) {
            int count = 0;
            String subj = Sequence.get(i);
            for (int j = 0; j < Query.length(); i++) {
                if (Query.substring(j, j + 1).equalsIgnoreCase(subj.substring(j, j + 1))) {
                    count++;
                }
            }
            double simil = (double) count / Query.length();
            if (simil > UpperBound || simil < LowerBound) {
                Sequence.remove(i);
                Description.remove(i);
                remove++;
            }
        }
        System.out.println("ProtID: " + Description.get(0) + "\t Remove: " + remove);
        System.out.println();
        String[] des = new String[Description.size()];
        String[] seq = new String[Sequence.size()];

        return new FastaSequence(Description.toArray(des), Sequence.toArray(seq));
    }

    public static ArrayList<ColPair_Score> getLstColPairScore(double[][] arr) {
        ArrayList<ColPair_Score> res = new ArrayList<>();
        int N = arr.length;
        if (arr.length != arr[0].length) {
            System.err.println("Not Square Matrix");
            System.exit(1);
        }
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                ColPair_Score col = new ColPair_Score(String.valueOf(i), String.valueOf(j), arr[i][j]);
                res.add(col);
            }
        }
        return res;
    }

    public static ArrayList<ColPair_Score> getTopCol(ArrayList<ColPair_Score> lst, double thres,
            int neighbor, boolean IsRealDis) {
        ArrayList<ColPair_Score> res = new ArrayList<>();

        for (ColPair_Score col : lst) {
            if (!IsRealDis) {
                if (col.getScore() >= thres) {
                    int p1 = fromResidueNum2Int(col.getP1());
                    int p2 = fromResidueNum2Int(col.getP2());
                    int diff = Math.abs(p2 - p1);
                    if (diff > neighbor) {
                        res.add(col);
                    }
                }
            } else {
                if (col.getScore() <= thres) {
                    int p1 = fromResidueNum2Int(col.getP1());
                    int p2 = fromResidueNum2Int(col.getP2());
                    int diff = Math.abs(p2 - p1);
                    if (diff > neighbor) {
                        res.add(col);
                    }
                }
            }
        }
        return res;
    }

    public static HashSet<String> fromColPair2ColSet(ArrayList<ColPair_Score> lst) {
        HashSet<String> res = new HashSet<>();
        for (ColPair_Score col : lst) {
            String s = col.getP1() + "_" + col.getP2();
            res.add(s);
        }
        return res;
    }

    public static HashSet<String> fromColPair2ColSet(ArrayList<ColPair_Score> lst,
            HashMap<String, Integer> MapFromChain1, HashMap<Integer, String> Map2Chain2) {

        HashSet<String> res = new HashSet<>();
        for (ColPair_Score col : lst) {
            String p1 = col.getP1();
            String p2 = Map2Chain2.get(MapFromChain1.get(col.getP2()));
            res.add(p1 + "_" + p2);
        }
        return res;
    }

    public static int fromResidueNum2Int(String s) {
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (Exception e) {
            return Integer.parseInt(s.substring(0, s.length() - 1));
        }
    }

    public static HashSet<String> getInterfaceRes(ArrayList<ColPair_Score> lst, int chain, double thres, boolean IsRealDistance) {
        HashSet<String> res = new HashSet<>();
        for (ColPair_Score col : lst) {
            if (IsRealDistance) {
                if (col.getScore() <= thres) {
                    if (chain == 1) {
                        res.add(col.getP1());
                    } else if (chain == 2) {
                        res.add(col.getP2());
                    } else {
                        System.err.println("Chain is 1 or 2");
                        System.exit(1);
                    }
                }
            } else {
                if (col.getScore() >= thres) {
                    if (chain == 1) {
                        res.add(col.getP1());
                    } else if (chain == 2) {
                        res.add(col.getP2());
                    } else {
                        System.err.println("Chain is 1 or 2");
                        System.exit(1);
                    }
                }
            }
        }
        return res;
    }

    public static HashSet<String> getInterfaceRes(ArrayList<ColPair_Score> lst, double thres,
            HashMap<String, Double> Map2RASA, int neighbor) {
        HashSet<String> res = new HashSet<>();
        for (ColPair_Score col : lst) {
            if (col.getScore() >= thres) {
                if (Map2RASA == null) {
                    if (!col.IsNeighbor(neighbor)) {
                        res.add(col.getP1());
                        res.add(col.getP2());
                    }
                } else {
                    if (Map2RASA.get(col.getP1()) >= Configuration.RASA_Thres
                            && Map2RASA.get(col.getP2()) >= Configuration.RASA_Thres) {
                        if (!col.IsNeighbor(neighbor)) {
                            res.add(col.getP1());
                            res.add(col.getP2());
                        }
                    }
                }
            }
        }
        return res;
    }

    public static HashSet<String> randomPickUp(HashSet<String> set, int num, HashMap<String, Double> Map2RASA) {
        List<String> lst = new ArrayList<>();
        lst.addAll(set);
        Iterator<String> iter = lst.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            if (Map2RASA.get(s) < Configuration.RASA_Thres) {
                iter.remove();
            }
        }
        Collections.shuffle(lst);
        List<String> tmp = lst.subList(0, num);
        HashSet<String> res = new HashSet<>();
        res.addAll(tmp);
        return res;
    }
    
    public static HashMap<String, String> getMapChain1ToChain2(String Path2PDB, String chain1, String chain2) throws StructureException, IOException{
        Structure s = (new PDBFileReader()).getStructure(Path2PDB);
        Chain c1 = s.getChainByPDB(chain1);
        Chain c2 = s.getChainByPDB(chain2);
        
        HashMap<String, Integer> MapChain1Res2Idx = getMapResNum2Idx(Path2PDB, chain1, 0);
        HashMap<Integer, String> MapIdx2Chain2Res = getMapIdx2ResNum(Path2PDB, chain2, 0);
        int len1 = MapChain1Res2Idx.size();
        int len2 = MapIdx2Chain2Res.size();
        if(len1!=len2){
            System.err.println("Chain 1 and chain 2 are different");
            System.err.println("Chain 1 : " + len1);
            System.err.println("Chain 2 : " + len2);
            System.out.println(c1.getAtomSequence());
            System.out.println(c2.getAtomSequence());
            return null;
        }
        HashMap<String, String> res = new HashMap<>();
        for(Group g:c1.getAtomGroups()){
            if(g.getType().equalsIgnoreCase("amino")){
                String str = g.getResidueNumber().toString();
                int idx = MapChain1Res2Idx.get(str);
                String another = MapIdx2Chain2Res.get(idx);
                res.put(str, another);
            }
        }
        return res;
    }
    public static HashSet<ColPair> convertColPair_Score2ColPair(HashSet<ColPair_Score> lst){
        HashSet<ColPair> res = new HashSet<>();
        for(ColPair_Score c: lst){
            res.add(new ColPair(c.getP1(), c.getP2()));
        }
        return res;
    }
}
