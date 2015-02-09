package NMI.SignificantFinder;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Thomas Franke
 * @contact t.fra@me.com
 * @work Georg-August-University Goettingen
 **/
public class SignificanceFinder_old {

    static int samples = 8000; //How many artificial column pairs to draw?
    static int betaDrawings = 4000; //How many beta-distributed values to draw for the p-value estimation?
    public static double orgVariance;
    public static double[] orgBoundaries = new double[2]; // they are filled by Class MathOps method estimateBoundaries
    public static double[] modifiedBoundaries = new double[2]; // they are filled by Class MathOps method estimateBoundaries
    static int controller = 0; // size of background pairs

    public static int process(ArrayList<Double> realPairs, ArrayList<Double> fakePairs ,double tFDR, String mode) {
        try {
            System.out.println("Computing H2R significance level...");
            if (realPairs.isEmpty()) {
                System.out.println("No valid pairs found in the MSA.");
                return 0;
            }
            ArrayList<Double> realValues = new ArrayList<Double>();
       //     ArrayList<Double> artificialValues = new ArrayList<Double>();
            Random rnd = new Random();
   //         int s = samples;
//            while (s > 0) {
//                double tmp = Sampler.buildColumnPairByShuffling(realPairs.get(rnd.nextInt(realPairs.size())), mode);
//                if (tmp >= 0) {
//                    artificialValues.add(tmp);
//                    --s;
//                }
//            }
            realValues.addAll(realPairs);
//            for (int i = 0; i < realPairs.size(); ++i) {
//                realValues.add(realPairs.get(i).getU());
//            }
//            double mean_fake = MathOps.getMean(fakePairs);
            double mean = MathOps.getMean(realValues);
            System.out.println("Expectation : " + mean);
            double variance =MathOps.getVariance(realValues, mean)/3.;//  MathOps.getVariance(artificialValues, MathOps.getMean(artificialValues));
//            double variance = MathOps.getVariance(fakePairs, mean_fake);
//            variance = variance*variance;
            orgVariance = variance;
            System.out.println("Variance : " + variance);
            double alpha = MathOps.estAlpha(mean, variance);
            double beta = MathOps.estBeta(mean, variance);
            System.out.println("ALPHA : " + alpha);
            System.out.println("BETA : " + beta);
            ArrayList<Double> pValues = new ArrayList<Double>();
            for (int i = 0; i < realPairs.size(); ++i) {
                double temp = realPairs.get(i);
                int bd = betaDrawings;
                int counter = 0;
                while (bd > 0) {
                    if (MathOps.getBetaDistrValue(alpha, beta) >= temp) {
                        ++counter;
                    }
                    --bd;
                }
                double p = ((double) counter / (double) betaDrawings);
//                temp.setP(p);
                pValues.add(p);
            }
            DistributionDisplayer.distribution(pValues);
            
//            if (MathOps.performChiSquareTest(pValues)) {
//                ArrayList<ColumnPair> significant = new ArrayList<ColumnPair>();
//                double tau = MathOps.getTau(pValues, tFDR, MathOps.getGamma(pValues, "unmodified"));
//                if (tau < 0) {
//                    System.out.println("Failed to compute lambda_1 and lambda_2 with sufficient distance between each other.");
//                    return 0;
//                }
//                for (int i = 0; i < realPairs.size(); ++i) {
//                    ColumnPair temp = realPairs.get(i);
//                    if (temp.getP() <= tau) {
//                        significant.add(temp);
//                    }
//                }
//
//
//                for (int i = 0; i < realPairs.size(); ++i) {
//                    ColumnPair temp = realPairs.get(i);
////                    System.out.print("Controller: "+temp.getP());
//                    if (temp.getP() >= orgBoundaries[0] && temp.getP() <= orgBoundaries[1]) {
//                        controller++;
//                    }
//                }
//                System.out.print("Controller: " + controller);
//
//                Output.writePValues(pValues, "pValues.out");
//                Output.writeSignificantPairs(significant, "significant.out");
////                return significant.size();
////            } else {
////                return 0;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
//
//    public static int processModified(ArrayList<ColumnPair> realPairs, int significanceLevel, double tFDR, double[][] dsm, String mode) {
//        try {
//            System.out.println("Computing SAMI2R significance level...");
//            if (realPairs.isEmpty()) {
//                System.out.println("No valid pairs found in the MSA.");
//                return 0;
//            }
//            ArrayList<Double> realValues = new ArrayList<Double>();
//            ArrayList<Double> artificialValues = new ArrayList<Double>();
//            Random rnd = new Random();
//            int s = samples;
//            /**
//             * Variance estimator
//             */
//            while (s > 0) {
////                double tmp = Sampler.buildColumnPairByShufflingModified(realPairs.get(rnd.nextInt(significanceLevel)), dsm, mode);
//                double tmp = Sampler.buildColumnPairByShufflingModified(realPairs.get(significanceLevel + rnd.nextInt(controller)), dsm, mode);
////                 double tmp = Sampler.buildColumnPairByShufflingModified(realPairs.get(rnd.nextInt(significanceLevel)), dsm, mode);
//                if (tmp >= 0) {
//                    artificialValues.add(tmp);
//                    --s;
//                }
//            }
//            for (int i = 0; i < realPairs.size(); ++i) {
//                realValues.add(realPairs.get(i).getU());
//            }
//            double mean = MathOps.getMean(realValues);
//            System.out.println("Expectation (SAMI2R): " + mean);
//            boolean check = true;
//            boolean boundraryCheck = true;
//            boolean boundraryCheck2 = true;
//            double step = 0.0;
//            int control = 0;
//            double variance = 0;
//            while (check) {
//
////                double variance = orgVariance + (step * orgVariance);// MathOps.getVariance(artificialValues, MathOps.getMean(artificialValues));
//                if (control == 0) {
////                    variance = orgVariance;
//                    variance = MathOps.getVariance(artificialValues, MathOps.getMean(artificialValues));
//                    System.out.println("Variance (SAMI2R): " + variance + " Variance (H2r): " + orgVariance);
//                    if (variance >= orgVariance) {
//                        variance = orgVariance;
//                    }
//                } else {
//                    variance -= variance * 0.1;
//                }
//                System.out.println("Variance (SAMI2R): " + variance + "  Step: " + control + " %");
//                double alpha = MathOps.estAlpha(mean, variance);
//                System.out.println("ALPHA : " + alpha);
//                double beta = MathOps.estBeta(mean, variance);
//                System.out.println("BETA : " + beta);
//                ArrayList<Double> pValues = new ArrayList<Double>();
//                for (int i = 0; i < realPairs.size(); ++i) {
//                    ColumnPair temp = realPairs.get(i);
//                    int bd = betaDrawings;
//                    int counter = 0;
//                    while (bd > 0) {
//                        if (MathOps.getBetaDistrValue(alpha, beta) >= temp.getU()) {
//                            ++counter;
//                        }
//                        --bd;
//                    }
//
//                    double p = ((double) counter / (double) betaDrawings);
////                System.out.println("P-Value Calculated");
//                    temp.setP(p);
//                    pValues.add(p);
//                }
//                System.out.println("**************** 3 : ");
//                if (MathOps.performChiSquareTest(pValues)) {
//                    ArrayList<ColumnPair> significant = new ArrayList<ColumnPair>();
//                    double tau = MathOps.getTau(pValues, tFDR, MathOps.getGamma(pValues, "modified"));
//                    if (tau < 0) {
//                        System.out.println("Failed to compute lambda_1 and lambda_2 with sufficient distance between each other.");
//                        return 0;
//                    }
//                    System.out.println("**************** 4 : ");
//                    for (int i = 0; i < realPairs.size(); ++i) {
//                        ColumnPair temp = realPairs.get(i);
//                        if (temp.getP() <= tau) {
//                            significant.add(temp);
//                        }
//                    }
//
//                    System.out.println((orgBoundaries[0]) + " - " + modifiedBoundaries[0] + " && " + (orgBoundaries[1]) + "- " + modifiedBoundaries[1]);
//                    int pInterval[] = Output.writePValues(pValues, "pValuesModified.out");
//                    int c1 = pInterval[pInterval.length - 1];
//                    int c2 = pInterval[pInterval.length - 2];
//                    int c3 = pInterval[pInterval.length - 3];
//                    int c4 = pInterval[pInterval.length - 4];
//                    int c5 = pInterval[pInterval.length - 5];
//                    int c6 = pInterval[pInterval.length - 6];
//                    int c7 = pInterval[pInterval.length - 7];
//                    int c8 = pInterval[pInterval.length - 8];
//
//                    boolean uniformDist = MathOps.chiSquareTestForPVaule(pValues, modifiedBoundaries[0], modifiedBoundaries[1]);
////                    System.out.println("Uniform test :" +uniformDist);
//                    if (c1 > (c2 + c3 + c4 + c5) && boundraryCheck) {
//                        System.out.println(c1 + " : " + c2 + " : " + c3 + " : " + c4 + " : " + c5 + " : " + c6);
////                        if (uniformDist) {
//                        check = false;
//                        return significant.size();
////                        }
////                        else{ check = true;
////                            control += 1;}
//                    } else {
//                        boundraryCheck = false;
//                        if ((uniformDist || c1 > (c2 + c3 + c4 + c5 + c6 + c7)) & modifiedBoundaries[0] <= 0.351) {
////                        if (uniformDist) {
//                            System.out.println("Uniform test ::" + uniformDist);
//                            check = false;
//                            return significant.size();
//                        } else {
//                            check = true;
//                            control += 1;
//                        }
//                        System.out.println(c1 + " :> " + c2 + " : " + c3 + " : " + c4 + " : " + c5 + " : " + c6 + " : " + c7 + " : " + c8);
//
//                    }
//
//
////                    if(orgBoundaries[0]==0.0 && orgBoundaries[1]==1.0 ){
////                        check=false;
////                        return significant.size();
////                    }
////
////
////                    if (boundraryCheck2) {
////                        if (orgBoundaries[0] > modifiedBoundaries[0] && orgBoundaries[1] < modifiedBoundaries[1]) {
////                            step += 0.1;
////                            boundraryCheck = false;
////                        } else {
////                            control = 1;
////                        }
////                    }
////                    if (control == 1 && !boundraryCheck) {
////                        check = false;
////                        return significant.size();
////                    }
////
////                    if (boundraryCheck) {
////                        if (orgBoundaries[0] == modifiedBoundaries[0] && orgBoundaries[1] == modifiedBoundaries[1]) {
////                            System.out.println((orgBoundaries[0]) + " = " + modifiedBoundaries[0] + " && " + (orgBoundaries[1]) + " = " + modifiedBoundaries[1]);
////                            step += 0.1;
////                            boundraryCheck2 = false;
////                        } else if (orgBoundaries[0] < modifiedBoundaries[0]-0.05 && orgBoundaries[1] > modifiedBoundaries[1]+0.05) {
////                            step += 0.1;
////                            System.out.println((orgBoundaries[0] ) + " < " + (modifiedBoundaries[0]- 0.05) + " && " +
////                                               (orgBoundaries[1] ) + " > " + (modifiedBoundaries[1]+ 0.05));
////                            boundraryCheck2 = false;
////                        } else {
////                            check = false;
////                            boundraryCheck2 = false;
////                            Output.writePValues(pValues, "pValuesModified.out");
////                            return significant.size();
////                        }
////                    }
////
////                    if (step > 1) {
////                        check = false;
////                        return significant.size();
////                    }
////
//
////                    check=false;
////                     return significant.size();
//////                Output.writeSignificantPairs(significant, "significant.out");
//
//                } else {
//                    return 0;
//                }
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
}
