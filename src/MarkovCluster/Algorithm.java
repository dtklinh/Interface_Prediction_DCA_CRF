/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MarkovCluster;

import LinearAlgebra.MyOwnFloatMatrix;

/**
 *
 * @author t.dang
 */
public class Algorithm {

    public static MyOwnFloatMatrix process(MyOwnFloatMatrix m) throws InterruptedException {
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
        MyOwnFloatMatrix res = new MyOwnFloatMatrix(m.getArrayCopy());
        res.normalizeColumn();
//        res.normalizeColumn();
        while(dis>epsilon){
            
            MyOwnFloatMatrix tmp = res.times(res);
            tmp = inflate(tmp);
            dis = res.minus(tmp).absSum();
            res = tmp;
            
        }
        return res;
    }
//    private static MyOwnFloatMatrix inflate(MyOwnFloatMatrix m){
//        MyOwnFloatMatrix P = new MyOwnFloatMatrix(m.getArrayCopy());
//        double[] ss = new double[P.getColumnDimension()];
//        for()
//    }
    private static MyOwnFloatMatrix inflate(MyOwnFloatMatrix m){
        MyOwnFloatMatrix tmp = m.timesElementWise(m);
        float[][] A = tmp.getArrayCopy();
        float[] sumcol = tmp.sumColumn();
        for(int c=0; c<tmp.getColumnDimension(); c++){
            for(int r=0; r<tmp.getRowDimension(); r++){
                A[r][c] = A[r][c]/sumcol[c];
            }
        }
        return new MyOwnFloatMatrix(A);
    }
}
