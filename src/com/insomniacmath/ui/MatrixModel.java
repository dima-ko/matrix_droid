package com.insomniacmath.ui;

import com.insomniacmath.Constants;
import com.insomniacmath.Fraction;


public class MatrixModel implements Constants {

    public static final int INIT_SIZE = 2;
    public Fraction[][] mFrac = null;
    public Fraction[] sideFrac = null;
    public int columns = INIT_SIZE, rows = INIT_SIZE;

    public MatrixModel() {
        mFrac = new Fraction[INIT_SIZE][];
        for (int i = 0; i < INIT_SIZE; i++) {
            mFrac[i] = new Fraction[INIT_SIZE];
            mFrac[i] = new Fraction[INIT_SIZE];
            for (int j = 0; j < INIT_SIZE; j++) {
                mFrac[i][j] = new Fraction(0);
            }
        }
    }

    public MatrixModel(MatrixModel orig) {
        int rows = orig.mFrac.length;
        int columns = orig.mFrac[0].length;
        mFrac = new Fraction[rows][];
        for (int i = 0; i < rows; i++) {
            mFrac[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                mFrac[i][j] = orig.mFrac[i][j].clone();
            }
        }
    }

}
