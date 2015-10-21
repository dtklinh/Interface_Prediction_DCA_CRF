/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class ThresholdClassifier {
    private List<Double> LstValuePos;
    private List<Double> LstValueNeg;

    /**
     * @return the LstValuePos
     */
    public List<Double> getLstValue() {
        return LstValuePos;
    }

    /**
     * @param LstValuePos the LstValuePos to set
     */
    public void setLstValue(List<Double> LstValue) {
        this.LstValuePos = LstValue;
    }
    
    public ThresholdClassifier(List<Double> lstPos, List<Double> lstNeg){
        LstValuePos = new ArrayList<>();
        LstValuePos.addAll(lstPos);
        LstValueNeg = new ArrayList<>();
        LstValueNeg.addAll(lstNeg);
    }
    public double calcAUC(){
        int sizeP = LstValuePos.size();
        int sizeN = LstValueNeg.size();
        Collections.sort(LstValuePos);
        Collections.sort(LstValueNeg);
        double min = Math.min(LstValuePos.get(0), LstValueNeg.get(0));
        double max = Math.max(LstValuePos.get(sizeP-1),LstValueNeg.get(sizeN-1));
        int NumPoint = 20;
        double step = (max-min)/NumPoint;
        double thres = min + step;
        ArrayList<GeneralResult> Lst = new ArrayList<>();
        while(thres<max){
            GeneralResult g = doEvaluate(thres);
            Lst.add(g);
            thres += step;
        }
        double area = GeneralResult.CalculateAUC(Lst);
        return area;
    }
    public GeneralResult doEvaluate(double thres){
        int sizeP = LstValuePos.size();
        int sizeN = LstValueNeg.size();
        int TP =0, FN=0, TN=0, FP=0;
        for(double d:LstValuePos){
            if(d>=thres){
                TP++;
            }
            else{
                FN++;
            }
        }
        for(double d: LstValueNeg){
            if(d<thres){
                TN++;
            }
            else{
                FP++;
            }
        }
        return new GeneralResult(TP, TN, FP, FN);
    }
}
