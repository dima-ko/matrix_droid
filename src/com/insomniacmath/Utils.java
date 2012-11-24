package com.insomniacmath;


import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

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

    public static String bra(double number, boolean needBraketNegative) {
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

    public static Fraction[] gauss(Fraction[][] A, Fraction[] b) {
        int N = b.length;
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
//            // singular or nearly singular
//            if (Math.abs(A[p][p].doubleValue()) <= EPSILON) {
//                throw new RuntimeException("Matrix is singular or nearly singular");
//            }

            // pivot within A and b
            for (int i = p + 1; i < N; i++) {
                Fraction alpha = A[i][p].divide(A[p][p]);
                b[i] = b[i].subtract(alpha.multiply(b[p]));
                for (int j = p; j < N; j++) {
                    A[i][j] = A[i][j].subtract(alpha.multiply(A[p][j]));
                }
            }
        }

        // back substitution
        Fraction[] x = new Fraction[N];
        for (int i = N - 1; i >= 0; i--) {
            Fraction sum = new Fraction(0);
            for (int j = i + 1; j < N; j++) {
                sum = sum.add(A[i][j].multiply(x[j]));
            }
            x[i] = (b[i].subtract(sum)).divide(A[i][i]);
        }

        Log.d("zzzzzzzzzzzz", "end gauss at" + System.currentTimeMillis());

        return x;
    }

//     2 + 3 = 4
//     4 + 4 = 6
//
//    1/2 , 1


}
