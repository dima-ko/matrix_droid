package com.insomniacmath;
//0xFF234563

import android.content.Context;
import android.view.View;
import android.widget.*;
import com.insomniacmath.exceptions.BadSymbolException;
import com.insomniacmath.exceptions.NotSquareException;
import com.insomniacmath.exceptions.SingularMatrixException;
import org.ejml.simple.SimpleMatrix;


public abstract class MatrixModel implements Constants {

    public static final int SIDE_COL_ID = 80;
    protected final Context context;
    public double[][] m;
    public Fraction[][] mFrac = null;
    public Fraction[] sideFrac;
    public int columns = 2, rows = 2;
    public int number; // made for testing purposes

    public boolean elementsFractions = false;
    Double[] side;

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        public void onFocusChange(View view, boolean b) {
            if (b)
                Solver.curEditId = view.getId();
        }
    };
//    private boolean mutable = true;

    public  MatrixModel(Context context, int number) {    //todo: clear editbox on longclick
        this.context = context;


//        mutable = isMutable;
        m = new double[2][];
        for (int i = 0; i < 2; i++) {
            m[i] = new double[2];
        }
        this.number = number;
    }


    public void addRow() {
        if (rows < MAX_ROWS) {
            rows++;
            double[][] temp = new double[rows][columns];
            for (int i = 0; i < rows - 1; i++) {
                temp[i] = new double[columns];
                for (int j = 0; j < columns; j++) {
                    temp[i][j] = m[i][j];
                }
            }
            temp[rows - 1] = new double[columns];
            m = temp.clone();
        } else
            Toast.makeText(context, "tooobig", 2000).show();
        refreshVisible();
    }

    protected abstract void refreshVisible();

    public void removeRow() {
        if (rows > 1) {
            rows--;
            double[][] temp = new double[rows][columns];
            for (int i = 0; i < rows; i++) {
                temp[i] = new double[columns];
                for (int j = 0; j < columns; j++) {
                    temp[i][j] = m[i][j];
                }
            }
            m = temp.clone();
        } else
            Toast.makeText(context, "tooosmall", 2000).show();
        refreshVisible();
    }

    public void addColumn() {
        if (columns < MAX_COLUMNS) {
            columns++;
            double[][] temp = new double[rows][columns];
            for (int i = 0; i < rows; i++) {
                temp[i] = new double[columns];
                for (int j = 0; j < columns - 1; j++) {
                    temp[i][j] = m[i][j];
                }
                temp[i][columns - 1] = 0;
            }
            m = temp.clone();
        } else
            Toast.makeText(context, "tooobig", 2000).show();
        refreshVisible();
    }

    public void removeColumn() {
        if (columns > 1) {
            columns--;
            double[][] temp = new double[rows][columns];
            for (int i = 0; i < rows; i++) {
                temp[i] = new double[columns];
                for (int j = 0; j < columns; j++) {
                    temp[i][j] = m[i][j];
                }
            }
            m = temp.clone();
        } else
            Toast.makeText(context, "tooosmall", 2000).show();
        refreshVisible();
    }


    public double findDeterminant() throws NotSquareException, BadSymbolException {
        fillMatrixFromViews();
        if (columns != rows) {
//            Toast.makeText(context, "no square", 2000).show();
            throw new NotSquareException();
        } else {
            return Utils.determin(m);
        }

    }

    protected abstract void fillMatrixFromViews() throws BadSymbolException;

    public void adjustSizeTo(int newColumns, int newRows) {
        rows = newRows;
        columns = newColumns;
        m = new double[rows][];
        for (int i = 0; i < rows; i++) {
            m[i] = new double[columns];
        }
    }

    public SimpleMatrix findInverseDouble() {
        SimpleMatrix orig = new SimpleMatrix(m);
        return orig.invert();
    }

    public Fraction[][] findInverseFraction() {
        return Utils.inverse(m);
    }

    public double findRang() throws BadSymbolException {
        fillMatrixFromViews();
        SimpleMatrix orig = new SimpleMatrix(m);
        return orig.svd(true).rank();
    }

    public SimpleMatrix solveSLEDouble() {            //todo: exception
        double[][] rightPart = new double[rows][];
        for (int i = 0; i < rows; i++) {
            rightPart[i] = new double[1];
            rightPart[i][0] = side[i];
        }
        SimpleMatrix A = new SimpleMatrix(m);
        SimpleMatrix b = new SimpleMatrix(rightPart);
        return A.solve(b);
    }

    public Fraction[] solveSLEFraction() throws SingularMatrixException {
        return Utils.gauss(mFrac, sideFrac);
    }







}
