/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLAST;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.align.util.AtomCache;

/**
 *
 * @author t.dang
 */
public class CreateMSAfromBLAST {

    private String Dir2OutFile;
    private String ProtSequence;
    private String FileName;
    private ArrayList<AlignedBlock> Lst_AlignedBlock;

    public CreateMSAfromBLAST(String dir, String seq, String fname) throws IOException, StructureException {
        this.Dir2OutFile = dir;
        this.FileName = fname;
        if (!seq.isEmpty()) {
            this.ProtSequence = seq;
        } else {
            AtomCache a = new AtomCache();
            Structure s = a.getStructure(fname.trim().substring(0, 4));
            Chain c = s.getChainByPDB(fname.trim().substring(5, 6));
            this.ProtSequence = c.getAtomSequence();
        }
    }

    /**
     * @return the Dir2OutFile
     */
    public String getDir2OutFile() {
        return Dir2OutFile;
    }

    /**
     * @param Dir2OutFile the Dir2OutFile to set
     */
    public void setDir2OutFile(String Dir2OutFile) {
        this.Dir2OutFile = Dir2OutFile;
    }

    /**
     * @return the ProtSequence
     */
    public String getProtSequence() {
        return ProtSequence;
    }

    /**
     * @param ProtSequence the ProtSequence to set
     */
    public void setProtSequence(String ProtSequence) {
        this.ProtSequence = ProtSequence;
    }

    /**
     * @return the FileName
     */
    public String getFileName() {
        return FileName;
    }

    /**
     * @param FileName the FileName to set
     */
    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    /**
     * @return the Lst_AlignedBlock
     */
    public ArrayList<AlignedBlock> getLst_AlignedBlock() {
        return Lst_AlignedBlock;
    }

    /**
     * @param Lst_AlignedBlock the Lst_AlignedBlock to set
     */
    public void setLst_AlignedBlock(ArrayList<AlignedBlock> Lst_AlignedBlock) {
        this.Lst_AlignedBlock = Lst_AlignedBlock;
    }

    public void BuildAlignedBlock() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(Dir2OutFile + FileName);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        this.Protein = new KeyProtein(filename.substring(0, 4), filename.substring(5, 6));
//        this.Protein.LoadingFromPDBFile();
        this.Lst_AlignedBlock = new ArrayList<AlignedBlock>();
        String line = "";
        boolean isblock = false;
        AlignedBlock b = null;
        String query = "";
        String subject = "";
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith(" Score =")) {
                isblock = true;
                if (b != null) {
                    b.setQuery_str(query);
                    b.setSubject_str(subject);
                    b.EliminateGap();
                    this.Lst_AlignedBlock.add(b);
                }
                b = new AlignedBlock();
                query = "";
                subject = "";
            }
            if (line.startsWith("Query ")) {
//                int query_start_idx = Integer.parseInt(line.substring(7, 11).trim());
//                if (b.getQuery_start() > query_start_idx) {
//                    b.setQuery_start(query_start_idx);
//                }
//                String query_tmp = line.substring(11, 73).trim();
//                query = query + query_tmp;
//                int query_end_idx = Integer.parseInt(line.substring(73).trim());
//                if (b.getQuery_end() < query_end_idx) {
//                    b.setQuery_end(query_end_idx);
//                }
                String[] lst_tmp = line.trim().split("\\s+");
                int query_start_idx = Integer.parseInt(lst_tmp[1]);
                if (b.getQuery_start() > query_start_idx) {
                    b.setQuery_start(query_start_idx);
                }
                String query_tmp = lst_tmp[2];
                query = query + query_tmp;
                int query_end_idx = Integer.parseInt(lst_tmp[3]);
                if (b.getQuery_end() < query_end_idx) {
                    b.setQuery_end(query_end_idx);
                }
            }
            if (line.startsWith("Sbjct ")) {
                String[] lst_tmp = line.trim().split("\\s+");
                subject = subject + lst_tmp[2];
//                subject = subject + line.substring(11, 73).trim();
            }
        }
    }

    public ArrayList<String> CreateMSA() {
        ArrayList<String> msa = new ArrayList<String>();
//        int num = this.Lst_AlignedBlock.size();
        int pro_len = this.ProtSequence.length();
        String pro_name = this.FileName.substring(0, 6);
        msa.add(">" + pro_name);
        msa.add(this.ProtSequence);
        int count = 1;
        for (AlignedBlock b : this.Lst_AlignedBlock) {
//            msa.add(">sequence_" + count);
//            count++;
            String str = "";
            for (int i = 1; i < b.getQuery_start(); i++) {
                str = str + "-";
            }
            str = str + b.getSubject_str();
            for (int i = b.getQuery_end() + 1; i <= pro_len; i++) {
                str = str + "-";
            }
//            msa.add(str);
            if (!str.equalsIgnoreCase(this.ProtSequence)) {
                msa.add(">sequence_" + count);
                msa.add(str);
                count++;
            }
        }
        return msa;
    }
}
