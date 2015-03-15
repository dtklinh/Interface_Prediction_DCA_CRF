/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author t.dang
 */
import java.lang.*;
import java.io.*;
import java.util.*;

/**
 * This class will read first sequence from a Fasta format file
 */
public final class FastaSequence {

    private String[] description;
    private String[] sequence;

    public FastaSequence(String filename) {
        readSequenceFromFile(filename);
    }
    public FastaSequence(String[] desc, String[] seq){
        description = new String[desc.length];
        sequence = new String[seq.length];
        System.arraycopy(desc, 0, description, 0, desc.length);
        System.arraycopy(seq, 0, sequence, 0, seq.length);
    }

    void readSequenceFromFile(String file) {
        List desc = new ArrayList();
        List seq = new ArrayList();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line = in.readLine();

            if (line == null) {
                throw new IOException(file + " is an empty file");
            }

            if (line.charAt(0) != '>') {
                throw new IOException("First line of " + file + " should start with '>'");
            } else {
                desc.add(line);
            }
            for (line = in.readLine().trim(); line != null; line = in.readLine()) {
                if (line.length() > 0 && line.charAt(0) == '>') {
                    seq.add(buffer.toString());
                    buffer = new StringBuffer();
                    desc.add(line);
                } else {
                    buffer.append(line.trim());
                }
            }
            if (buffer.length() != 0) {
                seq.add(buffer.toString());
            }
        } catch (IOException e) {
            System.out.println("Error when reading " + file);
            e.printStackTrace();
        }

        description = new String[desc.size()];
        sequence = new String[seq.size()];
        for (int i = 0; i < seq.size(); i++) {
            description[i] = (String) desc.get(i);
            sequence[i] = (String) seq.get(i);
        }

    }

    //return first sequence as a String
    public String getSequence() {
        return sequence[0];
    }

    //return first xdescription as String
    public String getDescription() {
        return description[0];
    }

    //return sequence as a String
    public String getSequence(int i) {
        return sequence[i];
    }
    public ArrayList<String> getAllSequence(){
        ArrayList<String> lst = new ArrayList<>();
        for(int i=0; i<sequence.length;i++){
            lst.add(sequence[i]);
        }
        return lst;
    }
    public String[] getSequences(){
        String[] seq = new String[sequence.length];
        System.arraycopy(sequence, 0, seq, 0, sequence.length);
        return seq;
    }
    
    public ArrayList<String> getAllSequence(int max){
        ArrayList<String> lst = new ArrayList<>();
        if(max>sequence.length){
            max = sequence.length;
        }
        for(int i=0; i<max;i++){
            lst.add(sequence[i]);
        }
        return lst;
    }

    //return description as String
    public String getDescription(int i) {
        return description[i];
    }
    public String[] getAllDescription(){
        String[] desc = new String[description.length];
        System.arraycopy(description, 0, desc, 0, description.length);
        return desc;
    }

    public int size() {
        return sequence.length;
    }

//    public static void main(String[] args) throws Exception {
//        String fn = "";
//        if (args.length > 0) {
//            fn = args[0];
//        } else {
//            System.out.print("Enter the name of the FastaFile:");
//            fn = (new BufferedReader(new InputStreamReader(System.in))).readLine();
//        }
//        FastaSequence fsf = new FastaSequence(fn);
//        for (int i = 0; i < fsf.size(); i++) {
//            System.out.println("One sequence read from file " + fn + " with length " + fsf.getSequence().length());
//            System.out.println("description: \n" + fsf.getDescription(i));
//            System.out.println("Sequence: \n" + fsf.getSequence(i));
//        }
//    }
    
    public ArrayList<String> getAllColumn(){
        ArrayList<String> seq = getAllSequence();
        ArrayList<String> cols = new ArrayList<>();
        int len = getSequence().length();
        for(int i=0; i<len; i++){
            String tmp = "";
            for(int j=0; j<seq.size();j++){
                tmp = tmp + seq.get(j).substring(i, i+1);
            }
            cols.add(tmp);
        }
        return cols;
    }
    public String[] getAllUniprotID(){
        String[] ids = new String[this.description.length-1];
        for(int i=1; i<description.length; i++){
            String[] arr = description[i].split("|");
            ids[i-1] = arr[2];
        }
        return ids;
    }
    public HashSet<String> getAllUniprotID2Set(){
        HashSet<String> res = new HashSet<>();
        for(int i=1; i<description.length; i++){
            String[] arr = description[i].split("\\|");
            res.add(arr[2].trim().split("\\s+")[0]);
        }
        return res;
    }
    
}
