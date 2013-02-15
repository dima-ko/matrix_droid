package com.insomniacmath;


import android.util.Log;
import com.insomniacmath.etc.Utils;
import com.insomniacmath.exceptions.BadSymbolException;
import com.insomniacmath.exceptions.NotSquareException;
import com.insomniacmath.exceptions.SingularMatrixException;
import org.ejml.simple.SimpleMatrix;

public class MatrixUtils {

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

    public static Fraction[][] removeRowAndColumn(Fraction[][] a, int excludedRow, int excludedColumn) {
        int size = a.length - 1;
        Fraction[][] res = new Fraction[size][];
        for (int i = 0; i < size; i++) {
            res[i] = new Fraction[size];
            for (int j = 0; j < size; j++) {
                int origRow = (i < excludedRow) ? i : i + 1;
                int origColumn = (j < excludedColumn) ? j : j + 1;
                res[i][j] = a[origRow][origColumn];
            }
        }
        return res;
    }

    public static SimpleMatrix findInverseDouble() {
        SimpleMatrix orig = new SimpleMatrix(m);
        return orig.invert();
    }

    public static Fraction[][] findInverseFraction() {
        return Utils.inverse(m);
    }

    public static double findRang() throws BadSymbolException {
        SimpleMatrix orig = new SimpleMatrix(m);
        return orig.svd(true).rank();
    }

    public static SimpleMatrix solveSLEDouble() {            //todo: exception
        double[][] rightPart = new double[rows][];
        for (int i = 0; i < rows; i++) {
            rightPart[i] = new double[1];
            rightPart[i][0] = side[i];
        }
        SimpleMatrix A = new SimpleMatrix(m);
        SimpleMatrix b = new SimpleMatrix(rightPart);
        return A.solve(b);
    }

    public static double findDeterminant() throws NotSquareException, BadSymbolException {
        fillMatrixFromViews();
        if (columns != rows) {
//            Toast.makeText(context, "no square", 2000).show();
            throw new NotSquareException();
        } else {
            return Utils.determin(m);
        }

    }

    public static Fraction[] solveSLEFraction() throws SingularMatrixException {
        return Utils.gauss(mFrac, sideFrac);
    }

}
