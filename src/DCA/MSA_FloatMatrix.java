/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

//import MyJama.MyMatrix;
//import MyJama.SuperFloatMatrix;
import Common.ColPairAndScores.ColPair_Score;
import LinearAlgebra.MyOwnFloatMatrix;
import LinearAlgebra.SuperFloatMatrix;
import NMI.Compute;
import Common.Configuration;
import Common.FastaSequence;
import Common.MyIO;
import Common.StaticMethod;
import Jama.Matrix;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.ResidueNumber;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileParser;
import org.biojava.bio.structure.io.PDBFileReader;
import utils.Utils;

/**
 *
 * @author t.dang
 */
public class MSA_FloatMatrix {

    private final int q = 21;
    private final float pseudocount_weight = 0.5f;  // lamda
    private final float theta = 0.2f;
    private int M; // number of sequence in MSA_FloatMatrix
    private int N; // lenght of protein
    private int[][] AlgnMx; // numeric alignment of MSA_FloatMatrix
    private String Name;
    private String Dir;

    public MSA_FloatMatrix(int m, int n, int[][] mx, String name) {
        M = m;
        N = n;
        AlgnMx = mx;
        Name = name;
    }

    public MSA_FloatMatrix(int m, int n, String dir, String filename) throws FileNotFoundException, IOException {
        M = m;
        N = n;
        Dir = dir;
        AlgnMx = MyIO_DCA.ReturnAlignment(dir + filename);
        Name = filename.substring(0, 6);
    }

    public MSA_FloatMatrix(String dir, String filename) throws FileNotFoundException, IOException {
        Dir = dir;
        Name = filename.substring(0, 6);
        String endfile = StaticMethod.FindEndName(dir);
        AlgnMx = MyIO_DCA.ReturnAlignment(dir + Name+endfile);
        M = AlgnMx.length;
        N = AlgnMx[0].length;
        
    }
    public MSA_FloatMatrix(String dir, String filename, int[][] mx){
        AlgnMx = mx;
        M = AlgnMx.length;
        N = AlgnMx[0].length;
        Dir = dir;
        Name = filename.substring(0, 6);
    }
    public MSA_FloatMatrix(MSA_FloatMatrix m){
        this.AlgnMx = m.getAlgnMx();
        this.Dir = m.getDir();
        this.M = m.getM();
        this.N = m.getN();
        this.Name = m.getName();
    }

    public int[] Compute_W() { // vector of Km in the paper
        int[] mx = new int[getM()];

        for (int i = 0; i < getM(); i++) {
            int dis = 0;
            for (int j = 0; j < getM(); j++) {
                int count = 0;
                for (int k = 0; k < getN(); k++) {
                    if (getAlgnMx()[i][k] != getAlgnMx()[j][k]) {
                        count++;
                    }
                }
                if ((double) count / getN() < theta) {
                    dis++;
                }
            }
//            mx.set(1, i, dis);
            mx[i] = dis;
        }

        return mx;
    }
     public float Compute_M_eff(){
        int[] Km = this.Compute_W();
        float res = 0f;
        for(int i=0; i<Km.length; i++){
            res += 1f/Km[i];
        }
        return res;
    }
// calculate frequency after ajusting weight of sequence (Eq(4))

//    public double[][] Compute_ReweightedFreqSingle(int[] W_Km) {
//        double[][] freq = new double[getN()][q];
//
//        for (int i = 0; i < getN(); i++) {
//            for (int j = 0; j < q; j++) {
//                double d = 0.0;
//                for (int k = 0; k < getM(); k++) {
//                    if (getAlgnMx()[k][i] == j) {
//                        d += 1.0 / W_Km[k];
//                    }
//                }
//                freq[i][j] = d;
//            }
//        }
//        double M_eff = 0.0;
//        for (int i = 0; i < getM(); i++) {
//            M_eff += 1.0 / W_Km[i];
//        }
//        for (int i = 0; i < getN(); i++) {
//            for (int j = 0; j < q; j++) {
//                freq[i][j] = (freq[i][j] + pseudocount_weight / q) / (pseudocount_weight + M_eff);
//            }
//        }
//
//        return freq;
//    }
    // compute true frequency Pi_true

    public MyOwnFloatMatrix Compute_TrueFreqSingle(MyOwnFloatMatrix W) {
        MyOwnFloatMatrix Pi_true = MyOwnFloatMatrix.Zeros(getN(), q);
        for (int j = 0; j < getM(); j++) {
            for (int i = 0; i < getN(); i++) {
                Pi_true.set(i, getAlgnMx()[j][i], Pi_true.get(i, getAlgnMx()[j][i]) + W.get(0, j));
            }
        }
        Pi_true = Pi_true.times(1f / W.sum());
        return Pi_true;

    }
    // compute Pi with pseudo count

    public MyOwnFloatMatrix Compute_pcSingle(MyOwnFloatMatrix Pi_true) {
        MyOwnFloatMatrix tmp1 = Pi_true.times(1 - this.pseudocount_weight);
        MyOwnFloatMatrix tmp2 = MyOwnFloatMatrix.Ones(getN(), q).times(this.pseudocount_weight / q);
        return tmp1.add(tmp2);
    }
    // calculate frequency of pair after adjusting weight (Eq(5))

//    public double[][][][] Compute_ReweightedFreqpair(int[] W_Km) {
//        double[][][][] freq = new double[getN()][getN()][q][q];
//        for (int i1 = 0; i1 < getN() - 1; i1++) {
//            for (int i2 = i1 + 1; i2 < getN(); i2++) {
//                for (int j1 = 0; j1 < q; j1++) {
//                    for (int j2 = 0; j2 < q; j2++) {
//                        for (int k = 0; k < getM(); k++) {
//                            if (getAlgnMx()[k][i1] == j1 && getAlgnMx()[k][i2] == j2) {
//                                freq[i1][i2][j1][j2] += 1.0 / W_Km[k];
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        double M_eff = 0.0;
//        for (int i = 0; i < getM(); i++) {
//            M_eff += 1.0 / W_Km[i];
//        }
//        for (int i1 = 0; i1 < getN() - 1; i1++) {
//            for (int i2 = i1 + 1; i2 < getN(); i2++) {
//                for (int j1 = 0; j1 < q; j1++) {
//                    for (int j2 = 0; j2 < q; j2++) {
//                        freq[i1][i2][j1][j2] = (freq[i1][i2][j1][j2] + pseudocount_weight / (q * q)) / (pseudocount_weight + M_eff);
//
//                    }
//                }
//            }
//        }
//        return freq;
//    }

    // compute true frequency of pair columns
    public SuperFloatMatrix Compute_TrueFreqPair(MyOwnFloatMatrix Pi_true, MyOwnFloatMatrix W) {
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
    // compute Pij with pseudo count

    public SuperFloatMatrix Compute_pcPair(SuperFloatMatrix Pij_true) {
        SuperFloatMatrix tmp = Pij_true.clone();
        tmp.timesScalar(1 - this.pseudocount_weight);
//        SuperFloatMatrix tmp1 = SuperFloatMatrix.ones(N, N, q, q);
//        tmp1.timesScalar(this.pseudocount_weight / (q * q));
//        tmp = tmp.add(tmp1);
        tmp.addScalar(pseudocount_weight / (q * q));
        
        MyOwnFloatMatrix scra = MyOwnFloatMatrix.Eyes(q, q);

        for (int i = 0; i < getN(); i++) {
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

    public double[] CalculateMI(double[][] fi, double[][][][] fij) {
        int len = fi.length * (fi.length - 1) / 2;
        double[] MI = new double[len];
        int count = 0;
        for (int i = 0; i < getN() - 1; i++) {
            for (int j = i + 1; j < getN(); j++) {
                double sum = 0.0;
                for (int k1 = 0; k1 < q; k1++) {
                    for (int k2 = 0; k2 < q; k2++) {
                        if (fij[i][j][k1][k2] != 0) {
                            sum += fij[i][j][k1][k2] * Math.log(fij[i][j][k1][k2] / (fi[i][k1] * fi[j][k2]));
                        }
                    }
                }
                MI[count] = sum;
                count++;

            }
        }
        return MI;
    }
    // compute correlation matrix

//    public double[][] Compute_C(double[][][][] Pij, double[][] Pi) {
//        double[][] C = new double[N * (q - 1)][N * (q - 1)];
////        Matrix rd = Matrix.random(N*(q-1), N*(q-1));
////        rd = StaticMethod.Normalize(rd);
////        double[][] C = rd.getArray();
//        
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                for (int AA1 = 0; AA1 < q - 1; AA1++) {
//                    for (int AA2 = 0; AA2 < q - 1; AA2++) {
//                        C[StaticMethod_DCA.Mapkey(i, AA1, q - 1)][StaticMethod_DCA.Mapkey(j, AA2, q - 1)] = Pij[i][j][AA1][AA2] - Pi[i][AA1] * Pi[j][AA2];
//
//                    }
//                }
//            }
//        }
//        return C;
//    }
    // another computation of Matrix C
    public MyOwnFloatMatrix Compute_C(SuperFloatMatrix Pij, MyOwnFloatMatrix Pi) {
        MyOwnFloatMatrix C = MyOwnFloatMatrix.Ones(N * (q - 1), N * (q - 1));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int alpha = 0; alpha < q - 1; alpha++) {
                    for (int beta = 0; beta < q - 1; beta++) {
                        int r = StaticMethod_DCA.Mapkey(i, alpha, q - 1);
                        int c = StaticMethod_DCA.Mapkey(j, beta, q - 1);
//                        if (r == 20 && c == 0) {
//                            System.out.println("i: " + i + " j: " + j + " alpha: " + alpha + " beta: " + beta
//                                    + " >> " + r + " : " + c);
//                            System.out.println("Pij="+Pij.get(i, j, alpha, beta)
//                                    + " : Pi(i,alpha)="+Pi.get(i, alpha)+
//                                    " : Pi(j,beta)="+ Pi.get(j, beta));
//                        }
                        float val = Pij.get(i, j, alpha, beta) - Pi.get(i, alpha) * Pi.get(j, beta);
                        C.set(r, c, val);
                    }
                }
            }
        }
        return C;
    }

//    public double[][] Return_W(double[][] C, int i, int j) {
//
//        double[][] m = new double[q][q];
//        for (int offset1 = 0; offset1 < q - 1; offset1++) {
//            for (int offset2 = 0; offset2 < q - 1; offset2++) {
//                m[offset1][offset2] = C[i * (q - 1) + offset1][j * (q - 1) + offset2];
//            }
//        }
//        return m;
//    }
    public MyOwnFloatMatrix Return_W(MyOwnFloatMatrix C, int i, int j) {
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
    // return Matrix 2 x q, which contain two vectors mu1 and mu2

//    public double[][] Compute_mu(int i, int j, double[][] W, double[][] Pi) {
//        double[][] Exp_W = StaticMethod_DCA.ExpMatrix_Component(StaticMethod_DCA.Negative_Matrix(W));
//        double[][] mu = new double[2][q];
//        // init
//        for (int ii = 0; ii < q; ii++) {
//            mu[1][ii] = 1.0 / q;
//            mu[2][ii] = 1.0 / q;
//        }
//        MyOwnFloatMatrix m_mu = new MyOwnFloatMatrix(mu);
//        MyOwnFloatMatrix m_mu1 = m_mu.getMyOwnMatrix(0, 0, 0, q - 1);
//        MyOwnFloatMatrix m_mu2 = m_mu.getMyOwnMatrix(1, 1, 0, q - 1);
//        MyOwnFloatMatrix m_W = new MyOwnFloatMatrix(Exp_W);
//        MyOwnFloatMatrix m_Pi = new MyOwnFloatMatrix(Pi);
//        MyOwnFloatMatrix m_p1 = m_Pi.getMyOwnMatrix(i, i, 0, q - 1);
//        MyOwnFloatMatrix m_p2 = m_Pi.getMyOwnMatrix(j, j, 0, q - 1);
//        double epsilon = 1e-4;
//        double diff = 1.0;
//        while (diff > epsilon) {
//            MyOwnFloatMatrix scar1 = m_mu2.times(m_W.transpose());
//            MyOwnFloatMatrix scar2 = m_mu1.times(m_W);
//
////            MyOwnFloatMatrix new1 = StaticMethod.DivideComponentWise(m_p1, scar1);
////            MyMatrix new2 = StaticMethod.DivideComponentWise(m_p2, scar2);
//            
//            MyOwnFloatMatrix new1 = m_p1.divideElementWise(scar1);
//            MyOwnFloatMatrix new2 = m_p2.divideElementWise(scar2);
//            
////            new1 = StaticMethod.Normalize(new1);
////            new2 = StaticMethod.Normalize(new2);
//            new1 = new1.times(1.0/new1.sum());
//            new2 = new2.times(1.0/new2.sum());
//
////            double d1 = StaticMethod.Diff(new1, m_mu1);
////            double d2 = StaticMethod.Diff(new2, m_mu2);
//            double d1 = (new1.minus(m_mu1)).findMaxAbs();
//            double d2 = (new2.minus(m_mu2)).findMaxAbs();
//            
//            diff = Math.max(d1, d2);
//
//            m_mu1 = new1;
//            m_mu2 = new2;
//        }
//        for (int ii = 0; ii < q; ii++) {
//            mu[0][ii] = m_mu1.get(0, ii);
//            mu[1][ii] = m_mu2.get(0, ii);
//        }
//        return mu;
//    }
    public ArrayList<MyOwnFloatMatrix> Compute_mu(int i, int j, MyOwnFloatMatrix W, MyOwnFloatMatrix P1) {
        ArrayList<MyOwnFloatMatrix> res = new ArrayList<MyOwnFloatMatrix>();
        float epsilon = 1e-4f;
        float diff = 1.0f;
        MyOwnFloatMatrix mu1 = MyOwnFloatMatrix.Ones(1, q).times(1f / q);
        MyOwnFloatMatrix mu2 = MyOwnFloatMatrix.Ones(1, q).times(1f / q);
        MyOwnFloatMatrix pi = P1.getRow(i);
        MyOwnFloatMatrix pj = P1.getRow(j);
        int count = 0;
        while (diff > epsilon && count < 1000) {
//            System.out.println("Iteration: "+ count+"  :  "+diff);
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
            System.err.println("Sth is wrong when compute mu, " + this.Name+"\t i: "+i+"\t j: "+j);
            System.err.println("Diff: "+ diff);
           
        }
        res.add(mu1);
        res.add(mu2);
        return res;
    }
    // compute Direct Information
//    public double CalculateDI(int i, int j, double[][] W, double[][]mu, double[][] Pi){
////        double tiny = 1.0e-20;
//        double[][] Exp_W = StaticMethod_DCA.ExpMatrix_Component(StaticMethod_DCA.Negative_Matrix(W));
//        MyOwnFloatMatrix m_W = new MyOwnFloatMatrix(Exp_W);
//        MyOwnFloatMatrix m_mu = new MyOwnFloatMatrix(mu);
//        MyOwnFloatMatrix m_mu1 = m_mu.getMyOwnMatrix(0, 0, 0, q-1);
//        MyOwnFloatMatrix m_mu2 = m_mu.getMyOwnMatrix(1, 1, 0, q-1);
//        
//        MyOwnFloatMatrix tmp = m_mu1.transpose().times(m_mu2);
////        MyMatrix Pdir = StaticMethod.Multiply_ComponentWise(m_W, tmp);
//        MyOwnFloatMatrix Pdir = m_W.timesElementWise(tmp);
//        
////        Pdir = StaticMethod.Normalize(Pdir);
//        Pdir = Pdir.normalize();
//        
//        MyOwnFloatMatrix m_Pi = new MyOwnFloatMatrix(Pi);
//        MyOwnFloatMatrix m_p1 = m_Pi.getMyOwnMatrix(i, i, 0, q - 1);
//        MyOwnFloatMatrix m_p2 = m_Pi.getMyOwnMatrix(j, j, 0, q - 1);
//        MyOwnFloatMatrix Pfac = m_p1.transpose().times(m_p2);
//        
////        MyOwnFloatMatrix log = StaticMethod.Log_ComponentWise(StaticMethod.DivideComponentWise(Pdir, Pfac));
////        MyMatrix res = Pdir.transpose().times(log);
//        MyOwnFloatMatrix res = Pdir.transpose().times(Pdir.divideElementWise(Pfac).logElementWise());
//        return res.trace();
//        
//    }

    public float CalculateDI(int i, int j, MyOwnFloatMatrix W, MyOwnFloatMatrix mu1, MyOwnFloatMatrix mu2, MyOwnFloatMatrix Pia) {
        MyOwnFloatMatrix Pdir = W.timesElementWise(mu1.transpose().times(mu2));
        Pdir = Pdir.times(1f / Pdir.sum());

        MyOwnFloatMatrix Pfac = Pia.getRow(i).transpose().times(Pia.getRow(j));
        MyOwnFloatMatrix DI = Pdir.transpose().times(Pdir.divideElementWise(Pfac).logElementWise());
        return DI.trace();
    }
//    public double[] GetResult() throws FileNotFoundException, IOException{
//        double[] res = new double[N*(N-1)/2];
////        int[][] algnmnt = MyIO.ReturnAlignment(filename);
//        int[] W_Km = this.Conpute_W();
////        MyIO.PrintVector(W_Km);System.exit(0);
//        double[][] fi = this.Compute_ReweightedFreqSingle(W_Km);
//        System.err.println("fi is normalize?: "+ StaticMethod_DCA.CheckNormalizeRow(fi));
//        double[][][][] fij = this.Compute_ReweightedFreqpair(W_Km);
//        double[][] C = this.Compute_C(fij, fi);
//        
////        MyIO.PrintMatrix(C);
//        
//        double[][] inv_C = StaticMethod_DCA.InvMatrix(C);
//        int count = 0;
//        for(int i=0; i<N-1; i++){
//            for(int j=i+1; j<N; j++){
//                double[][] W = this.Return_W(inv_C, i, j);
//                double[][] mu = this.Compute_mu(i, j, W, fi);
//                double val = this.CalculateDI(i, j, W, mu, fi);
//                res[count] = val;
//                count++;
//            }
//        }
//        return res;
//    }

    public float[][] GetResult2() throws IOException {
        int[] W_Km = this.Compute_W();
        // Print W_Km
        String str = Configuration.DirTest_DCA + this.Name.substring(0, 6);
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

        float[][] res = new float[getN() * (getN() - 1) / 2][3];
        int count = 0;
        for (int i = 0; i < getN() - 1; i++) {
            for (int j = i + 1; j < getN(); j++) {
                MyOwnFloatMatrix W_mf = this.Return_W(invC, i, j);
//                if(i==0 && j==1){
//                    MyIO.WriteToFile(str+".0_1.W_mf", W_mf.getArrayCopy());
//                }

                ArrayList<MyOwnFloatMatrix> mu = this.Compute_mu(i, j, W_mf, Pi);
                float di = this.CalculateDI(i, j, W_mf, mu.get(0), mu.get(1), Pi);
                res[count][0] = i; // same idx with matlab, must change later
                res[count][1] = j;
                res[count][2] = di;
                count++;
//                System.exit(1);
            }
        }

        return res;
    }
    public String[][] GetResult3(String Dir2PDBSingleChain) throws IOException {
        String endfile = StaticMethod.FindEndName(Dir2PDBSingleChain);
        Structure s = (new PDBFileReader()).getStructure(Dir2PDBSingleChain+Name+endfile);
        Chain c = s.getChain(0);
        List<Group> groups = c.getAtomGroups();
        ArrayList<ResidueNumber> lst_ResidueNum = new ArrayList<>();
        for(int i=0;i<groups.size(); i++){
            lst_ResidueNum.add(groups.get(i).getResidueNumber());
        }
        
        
        int[] W_Km = this.Compute_W();
        // Print W_Km
        String str = Configuration.DirTest_DCA + this.Name.substring(0, 6);
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

        String[][] res = new String[getN() * (getN() - 1) / 2][3];
        int count = 0;
        for (int i = 0; i < getN() - 1; i++) {
            for (int j = i + 1; j < getN(); j++) {
                MyOwnFloatMatrix W_mf = this.Return_W(invC, i, j);
//                if(i==0 && j==1){
//                    MyIO.WriteToFile(str+".0_1.W_mf", W_mf.getArrayCopy());
//                }

                ArrayList<MyOwnFloatMatrix> mu = this.Compute_mu(i, j, W_mf, Pi);
                float di = this.CalculateDI(i, j, W_mf, mu.get(0), mu.get(1), Pi);
                res[count][0] = lst_ResidueNum.get(i).toString(); 
                res[count][1] = lst_ResidueNum.get(j).toString();
                res[count][2] = String.valueOf(di);
                count++;
//                System.exit(1);
            }
        }

        return res;
    }
    public float[][] GetResult2SquareMatrix() throws IOException {
        int[] W_Km = this.Compute_W();
        // Print W_Km
        String str = Configuration.DirTest_DCA + this.Name.substring(0, 6);
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
        System.out.println("Finish compute C Matrix");
//        
//        MyIO.WriteToFile(str + ".C", C.getArrayCopy());
        
//        System.exit(0);
        Pij_true = null; Pij = null;

        
        MyOwnFloatMatrix invC = C.inverseMyOwnMatrix();
//        MyIO.WriteToFile(str + ".invC", invC.getArrayCopy());
//        System.out.println("Finish write to invC");
//        System.exit(0);

//        double[][] res = new double[getN() * (getN() - 1) / 2][3];
        float[][] res = new float[N][N];
        int count = 0;
        for (int i = 0; i < getN() - 1; i++) {
            for (int j = i + 1; j < getN(); j++) {
                MyOwnFloatMatrix W_mf = this.Return_W(invC, i, j);
//                if(i==0 && j==1){
//                    MyIO.WriteToFile(str+".0_1.W_mf", W_mf.getArrayCopy());
//                }

                ArrayList<MyOwnFloatMatrix> mu = this.Compute_mu(i, j, W_mf, Pi);
                float di = this.CalculateDI(i, j, W_mf, mu.get(0), mu.get(1), Pi);
                res[i][j] = di;
                res[j][i] = di;
//                res[count][0] = i;
//                res[count][1] = j;
//                res[count][2] = di;
//                count++;
//                System.exit(1);
            }
        }

        return res;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Dir
     */
    public String getDir() {
        return Dir;
    }

    /**
     * @param Dir the Dir to set
     */
    public void setDir(String Dir) {
        this.Dir = Dir;
    }

    /**
     * @return the M
     */
    public int getM() {
        return M;
    }

    /**
     * @param M the M to set
     */
    public void setM(int M) {
        this.M = M;
    }

    /**
     * @return the N
     */
    public int getN() {
        return N;
    }

    /**
     * @param N the N to set
     */
    public void setN(int N) {
        this.N = N;
    }

    /**
     * @return the AlgnMx
     */
    public int[][] getAlgnMx() {
        return AlgnMx;
    }

    /**
     * @param AlgnMx the AlgnMx to set
     */
    public void setAlgnMx(int[][] AlgnMx) {
        this.AlgnMx = AlgnMx;
        this.M = AlgnMx.length;
        this.N = AlgnMx[0].length;
    }
    
    
    ////////////////// for Compensatory Mutation Finder
    public String[][] NormalizedMutualInformation(double[][] dsm){
        String path = "";
        if(Name.indexOf(".")<0){
            path = Dir + Name + StaticMethod.FindEndName(Dir);
        }
        else{
            path = Dir + Name;
        }
        FastaSequence f = new FastaSequence(path);
        ArrayList<String> lst_cols = f.getAllColumn();
        
        ArrayList<ColPair_Score> unmodified = Compute.compute(lst_cols);
        ArrayList<ColPair_Score> modified = Compute.computeModified(lst_cols, dsm);
        if(unmodified.size()!=modified.size()){
            System.err.println("WRONG");
            System.exit(1);
        }
        String[][] res = new String[unmodified.size()][4];
        for(int i=0; i<unmodified.size(); i++){
            res[i][0] = unmodified.get(i).getP1();
            res[i][1] = unmodified.get(i).getP2();
            res[i][2] = String.valueOf(unmodified.get(i).getScore());
            res[i][3] = String.valueOf(modified.get(i).getScore());
        }
        return res;
    }
    
}
