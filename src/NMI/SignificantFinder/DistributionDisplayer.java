/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NMI.SignificantFinder;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mgueltas
 */
public class DistributionDisplayer {

       public static void distribution(ArrayList<Double> test) {

        double max = Collections.max(test);
        double min = Collections.min(test);

        System.out.println("max :" + max + " min : " + min);
//        System.out.println("max-min :" + (max - min));
        int step = (int) Math.floor(max / 0.05);
        double array[] = {0.0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0, 1.1};
//        double array[] = new double[step + 2];
        double step1 = min;
        for (int i = 0; i < array.length; i++) {
            array[i] = step1;
            step1 += 0.05;
        }
        for (int i = 0; i < array.length - 2; i++) {
            int counter = 0;
            for (int j = 0; j < test.size(); j++) {
                if (test.get(j) >= array[i] & test.get(j) < array[i + 1]) {
                    counter++;
                }
            }
//            System.out.println("["+array[i]+"- "+ "["+array[i+1]+"] ; "+(int) counter);
            System.out.println((int) counter);
        }

    }
}
