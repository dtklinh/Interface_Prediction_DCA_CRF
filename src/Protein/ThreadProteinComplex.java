/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.ColPairAndScores.ColPair_Score;
import Common.MyIO;
import MultipleCore.MyObject;
import StaticMethods.ProteinIO;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class ThreadProteinComplex extends MyObject{
    private String Dir2ScoreFile;
    private ArrayList<ProteinComplex> LstProteinComplex;
    
    public ThreadProteinComplex(String dir, ArrayList<ProteinComplex> lst){
        this.Dir2ScoreFile = dir;
        this.LstProteinComplex = lst;
    }
    public void run() throws IOException, Exception{
        for(ProteinComplex s: LstProteinComplex){
            System.out.println("Thread "+Thread.currentThread().getName()+" run on "+s.getProteinID());
            utils.Utils.tic();
                       
            ArrayList<ColPair_Score> d = s.DistancePairwise();
            ProteinIO.writeColPairScore2File(Dir2ScoreFile+s.getProteinID()+"_"+s.getChain1_ID()+"_"+s.getChain2_ID()+".3d", d);
            utils.Utils.tac();
        }
    }
}
