package com.insomniacmath;

/**
 * The Fraction class implements non-negative fractions, i.e., rational
 * numbers.
 */
class Fraction {

    public int getNum() {
        return num;
    }

    public int getDenom() {
        return denom;
    }

    private int num;
    private int denom;

    public Fraction(int num, int denom) {
        this.num = num;
        this.denom = denom;
        if (denom < 0) {
            num = num * -1;
            denom = denom * -1;
        }
    }

    public Fraction(int num) {
        this(num, 1);
    }


    // reduce to lowest terms

    public void simplify() {

        // use Euclid's algorithm to find gcd

        int gcd = GreatestCommonDivisor(num, denom);

        num /= gcd;

        denom /= gcd;

    }


    //Euclids algorithm

    public int GreatestCommonDivisor(int a, int b)

    {
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
}