package NMI;

import java.util.HashMap;

public class H2r {

    /*Computes the U(i,j) value for the given column-pair (i,j)*/
    public static double evaluate(String columnOne, String columnTwo, String mode) {

        char[] alphabet = new char[] {};
//        if ( mode.equals("REDUCED") ) {
//            alphabet = ReducedAlphabet.getCategories();
//         }
        if ( mode.equals("STANDARD") ) {
            alphabet = Alphabet.getAlphabet();
        }
        else {
            System.out.println("Non-applicable mode. Computation aborted.");
            System.exit(1);
        }
        
        int sequences = Math.min(columnOne.length(), columnTwo.length());

        if (sequences < 125) //If the MSA is too small to generate reliable results abort
        {
            return -2.0;
        }

        HashMap<Character, Integer> index = new HashMap<Character, Integer>();
        double[] aminoCounterOne = new double[alphabet.length];
        double[] aminoCounterTwo = new double[alphabet.length];
        double[][] pairCounter = new double[alphabet.length][alphabet.length];

        /*This initializes the HashMap (index) which holds the index of each single amino-acid in the counter arrays (A = 0, R = 1, ...)*/
        for (int i = 0; i < alphabet.length; ++i) {
            index.put(alphabet[i], i);
        }
        /*This part initializes the arrays with 0.0 in order to make sure that the actual counting gets not messed up*/
        for (int i = 0; i < alphabet.length; ++i) {
            aminoCounterOne[i] = 0.0;
            aminoCounterTwo[i] = 0.0;
            for (int j = 0; j < alphabet.length; ++j) {
                pairCounter[i][j] = 0.0;
            }
        }
        /*This part does the actual counting*/
        int numberOfPairs = 0;
        for (int i = 0; i < sequences; ++i) {
            char a = columnOne.charAt(i);
            char b = columnTwo.charAt(i);
            if (index.containsKey(a) && index.containsKey(b)) { //Tests whether both characters are indeed amino-acids (i.e. no gaps)
                aminoCounterOne[index.get(a)] += 1.0;
                aminoCounterTwo[index.get(b)] += 1.0;
                pairCounter[index.get(a)][index.get(b)] += 1.0;
                ++numberOfPairs;
            }
        }

        if (numberOfPairs < 125) //Abort if the MSA is too small to generate reliable results
        {
            return -2.0;
        }


        double Hi = Entropy.entropy(aminoCounterOne);
        double Hj = Entropy.entropy(aminoCounterTwo);

        double Hij = Entropy.jointEntropy(pairCounter);

        if (Hi < 0.0 || Hj < 0.0) {
            return -2.0;
        }

        if ((Hi + Hj) == 0) {
            return -1.0;
        }

        if ((2 * ((Hi + Hj - Hij) / (Hi + Hj))) > 1) {
            return -3.0;
        }

        return (2 * ((Hi + Hj - Hij) / (Hi + Hj)));

    }

}
