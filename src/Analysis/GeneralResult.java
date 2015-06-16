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
    
    public GeneralResult(){
        TP = 0;
        TN = 0;
        FP = 0;
        FN = 0;
    }

    public GeneralResult(int tp, int tn, int fp, int fn) {
        TP = tp;
        TN = tn;
        FP = fp;
        FN = fn;
    }

    public double getAccuracy() {
        return (double) (getTP() + getFN()) / (getTP() + getTN() + getFP() + getFN());
    }

    public double getFalseAlarmRate() {
        return (getFP()) / (getFP() + getTN());
    }

    public double getSensitivity() {
        return (double) (getTP()) / (getTP() + getFN());
    }

    public double getSpecficity() {
        return (double) getTN() / (getFP() + getTN());
    }

    public double getPrecision() {
        return (double) getTP() / (getTP() + getFP());
    }

    public double getFalseDiscoveryRate() {
        return (double) (getFP()) / (getFP() + getTP());
    }

    public double getMCC() {
        double tu = 1.0 * (getTP() * getTN() - getFP() * getFN());
        double mau1 = Math.sqrt((getTP() + getFP()));
        double mau2 = Math.sqrt(getTP() + getFN());
        double mau3 = Math.sqrt(getTN() + getFP());
        double mau4 = Math.sqrt(getTN() + getFN());
        double res = tu/mau1;
        res = res/mau2;
        res = res/mau3;
        res = res/mau4;
        return res;
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

    public void addResult(GeneralResult other) {
        this.setFN(this.getFN() + other.getFN());
        this.setFP(this.getFP() + other.getFP());
        this.setTN(this.getTN() + other.getTN());
        this.setTP(this.getTP() + other.getTP());
    }

    /**
     * @return the TP
     */
    public int getTP() {
        return TP;
    }

    /**
     * @param TP the TP to set
     */
    public void setTP(int TP) {
        this.TP = TP;
    }

    /**
     * @return the TN
     */
    public int getTN() {
        return TN;
    }

    /**
     * @param TN the TN to set
     */
    public void setTN(int TN) {
        this.TN = TN;
    }

    /**
     * @return the FP
     */
    public int getFP() {
        return FP;
    }

    /**
     * @param FP the FP to set
     */
    public void setFP(int FP) {
        this.FP = FP;
    }

    /**
     * @return the FN
     */
    public int getFN() {
        return FN;
    }

    /**
     * @param FN the FN to set
     */
    public void setFN(int FN) {
        this.FN = FN;
    }
    
    public void PrintResult(String name){
        System.out.println(name+" Sen \t Spec \t Precision \t MCC: \t"+this.getSensitivity()
                +"\t"+getSpecficity()+"\t"+ getPrecision()+"\t"+ getMCC());
//        System.out.println("TP : TN : FP : FN -->\t"+TP +"\t"+ TN +"\t"+FP+"\t"+FN);
    }
}
