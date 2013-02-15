package com.insomniacmath.ui;

import com.insomniacmath.Constants;
import com.insomniacmath.Fraction;


public abstract class MatrixModel implements Constants {

    public Fraction[][] mFrac = null;
    public Fraction[] sideFrac = null;
    public int columns = 2, rows = 2;

    public MatrixModel() {
        mFrac = new Fraction[2][];
        for (int i = 0; i < 2; i++) {
            mFrac[i] = new Fraction[2];
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
