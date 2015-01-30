/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLAST;

import MultipleCore.MyObject;
import java.io.IOException;
import java.util.ArrayList;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.align.util.AtomCache;

/**
 *
 * @author t.dang
 */
public class BLAST extends MyObject{

    private String Database;
    private String PathOut;
    private String PathIn;
    private int NumInterations;
    private String Path2BLAST;
    private ArrayList<String> Lst_Query;

    /**
     * @return the Database
     */
    public String getDatabase() {
        return Database;
    }

    /**
     * @param Database the Database to set
     */
    public void setDatabase(String Database) {
        this.Database = Database;
    }

    


    /**
     * @return the NumInterations
     */
    public int getNumInterations() {
        return NumInterations;
    }

    /**
     * @param NumInterations the NumInterations to set
     */
    public void setNumInterations(int NumInterations) {
        this.NumInterations = NumInterations;
    }

    public BLAST(String path, ArrayList<String> lst_query, String db, String out, String in, int iter) {
        this.Path2BLAST = path;
        this.Lst_Query = lst_query;
        this.Database = db;
        this.PathOut = out;
        this.NumInterations = iter;
        this.PathIn = in;
    }

    public void run() throws IOException, InterruptedException {
        for (String Query : Lst_Query) {
            String cmd = Path2BLAST + " -query " +PathIn + "/"+ Query;
            cmd = cmd + " -db " + Database;
            cmd = cmd + " -out " + PathOut + "/"+Query+".out";
            cmd = cmd + " -num_iterations " + NumInterations;
            cmd = cmd + " -out_ascii_pssm " + PathOut + "/"+ Query+".pssm";
            cmd = cmd + " -comp_based_stats 1";

            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+Query);
            utils.Utils.tic();
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);
            pr.waitFor();
            pr.destroy();
            rt.freeMemory();
            utils.Utils.tac();
        }
    }
}
