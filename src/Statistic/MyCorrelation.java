/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic;

import Common.ColPair_Score;
import java.util.ArrayList;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.stat.correlation.Covariance;
import org.apache.commons.math.stat.correlation.PearsonsCorrelation;

/**
 *
 * @author t.dang
 */
public class MyCorrelation {
    private ArrayList<ColPair_Score> Lst_3D;
    private ArrayList<ColPair_Score> Lst_DCA;
    private ArrayList<ColPair_Score> Lst_NMI;
    
    public MyCorrelation(ArrayList<ColPair_Score> _3d, ArrayList<ColPair_Score> dca, ArrayList<ColPair_Score> nmi){
        Lst_3D = _3d;
        Lst_DCA = dca;
        Lst_NMI = nmi;
    }
    public RealMatrix getCovariance(){
        if(Lst_3D.size()!=Lst_DCA.size() || Lst_3D.size()!=Lst_NMI.size()
                || Lst_DCA.size()!=Lst_NMI.size()){
            System.err.println("ERROR: different length");
            System.exit(1);
        }
        int len = Lst_3D.size();
        boolean found = false;
        double[][] arr = new double[len][3];
        for(int i=0; i<len; i++){
            ColPair_Score s = Lst_3D.get(i);
            arr[i][0] = s.getScore();
            for(ColPair_Score p: Lst_DCA){
                if(s.getP1()== p.getP1() && s.getP2()==p.getP2()){
                    arr[i][1] = p.getScore();
                    found = true;
                    break;
                }
            }
            if(!found){
                System.err.println("ERROR: not find Col_Score in DCA");
            System.exit(1);
            }
            found = false;
            for(ColPair_Score p: Lst_NMI){
                if(s.getP1()== p.getP1() && s.getP2()==p.getP2()){
                    arr[i][2] = p.getScore();
                    found = true;
                    break;
                }
            }
            if(!found){
                System.err.println("ERROR: not find Col_Score in NMI");
            System.exit(1);
            }
            found = false;
        }
        
        PearsonsCorrelation corr = new PearsonsCorrelation(arr);
        return corr.getCorrelationMatrix();
    }
}
