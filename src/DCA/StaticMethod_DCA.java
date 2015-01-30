/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DCA;

import Jama.Matrix;
//import MyJama.MyMatrix;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author t.dang
 */
public class StaticMethod_DCA {

    public static int[] getFrequencyOfAA(ArrayList<Integer> lst) {
        int[] res = new int[21];
        for (int i = 0; i < 21; i++) {
            int count = Collections.frequency(lst, i);
            res[i] = count;
        }
        return res;
    }

    public static int Mapkey(int i, int offset, int width) {
        int res = i * (width) + offset;
        return res;
    }

    public static double[][] Return_W(double[][] C, int i, int j, int width) {

        double[][] m = new double[width + 1][width + 1];
        for (int offset1 = 0; offset1 < width; offset1++) {
            for (int offset2 = 0; offset2 < width; offset2++) {
                m[offset1][offset2] = C[i * width + offset1][j * width + offset2];
            }
        }
        return m;
    }
    public static double[][] InvMatrix(double[][] m){
        Matrix mx = new Matrix(m);
        Matrix n = mx.inverse();
        return n.getArrayCopy();
    }
    public static double[][] ExpMatrix_Component(double[][] m){
        double[][] mtx = m.clone();
        for(int i=0; i<m.length; i++){
            for(int j=0; j<m[0].length; j++){
                mtx[i][j] = Math.exp(m[i][j]);
            }
        }
        return mtx;
    }
    public static double[][] Negative_Matrix(double[][] m){
        double[][] mtx = m.clone();
        for(int i=0; i<m.length; i++){
            for(int j=0; j<m[0].length; j++){
                mtx[i][j] = -m[i][j];
            }
        }
        return mtx;
    }
    public static MyOwnMatrix DivideComponentWise(MyOwnMatrix X, MyOwnMatrix Y){
        if(X.getRowDimension()!= Y.getRowDimension() || X.getColumnDimension()!=Y.getColumnDimension()){
            return null;
        }
        double[][] arr_X = X.getArrayCopy();
        double[][] arr_Y = Y.getArrayCopy();
        for(int i=0; i<arr_X.length;i++){
            for(int j=0; j<arr_X[0].length; j++){
                arr_X[i][j] = arr_X[i][j]/arr_Y[i][j];
            }
        }
        return new MyOwnMatrix(arr_X);
    }
//    public static MyMatrix Normalize(MyMatrix X){
//        double[][] arr = X.getArrayCopy();
//        double sum = 0.0;
//        for(int i=0; i<arr.length; i++){
//            for(int j=0; j<arr[0].length; j++){
//                sum += arr[i][j];
//            }
//        }
//        for(int i=0; i<arr.length; i++){
//            for(int j=0; j<arr[0].length; j++){
//                arr[i][j] = arr[i][j]/sum;
//            }
//        }
//        return new MyMatrix(arr);
//    }
//    public static double Diff(MyMatrix X, MyMatrix Y){
//        MyMatrix Z = X.minus(Y);
//        double max = 0.0;
//        
//        double[][] arr = Z.getArray();
//        for(int i=0; i<arr.length; i++){
//            for(int j=0; j<arr[0].length; j++){
//                if(max < Math.abs(arr[i][j])){
//                    max = Math.abs(arr[i][j]);
//                }
//            }
//        }
//        return max;
//    }
//    public static MyMatrix Log_ComponentWise(MyMatrix X){
//        double[][] m = X.getArrayCopy();
//        for(int i=0; i<m.length; i++){
//            for(int j=0; j<m[0].length; j++){
//                m[i][j] = Math.log(m[i][j]);
//            }
//        }
//        return new MyMatrix(m);
//    }
//    public static MyMatrix Multiply_ComponentWise(MyMatrix X, MyMatrix Y){
//        if(X.getRowDimension()!= Y.getRowDimension() || X.getColumnDimension()!=Y.getColumnDimension()){
//            System.err.println("Matrix multiple mismatch");
//            return null;
//        }
//        double[][] arr_X = X.getArrayCopy();
//        double[][] arr_Y = Y.getArrayCopy();
//        for(int i=0; i<arr_X.length;i++){
//            for(int j=0; j<arr_X[0].length; j++){
//                arr_X[i][j] = arr_X[i][j]*arr_Y[i][j];
//            }
//        }
//        return new MyMatrix(arr_X);
//    }
    public static boolean CheckNormalizeRow(double[][] X){
        
        for(int i=0; i<X.length; i++){
            double s = 0.0;
            for(int j=0; j<X[0].length; j++){
                s += X[i][j];
            }
            if(Math.abs(s-1)>0.000001){
                return false;
               // System.exit(1);
            }
        }
        return true;
    }
    public static boolean CheckRedundantColumn(ArrayList<String> lst){
        int len = lst.get(0).length();
        HashSet<String> arr = new HashSet<String>();
        for(int i=0; i<len; i++){
            String s = "";
            for(int j=0; j<lst.size(); j++){
                s = s + lst.get(i).substring(i, i+1);
            }
            arr.add(s);
        }
        if(len!=arr.size()){
            return true;
        }
        return false;
    }
    public static void ConvertToMSA(String filename, String fileout) throws FileNotFoundException, IOException{
        ArrayList<String> lines = MyIO_DCA.ReadFile(filename);
        ArrayList<String> tmp = new ArrayList<String>();
        for(String s: lines){
            s = s.trim();
            String[] arr = s.split("\\s+");
            tmp.add(">"+ arr[0]);
            tmp.add(arr[1]);
        }
        MyIO_DCA.WriteToFile(fileout, tmp);
    }
    public static double[] Int2Double(int[] X){
        double[] tmp = new double[X.length];
        for(int i=0; i<X.length; i++){
            tmp[i] = (double)X[i];
        }
        return tmp;
    }
    public static void printMatrix2Screen(double[][] d){
        for(int i=0; i<d.length; i++){
            for(int j=0; j<d[0].length; j++){
                System.out.print(d[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
