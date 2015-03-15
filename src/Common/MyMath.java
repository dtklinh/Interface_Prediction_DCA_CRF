/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author t.dang
 */
public class MyMath {
    public static float atanh(float x){
        return (float) (0.5*Math.log((1+x)/(1-x)));
    }
}
