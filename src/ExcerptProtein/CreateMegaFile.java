/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcerptProtein;

import Common.ColPair_Score;
import Common.MyIO;
import StaticMethods.DistanceCalc;
import StaticMethods.LookUpCollections;
import StaticMethods.ProteinIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.StructureException;

/**
 *
 * @author t.dang
 */
public class CreateMegaFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, StructureException {
        // TODO code application logic here
        String PDB_Path = "Input/SmallSet/PDB/1a70.pdb";
        String ProtID = "1A70";
        String ChainID = "A";
        String Path2CondMIScores = "Input/SmallSet/CMI/1A70_apc.cmi";
//        String Path2PlmDCA = "Input/SmallSet/plmdca/1a70_A.plmdca";
        String Path2PlmDCA = "Input/SmallSet/dcaSoeding/1A70_v2.dca";
        String Path2_3D = "Input/SmallSet/3D/1a70.3d_CA_Vdw";
        String Path2Gremlin = "Input/SmallSet/Gremlin/1a70_A_v2.txt";
        ArrayList<ColPair_Score> LstCMI = ProteinIO.readColPairScore2ArrayList(Path2CondMIScores, "\\s+");
        ArrayList<ColPair_Score> LstPlmdca = ProteinIO.readColPairScore2ArrayList(Path2PlmDCA, "\\s+");
        ArrayList<ColPair_Score> Lst3D = ProteinIO.readColPairScore2ArrayList(Path2_3D, "\\s+");
        ArrayList<ColPair_Score> LstGremlin = ProteinIO.readColPairScore2ArrayList(Path2Gremlin, "\\s+");
        ArrayList<String> lst = new ArrayList<>();
        for(ColPair_Score c:LstPlmdca){
            if(c.isNeighbor(0)){
                continue;
            }
            String P1 = c.getP1();
            String P2 = c.getP2();
//            double CMI = c.getScore();
            double CMI = LookUpCollections.LookUpScoreInLstColPair_Score(LstCMI, P1, P2);
            double plmdca = c.getScore();//LookUpCollections.LookUpScoreInLstColPair_Score(LstPlmdca, P1, P2);
            double ThreeD = LookUpCollections.LookUpScoreInLstColPair_Score(Lst3D, P1, P2);
            double Gremlin = LookUpCollections.LookUpScoreInLstColPair_Score(LstGremlin, P1, P2);
            String str = P1+"\t"+P2+"\t"+CMI + "\t"+plmdca+"\t"+ThreeD+ "\t"+Gremlin ;
            lst.add(str);
        }
        MyIO.WriteToFile("Input/SmallSet/Mega/1A70_CMI_plm_3D_Gremlin.txt", lst);
//        String IdCenter = "18";
//        ExcerptProt e = new ExcerptProt(IdCenter, ProtID, ChainID, PDB_Path);
//        List<Group> lst = e.makeExcerpt(2);
   //     System.out.println(lst.size());
//        Set<ColPair_Score> scores = DistanceCalc.calcDistancePairwise(lst);
//        ProteinIO.writeColPairScore2File("Input/SmallSet/Excerpt/1A70.txt", scores);
    }
    
}
