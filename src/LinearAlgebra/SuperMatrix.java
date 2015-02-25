/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LinearAlgebra;

/**
 *
 * @author t.dang
 */
public class SuperMatrix {
    private int M1;
    private int M2;
    private int N1;
    private int N2;
    private float[][][][] A;
    public SuperMatrix(int m1, int m2, int n1, int n2){
        this.M1 = m1;
        this.M2 = m2;
        this.N1 = n1;
        this.N2 = n2;
        this.A = new float[m1][m2][n1][n2];
    }
    public SuperMatrix(float[][][][] X){
        this.M1 = X.length;
        this.M2 = X[0].length;
        this.N1 = X[0][0].length;
        this.N2 = X[0][0][0].length;
        this.A = X;
    }
    public SuperMatrix(SuperMatrix X){
        
    }

    /**
     * @return the M1
     */
    public int getM1() {
        return M1;
    }

    /**
     * @param M1 the M1 to set
     */
    public void setM1(int M1) {
        this.M1 = M1;
    }

    /**
     * @return the M2
     */
    public int getM2() {
        return M2;
    }

    /**
     * @param M2 the M2 to set
     */
    public void setM2(int M2) {
        this.M2 = M2;
    }

    /**
     * @return the N1
     */
    public int getN1() {
        return N1;
    }

    /**
     * @param N1 the N1 to set
     */
    public void setN1(int N1) {
        this.N1 = N1;
    }

    /**
     * @return the N2
     */
    public int getN2() {
        return N2;
    }

    /**
     * @param N2 the N2 to set
     */
    public void setN2(int N2) {
        this.N2 = N2;
    }

    /**
     * @return the A
     */
    public float[][][][] getA() {
        return A;
    }

    /**
     * @param A the A to set
     */
    public void setA(float[][][][] A) {
        this.A = A;
        this.M1 = A.length;
        this.M2 = A[0].length;
        this.N1 = A[0][0].length;
        this.N2 = A[0][0][0].length;
    }
    public void set(int m1, int m2, int n1, int n2, float val){
        A[m1][m2][n1][n2] = val;
    }
    public void divideScalar(float d){
        for(int i1=0; i1<M1; i1++){
            for(int i2=0; i2<M2; i2++){
                for(int j1=0; j1<N1; j1++){
                    for(int j2=0; j2<N2; j2++){
                        A[i1][i2][j1][j2] = A[i1][i2][j1][j2]/d;
                    }
                }
            }
        }
    }
    public void timesScalar(float d){
        for(int i1=0; i1<M1; i1++){
            for(int i2=0; i2<M2; i2++){
                for(int j1=0; j1<N1; j1++){
                    for(int j2=0; j2<N2; j2++){
                        A[i1][i2][j1][j2] = A[i1][i2][j1][j2]*d;
                    }
                }
            }
        }
    }
    public SuperMatrix add(SuperMatrix X){
        if(X.M1!=this.M1 || X.M2!=this.M2 || X.N1!=this.N1 || X.N2!=this.N2){
            System.err.println("mismatch dim when adding supermatrix");
            return null;
        }
        float[][][][] a = this.getArrayCopy();
        float[][][][] b = X.getArrayCopy();
        for(int i1=0; i1<M1; i1++){
            for(int i2=0; i2<M2; i2++){
                for(int j1=0; j1<N1; j1++){
                    for(int j2=0; j2<N2; j2++){
                        a[i1][i2][j1][j2] += b[i1][i2][j1][j2];
                    }
                }
            }
        }
        return new SuperMatrix(a);
    }
    public void addScalar(float v){
        for(int i1=0; i1<M1; i1++){
            for(int i2=0; i2<M2; i2++){
                for(int j1=0; j1<N1; j1++){
                    for(int j2=0; j2<N2; j2++){
                        A[i1][i2][j1][j2] += v;
                    }
                }
            }
        }
    }
    public float[][][][] getArrayCopy(){
        float[][][][] tmp = new float[M1][M2][N1][N2];
        for(int i1=0; i1<M1; i1++){
            for(int i2=0; i2<M2; i2++){
                for(int j1=0; j1<N1; j1++){
                    for(int j2=0; j2<N2; j2++){
                        tmp[i1][i2][j1][j2] = A[i1][i2][j1][j2];
                    }
                }
            }
        }
        return tmp;
    }
    public static SuperMatrix ones(int m1, int m2, int n1, int n2){
        float[][][][] tmp = new float[m1][m2][n1][n2];
        for(int i1=0; i1<m1; i1++){
            for(int i2=0; i2<m2; i2++){
                for(int j1=0; j1<n1; j1++){
                    for(int j2=0; j2<n2; j2++){
                        tmp[i1][i2][j1][j2] = 1f;
                    }
                }
            }
        }
        return new SuperMatrix(tmp);
    }
    public SuperMatrix clone(){
        float[][][][] a = this.getArrayCopy();
        return new SuperMatrix(a);
    }
    public float get(int m1, int m2, int n1, int n2){
        return A[m1][m2][n1][n2];
    }
    
    
}
