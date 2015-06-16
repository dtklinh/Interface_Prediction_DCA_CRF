package NMI;


public class Entropy {

     /*Computes the entropyDouble H(i) of the given column*/
    public static double entropyDouble(double[] aminoCounter) {

        double sum = 0.0;
        double result = 0.0;
        double frequency = 0.0;

        for (int i = 0; i < aminoCounter.length; ++i) {
            sum += aminoCounter[i];
        }

        for (int i = 0; i < aminoCounter.length; ++i) {
            if (aminoCounter[i] != 0) {
                frequency = (aminoCounter[i] / sum);
//                if (frequency >= 0.95) //If one amino-acid happens to occur more than 95% of the time the column is regarded as strictly conserved and therefore ignored
//                {
//                    return -1.0;		 //This return value identifies strictly conserved columns
//                }
                if(frequency!=0)
                    result += (frequency * Math.log(frequency));
            }
        }

        result *= -1;

        return result;

    }
    public static float entropy(float[] aminoCounter) {

        float sum = 0.0f;
        float result = 0.0f;
        float frequency = 0.0f;

        for (int i = 0; i < aminoCounter.length; ++i) {
            sum += aminoCounter[i];
        }

        for (int i = 0; i < aminoCounter.length; ++i) {
            if (aminoCounter[i] != 0) {
                frequency = (aminoCounter[i] / sum);
//                if (frequency >= 0.95) //If one amino-acid happens to occur more than 95% of the time the column is regarded as strictly conserved and therefore ignored
//                {
//                    return -1.0;		 //This return value identifies strictly conserved columns
//                }
                if(frequency!=0)
                    result += (frequency * Math.log(frequency));
            }
        }

        result *= -1;

        return result;

    }

    /*Computes the entropyDouble H(i,j) of the given column-pair i,j*/
    public static double jointEntropy(double[][] pairCounter) {

        double sum = 0.0;
        double result = 0.0;
        double frequency = 0.0;

        for (int i = 0; i < pairCounter.length; ++i) {
            for (int j = 0; j < pairCounter[i].length; ++j) {
                sum += pairCounter[i][j];
            }
        }

        for (int i = 0; i < pairCounter.length; ++i) {
            for (int j = 0; j < pairCounter[i].length; ++j) {
                if (pairCounter[i][j] != 0) {
                    frequency = (pairCounter[i][j] / sum);
                    result += (frequency * Math.log(frequency));
                }
            }
        }

        result *= -1;

        return result;

    }

}
