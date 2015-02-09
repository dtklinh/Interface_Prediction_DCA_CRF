package NMI.SignificantFinder;

import java.lang.Double;
import java.util.ArrayList;
import java.util.Random;
import umontreal.iro.lecuyer.probdist.ChiSquareDist;

/**
 * @author Thomas Franke
 * @contact t.fra@me.com
 * @work Georg-August-University Goettingen
 **/
public class MathOps {

    public static double getMean(ArrayList<Double> values) {
        double result = 0.0;
        for (int i = 0; i < values.size(); ++i) {
            result += values.get(i);
        }
        return (result / (double) values.size());
    }

    public static double getVariance(ArrayList<Double> values, double mean) {
        double result = 0.0;
        for (int i = 0; i < values.size(); ++i) {
            result += Math.pow((values.get(i) - mean), 2);
        }
        return Math.sqrt(result / (double) (values.size() - 1));
    }

    public static double estAlpha(double mean, double variance) {
        return (mean * (((mean * (1 - mean)) / (variance * variance)) - 1));
    }

    public static double estBeta(double mean, double variance) {
        return ((1 - mean) * (((mean * (1 - mean)) / (variance * variance)) - 1));
    }

    public static double getBetaDistrValue(double alpha, double beta) {
        Random generator = new Random();
        double x, A, B, C, L, mue, sigma, s, u;
        boolean accept = false;
        A = alpha - 1;
        B = beta - 1;
        C = A + B;
        L = C * Math.log(C);
        mue = A / C;
        sigma = 0.5 / Math.sqrt(C);
        do {
            do {
                s = generator.nextGaussian();
                x = s * sigma + mue;
            } while (x < 0 || x > 1);
            u = Math.random();
            if (Math.log(u) <= (A * Math.log(x / A) + B * Math.log((1 - x) / B) + L + 0.5 * s * s)) {
                accept = true;
            }

        } while (!accept);
        return x;
    }

    public static double getTau(ArrayList<Double> pValues, double tFDR, double gamma) {
        if (gamma < 0) {
            return -1.0;
        }
        double tau = 0.0;
        double lastTau = 0.0;
        int tests = 1000;
        for (int i = 0; i < tests; ++i) {
            lastTau = tau;
            tau += (1 / (double) tests);
            double counter = 0.0;
            for (int j = 0; j < pValues.size(); ++j) {
                if (pValues.get(j) < tau) {
                    ++counter;
                }
            }
            double FDR = gamma * pValues.size() * (tau / counter);
            if (FDR > tFDR) {
                return lastTau;
            }
        }
        return -1.0;
    }

    public static double getGamma(ArrayList<Double> pValues, String type) {
        double[] boundaries = estimateBoundaries(pValues, type);
        double lambda_1 = boundaries[0];
        double lambda_2 = boundaries[1];
        if ((lambda_2 - lambda_1) < 0.18) {
            return -1.0;
        }
        double counter = 0.0;
        for (int i = 0; i < pValues.size(); ++i) {
            if (pValues.get(i) >= lambda_1 && pValues.get(i) <= lambda_2) {
                ++counter;
            }
        }
        return (counter / (pValues.size() * (lambda_2 - lambda_1)));
    }

    public static double[] estimateBoundaries(ArrayList<Double> pValues, String type) {
        double[] boundaries = new double[]{0.0, 1.0};
        double lambda_1 = 0.2;
        double lambda_2 = 0.8;
        double gamma = 0.0;
        double last_gamma = 0.0;
        boolean accept = false;
        while (!accept && (lambda_1 < lambda_2)) {
            double counter = 0.0;
            for (int i = 0; i < pValues.size(); ++i) {
                if (pValues.get(i) >= lambda_1 && pValues.get(i) <= lambda_2) {
                    ++counter;
                }
            }
            gamma = (counter / (pValues.size() * (lambda_2 - lambda_1)));
            double variance = last_gamma - gamma;
            if (variance < 0.01 && variance > -0.01) {
                boundaries[0] = lambda_1 - 0.05;
                boundaries[1] = lambda_2 + 0.05;
                accept = true;
            } else {
                last_gamma = gamma;
                lambda_1 += 0.05;
                lambda_2 -= 0.05;
            }
        }
        System.out.print("Type is ");
        if (type.equalsIgnoreCase("unmodified")) {
            System.out.println(type);
            SignificanceFinder_old.orgBoundaries[0] = boundaries[0];
            SignificanceFinder_old.orgBoundaries[1] = boundaries[1];
        } else if (type.equalsIgnoreCase("modified")) {
            System.out.println(type);
            SignificanceFinder_old.modifiedBoundaries[0] = boundaries[0];
            SignificanceFinder_old.modifiedBoundaries[1] = boundaries[1];
        }

        System.out.println("lambda_1: " + boundaries[0] + " and lambda_2: " + boundaries[1]);
        return boundaries;
    }

    public static boolean performChiSquareTest(ArrayList<Double> pValues) {
        int[] interval = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < pValues.size(); ++i) {
            double p = pValues.get(i);
            for (int j = 1; j < 21; ++j) {
                if (p == 1) {
                    interval[19]++;
                    break;
                } else if (((j - 1) * 0.05) <= p && p < (j * 0.05)) {
                    interval[j - 1]++;
                    break;
                }
            }
        }
        double n_0 = (1 / (double) interval.length) * pValues.size();
        double chiSquare = 0.0;
        for (int i = 0; i < interval.length; ++i) {
            chiSquare += (Math.pow((interval[i] - n_0), 2) / n_0);
        }
//        System.out.println("Chi^2: " + chiSquare);
        if (chiSquare < 38.58) {
            return false;
        }
        return true;
    }

    public static boolean chiSquareTestForPVaule(ArrayList<Double> pValues, double lamda1, double lamda2) {

        double array[] = {0.0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0};
        ArrayList<Double> intervalList = new ArrayList<Double>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i] >= lamda1 && array[i] <= lamda2+0.001) {
                intervalList.add(array[i]);
            }
        }
//        for (int i = 0; i < intervalList.size(); ++i) {
//            System.out.println("Interval  :" + intervalList.get(i));
//        }
        double[] interval = new double[intervalList.size()];
        double sum = 0;
        for (int j = 0; j < intervalList.size() - 1; j++) {
            double counter = 0;
            for (int i = 0; i < pValues.size(); ++i) {
                double p = pValues.get(i);
                if (p >= intervalList.get(j) && p < intervalList.get(j + 1)) {
                    counter++;
                }
            }
            interval[j] = counter;
//            sum+=counter;
        }

        for (int i = 0; i < interval.length; ++i) {
            sum += interval[i];
        }
        System.out.println("summe :" + sum + " länge :" + (interval.length));
        double n_0 = (1 / ((double) (interval.length-1))) * sum;
        double chiSquare = 0.0;
        System.out.println("E(X):" + n_0);
        for (int i = 0; i < interval.length-1; ++i) {
//            System.out.println("interval :" + interval[i]);
            chiSquare += (Math.pow((interval[i] - n_0), 2) / n_0);
        }
        System.out.println(chiSquare);
//        System.out.println("Chi^2: " + chiSquare);
        if (chiSquare > (ChiSquareDist.inverseF(interval.length - 2, 0.99)+3)) {
            return false;
        }
        return true;
    }
}
