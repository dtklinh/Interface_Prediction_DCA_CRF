/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticMethods;

/**
 *
 * @author t.dang
 */
public class CorrectionMethod {
    // Average Product Correction
    public static double[][] APC(double[][] A){
        int nRows = A.length;
        int nCols = A[0].length;
        double[] meanCols = new double[nCols];
        double[] meanRows = new double[nRows];
        double mean = 0;
        for(int i=0; i<nRows;i++){
            for(int j=0; j<nCols; j++){
                meanRows[i] += A[i][j];
                mean += A[i][j];
            }
            meanRows[i] = meanRows[i]/nCols;
        }
        mean = mean/(nCols*nRows);
        for(int i=0; i<nCols; i++){
            for(int j=0; j<nRows; j++){
                meanCols[i] += A[j][i];
            }
            meanCols[i] = meanCols[i]/nRows;
        }
        double[][] res = new double[nRows][nCols];
        for(int i=0; i<nRows; i++){
            for(int j=0; j<nCols; j++){
                res[i][j] = A[i][j] -(meanRows[i]*meanCols[j]/mean);
            }
        }
        return res;
    }
}
