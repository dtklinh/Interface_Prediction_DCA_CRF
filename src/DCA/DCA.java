/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import Common.ColPair_Score;
import Common.Configuration;
import LinearAlgebra.MyOwnFloatMatrix;
import LinearAlgebra.SuperFloatMatrix;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashMap;

/**
 *
 * @author t.dang
 */
/* This class is dedicated to calculate direct coupling method by using mean field approximation
 * More detail can see in the paper of Debora S Marks. "Protein 3D structure computed from 
 * evolutionary sequence variation"
 */
public class DCA {

    private final int q = 21;
    private final float pseudocount_weight = 0.5f;  // lamda
    private final float theta = 0.2f;
    private int M; // number of sequence in MSA_FloatMatrix
    private int N; // lenght of protein
    private int[][] AlgnMx; // numeric alignment of MSA_FloatMatrix

    public DCA(int m, int n, int[][] mx) {
        M = m;
        N = n;
        AlgnMx = new int[M][];
        for (int i = 0; i < M; i++) {
            System.arraycopy(mx[i], 0, AlgnMx[i], 0, N);
        }
    }

    public DCA(int[][] mx) {
        M = mx.length;
        N = mx[0].length;
        AlgnMx = new int[M][N];
        for (int i = 0; i < M; i++) {
            System.arraycopy(mx[i], 0, AlgnMx[i], 0, N);
            
        }
    }

    public float Compute_M_eff() {
        int[] Km = this.Compute_W();
        float res = 0f;
        for (int i = 0; i < Km.length; i++) {
            res += 1f / Km[i];
        }
        return res;
    }

    private int[] Compute_W() { // vector of Km in the paper
        int[] mx = new int[M];

        for (int i = 0; i < M; i++) {
            int dis = 0;
            for (int j = 0; j < M; j++) {
                int count = 0;
                for (int k = 0; k < N; k++) {
                    if (AlgnMx[i][k] != AlgnMx[j][k]) {
                        count++;
                    }
                }
                if (((double) count / N) < theta) {
                    dis++;
                }
            }
//            mx.set(1, i, dis);
            mx[i] = dis;
        }

        return mx;
    }

    private MyOwnFloatMatrix Compute_TrueFreqSingle(MyOwnFloatMatrix W) {
        MyOwnFloatMatrix Pi_true = MyOwnFloatMatrix.Zeros(N, q);
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                Pi_true.set(i, AlgnMx[j][i], Pi_true.get(i, AlgnMx[j][i]) + W.get(0, j));
            }
        }
        Pi_true = Pi_true.times(1f / W.sum());
        return Pi_true;
    }

    private SuperFloatMatrix Compute_TrueFreqPair(MyOwnFloatMatrix Pi_true, MyOwnFloatMatrix W) {
        float[][][][] Pij_true = new float[N][N][q][q];
        for (int l = 0; l < M; l++) {
            for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                    Pij_true[i][j][AlgnMx[l][i]][AlgnMx[l][j]] += W.get(0, l);
                    Pij_true[j][i][AlgnMx[l][j]][AlgnMx[l][i]] = Pij_true[i][j][AlgnMx[l][i]][AlgnMx[l][j]];
                }
            }
        }
        SuperFloatMatrix m_Pij_true = new SuperFloatMatrix(Pij_true);
        m_Pij_true.divideScalar(W.sum());
        MyOwnFloatMatrix scra = MyOwnFloatMatrix.Eyes(q, q);
        for (int i = 0; i < N; i++) {
            for (int alpha = 0; alpha < q; alpha++) {
                for (int beta = 0; beta < q; beta++) {
                    m_Pij_true.set(i, i, alpha, beta, Pi_true.get(i, alpha) * scra.get(alpha, beta));
                }
            }
        }
        return m_Pij_true;
    }

    private MyOwnFloatMatrix Compute_pcSingle(MyOwnFloatMatrix Pi_true) {
        MyOwnFloatMatrix tmp1 = Pi_true.times(1 - this.pseudocount_weight);
        MyOwnFloatMatrix tmp2 = MyOwnFloatMatrix.Ones(N, q).times(this.pseudocount_weight / q);
        return tmp1.add(tmp2);
    }

    private SuperFloatMatrix Compute_pcPair(SuperFloatMatrix Pij_true) {
        SuperFloatMatrix tmp = Pij_true.clone();
        tmp.timesScalar(1 - this.pseudocount_weight);
//        SuperFloatMatrix tmp1 = SuperFloatMatrix.ones(N, N, q, q);
//        tmp1.timesScalar(this.pseudocount_weight / (q * q));
//        tmp = tmp.add(tmp1);
        tmp.addScalar(pseudocount_weight / (q * q));

        MyOwnFloatMatrix scra = MyOwnFloatMatrix.Eyes(q, q);

        for (int i = 0; i < N; i++) {
            for (int alpha = 0; alpha < q; alpha++) {
                for (int beta = 0; beta < q; beta++) {
                    float val = 0.0f;
                    val = Pij_true.get(i, i, alpha, beta) * (1 - pseudocount_weight);
                    val += scra.get(alpha, beta) * pseudocount_weight / q;
                    tmp.set(i, i, alpha, beta, val);
                }
            }
        }
        return tmp;
    }

    private MyOwnFloatMatrix Compute_C(SuperFloatMatrix Pij, MyOwnFloatMatrix Pi) {
        MyOwnFloatMatrix C = MyOwnFloatMatrix.Ones(N * (q - 1), N * (q - 1));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int alpha = 0; alpha < q - 1; alpha++) {
                    for (int beta = 0; beta < q - 1; beta++) {
                        int r = StaticMethod_DCA.Mapkey(i, alpha, q - 1);
                        int c = StaticMethod_DCA.Mapkey(j, beta, q - 1);
                        float val = Pij.get(i, j, alpha, beta) - Pi.get(i, alpha) * Pi.get(j, beta);
                        C.set(r, c, val);
                    }
                }
            }
        }
        return C;
    }

    private MyOwnFloatMatrix Return_W(MyOwnFloatMatrix C, int i, int j) {
        MyOwnFloatMatrix W = MyOwnFloatMatrix.Ones(q, q);
        for (int offset1 = 0; offset1 < q - 1; offset1++) {
            for (int offset2 = 0; offset2 < q - 1; offset2++) {
                float val = C.get(i * (q - 1) + offset1, j * (q - 1) + offset2);
                W.set(offset1, offset2, val);
            }
        }
        W = W.times(-1);
        W = W.expElementWise();
        return W;
    }
    private ArrayList<MyOwnFloatMatrix> Compute_mu(int i, int j, MyOwnFloatMatrix W, MyOwnFloatMatrix P1) {
        ArrayList<MyOwnFloatMatrix> res = new ArrayList<MyOwnFloatMatrix>();
        float epsilon = 1e-4f;
        float diff = 1.0f;
        MyOwnFloatMatrix mu1 = MyOwnFloatMatrix.Ones(1, q).times(1f / q);
        MyOwnFloatMatrix mu2 = MyOwnFloatMatrix.Ones(1, q).times(1f / q);
        MyOwnFloatMatrix pi = P1.getRow(i);
        MyOwnFloatMatrix pj = P1.getRow(j);
        int count = 0;
        while (diff > epsilon && count < 1000) {

            MyOwnFloatMatrix scra1 = mu2.times(W.transpose());
            MyOwnFloatMatrix scra2 = mu1.times(W);

            MyOwnFloatMatrix new1 = pi.divideElementWise(scra1);
            new1 = new1.times(1f / new1.sum());

            MyOwnFloatMatrix new2 = pj.divideElementWise(scra2);
            new2 = new2.times(1f / new2.sum());

            float max1 = (new1.minus(mu1)).findMaxAbs();
            float max2 = (new2.minus(mu2)).findMaxAbs();
            diff = Math.max(max1, max2);
            mu1 = new1;
            mu2 = new2;
            count++;
        }
        if (count >= 1000) {
            System.err.println("Sth is wrong when compute mu, " +"\t i: "+i+"\t j: "+j);
            System.err.println("Diff: "+ diff);
           
        }
        res.add(mu1);
        res.add(mu2);
        return res;
    }
    private float CalculateDI(int i, int j, MyOwnFloatMatrix W, MyOwnFloatMatrix mu1, MyOwnFloatMatrix mu2, MyOwnFloatMatrix Pia) {
        MyOwnFloatMatrix Pdir = W.timesElementWise(mu1.transpose().times(mu2));
        Pdir = Pdir.times(1f / Pdir.sum());

        MyOwnFloatMatrix Pfac = Pia.getRow(i).transpose().times(Pia.getRow(j));
        MyOwnFloatMatrix DI = Pdir.transpose().times(Pdir.divideElementWise(Pfac).logElementWise());
        return DI.trace();
    }

    public float[][] GetResult() throws IOException {
        int[] W_Km = this.Compute_W();
        // Print W_Km
//        String str = Configuration.DirTest_DCA + this.Name.substring(0, 6);
//        MyIO.WriteToFile(str+".km", W_Km);

        float[] dW_Km = StaticMethod_DCA.Int2Float(W_Km);
        MyOwnFloatMatrix W = new MyOwnFloatMatrix(dW_Km, 1);
        W = W.invElementWise();
//        MyIO.WriteToFile(str+".W", W.getArrayCopy()); 
//        System.err.println("Meff: "+ W.sum());
//        
//        double Meff = W.sum();
        MyOwnFloatMatrix Pi_true = this.Compute_TrueFreqSingle(W);
//        MyIO.WriteToFile(str+".Pi_true", Pi_true.getArrayCopy());

        SuperFloatMatrix Pij_true = this.Compute_TrueFreqPair(Pi_true, W);
//        MyIO.WriteToFile(str + ".Pij_true", Pij_true.getArrayCopy());


        MyOwnFloatMatrix Pi = this.Compute_pcSingle(Pi_true);
//        MyIO.WriteToFile(str + ".Pi", Pi.getArrayCopy());

        SuperFloatMatrix Pij = this.Compute_pcPair(Pij_true);
//        MyIO.WriteToFile(str + ".Pij", Pij.getArrayCopy());

        MyOwnFloatMatrix C = this.Compute_C(Pij, Pi);
//        
//        MyIO.WriteToFile(str + ".C", C.getArrayCopy());

//        System.exit(0);

        MyOwnFloatMatrix invC = C.inverseMyOwnMatrix();
//        MyIO.WriteToFile(str + ".invC", invC.getArrayCopy());
//        System.out.println("Finish write to invC");
//        System.exit(0);

        float[][] res = new float[N * (N - 1) / 2][3];
        int count = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                MyOwnFloatMatrix W_mf = this.Return_W(invC, i, j);
//                if(i==0 && j==1){
//                    MyIO.WriteToFile(str+".0_1.W_mf", W_mf.getArrayCopy());
//                }

                ArrayList<MyOwnFloatMatrix> mu = this.Compute_mu(i, j, W_mf, Pi);
                float di = this.CalculateDI(i, j, W_mf, mu.get(0), mu.get(1), Pi);
                res[count][0] = i; 
                res[count][1] = j;
                res[count][2] = di;
                count++;
//                System.exit(1);
            }
        }

        return res;
    }
    public ArrayList<ColPair_Score> GetResult(HashMap<Integer,String> map) throws IOException{
        ArrayList<ColPair_Score> res = new ArrayList<>();
        float[][] A = this.GetResult();
        for(int i=0; i<A.length; i++){
            String p1 = map.get((int)A[i][0]);
            String p2 = map.get((int)A[i][1]);
            res.add(new ColPair_Score(p1, p2, A[i][2]));
        }
        return res;
    }
    private MyOwnFloatMatrix Compute_JTAP(int i, int j, MyOwnFloatMatrix invC, MyOwnFloatMatrix Pi){
        MyOwnFloatMatrix invC_ij = Return_W(invC, i, j);
        MyOwnFloatMatrix mi = Pi.getRow(i);
        MyOwnFloatMatrix mj = Pi.getRow(j);
        
        MyOwnFloatMatrix mx = MyOwnFloatMatrix.Ones(q, q);
        MyOwnFloatMatrix tmp = ((mi.transpose()).times(mj));
        tmp = tmp.timesElementWise(invC_ij);
        tmp = tmp.times(8f);
        mx = mx.minus(tmp);
        mx = mx.sqrtElementwise();
        mx = mx.minus(MyOwnFloatMatrix.Ones(q, q));
         tmp = ((mi.transpose()).times(mj)).times(4f);
        mx = mx.divideElementWise(tmp);
        return mx;
    }
    private MyOwnFloatMatrix Compute_JBA(int i, int j, MyOwnFloatMatrix invC, MyOwnFloatMatrix Pi){
        MyOwnFloatMatrix invC_ij = Return_W(invC, i, j);
        MyOwnFloatMatrix mi = Pi.getRow(i);
        MyOwnFloatMatrix mj = Pi.getRow(j);
        
        MyOwnFloatMatrix mimj = mi.transpose().times(mj);
        MyOwnFloatMatrix CC = invC_ij.timesElementWise(invC_ij);
        
        MyOwnFloatMatrix m1 = MyOwnFloatMatrix.Ones(q, q);
        m1 = m1.minus(mi.timesElementWise(mi));
        MyOwnFloatMatrix m2 = MyOwnFloatMatrix.Ones(q, q);
        m2 = m2.minus(mj.timesElementWise(mj));
        
        MyOwnFloatMatrix mx = m1.transpose().times(m2);
        mx = mx.times(4f).timesElementWise(CC);
        mx = mx.add(MyOwnFloatMatrix.Ones(q, q)).sqrtElementwise();
        
        MyOwnFloatMatrix T1 = mx.clone();
        T1 = (T1.divideElementWise(invC_ij)).times(0.5f);
        T1 = T1.minus(mimj);
        
        MyOwnFloatMatrix T2 = mx.clone();
        MyOwnFloatMatrix tmp = mimj.times(invC_ij).times(2f);
        T2 = T2.minus(tmp);
        T2 = T2.times(T2);
        T2 = T2.minus(CC.times(4f));
        T2 = T2.sqrtElementwise();
        T2 = T2.divideElementWise(invC_ij).times(0.5f);
        
        MyOwnFloatMatrix T = T1.minus(T2);
        T = T.atanhElementwise();
        T = T.times(-1f);
        return T;
    }
    
}
