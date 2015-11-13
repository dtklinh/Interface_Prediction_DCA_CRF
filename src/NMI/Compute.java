package NMI;

import Common.FastaSequence;
import Common.ColPairAndScores.ColPair_Score;
import java.util.ArrayList;

public class Compute {

    /*This takes a list of columns (from one protein), computes the u-value of every*
     *possible column-pair and returns an ascending sorted list containing these column-pairs*/
    public static ArrayList<ColPair_Score> compute( ArrayList<String> columns ) {

        ArrayList<ColPair_Score> pairs = new ArrayList<ColPair_Score>();
//        String mode = columns.get(0);
        String mode = "STANDARD";
        
        for (int i = 0; i < columns.size()-1; ++i) {
            for (int j = i + 1; j < columns.size(); ++j) {
                double uValue = H2r.evaluate(columns.get(i), columns.get(j), mode);
                pairs.add(new ColPair_Score(String.valueOf(i), String.valueOf(j), uValue));
//                if (uValue >= 0.0) {
//                    int place = 0;
//                    boolean toAdd = true;
//                    while (place < pairs.size() && toAdd) {
//                        if (uValue < pairs.get(place).getValue()) {
//                            ColumnPair columnPair = new ColumnPair(i, j, uValue, columns.get(i), columns.get(j));
//                            pairs.add(place, columnPair);
//                            toAdd = false;
//                        } else {
//                            ++place;
//                        }
//                    }
//                    if (toAdd) {
//                        ColumnPair columnPair = new ColumnPair(i, j, uValue, columns.get(i), columns.get(j));
//                        pairs.add(columnPair);
//                    }
//                }
            }
        }

        return pairs;

    }

    /*This takes a list of columns (from one protein), computes the modified (via the dsm) u-value of every*
     *possible column-pair and returns an ascending sorted list containing these column-pairs*/
    public static ArrayList<ColPair_Score> computeModified( ArrayList<String> columns, double[][] dsm ) {

    ArrayList<ColPair_Score> pairs = new ArrayList<ColPair_Score>();
//    String mode = columns.get(0);
    String mode = "STANDARD";

    for (int i = 0; i < columns.size()-1; ++i) {
        for (int j = i + 1; j < columns.size(); ++j) {
            double uValue = H2rModified.evaluate(columns.get(i), columns.get(j), dsm, mode);
            pairs.add(new ColPair_Score(String.valueOf(i), String.valueOf(j), uValue));
//            if (uValue >= 0.0) {
//                int place = 0;
//                boolean toAdd = true;
//                while (place < pairs.size() && toAdd) {
//                    if (uValue < pairs.get(place).getValue()) {
//                        ColumnPair columnPair = new ColumnPair(i, j, uValue, columns.get(i), columns.get(j));
//                        pairs.add(place, columnPair);
//                        toAdd = false;
//                    } else {
//                        ++place;
//                    }
//                }
//                if (toAdd) {
//                    ColumnPair columnPair = new ColumnPair(i, j, uValue, columns.get(i), columns.get(j));
//                    pairs.add(columnPair);
//                }
//            }
        }
    }

    return pairs;

    }
    
    /////////////////////////////////////////////////
//    public static ArrayList<ColumnPair> compute(String Dir2MSA, String MSAFile){
//        FastaSequence f = new FastaSequence(Dir2MSA+MSAFile);
//        ArrayList<String> lst_cols = f.getAllColumn();
//        
//    }

}
