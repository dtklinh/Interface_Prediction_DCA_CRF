/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConvexOptimize;

import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import com.joptimizer.optimizers.NewtonUnconstrained;
import com.joptimizer.optimizers.OptimizationRequest;

//import cern.colt.matrix.linalg.Algebra;
import com.joptimizer.optimizers.NewtonLEConstrainedISP;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author t.dang
 */
public class MyOpt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        RealMatrix Pmatrix = new Array2DRowRealMatrix(new double[][] { 
				{ 1.68, 0.34, 0.38 },
				{ 0.34, 3.09, -1.59 }, 
				{ 0.38, -1.59, 1.54 } });
		RealVector qVector = new ArrayRealVector(new double[] { 0.018, 0.025, 0.01 });

		// Objective function
		double theta = 0.01522;
		RealMatrix P = Pmatrix.scalarMultiply(theta);
		RealVector q = qVector.mapMultiply(-1);
		PDQuadraticMultivariateRealFunction objectiveFunction = new PDQuadraticMultivariateRealFunction(P.getData(), q.toArray(), 0);

		OptimizationRequest or = new OptimizationRequest();
		or.setF0(objectiveFunction);
		or.setInitialPoint(new double[] { 0.1, 0.1, 0.1 });//LE-infeasible starting point
		or.setA(new double[][] { { 1, 1, 1 } });
		or.setB(new double[] { 1 });

		// optimization
		NewtonLEConstrainedISP opt = new NewtonLEConstrainedISP();
		opt.setOptimizationRequest(or);
		int returnCode = opt.optimize();
                double[] sol = opt.getOptimizationResponse().getSolution();
		for(int i=0; i<sol.length; i++){
                    System.out.println(sol[i]);
                }
		
    }
}
