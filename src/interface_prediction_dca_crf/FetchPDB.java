/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_prediction_dca_crf;

import Common.MyIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.align.util.AtomCache;

/**
 *
 * @author t.dang
 */
public class FetchPDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, StructureException {
        // TODO code application logic here

        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> lst = MyIO.ReadLines("Input/ListPDB.txt");
        for (String pdb : lst) {
            AtomCache cache = new AtomCache();
            Structure s = null;
            try {
                s = cache.getStructure(pdb);
            } catch (Exception e) {
                continue;
            }
            List<Chain> chains = s.getChains();
            Iterator<Chain> iter = chains.iterator();
            while (iter.hasNext()) {
                Chain c = iter.next();
                if (!c.getAtomGroup(0).getType().equalsIgnoreCase("amino")) {
                    iter.remove();
                }
            }
            if (chains.size() == 2) {
                res.add(pdb);
                System.out.println("Added: "+ pdb);
            }
        }
        MyIO.WriteToFile("Input/LstPDB_Filter.txt", res);
    }
}
