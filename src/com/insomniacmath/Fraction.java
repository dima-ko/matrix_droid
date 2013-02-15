package com.insomniacmath;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

/**
 * The Fraction class implements non-negative fractions, i.e., rational
 * numbers.
 */
public class Fraction {

    private int num;
    private int denom;

    public Fraction(int num, int denom) {
        this.num = num;
        this.denom = denom;
        if (denom < 0) {
            this.num = num * -1;
            this.denom = denom * -1;
        }
        simplify();
    }

    public Fraction(int num) {
        this(num, 1);
    }

    public static Fraction[] deepCopy(Fraction[] array) {
        Fraction[] result = new Fraction[array.length];
        for (int i = 0; i < result.length; ++i)
            try {
                result[i] = array[i].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        return result;
    }

    public static Fraction[][] deepCopy(Fraction[][] array) {
        Fraction[][] result = new Fraction[array.length][];
        for (int i = 0; i < result.length; ++i)
            result[i] = deepCopy(array[i]);
        return result;
    }

    public int getNum() {
        return num;
    }

    public Fraction clone() {
        return new Fraction(num, denom);
    }

    public int getDenom() {
        return denom;
    }

    // reduce to lowest terms

    public void simplify() {
        // use Euclid's algorithm to find gcd
        int gcd = GreatestCommonDivisor(num, denom);
        num /= gcd;
        denom /= gcd;
        if (denom < 0) {
            num = num * -1;
            denom = denom * -1;
        }
    }

    @Override
    public String toString() {
        String fracText = getNum() + "";
        if (denom != 1)
            fracText += "\n" + denom;
        return fracText;
    }

    //Euclids algorithm

    public int GreatestCommonDivisor(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        int temp = a % b;
        while (temp > 0) {
            a = b;
            b = temp;
            temp = a % b;
        }
        return b;
    }


    // adds first & second to produce third in

    // simplified form

    public Fraction add(Fraction f2) {
        Fraction result = new Fraction((this.num * f2.denom) + (f2.num * this.denom), denom * f2.denom);
        result.simplify();
        return result;
    }

    public Fraction subtract(Fraction f2) {
        Fraction result = new Fraction((this.num * f2.denom) - (f2.num * this.denom), denom * f2.denom);
        result.simplify();
        return result;
    }

    public Fraction divide(Fraction f2) {
        Fraction result = new Fraction(this.num * f2.denom, this.denom * f2.num);
        result.simplify();
        return result;
    }

    public Fraction multiply(Fraction f2) {
        Fraction result = new Fraction(this.num * f2.num, this.denom * f2.denom);
        result.simplify();
        return result;
    }

    public double doubleValue() {
        return ((double) num) / denom;
    }

    public SpannableString toSpanString() {

        String fracText = getNum() + "";
        if (denom != 1) {
            fracText += "\n" + denom;
            int end = fracText.indexOf("\n");
            SpannableString ss = new SpannableString(fracText);
            ss.setSpan(new UnderlineSpan(), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        } else return new SpannableString(fracText);

    }

    public Fraction negative() {
        return new Fraction(-num, denom);
    }
}