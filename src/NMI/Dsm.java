package NMI;

public class Dsm {

    static double epsilon = 0.000000000000009;

    private static float[][] calculate(float[][] matrix) {

        int loop = 0;
        boolean check = false;
        while (!check) {
            System.out.println("**************LOOP: " + loop + "*********************************");
            float[] rowSum = rowSum(matrix);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[i][j] = matrix[i][j] / rowSum[i];
                }
            }
            float[] columnSum = columnSum(matrix);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[j][i] = matrix[j][i] / columnSum[i];
                }
            }
            check = check(matrix);
            loop++;
            if(loop>10000)break;
        }
        System.out.println("Number of loop: "+ loop);

        return matrix;

    }

    private static float[] rowSum(float matrix[][]) {

        float row[] = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            row[i] = 0;
            for (int j = 0; j < matrix.length; j++) {
                row[i] += matrix[i][j];
            }
        }
        return row;
    }

    private static float[] columnSum(float matrix[][]) {

        float column[] = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = 0;
            for (int j = 0; j < matrix.length; j++) {
                column[i] += matrix[j][i];
            }
        }
        return column;
    }

    private static boolean check(float matrix[][]) {

        float[] rowSum = kahanRowSum(matrix);
        float[] columnSum = kahanColumnSum(matrix);

        boolean result = true;
        for (int i = 0; i < matrix.length; i++) {
            if (rowSum[i] != 1.0) {
                return false;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            if (columnSum[i] != 1.0) {
                return false;
            }
        }
        return result;
    }

    private static float[] kahanRowSum(float[][] input) {
        float sum[] = new float[input.length];
        float c = 0f, y, t;          //A running compensation for lost low-order bits.
        for (int i = 0; i < input.length; i++) {
            sum[i] = 0f;
            for (int j = 0; j < input.length; j++) {
                y = input[i][j] - c;    //So far, so good: c is zero.
                t = sum[i] + y;         //Alas, sum is big, y small, so low-order digits of y are lost.
                c = (t - sum[i]) - y;   //(t - sum) recovers the high-order part of y; subtracting y recovers -(low part of y)
                sum[i] = t;             //Algebraically, c should always be zero. Beware eagerly optimising compilers!

            }
            if (sum[i] >= (1 - epsilon) & sum[i] <= (1 + epsilon)) {
                sum[i] = 1;
            }
        }
        return sum;
    }

    private static float[] kahanColumnSum(float[][] input) {
        float sum[] = new float[input.length];
        float c = 0f, y, t;          //A running compensation for lost low-order bits.
        for (int i = 0; i < input.length; i++) {
            sum[i] = 0f;
            for (int j = 0; j < input.length; j++) {
                y = input[j][i] - c;    //So far, so good: c is zero.
                t = sum[i] + y;         //Alas, sum is big, y small, so low-order digits of y are lost.
                c = (t - sum[i]) - y;   //(t - sum) recovers the high-order part of y; subtracting y recovers -(low part of y)
                sum[i] = t;             //Algebraically, c should always be zero. Beware eagerly optimising compilers!
            }
            if (sum[i] >= (1 - epsilon) & sum[i] <= (1 + epsilon)) {
                sum[i] = 1;
            }
        }
        return sum;
    }

    /**/
    public static float[][] processMatrix( float[][] matrix ) {
        for(int i=0;i<matrix.length;i++){
            double s=0;
            for(int j=0;j<matrix[0].length;j++){
                s += matrix[i][j];
            }
            if(s==0){
                System.err.println("All entries is zero");
                System.exit(1);
            }
        }
        return calculate(matrix);

    }

}
