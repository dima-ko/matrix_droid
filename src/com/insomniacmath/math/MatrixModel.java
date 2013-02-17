package com.insomniacmath.math;

import com.insomniacmath.Constants;


public class MatrixModel implements Constants {

    public Fraction[][] mFrac = null;
    public int columns = INIT_SIZE, rows = INIT_SIZE;

    public MatrixModel() {
        mFrac = new Fraction[INIT_SIZE][];
        for (int i = 0; i < INIT_SIZE; i++) {
            mFrac[i] = new Fraction[INIT_SIZE];
        }
    }

    public MatrixModel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        mFrac = new Fraction[rows][];
        for (int i = 0; i < rows; i++) {
            mFrac[i] = new Fraction[columns];
        }
    }

    public MatrixModel(MatrixModel orig) {
        rows = orig.mFrac.length;
        columns = orig.mFrac[0].length;
        mFrac = new Fraction[rows][];
        for (int i = 0; i < rows; i++) {
            mFrac[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                mFrac[i][j] = orig.mFrac[i][j].clone();
            }
        }
    }

    public MatrixModel(Fraction[] column) {
        int rows = column.length;
        int columns = 1;
        mFrac = new Fraction[rows][];
        for (int i = 0; i < rows; i++) {
            mFrac[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                mFrac[i][j] = column[i].clone();
            }
        }
    }

    public void addRow() {
        rows++;
        Fraction[][] temp = new Fraction[rows][columns];
        for (int i = 0; i < rows - 1; i++) {
            temp[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                if (mFrac[i][j] != null)
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
                if (mFrac[i][j] != null)
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
                if (mFrac[i][j] != null)
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
                if (mFrac[i][j] != null)
                    temp[i][j] = mFrac[i][j].clone();
            }
        }
        mFrac = temp;
    }
}
