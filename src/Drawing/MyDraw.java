/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Drawing;

import java.applet.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import org.jfree.ui.RefineryUtilities;


/**
 *
 * @author linh
 */
public class MyDraw extends Applet {

//    private final ArrayList<String> ProtPosition;
    private ArrayList<Double> Value;
    private String Title;
    private Color[] MyColor;
    private ArrayList<Integer> HighlightPosition;

    public MyDraw(){
       
//        this.ProtPosition = new ArrayList<String>();
        this.Value = new ArrayList<Double>();
    }
    public MyDraw(String t, ArrayList<Double> v){
        this.Title = t;
//        this.ProtPosition = p;
        this.Value = v;
    }
    public void PaintJFreeChart(){
//        final MyJFreeChart demo = new MyJFreeChart(this.Title);
        ArrayList<Color> colo = new ArrayList<Color>();
        for(int i=0;i<this.MyColor.length;i++){
            colo.add(this.MyColor[i]);
        }
        final MyJFreeChart demo = new MyJFreeChart(this.Title, this.Value, colo);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        
    }

    public void PaintJFrame() {
        JFrame f = new JFrame();
        f.setSize(800, 600);
//        String[] pos = new String[this.ProtPosition.size()];
//        pos = this.ProtPosition.toArray(pos);
        double[] val = new double[this.Value.size()];
        for(int i=0;i<this.Value.size();i++){
            val[i] = this.Value.get(i);
        }
        
        f.getContentPane().add(new ChartPanel(val,this.Title));

        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        f.addWindowListener(wndCloser);
        f.setVisible(true);
    }

    

    
    /**
     * @return the value
     */
    public ArrayList<Double> getValue() {
        return Value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(ArrayList<Double> value) {
        this.Value = value;
    }

    /**
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return the MyColor
     */
    public Color[] getMyColor() {
        return MyColor;
    }

    /**
     * @param MyColor the MyColor to set
     */
    public void setMyColor(Color[] MyColor) {
        this.MyColor = MyColor;
    }

    /**
     * @return the HighlightPosition
     */
    public ArrayList<Integer> getHighlightPosition() {
        return HighlightPosition;
    }

    /**
     * @param HighlightPosition the HighlightPosition to set
     */
    public void setHighlightPosition(ArrayList<Integer> HighlightPosition) {
        this.HighlightPosition = HighlightPosition;
    }
    public void AdjustColor(){
        int num = this.Value.size();
        this.MyColor = new Color[num];
        for(int i=0;i<num;i++){
            if(HighlightPosition.contains(i)){
                MyColor[i] = Color.RED;
            }
            else{
                MyColor[i] = Color.GRAY;
            }
        }
    }
}
