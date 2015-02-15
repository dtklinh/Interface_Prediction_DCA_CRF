/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MarkovCluster;

import DCA.MyOwnMatrix;

/**
 *
 * @author t.dang
 */
public class Algorithm {

    public static MyOwnMatrix process(MyOwnMatrix m) throws InterruptedException {
//        if (!m.checkSymetric()) {
//            System.err.println("Matrix is not symetric");
////            System.exit(1);
//        }
        int round = 0;
//        m = m.normalize();
        System.out.println("Round "+round);
//        Thread.sleep(2000);
//        m.print2Screen();
        round++;
        double epsilon = 1e-6;
        double dis = 1;
        MyOwnMatrix res = new MyOwnMatrix(m.getArrayCopy());
        res.normalizeColumn();
//        res.normalizeColumn();
        while(dis>epsilon){
            
            MyOwnMatrix tmp = res.times(res);
            tmp = inflate(tmp);
            dis = res.minus(tmp).absSum();
            res = tmp;
            
        }
        return res;
    }
//    private static MyOwnMatrix inflate(MyOwnMatrix m){
//        MyOwnMatrix P = new MyOwnMatrix(m.getArrayCopy());
//        double[] ss = new double[P.getColumnDimension()];
//        for()
//    }
    private static MyOwnMatrix inflate(MyOwnMatrix m){
        MyOwnMatrix tmp = m.timesElementWise(m);
        double[][] A = tmp.getArrayCopy();
        double[] sumcol = tmp.sumColumn();
        for(int c=0; c<tmp.getColumnDimension(); c++){
            for(int r=0; r<tmp.getRowDimension(); r++){
                A[r][c] = A[r][c]/sumcol[c];
            }
        }
        return new MyOwnMatrix(A);
    }
}
