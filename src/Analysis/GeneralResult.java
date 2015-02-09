/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author t.dang
 */
public class GeneralResult {
    private int TP;
    private int TN;
    private int FP;
    private int FN;
    public GeneralResult(int tp, int tn, int fp, int fn){
        TP = tp;
        TN = tn;
        FP = fp;
        FN = fn;
    }
    public double getAccuracy(){
        return (double)(TP+FN)/(TP+TN+FP+FN);
    }
    public double getFalseAlarmRate(){
        return (FP)/(FP+TN);
    }
    public double getSensitivity(){
        return (double)(TP)/(TP+FN);
    }
    public double getSpecficity(){
        return (double)TN/(FP+TN);
    }
    public double getPrecision(){
        return (double)TP/(TP+FP);
    }
    public double getFalseDiscoveryRate(){
        return (double)(FP)/(FP+TP);
    }
    public double getMCC(){
        double tu = 1.0*(TP*TN-FP*FN);
        double mau = Math.sqrt((TP+FP)+(TP+FN)*(TN+FP)*(TN+FN));
        return tu/mau;
    }
    public static double CalculateAUC(ArrayList<GeneralResult> lst_res) {
        double AUC = 0.0;
//        double tmp;
        int len = lst_res.size();
        ArrayList<Double> arr_sen = new ArrayList<Double>();
        ArrayList<Double> arr_alarm = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            arr_sen.add((double) lst_res.get(i).getSensitivity());
            arr_alarm.add(1.0 - lst_res.get(i).getSpecficity());
        }
        arr_sen.add(len, 1.0);
        arr_alarm.add(len, 1.0);
        arr_sen.add(0, 0.0);
        arr_alarm.add(0, 0.0);

        // sorting
        Collections.sort(arr_alarm);
        Collections.sort(arr_sen);

        // calculate area
        for (int i = 0; i < arr_alarm.size() - 1; i++) {

            double area = 0.5 * (arr_sen.get(i) + arr_sen.get(i + 1)) * (arr_alarm.get(i + 1) - arr_alarm.get(i));
            AUC += area;

        }
        return AUC;
    }
}
