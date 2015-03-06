/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.MyIO;
import Common.ColPair_Score;
import MultipleCore.MyObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class ThreadProteinChain extends MyObject{
    private String Dir2ScoreFile;
    private ArrayList<ProteinChain> LstProteinChain;
    
    public ThreadProteinChain(String dir, ArrayList<ProteinChain> lst){
        this.Dir2ScoreFile = dir;
        this.LstProteinChain = lst;
    }
    public void run() throws IOException, Exception{
        for(ProteinChain s: LstProteinChain){
            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+s.getProteinChainID());
            utils.Utils.tic();
                       
            ArrayList<ColPair_Score> d = s.DistancePairwise();
            MyIO.WriteLstToFile(Dir2ScoreFile+s.getProteinChainID()+".3d", d);
            utils.Utils.tac();
        }
    }
}
