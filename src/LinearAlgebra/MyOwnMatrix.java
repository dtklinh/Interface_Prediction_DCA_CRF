/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LinearAlgebra;

import Jama.Matrix;
import NMI.Dsm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author t.dang
 */
public class MyOwnMatrix extends FloatMatrix{
    public MyOwnMatrix(float[][] X){
        super(X);
    }
    public MyOwnMatrix(float[] X, int i){
        super(X,i);
    }
    
    public void print2Screen(){
        for(int i=0; i<this.getRowDimension(); i++){
            for(int j=0; j<this.getColumnDimension(); j++){
                System.out.print(this.get(i, j)+"\t");
            }
            System.out.println();
        }
    }
    public MyOwnMatrix expElementWise(){
        float[][] X = this.getArrayCopy();
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                X[i][j] = (float)Math.exp(X[i][j]);
                
            }
        }
        return new MyOwnMatrix(X);
    }
    public MyOwnMatrix logElementWise(){
        float[][] X = this.getArrayCopy();
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                X[i][j] = (float)Math.log(X[i][j]);
            }
        }
        return new MyOwnMatrix(X);
    }
    public MyOwnMatrix invElementWise(){
        float[][] X = this.getArrayCopy();
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                X[i][j] = 1f/(X[i][j]);
            }
        }
        return new MyOwnMatrix(X);
    }
    public static MyOwnMatrix random(int m, int n){
        return new MyOwnMatrix(FloatMatrix.random(m, n).getArrayCopy());
    }
    public MyOwnMatrix getRow(int idx){
        FloatMatrix X = this.getFloatMatrix(idx, idx, 0, this.getColumnDimension()-1);
        return new MyOwnMatrix(X.getArrayCopy());
    }
    public MyOwnMatrix getMyOwnMatrix(int r1, int r2, int c1, int c2){
        FloatMatrix X = this.getFloatMatrix(r1, r2, c1, c2);
        return new MyOwnMatrix(X.getArrayCopy());
    }
    public void setRow(int idx, float[] X){
        if(X.length!=this.getColumnDimension()
                || idx<0 || idx>=this.getRowDimension()){
            System.err.println("mismatch in dim when setrow");
            return;
        }
        for(int i=0; i<this.getColumnDimension(); i++){
            this.set(idx, i, X[i]);
        }
    }
    public void setRow(int idx, MyOwnMatrix X){
        if(X.getRowDimension()!=1 || X.getColumnDimension()!=this.getColumnDimension()
                || idx<0 || idx>=this.getRowDimension()){
            System.err.println("mismatch dim when setting row by matrix");
            return;
        }
        this.setFloatMatrix(idx, idx, 0, this.getColumnDimension()-1, X);
    }
    public MyOwnMatrix getColumn(int idx){
        FloatMatrix X = this.getFloatMatrix(0, this.getRowDimension()-1, idx, idx);
        return new MyOwnMatrix(X.getArrayCopy());
    }
    public void setColumn(int idx, MyOwnMatrix X){
        this.setFloatMatrix(0, this.getRowDimension()-1, idx, idx, X);
    }
    public void setColumn(int idx, float[]X){
        MyOwnMatrix tmp = new MyOwnMatrix(X, X.length);
        this.setColumn(idx, tmp);
    }
    public static MyOwnMatrix Zeros(int m, int n){
        float[][] d = new float[m][n];
        return new MyOwnMatrix(d);
    }
    public static MyOwnMatrix Ones(int m, int n){
        float[][] d = new float[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                d[i][j] = 1f;
            }
        }
        return new MyOwnMatrix(d);
    }
//    public void setArray(double[][] arr){
//        this = new MyOwnMatrix(arr);
//    }
    public static MyOwnMatrix Eyes(int m, int n){
        float[][] d = new float[m][n];
        int min = Math.min(m, n);
        for(int i=0; i<min; i++){
            d[i][i] = 1f;
        }
        return new MyOwnMatrix(d);
    }
    public float sum(){
        float s =0f;
        int R = this.getRowDimension();
        int C = this.getColumnDimension();
        for(int i=0; i<R; i++){
            for(int j=0; j<C; j++){
                s += this.get(i, j);
            }
        }
        return s;
    }
    public float[] sumColumn(){
        int dim_col = this.getColumnDimension();
        int dim_row = this.getRowDimension();
        float[] res = new float[dim_col];
        float[][]A = this.getArrayCopy();
        for(int c=0; c<dim_col; c++){
            for(int r=0; r<dim_row; r++){
                res[c] += A[r][c];
            }
        }
        return res;
    }
    public double sumColumn(int idx){
        double s = 0.0;
        if(idx<0 || idx>=this.getColumnDimension()){
            System.err.println("Out of range");
            System.exit(1);
        }
        for(int i=0; i<this.getRowDimension(); i++){
            s += this.get(i, idx);
        }
        return s;
    }
    @Override
 public MyOwnMatrix times(float x){
        FloatMatrix tmp = this.timesEquals(x);
        return new MyOwnMatrix(tmp.getArrayCopy());
    }
    
    public MyOwnMatrix times(MyOwnMatrix X){
        FloatMatrix P = new FloatMatrix(this.getArrayCopy());
        FloatMatrix Q = new FloatMatrix(X.getArrayCopy());
        P = P.times(Q);
        return new MyOwnMatrix(P.getArrayCopy());
    }
    public MyOwnMatrix timesElementWise(MyOwnMatrix X){
        if(this.getColumnDimension()!=X.getColumnDimension() 
                || this.getRowDimension()!=X.getRowDimension()){
            System.err.println("mismatch dim when times elements wise");
            return null;
        }
        float[][] P = this.getArrayCopy();
        float[][] Q = X.getArrayCopy();
        for(int i=0; i<P.length; i++){
            for(int j=0; j<P[0].length; j++){
                P[i][j] = P[i][j]*Q[i][j];
            }
        }
        return new MyOwnMatrix(P);
    }
    public MyOwnMatrix divideElementWise(MyOwnMatrix X){
        MyOwnMatrix Y = X.invElementWise();
        return this.timesElementWise(Y);
    }
    public MyOwnMatrix add(MyOwnMatrix X){
        FloatMatrix P = new FloatMatrix(this.getArrayCopy());
        FloatMatrix Q = new FloatMatrix(X.getArrayCopy());
        P = P.plus(Q);
        return new MyOwnMatrix(P.getArrayCopy());
    }
    public MyOwnMatrix minus(MyOwnMatrix X){
        MyOwnMatrix tmp = new MyOwnMatrix(X.getArrayCopy());
        MyOwnMatrix P = new MyOwnMatrix(this.getArrayCopy());
        return P.add(tmp.times(-1));
    }
    
    public MyOwnMatrix inverseMyOwnMatrix(){
//        Matrix P = new Matrix(this.getArrayCopy());
//        P = P.inverse();
        return new MyOwnMatrix(this.inverse().getArrayCopy());
        //return new MyOwnMatrix(P.getArrayCopy());
    }
    @Override
    public MyOwnMatrix transpose(){
        FloatMatrix tmp = new FloatMatrix(this.getArrayCopy());
        tmp = tmp.transpose();
        return new MyOwnMatrix(tmp.getArrayCopy());
    }
    public float findMaxAbs(){
        float[] X = this.getRowPackedCopy();
        float max = 0f;
        for(int i=0; i<X.length; i++){
            if(Math.abs(X[i])>max){
                max = Math.abs(X[i]);
            }
        }
        return max;
    }
    public MyOwnMatrix normalize(){
        return this.times(1f/this.sum());
    }
    public static MyOwnMatrix fromPairScore2Matrix(String path2file, float epsilon) throws IOException{
        List<String> lst = utils.Utils.file2list(path2file);
        int N = (int) ((Math.sqrt(8*lst.size()+1)+1)/2);
        if(N*(N-1)/2 != lst.size()){
            System.err.println("Error: problem with length");
            System.exit(1);
        }
        
        // find median
        ArrayList<Float> lst_score = new ArrayList<>();
        
        
        float[][] mtr = new float[N][N];
        for(String s: lst){
            String[] arr = s.split("\\s+");
            int R = (int)Double.parseDouble(arr[0]);
            int C = (int)Double.parseDouble(arr[1]);
            if(Math.abs(R-C)<=4) continue;
            float val = Float.parseFloat(arr[2]);
            mtr[R][C] = val;
            mtr[C][R] = val;
            lst_score.add(val);
        }
        Collections.sort(lst_score);
        float median = lst_score.get(lst_score.size()/2);
        for(int i=0; i<N-1;i++){
            for(int j=i+1; j<N; j++){
                if(mtr[i][j]<median){
                    mtr[i][j] = 0f;
                    mtr[j][i] = 0f;
                }
            }
        }
        for(int i=0; i<N;i++){
            mtr[i][i] = epsilon;
        }
        return new MyOwnMatrix(mtr);
    }
    public boolean checkSymetric(){
        if(this.getColumnDimension()!=this.getRowDimension())
            return false;
        for(int i=0; i<this.getColumnDimension()-1; i++){
            for(int j=i+1; j<this.getColumnDimension(); j++){
                if(this.get(i, j)!=this.get(j, i))
                    return false;
            }
        }
        return true;
    }
    public void makeTransitionMatrix(){
        float[][] a = this.getArray();
        for(int i=0; i<a.length;i++){
            float s = 0f;
            for(int j=0; j<a[i].length;j++){
                s += a[i][j];
            }
            for(int j=0; j<a[i].length;j++){
                a[i][j] = a[i][j]/s;
            }
        }
        
    }
    public double absSum(){
        double s =0.0;
        int R = this.getRowDimension();
        int C = this.getColumnDimension();
        for(int i=0; i<R; i++){
            for(int j=0; j<C; j++){
                s += Math.abs(this.get(i, j));
            }
        }
        return s;
    }
    public void normalizeColumn(){
        float[][] A = this.getArray();
        float[] sum_col = this.sumColumn();
        for(int i=0; i<A.length; i++){
            for(int j=0;j<A[0].length;j++){
                A[i][j] = A[i][j]/sum_col[j];
            }
        }
    }
    public void makeDSM(){
        float[][] A = this.getArray();
        A = Dsm.processMatrix(A);
    }
}
