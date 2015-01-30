package utils;

/**
 * 
 * @author Keyu Wang
 */
public class Evaluation {

    private float upper, lower, lambda;
    private String featureName;

    public Evaluation(float up, float lo, float lamb, String fn) {
        upper = up;
        lower = lo;
        lambda = lamb;
        featureName = fn;
    }

    public String getFeatureName() {
        return featureName;
    }

    public float getLambda() {
        return lambda;
    }

    public float getLower() {
        return lower;
    }

    public float getUpper() {
        return upper;
    }

    public static float evalIntervalCaseONEminusONE(float tmp, float upperbound, float lowerbound) {
        if (tmp >= lowerbound && tmp <= upperbound) {
            return 1f;
        } else {
            return -1f;
        }
    }
    public static float evalIntervalCaseONEZERO(float tmp, float upperbound, float lowerbound) {
        if (tmp >= lowerbound && tmp <= upperbound) {
            return 1f;
        } else {
            return 0f;
        }
    }
}
