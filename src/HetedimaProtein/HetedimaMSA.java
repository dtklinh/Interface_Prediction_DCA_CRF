/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HetedimaProtein;

import Common.FastaSequence;
import Common.MyIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author t.dang
 */
public class HetedimaMSA {

    private FastaSequence MSA1;
    private FastaSequence MSA2;
    private HashMap<String, ArrayList<String>> Map_Uniprot_EMBL;

    /**
     * @return the MSA1
     */
    public FastaSequence getMSA1() {
        return MSA1;
    }

    /**
     * @param MSA1 the MSA1 to set
     */
    public void setMSA1(FastaSequence MSA1) {
        this.MSA1 = MSA1;
    }

    /**
     * @return the MSA2
     */
    public FastaSequence getMSA2() {
        return MSA2;
    }

    /**
     * @param MSA2 the MSA2 to set
     */
    public void setMSA2(FastaSequence MSA2) {
        this.MSA2 = MSA2;
    }

    /**
     * @return the Map_Uniprot_EMBL
     */
    public HashMap<String, ArrayList<String>> getMap_Uniprot_EMBL() {
        return Map_Uniprot_EMBL;
    }

    /**
     * @param Map_Uniprot_EMBL the Map_Uniprot_EMBL to set
     */
    public void setMap_Uniprot_EMBL(HashMap<String, ArrayList<String>> Map_Uniprot_EMBL) {
        this.Map_Uniprot_EMBL = Map_Uniprot_EMBL;
    }

    public HetedimaMSA(String path2msa1, String path2msa2, String path2map) throws FileNotFoundException, IOException {
        this.MSA1 = new FastaSequence(path2msa1);
        this.MSA2 = new FastaSequence(path2msa2);
        this.Map_Uniprot_EMBL = MyIO.ReadMapUniprotEMBL(path2map);
    }

    public FastaSequence CreateConcatMSA() {
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> sequence = new ArrayList<>();
        String[] MSA1_desc, MSA1_seq, MSA2_desc, MSA2_seq;


        MSA1_desc = MSA1.getAllDescription();
        MSA1_seq = MSA1.getSequences();
        MSA2_desc = MSA2.getAllDescription();
        MSA2_seq = MSA2.getSequences();


        String first_desc = MSA1_desc[0] + "\t" + MSA2_desc[0];
        String first_sequence = MSA1_seq[0] + MSA2_seq[0];
        description.add(first_desc);
        sequence.add(first_sequence);

        for (int i = 1; i < MSA1_seq.length; i++) {
            String uniprotID = FindUniprotID(MSA1_desc[i]);
            int idx = LookUpCloseSeq(MSA2_desc, uniprotID);
            if(idx>=0){
                String id2 = FindUniprotID(MSA2_desc[idx]);
                description.add(uniprotID+"\t"+ id2);
                sequence.add(MSA1_seq[i]+MSA2_seq[idx]);
            }
        }
        String[] d = new String[description.size()];
        String[] s = new String[sequence.size()];
        return new FastaSequence(description.toArray(d), sequence.toArray(s));
    }

    private String FindUniprotID(String s) {
        try{
        String[] arr = s.split("\\|");
        return arr[2].trim().split("\\s+")[0];
        }
        catch(Exception e){
            System.err.println("s: "+s);
            System.exit(1);
            return null;
        }
    }
    private int LookUpCloseSeq(String[] lst, String uniprotID){
        int score = 100000;
        int idx = -1;
        for(int i=1; i<lst.length; i++){
            String otherID = FindUniprotID(lst[i]);
            if(uniprotID.split("_")[1].equalsIgnoreCase(otherID.split("_")[1])){
                int sc = DistanceBtwUniprots(otherID, uniprotID);
                if(sc>0 && sc < score){
                    score = sc;
                    idx = i;
                }
            }
        }
        if(score==100000){
            System.err.println("Not in the same spiece, haha");
        }
        return idx;
    }
    private int DistanceBtwUniprots(String id1, String id2){
        if(!id1.split("_")[1].equalsIgnoreCase(id2.split("_")[1])){
            System.err.println("Not in the same spiece");
            return -1;
        }
        ArrayList<String> embl1 = this.Map_Uniprot_EMBL.get(id1);
        ArrayList<String> embl2 = this.Map_Uniprot_EMBL.get(id2);
        if(embl1==null || embl2==null){
            return -1;
        }
        double score = -1;
        for(String s1: embl1){
            String sign1 = s1.substring(0, 3);
            double accNum1 = Double.parseDouble(s1.substring(3));
            for(String s2: embl2){
                String sign2 = s2.substring(0, 3);
                double accNum2 = Double.parseDouble(s2.substring(3));
                if(sign1.equalsIgnoreCase(sign2)){
                    if(score<0 || score<(Math.abs(accNum1-accNum2))){
                        score = Math.abs(accNum1-accNum2);
                    }
                }
            }
        }
        return (int)score;
    }
}
