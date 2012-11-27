package com.insomniacmath;


import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import org.ejml.simple.SimpleMatrix;

public class Utils {

    public static boolean isHoneyCombOrMore = false;
    public static int height;
    public static int width;
    public static int flowidth;
    public static int smaller2dim;
    public static int smallerDim;
    public static boolean isTab;
    public static boolean isSmall;
    public static boolean isPortrait;
    public static float scale;
    public static Typeface roboto_light;

    public static void resolvePlatform(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        int vers = Integer.parseInt(Build.VERSION.RELEASE.substring(0, 1));
        if (vers > 2) Utils.isHoneyCombOrMore = true;

        int screenSizeType = activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSizeType == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            isTab = true;
        } else if (screenSizeType == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            isSmall = true;
        }

        flowidth = isTab ? 400 : width;
        isPortrait = (height > width);
        smallerDim = (height > width) ? width : height;

        smaller2dim = isPortrait ? height / 2 : width / 2;


        scale = activity.getResources().getDisplayMetrics().density;

        roboto_light = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Light.ttf");
    }

    /**
     * converts number to string, wraps with brakets , if number is negative
     *
     * @param number
     * @param needBraketNegative
     * @return
     */
    public static String round(double number, boolean needBraketNegative) {
        if (number == (int) number) {
            if (number < 0 && needBraketNegative)
                return "(" + (int) number + ")";
            else return (int) number + "";
        } else {
            if (number < 0 && needBraketNegative)
                return "(" + number + ")";
            else return number + "";
        }
    }

    private static final double EPSILON = 1e-10;

    public static double determin(double[][] m) {
        int size = m.length;
        double D = 0;
        if (size == 1)
            return m[0][0];
        else {
            for (int k = 0; k < size; k++) {
                double[][] temp = new double[size - 1][];
                for (int i = 0; i < size - 1; i++) {
                    temp[i] = new double[size - 1];
                    for (int j = 0; j < size - 1; j++) {
                        temp[i][j] = (j >= k) ? m[i + 1][j + 1] : m[i + 1][j];
                    }
                }
                D += Math.pow(-1, k) * m[0][k] * determin(temp);
            }
        }
        return D;
    }

    public static int[][] matrixOfCofactors(double orig[][]) {
        int size = orig.length;
        int[][] result = new int[size][];
        SimpleMatrix origM = new SimpleMatrix(orig);
        SimpleMatrix inverted = origM.invert();
        double determin = origM.determinant();
        for (int i = 0; i < size; i++) {
            result[i] = new int[size];
            for (int j = 0; j < size; j++) {
                result[i][j] = (int) (inverted.get(i, j) * determin);
            }
        }
        return result;
    }

    public static Fraction[] gauss(Fraction[][] a, Fraction[] b) throws SingularMatrixException {

        Fraction[][] mA = Fraction.deepCopy(a);
        Fraction[] mB = Fraction.deepCopy(b);
        int N = mB.length;
        Log.d("zzzzzzzzzzzz", "start gaussat at" + System.currentTimeMillis());
        for (int p = 0; p < N; p++) {

            // find pivot row and swap     //todo
//            int max = p;
//            for (int i = p + 1; i < N; i++) {
//                if (Math.abs(A[i][p].doubleValue()) > Math.abs(A[max][p].doubleValue())) {
//                    max = i;
//                }
//            }
//            Fraction[] temp = A[p];
//            A[p] = A[max];
//            A[max] = temp;
//            Fraction t = b[p];
//            b[p] = b[max];
//            b[max] = t;
//
            // singular or nearly singular
            if (Math.abs(mA[p][p].doubleValue()) <= EPSILON) {
                throw new SingularMatrixException();
            }

            // pivot within A and b
            for (int i = p + 1; i < N; i++) {
                Fraction alpha = mA[i][p].divide(mA[p][p]);
                mB[i] = mB[i].subtract(alpha.multiply(mB[p]));
                for (int j = p; j < N; j++) {
                    mA[i][j] = mA[i][j].subtract(alpha.multiply(mA[p][j]));
                }
            }
        }

        // back substitution
        Fraction[] x = new Fraction[N];
        for (int i = N - 1; i >= 0; i--) {
            Fraction sum = new Fraction(0);
            for (int j = i + 1; j < N; j++) {
                sum = sum.add(mA[i][j].multiply(x[j]));
            }
            x[i] = (mB[i].subtract(sum)).divide(mA[i][i]);
        }

        Log.d("zzzzzzzzzzzz", "end gauss at" + System.currentTimeMillis());

        return x;
    }

    public static Fraction[][] inverse(double[][] A) {
        int size = A.length;
        int cofac[][] = matrixOfCofactors(A);
        SimpleMatrix origM = new SimpleMatrix(A);
        int determin = (int) origM.determinant();
        Fraction[][] result = new Fraction[size][];
        for (int i = 0; i < size; i++) {
            result[i] = new Fraction[size];
            for (int j = 0; j < size; j++) {
                result[i][j] = new Fraction(cofac[i][j], determin);
            }
        }
        return result;
    }

}
