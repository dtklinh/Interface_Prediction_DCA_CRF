/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protein;

import Common.ColPair_Score;
import Common.Configuration;
import Common.MyIO;
import Common.StaticMethod;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
// for single chain and complex too
public class NewProtein_Pairwise_ScoreRef {

    private double InteracDistance;
    private ArrayList<ColPair_Score> Lst3DDistance;
    private HashMap<String, Double> LstRASAScore;
    private final double Threshold = 0.15;

    public NewProtein_Pairwise_ScoreRef(String Path2ThreeDimFile, double dis) throws FileNotFoundException, IOException {
        this.InteracDistance = dis;
        this.Lst3DDistance = MyIO.read2ColPair(Path2ThreeDimFile, "\\s+");
    }

    public NewProtein_Pairwise_ScoreRef(String Path2ThreeDimFile, String Path2RASA, double dis) throws FileNotFoundException, IOException {
        this.InteracDistance = dis;
        this.Lst3DDistance = MyIO.read2ColPair(Path2ThreeDimFile, "\\s+");
        this.LstRASAScore = MyIO.readRASAFile(Path2RASA);
    }

    public boolean isPairOnInterfaceOrContact(ColPair_Score c) {
        String p1 = c.getP1();
        String p2 = c.getP2();
        for (ColPair_Score s : Lst3DDistance) {
            if (s.getP1().equalsIgnoreCase(p1) && s.getP2().equalsIgnoreCase(p2)
                    && s.getScore() <= InteracDistance) {
                return true;
            }
        }
        return false;
    }

    public boolean isOneResidueOnInterface(ColPair_Score c) {
        String p1 = c.getP1();
        String p2 = c.getP2();
        for (ColPair_Score s : Lst3DDistance) {
            if (s.getScore() <= InteracDistance) {
                if (s.getP1().equalsIgnoreCase(p1) || s.getP2().equalsIgnoreCase(p2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPairOnSurface(ColPair_Score c) {
        if (LstRASAScore == null) {
            System.err.println("Don't have rasa core");
            System.exit(1);
        }
        String p1 = c.getP1();
        String p2 = c.getP2();
        if (LstRASAScore.get(p1) > Threshold && LstRASAScore.get(p2) > Threshold) {
            return true;
        }
        return false;
    }

    public String getPairPosition(ColPair_Score c) {
        if (LstRASAScore == null) {
            System.err.println("Don't have rasa core");
            System.exit(1);
        }
        String p1 = c.getP1();
        String p2 = c.getP2();
        if (LstRASAScore.get(p1) > Threshold && LstRASAScore.get(p2) > Threshold) {
            return "Surf-Surf";
        } else if (LstRASAScore.get(p1) > Threshold && LstRASAScore.get(p2) <= Threshold) {
            return "Surf-Core";
        } else if (LstRASAScore.get(p1) <= Threshold && LstRASAScore.get(p2) > Threshold) {
            return "Surf-Core";
        } else {
            return "Core-Core";
        }
    }

    public void printPairPosition(List<ColPair_Score> lst) {
        int ss = 0, sc = 0, cc = 0;
        for (ColPair_Score c : lst) {
            String s = getPairPosition(c);
            if (s.equalsIgnoreCase("Surf-Surf")) {
                ss++;
            } else if (s.equalsIgnoreCase("Surf-Core")) {
                sc++;
            } else {
                cc++;
            }
            System.out.println("DCA: " + c.getScore() + " Pos: " + s);
        }
        System.out.println("Statistics");
        System.out.println("SS: " + (double) ss / lst.size());
        System.out.println("SC: " + (double) sc / lst.size());
        System.out.println("CC: " + (double) cc / lst.size());
    }

    public List<ColPair_Score> filterCandidateSS(List<ColPair_Score> lst) {
        int len = lst.size();
        for (int i = len - 1; i >= 0; i--) {
            ColPair_Score col = lst.get(i);
            if (!isPairOnSurface(col)) {
                lst.remove(i);
            }
        }
        return lst;
    }

    public int countFirstColumnContact() {
        HashSet<String> MySet = new HashSet<>();
        for (ColPair_Score s : Lst3DDistance) {
            if (s.getScore() <= InteracDistance) {
                MySet.add(s.getP1());
            }
        }
        return MySet.size();
    }

    public int countPairContact(int NeighborDistance, boolean OnlyOnSurface) {
        int count = 0;
        for (ColPair_Score col : Lst3DDistance) {
            if (!col.IsNeighbor(NeighborDistance) && col.getScore() <= InteracDistance) {
                if (!OnlyOnSurface) {
                    count++;
                } else {
                    if (isPairOnSurface(col)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int countPairContactOnSurface(int NeighborDistance) {
        int count = 0;
        for (ColPair_Score col : Lst3DDistance) {
            if (!col.IsNeighbor(NeighborDistance) && col.getScore() <= InteracDistance
                    && isPairOnSurface(col)) {
                count++;
            }
        }
        return count;
    }

    public int countTPFromPair(List<ColPair_Score> LstCandidate) {
        HashSet<String> MyInterface = new HashSet<>();
        System.out.println("Interfaces");
        for (ColPair_Score s : Lst3DDistance) {
            if (s.getScore() <= InteracDistance) {
                MyInterface.add(s.getP1());
            }
        }

        for (String s : MyInterface) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("My Predictions");
        HashSet<String> MySet = new HashSet<>();
        for (ColPair_Score c : LstCandidate) {
            MySet.add(c.getP1());
            MySet.add(c.getP1());
        }
        for (String s : MySet) {
            System.out.print(s + " ");
        }

        MyInterface.retainAll(MySet);
        System.out.println();
        System.out.println("Intersection");
        for (String s : MyInterface) {
            System.out.print(s + " ");
        }
        System.out.println();
//        System.exit(1);
        return MyInterface.size();
    }

    public static int countNumResinPair(List<ColPair_Score> LstCandidate) {
        HashSet<String> MySet = new HashSet<>();
        for (ColPair_Score c : LstCandidate) {
            MySet.add(c.getP1());
            MySet.add(c.getP1());
        }
        return MySet.size();
    }

    public int countTPwithConnDeg(List<ColPair_Score> LstCandidate) {
        ArrayList<Residue> LstResidue = StaticMethod.toLstResidue(LstCandidate);
        Collections.sort(LstResidue);
        double percent = Configuration.Percent;
        int len = LstResidue.size();
        List<Residue> PredictedPostive = LstResidue.subList((int) (len * (1-percent)), len);
        HashSet<String> MySet = new HashSet<>();
        for (Residue r : PredictedPostive) {
            MySet.add(r.getResidueNumber());
        }
        System.out.println();
        System.out.println("My Prediction");
        for (String s : MySet) {
            System.out.print(s + " ");
        }

        HashSet<String> MyInterface = new HashSet<>();
        for (ColPair_Score s : Lst3DDistance) {
            if (s.getScore() <= InteracDistance) {
                MyInterface.add(s.getP1());
            }
        }
        System.out.println();
        System.out.println("My Interface");
        for (String s : MyInterface) {
            System.out.print(s + " ");
        }

        MyInterface.retainAll(MySet);
        System.out.println();
        System.out.println("My Intersection");
        for (String s : MyInterface) {
            System.out.print(s + " ");
        }
        return MyInterface.size();
    }

    public static int countPredictedTPNumber(List<ColPair_Score> LstCandidate) {
        ArrayList<Residue> LstResidue = StaticMethod.toLstResidue(LstCandidate);
        Collections.sort(LstResidue);
        double percent = Configuration.Percent;
        int len = LstResidue.size();
        List<Residue> PredictedPostive = LstResidue.subList((int) (len * (1-percent)), len);
        return PredictedPostive.size();
    }

    public int countTPwithBestRASA(String path2pdb, String chain, int num) throws StructureException, IOException {
        List<Residue> LstRes = new ArrayList<>();
        HashMap<Integer, String> MapIdx2ResNum = StaticMethod.getMapIdx2ResNum(path2pdb, chain, 0);
        for(int i=0; i <MapIdx2ResNum.size(); i++){
            String str = MapIdx2ResNum.get(i);
            int score = (int)(LstRASAScore.get(str)*1000);
            LstRes.add(new Residue(str, score));
        }


        Collections.sort(LstRes);
        LstRes = LstRes.subList(LstRes.size() - num, LstRes.size());

        HashSet<String> MySet = new HashSet<>();
        for (Residue r : LstRes) {
            MySet.add(r.getResidueNumber());
        }
        System.out.println();
        System.out.println("Best RASA Prediction");
        for (String s : MySet) {
            System.out.print(s + " ");
        }

        HashSet<String> MyInterface = new HashSet<>();
        for (ColPair_Score s : Lst3DDistance) {
            if (s.getScore() <= InteracDistance) {
                MyInterface.add(s.getP1());
            }
        }

        MyInterface.retainAll(MySet);
        return MyInterface.size();
    }
}
