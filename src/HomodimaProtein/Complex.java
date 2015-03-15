/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HomodimaProtein;

import Common.Configuration;
import Common.StaticMethod;
import DCA.MSA_FloatMatrix;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author t.dang
 */
public class Complex extends MSA_FloatMatrix{
    private int Separate;
    private String ChainOneID;
    private String ChainTwoID;
    public Complex(String dir, String filename, int s, String Chain1, String Chain2) throws FileNotFoundException, IOException{
        super(dir,filename);
        this.Separate = s;
         ChainOneID = Chain1;
        ChainTwoID = Chain2;
    }
    public Complex(String dir, String filename, int[][] mx, int s,String Chain1, String Chain2){
        super(dir,filename,mx);
        this.Separate = s;
         ChainOneID = Chain1;
        ChainTwoID = Chain2;
    }
    public Complex(MSA_FloatMatrix m, int s, String Chain1, String Chain2){
        super(m);
        this.Separate = s;
        ChainOneID = Chain1;
        ChainTwoID = Chain2;
    }
    // because two chain are identical, thus identical MSA_FloatMatrix. 
    public void refineMSA(){
        int separate = Separate;
        int[][] arr = this.getAlgnMx();
        int row = arr.length;
        int col = arr[0].length;
        int[][] newarr = new int[row][2*col+separate];
        for(int i=0;i<row; i++){
            for(int j=0; j<col; j++){
                newarr[i][j] = arr[i][j];
                newarr[i][j+col+separate] = arr[i][j];
            }
            Random r = new Random();
            for(int j=col;j<col+separate;j++){
                int tmp ;
                if(i==0){
                    tmp = r.nextInt(20)+1;
                }
                else{
                    tmp = r.nextInt(21);
                }
                newarr[i][j] = tmp;
            }
        }
        this.setAlgnMx(newarr);
    }
    public static  int[][] refineMSA(int[][] arr, int separate){
//        int separate = Separate;
//        int[][] arr = this.getAlgnMx();
        int row = arr.length;
        int col = arr[0].length;
        int[][] newarr = new int[row][2*col+separate];
        for(int i=0;i<row; i++){
            for(int j=0; j<col; j++){
                newarr[i][j] = arr[i][j];
                newarr[i][j+col+separate] = arr[i][j];
            }
            Random r = new Random();
            for(int j=col;j<col+separate;j++){
                int tmp ;
                if(i==0){
                    tmp = r.nextInt(20)+1;
                }
                else{
                    tmp = r.nextInt(21);
                }
                newarr[i][j] = tmp;
            }
        }
        return newarr;
//        this.setAlgnMx(newarr);
    }
    public String[][] getResult() throws IOException{
        float[][] d = this.GetResult2SquareMatrix();
        // only take inter-protein scores // need to improve performence
       
        String Path2PDB_1 = Configuration.Dir2PDBSingleChain+this.getName().substring(0, 5)+ChainOneID +StaticMethod.FindEndName(Configuration.Dir2PDBSingleChain);
        String Path2PDB_2 = Configuration.Dir2PDBSingleChain+this.getName().substring(0, 5)+ChainTwoID +StaticMethod.FindEndName(Configuration.Dir2PDBSingleChain);
        int[] Residue_1 = StaticMethod.getResidueNum(Path2PDB_1);
        int[] Residue_2 = StaticMethod.getResidueNum(Path2PDB_2);
        
        int N = (this.getN()-Separate)/2;
//        double[][] res = new double[N*N][3];
        String[][] str = new String[N*N][5];
        int count = 0;
        for(int i=0; i<N; i++){
            for(int j=N+Separate; j<N*2+Separate; j++){
//                res[count][0] = Residue_1[i];
//                res[count][1] = Residue_2[j-N-Separate];
//                res[count][2] = d[i][j];
                
                str[count][0] = ChainOneID;
                str[count][1] = String.valueOf(Residue_1[i]);
                str[count][2] = ChainTwoID;
                str[count][3] = String.valueOf(Residue_2[j-N-Separate]);
                str[count][4] = String.valueOf(d[i][j]);
                count++;
            }
                
        }
        
//        res = StaticMethod.AdjustIndex(res, Path2PDB);
        return str;
    }
    
    
}
