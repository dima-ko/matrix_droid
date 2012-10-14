package com.insomniacmath;


public class Utils {

    public  static String round(double i) {
        if (i % 1 == 0)
            return Integer.toString((int) i);
        else
            return Double.toString(i);
    }

}
