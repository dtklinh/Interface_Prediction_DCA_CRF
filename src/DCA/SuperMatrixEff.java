/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import Jama.Matrix;

/**
 *
 * @author t.dang
 */
public class SuperMatrixEff{
    // matrix m1 x m2 x n1 x n2
    private int M1;
    private int M2;
    private int N1;
    private int N2;
    private int[][] Index;
    private float[] Value;
    // s = m1*m2*n1*n2
    public SuperMatrixEff(int m1, int m2, int n1, int n2){
        int s= m1*m2*n1*n2;
        int le2 = m2*n1*n2;
        int le3 = n1*n2;
        M1 = m1;
        M2 = m2;
        N1 = n1;
        N2 = n2;
        Index = new int[4][s];
        Value = new float[s];
        // init value for index
        // init first row
//        for(int i=0; i<m1; i++){
//            for(int j=)
//            
//        }
        
    }
    public SuperMatrixEff add(SuperMatrixEff A){
        return null;
    }
}
