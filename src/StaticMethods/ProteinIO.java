/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticMethods;

import Common.ColPair_MegaScore;
import Common.ColPair_Score;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author t.dang
 */
public class ProteinIO {
    public static HashMap<String, Double> readRASAFile(String Path2File) throws FileNotFoundException, IOException {
        HashMap<String, Double> map = new HashMap<>();
        FileInputStream fstream = new FileInputStream(Path2File);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (!line.isEmpty()) {
                String[] arr = line.split("\\s+");
                String Resnum = arr[0].split("_")[2];
                double score = Double.parseDouble(arr[3]);
                map.put(Resnum, score);
            }
        }
        return map;
    }
    public static List<Group> readGroups(String PDBPath, String chain) throws IOException, StructureException{
        Structure s = (new PDBFileReader()).getStructure(PDBPath);
        Chain c = s.getChainByPDB(chain);
        List<Group> g = c.getAtomGroups();
        Iterator<Group> iter = g.iterator();
        while(iter.hasNext()){
            Group i = iter.next();
            if(!i.getType().equalsIgnoreCase("amino")){
                iter.remove();
            }
        }
        return g;
    }
    
    public static void writeColPairScore2File(String filename, ArrayList<ColPair_Score> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
//        String tmp = "";
        for (ColPair_Score s : lst) {
            writer.write(s.getP1() + "\t" + s.getP2() + "\t" + s.getScore() + "\n");
        }
        writer.flush();
        writer.close();
    }
    public static void writeColPairScore2File(String filename, List<ColPair_Score> lst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
//        String tmp = "";
        for (ColPair_Score s : lst) {
            writer.write(s.getP1() + "\t" + s.getP2() + "\t" + s.getScore() + "\n");
        }
        writer.flush();
        writer.close();
    }
    public static void writeColPairScore2File(String filename, Set<ColPair_Score> lst) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename), 32768);
        for(ColPair_Score c:lst){
            writer.write(c.getP1()+"\t"+c.getP2()+"\t"+c.getScore()+"\n");
        }
        writer.flush();
        writer.close();
    }
    public static ArrayList<ColPair_Score> readColPairScore2ArrayList(String path2file, String regex) throws FileNotFoundException, IOException {
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
                ColPair_Score p = new ColPair_Score(arr[0], arr[1], Double.parseDouble(arr[2]));
                res.add(p);
            }
        }
        br.close();
        return res;
    }
    public static Set<ColPair_Score> readColPairScore2Set(String filename, String regrex) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Set<ColPair_Score> res = new HashSet<>();
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (!line.isEmpty()) {
                String[] arr = line.split(regrex);
                ColPair_Score p = new ColPair_Score(arr[0], arr[1], Double.parseDouble(arr[2]));
                res.add(p);
            }
        }
        br.close();
        return res;
    }
    public static List<ColPair_MegaScore> readColPairMegaScore2List(String path2file, String regrex,
            String... args) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(path2file);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        List<ColPair_MegaScore> res = new ArrayList<>();
        while(true){
            line = br.readLine();
            if(line==null){
                break;
            }
            line = line.trim();
            if(!line.isEmpty()){
                String[] arr = line.split(regrex);
                String P1 = arr[0];
                String P2 = arr[1];
                double d3=0, dca=0, gremlin=0, cmi=0;
                for(int i=0; i<args.length;i++){
                    if(args[i].indexOf("three")>=0){
                        d3 = Double.parseDouble(arr[i+2]);
                    }
                    else if(args[i].indexOf("dca")>=0){
                        dca = Double.parseDouble(arr[i+2]);
                    }
                    else if(args[i].indexOf("gremlin")>=0){
                        gremlin = Double.parseDouble(arr[i+2]);
                    }
                    else if(args[i].indexOf("mi")>=0){
                        cmi = Double.parseDouble(arr[i+2]);
                    }
                    else{
                        System.err.println("Do not recognize format");
                        System.exit(1);
                    }
                }
                res.add(new ColPair_MegaScore(P1, P2, d3, dca, gremlin, cmi));
            }
        }
        return res;
    }
}
