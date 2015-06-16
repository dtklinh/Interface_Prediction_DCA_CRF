/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistic.ParameterEstimation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author t.dang
 */
public class MLGaussian {
    private List<RealVector> ArrayX;
    public MLGaussian(List<RealVector> arr){
        ArrayX = new ArrayList<>();
        ArrayX.addAll(arr);
    }
    public RealVector estimateMean(){
        int dim = ArrayX.get(0).getDimension();
        RealVector tmp = new ArrayRealVector(dim);
        for(int i=0; i<ArrayX.size(); i++){
            tmp = tmp.add(ArrayX.get(i));
        }
        tmp = tmp.mapDivide(ArrayX.size());
        return tmp;
    }
    public RealMatrix estimateCovariance(){
        RealVector Muy_ML = estimateMean();
        int dim = ArrayX.get(0).getDimension();
        int N = ArrayX.size();
        double[][] d = new double[dim][dim];
        RealMatrix tmp = new Array2DRowRealMatrix(d);
        for(int i=0; i<N; i++){
            RealVector v = ArrayX.get(i).subtract(Muy_ML);
            tmp = tmp.add(v.outerProduct(v));
        }
        tmp = tmp.scalarMultiply(1.0/N);
        return tmp;
    }
    public RealMatrix estimateCorrelation(){
        RealMatrix mxCov = estimateCovariance();
        double[][] Data = mxCov.getData();
        int dim = Data.length;
        double[][] Arr = new double[dim][dim];
        for(int i=0; i<dim-1; i++){
            for(int j=i+1; j<dim; j++){
                Arr[i][j] = Data[i][j]/Math.sqrt(Data[i][i]*Data[j][j]);
            }
        }
        return new Array2DRowRealMatrix(Arr);
    }
}
