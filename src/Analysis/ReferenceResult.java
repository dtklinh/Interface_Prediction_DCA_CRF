/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import Common.ColPairAndScores.ColPair;
import Common.ColPairAndScores.ColPair_Score;
import Common.StaticMethod;
import StaticMethods.ProteinCalc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author t.dang
 */
public class ReferenceResult {
    private HashSet<ColPair_Score> LstRef;
    private double PairContactDef;

    /**
     * @return the LstRef
     */
    public HashSet<ColPair_Score> getLstRef() {
        return LstRef;
    }

    /**
     * @param LstRef the LstRef to set
     */
    public void setLstRef(HashSet<ColPair_Score> LstRef) {
        this.LstRef = LstRef;
    }
    
    public ReferenceResult(HashSet<ColPair_Score> lst, double DisDef){
        this.LstRef = new HashSet<>();
        this.LstRef.addAll(lst);
        this.PairContactDef = DisDef;
    }
    public GeneralResult evaluate(HashSet<ColPair_Score> MyPrediction){
        // compute AllPair, RealPairContact
        HashSet<ColPair> RealPairContact = new HashSet<>();
        HashSet<ColPair> AllPair = new HashSet<>();
        AllPair.addAll(ProteinCalc.convertColPair_Score2ColPair(LstRef));
        for(ColPair_Score c: this.LstRef){
            if(c.getScore()<=this.PairContactDef){
                RealPairContact.add(new ColPair(c.getP1(), c.getP2()));
            }
        }
        HashSet<ColPair> MySet = new HashSet<>();
        MySet.addAll(ProteinCalc.convertColPair_Score2ColPair(MyPrediction));
        
        ///////////////////////////////////////////
        Set<ColPair> P = new HashSet<>();
        P.addAll(RealPairContact);
        
        Set<ColPair> N = new HashSet<>();
        N.addAll(AllPair);
        N.removeAll(P);
        
        Set<ColPair> PredP = new HashSet<>();
        PredP.addAll(MySet);
        
        Set<ColPair> PredN = new HashSet<>();
        PredN.addAll(AllPair);
        PredN.removeAll(PredP);
        // compute TP
        Set<ColPair> tmp = new HashSet<>();
        tmp.addAll(P);
        tmp.retainAll(PredP);
        int TP = tmp.size();
        
        // compute TN
        
        tmp.clear();
        tmp.addAll(N);
        tmp.retainAll(PredN);
        int TN = tmp.size();
        
        // compute FP
        tmp.clear();
        tmp.addAll(PredP);
        tmp.retainAll(N);
        int FP = tmp.size();
        
        // compute FN
        tmp.clear();
        tmp.addAll(P);
        tmp.retainAll(PredN);
        int FN = tmp.size();
        
        return new GeneralResult(TP, TN, FP, FN);
    }

    /**
     * @return the PairContactDef
     */
    public double getPairContactDef() {
        return PairContactDef;
    }

    /**
     * @param PairContactDef the PairContactDef to set
     */
    public void setPairContactDef(double PairContactDef) {
        this.PairContactDef = PairContactDef;
    }
    
}
