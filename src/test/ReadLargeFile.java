/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Common.MyIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author t.dang
 */
public class ReadLargeFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        String file = "TestDCA/DCA/10MH_A.Pij_true";
        ArrayList<String> lst = MyIO.ReadLines(file);
        int n = 5;
        int idx = 49;
//        for(int i=lst.size()-5; i<lst.size(); i++){
//            System.out.println(lst.get(i));
//        }
        System.out.println(lst.get(6867));
//        System.out.println("Size: "+ lst.size());
    }
}
