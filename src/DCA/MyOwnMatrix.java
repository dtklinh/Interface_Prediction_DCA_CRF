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
public class MyOwnMatrix extends Matrix{
    public MyOwnMatrix(double[][] X){
        super(X);
    }
    public MyOwnMatrix(double[] X, int i){
        super(X,i);
    }
    
    
    public MyOwnMatrix expElementWise(){
        double[][] X = this.getArrayCopy();
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                X[i][j] = Math.exp(X[i][j]);
            }
        }
        return new MyOwnMatrix(X);
    }
    public MyOwnMatrix logElementWise(){
        double[][] X = this.getArrayCopy();
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                X[i][j] = Math.log(X[i][j]);
            }
        }
        return new MyOwnMatrix(X);
    }
    public MyOwnMatrix invElementWise(){
        double[][] X = this.getArrayCopy();
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                X[i][j] = 1.0/(X[i][j]);
            }
        }
        return new MyOwnMatrix(X);
    }
    public static MyOwnMatrix random(int m, int n){
        return new MyOwnMatrix(Matrix.random(m, n).getArrayCopy());
    }
    public MyOwnMatrix getRow(int idx){
        Matrix X = this.getMatrix(idx, idx, 0, this.getColumnDimension()-1);
        return new MyOwnMatrix(X.getArrayCopy());
    }
    public MyOwnMatrix getMyOwnMatrix(int r1, int r2, int c1, int c2){
        Matrix X = this.getMatrix(r1, r2, c1, c2);
        return new MyOwnMatrix(X.getArrayCopy());
    }
    public void setRow(int idx, double[] X){
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
        this.setMatrix(idx, idx, 0, this.getColumnDimension()-1, X);
    }
    public MyOwnMatrix getColumn(int idx){
        Matrix X = this.getMatrix(0, this.getRowDimension()-1, idx, idx);
        return new MyOwnMatrix(X.getArrayCopy());
    }
    public void setColumn(int idx, MyOwnMatrix X){
        this.setMatrix(0, this.getRowDimension()-1, idx, idx, X);
    }
    public void setColumn(int idx, double[]X){
        MyOwnMatrix tmp = new MyOwnMatrix(X, X.length);
        this.setColumn(idx, tmp);
    }
    public static MyOwnMatrix Zeros(int m, int n){
        double[][] d = new double[m][n];
        return new MyOwnMatrix(d);
    }
    public static MyOwnMatrix Ones(int m, int n){
        double[][] d = new double[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                d[i][j] = 1.0;
            }
        }
        return new MyOwnMatrix(d);
    }
    public static MyOwnMatrix Eyes(int m, int n){
        double[][] d = new double[m][n];
        int min = Math.min(m, n);
        for(int i=0; i<min; i++){
            d[i][i] = 1.0;
        }
        return new MyOwnMatrix(d);
    }
    public double Sum(){
        double s =0.0;
        int R = this.getRowDimension();
        int C = this.getColumnDimension();
        for(int i=0; i<R; i++){
            for(int j=0; j<C; j++){
                s += this.get(i, j);
            }
        }
        return s;
    }
    @Override
 public MyOwnMatrix times(double x){
        Matrix tmp = this.timesEquals(x);
        return new MyOwnMatrix(tmp.getArrayCopy());
    }
    
    public MyOwnMatrix times(MyOwnMatrix X){
        Matrix P = new Matrix(this.getArrayCopy());
        Matrix Q = new Matrix(X.getArrayCopy());
        P = P.times(Q);
        return new MyOwnMatrix(P.getArrayCopy());
    }
    public MyOwnMatrix timesElementWise(MyOwnMatrix X){
        if(this.getColumnDimension()!=X.getColumnDimension() 
                || this.getRowDimension()!=X.getRowDimension()){
            System.err.println("mismatch dim when times elements wise");
            return null;
        }
        double[][] P = this.getArrayCopy();
        double[][] Q = X.getArrayCopy();
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
        Matrix P = new Matrix(this.getArrayCopy());
        Matrix Q = new Matrix(X.getArrayCopy());
        P = P.plus(Q);
        return new MyOwnMatrix(P.getArrayCopy());
    }
    public MyOwnMatrix minus(MyOwnMatrix X){
        return this.add(X.times(-1));
    }
    @Override
    public MyOwnMatrix inverse(){
        Matrix P = new Matrix(this.getArrayCopy());
        P = P.inverse();
        return new MyOwnMatrix(P.getArrayCopy());
    }
    @Override
    public MyOwnMatrix transpose(){
        Matrix tmp = new Matrix(this.getArrayCopy());
        tmp = tmp.transpose();
        return new MyOwnMatrix(tmp.getArrayCopy());
    }
    public double findMaxAbs(){
        double[] X = this.getRowPackedCopy();
        double max = 0.0;
        for(int i=0; i<X.length; i++){
            if(Math.abs(X[i])>max){
                max = Math.abs(X[i]);
            }
        }
        return max;
    }
    public MyOwnMatrix normalize(){
        return this.times(1.0/this.Sum());
    }
}
