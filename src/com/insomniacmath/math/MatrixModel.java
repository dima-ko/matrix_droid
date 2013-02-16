package com.insomniacmath.math;

import com.insomniacmath.Constants;


public class MatrixModel implements Constants {

    public static final int INIT_SIZE = 2;
    public Fraction[][] mFrac = null;
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

    public void addRow() {
        rows++;
        Fraction[][] temp = new Fraction[rows][columns];
        for (int i = 0; i < rows - 1; i++) {
            temp[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                temp[i][j] = mFrac[i][j].clone();
            }
        }
        temp[rows - 1] = new Fraction[columns];
        mFrac = temp;
    }

    public void removeRow() {
        rows--;
        Fraction[][] temp = new Fraction[rows][columns];
        for (int i = 0; i < rows; i++) {
            temp[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                temp[i][j] = mFrac[i][j].clone();
            }
        }
        mFrac = temp;
    }

    public void addColumn() {
        columns++;
        Fraction[][] temp = new Fraction[rows][columns];
        for (int i = 0; i < rows; i++) {
            temp[i] = new Fraction[columns];
            for (int j = 0; j < columns - 1; j++) {
                temp[i][j] = mFrac[i][j].clone();
            }
        }
        mFrac = temp;
    }

    public void removeColumn() {
        columns--;
        Fraction[][] temp = new Fraction[rows][columns];
        for (int i = 0; i < rows; i++) {
            temp[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                temp[i][j] = mFrac[i][j].clone();
            }
        }
        mFrac = temp;
    }
}
