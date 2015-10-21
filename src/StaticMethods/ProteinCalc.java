/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticMethods;

import Common.ColPair;
import Common.ColPair_Score;
import Common.Configuration;
import Protein.Residue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.ResidueNumber;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class ProteinCalc {
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
    public static HashSet<String> getInterfaceRes(ArrayList<ColPair_Score> lst, double thres,
            HashMap<String, Double> Map2RASA, int neighbor) {
        HashSet<String> res = new HashSet<>();
        for (ColPair_Score col : lst) {
            if (col.getScore() >= thres) {
                if (Map2RASA == null) {
                    if (!col.isNeighbor(neighbor)) {
                        res.add(col.getP1());
                        res.add(col.getP2());
                    }
                } else {
                    if (Map2RASA.get(col.getP1()) >= Configuration.RASA_Thres
                            && Map2RASA.get(col.getP2()) >= Configuration.RASA_Thres) {
                        if (!col.isNeighbor(neighbor)) {
                            res.add(col.getP1());
                            res.add(col.getP2());
                        }
                    }
                }
            }
        }
        return res;
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
    public static int fromResidueNum2Int(String s) {
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (Exception e) {
            return Integer.parseInt(s.substring(0, s.length() - 1));
        }
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
    
}
