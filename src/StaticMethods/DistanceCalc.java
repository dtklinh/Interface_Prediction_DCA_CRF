/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticMethods;

import Common.ColPairAndScores.ColPair_Score;
import Common.Configuration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class DistanceCalc {
    public static double DistanceBtwGroupsAnyAtom(Group A, Group B) {
        List<Atom> lst_Atom_A = A.getAtoms();
        List<Atom> lst_Atom_B = B.getAtoms();
        double distance = 100.0;

        for (Atom a : lst_Atom_A) {
            double Radius_A = findVanDerWaalsRadius(a);
            for (Atom b : lst_Atom_B) {
                double Radius_B = findVanDerWaalsRadius(b);
                double dis = DistanceBtwAtoms(a, b);
                if (Configuration.UseVanDerWaals) {
                    dis = dis - Radius_A - Radius_B;
                }
                if (dis < distance) {
                    distance = dis;
                }
            }
        }
        return distance;
    }

    private static double findVanDerWaalsRadius(Atom a) {
        double Radius_C = Configuration.VdW_C;
        double Radius_N = Configuration.VdW_N;
        double Radius_O = Configuration.VdW_O;
        double Radius_S = Configuration.VdW_S;
        double Radius_P = Configuration.VdW_P;
        double Radius_H = Configuration.VdW_H;

        String nameA = a.getName().substring(0, 1);
        double Radius1 = 0;
        if (nameA.equalsIgnoreCase("C")) {
            Radius1 = Radius_C;
        } else if (nameA.equalsIgnoreCase("N")) {
            Radius1 = Radius_N;
        } else if (nameA.equalsIgnoreCase("O")) {
            Radius1 = Radius_O;
        } else if (nameA.equalsIgnoreCase("S")) {
            Radius1 = Radius_S;
        } else if (nameA.equalsIgnoreCase("P")) {
            Radius1 = Radius_P;
        } else if (Character.isDigit(nameA.charAt(0)) || nameA.equalsIgnoreCase("H")) {
            Radius1 = Radius_H;
        } else {
            System.err.println("Not Amino acids :" + a.getFullName());
            System.exit(1);
        }
        return Radius1;
    }
    public static double DistanceBtwGroups(Group A, Group B) throws StructureException{
        if(Configuration.UseCarbonAlpha){
            return DistanceBtwGroups_CarbonAlpha(A, B);
        }
        else{
            return DistanceBtwGroupsAnyAtom(A, B);
        }
    }

    public static double DistanceBtwGroups_CarbonAlpha(Group A, Group B) throws StructureException {
        List<Atom> lst_Atom_A = A.getAtoms();
        List<Atom> lst_Atom_B = B.getAtoms();
        double tmp = -1.0;
        Atom A_CA = ((AminoAcid) A).getCA();
        Atom B_CA = ((AminoAcid) B).getCA();

//        for(Atom a: lst_Atom_A){
//            if(a.getName().equalsIgnoreCase("CA")){
//                A_CA = a;
//            }
//        }
//        for(Atom b: lst_Atom_B){
//            if(b.getName().equalsIgnoreCase("CA")){
//                B_CA = b;
//            }
//        }
        if (A_CA != null && B_CA != null) {
            if (!Configuration.UseVanDerWaals) {
                return DistanceBtwAtoms(A_CA, B_CA);
            } else {
                return (DistanceBtwAtoms(A_CA, B_CA) - findVanDerWaalsRadius(A_CA) - findVanDerWaalsRadius(B_CA));
            }
        } else {
            System.err.println("Wrong in calculate dis btw two amino acids");
            System.exit(1);
            return -1.0d;
        }
//        for (Atom a : lst_Atom_A) {
//            if (A.getType().equalsIgnoreCase("amino")) {
//                    if (!a.getName().equalsIgnoreCase("CA")) {
//                        continue;
//                    }
//            }
//            for (Atom b : lst_Atom_B) {
//                
//                if (B.getType().equalsIgnoreCase("amino")) {
//                    if (b.getName().equalsIgnoreCase("CA")) {
//                        continue;
//                    }
//                }
//                tmp = DistanceBtwAtoms(a, b);
//                break;
//
//            }
//        }
//        return tmp;
    }

    private static double DistanceBtwAtoms(Atom A, Atom B) {
        double dis = 0.0;
        double X1 = A.getX(); //System.out.println("X1: " + X1);
        double Y1 = A.getY(); //System.out.println("Y1: " + Y1);
        double Z1 = A.getZ(); //System.out.println("Z1: " + Z1);
        double X2 = B.getX(); //System.out.println("X2: " + X2);
        double Y2 = B.getY(); //System.out.println("Y2: " + Y2);
        double Z2 = B.getZ(); //System.out.println("Z2: " + Z2);
        dis = Math.pow(X1 - X2, 2) + Math.pow(Y1 - Y2, 2) + Math.pow(Z1 - Z2, 2);
        dis = Math.sqrt(dis);
        //System.out.println("Dis: "+ dis);
        //System.exit(0);
        return dis;
    }
    public static Set<ColPair_Score> calcDistancePairwise(List<Group> lst) throws StructureException{
        Set<ColPair_Score> res = new HashSet<>();
        int len = lst.size();
        for(int i=0; i<len-1;i++){
            Group g1 = lst.get(i);
            for(int j=i+1;j<len;j++){
                Group g2 = lst.get(j);
                double dis = DistanceCalc.DistanceBtwGroups(g1, g2);
                if(g2.getResidueNumber().compareTo(g1.getResidueNumber())>0){
                    res.add(new ColPair_Score(g1.getResidueNumber().toString(), g2.getResidueNumber().toString(), dis));
                }
                else{
                    res.add(new ColPair_Score(g2.getResidueNumber().toString(), g1.getResidueNumber().toString(), dis));
                }
            }
        }
        return res;
    }
}
