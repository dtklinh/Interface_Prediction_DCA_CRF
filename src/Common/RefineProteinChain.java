/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.PDBRecord;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.align.util.AtomCache;

/**
 *
 * @author t.dang
 */
public class RefineProteinChain {

    private String ProteinEntry;
    private String ProteinChain;

    /**
     * @return the ProteinEntry
     */
    public String getProteinEntry() {
        return ProteinEntry;
    }

    /**
     * @param ProteinEntry the ProteinEntry to set
     */
    public void setProteinEntry(String ProteinEntry) {
        this.ProteinEntry = ProteinEntry;
    }

    /**
     * @return the ProteinChain
     */
    public String getProteinChain() {
        return ProteinChain;
    }

    /**
     * @param ProteinChain the ProteinChain to set
     */
    public void setProteinChain(String ProteinChain) {
        this.ProteinChain = ProteinChain;
    }
    //

    public RefineProteinChain(String entry, String chain) {
        this.ProteinEntry = entry;
        this.ProteinChain = chain;
    }

    private ArrayList<String> FilterChain(List<String> pdb) {
        ArrayList<String> lst = new ArrayList<>();
        for (String s : pdb) {
            s = s.trim();
            if (s.startsWith("HEADER") || s.startsWith("END")) {
                lst.add(s);
            } else if (s.startsWith("SEQRES") && s.substring(11, 12).equalsIgnoreCase(ProteinChain)) {
                lst.add(s);
            } else if (s.startsWith("ATOM") && s.substring(21, 22).equalsIgnoreCase(ProteinChain)) {
                lst.add(s);
            } else if (s.startsWith("TER") && s.substring(21, 22).equalsIgnoreCase(ProteinChain)) {
                lst.add(s);
            }
        }
        for (String s : lst) {
            System.out.println(s);
        }
        return lst;
    }
    public void PrintFileOffLine(String pathin, String pathout) throws FileNotFoundException, IOException{
//        ArrayList<String> lines = MyIO.ReadPDBFile(this.ProteinEntry);
        List<String> lines = utils.Utils.file2list(pathin);
        ArrayList<String> arr = this.FilterChain(lines);
        MyIO.WriteToFile(pathout, arr);
        
    }
}
