/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.ColPairAndScores;

/**
 *
 * @author t.dang
 */
public class ScoreMetric {
    private String Name;
    private double Score;

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Score
     */
    public double getScore() {
        return Score;
    }

    /**
     * @param Score the Score to set
     */
    public void setScore(double Score) {
        this.Score = Score;
    }
    
    /////////////////
    public ScoreMetric(String name, double score){
        this.Name = name;
        this.Score = score;
    }
}
