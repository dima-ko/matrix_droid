package com.insomniacmath;
//0xFF234563

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.*;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.insomniacmath.Animations.MatrixCanvas;
import org.ejml.simple.SimpleMatrix;


public class MatrixWrapper implements Constants {

    public static final int SIDE_COL_ID = 80;
    private final Context context;
    public double[][] m;
    public Fraction[][] mFrac = null;
    public Fraction[] sideFrac;
    public int columns = 2, rows = 2;
    public int number; // made for testing purposes
    public EditText[] sideColumnEdits = new EditText[MAX_ROWS];
    public EditText[][] grid = new EditText[MAX_ROWS][];
    public LinearLayout _view;
    public LinearLayout bodyMatrix;
    public LinearLayout hintLayout;
    public MatrixCanvas canvas;
    public boolean elementsFractions = false;
    Double[] side;
    boolean isSideColumnVisible = false;
    LinearLayout[] gridRows = new LinearLayout[MAX_ROWS];
    LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapRel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams fillFill = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
    ImageView rightBraket, leftBraket;
    RelativeLayout relativeLayout;
    LinearLayout sideColumn;
    LinearLayout divider;
    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        public void onFocusChange(View view, boolean b) {
            if (b)
                Solver.curEditId = view.getId();
        }
    };
//    private boolean mutable = true;

    public MatrixWrapper(Context context, LinearLayout view, int number, boolean isMutable) {    //todo: clear editbox on longclick
        this.context = context;

        _view = view;
//        mutable = isMutable;
        m = new double[2][];
        for (int i = 0; i < 2; i++) {
            m[i] = new double[2];
        }
        this.number = number;
        buildView();
    }

    public MatrixCanvas getCanvas() {
        return canvas;
    }

    private void buildView() {

        leftBraket = new ImageView(context);
        leftBraket.setImageResource(R.drawable.left_braket);
        _view.addView(leftBraket, new LinearLayout.LayoutParams(35, ViewGroup.LayoutParams.FILL_PARENT));

        bodyMatrix = new LinearLayout(context);
        bodyMatrix.setOrientation(LinearLayout.VERTICAL);
        fillGrid();

        _view.addView(bodyMatrix, /*new LinearLayout.LayoutParams(200, 200)*/wrapWrap);

        divider = new LinearLayout(context);
        divider.setBackgroundColor(Color.WHITE);
        _view.addView(divider, new LinearLayout.LayoutParams(5, ViewGroup.LayoutParams.FILL_PARENT));

        sideColumn = new LinearLayout(context);
        sideColumn.setOrientation(LinearLayout.VERTICAL);
        sideColumn.setGravity(Gravity.CENTER);
        _view.addView(sideColumn, new LinearLayout.LayoutParams(wrapWrap));

        for (int i = 0; i < MAX_ROWS; i++) {
            sideColumnEdits[i] = new EditText(context);
            sideColumnEdits[i].setId(i + SIDE_COL_ID + 100 * number);
            sideColumnEdits[i].setInputType(InputType.TYPE_CLASS_PHONE);
            sideColumnEdits[i].setBackgroundResource(R.drawable.edit);
            sideColumnEdits[i].setTextColor(Color.WHITE);
            sideColumnEdits[i].setGravity(Gravity.CENTER);
            sideColumnEdits[i].setMinWidth(70);
            sideColumnEdits[i].setMinHeight(70);
            final View a = sideColumnEdits[i];
            sideColumnEdits[i].addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d("clicked on edittext", " id: " + a.getId());
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((EditText) a).setTextColor(Color.WHITE);
                    if (charSequence.toString().equals(""))
                        a.setBackgroundResource(R.drawable.edit);
                    else
                        a.setBackgroundResource(0);
                }

                public void afterTextChanged(Editable editable) {
                }
            });
            sideColumn.addView(sideColumnEdits[i], editParams);
        }

        rightBraket = new ImageView(context);
        rightBraket.setImageResource(R.drawable.right_braket);
        _view.addView(rightBraket, new LinearLayout.LayoutParams(35, ViewGroup.LayoutParams.FILL_PARENT));

        hintLayout = new LinearLayout(context);
        _view.addView(hintLayout, new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.FILL_PARENT));
        hintLayout.setVisibility(View.GONE);

        refreshVisible();

    }

    public void refreshVisible() {
        for (int i = 0; i < MAX_ROWS; i++)
            for (int j = 0; j < MAX_COLUMNS; j++)
                if (i < rows && j < columns)
                    grid[i][j].setVisibility(View.VISIBLE);
                else
                    grid[i][j].setVisibility(View.GONE);

        leftBraket.setImageResource(R.drawable.left_braket);
        rightBraket.setImageResource(R.drawable.right_braket);

        if (isSideColumnVisible) {
            sideColumn.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            for (int i = 0; i < MAX_ROWS; i++)
                if (i < rows)
                    sideColumnEdits[i].setVisibility(View.VISIBLE);
                else
                    sideColumnEdits[i].setVisibility(View.GONE);
        } else {
            sideColumn.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);

        }

    }

    private void fillGrid() {

        relativeLayout = new RelativeLayout(context);
        LinearLayout bodyMatrixRows = new LinearLayout(context);
        bodyMatrixRows.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < MAX_ROWS; i++) {
            gridRows[i] = new LinearLayout(context);
            gridRows[i].setOrientation(LinearLayout.HORIZONTAL);
            grid[i] = new EditText[MAX_COLUMNS];
            for (int j = 0; j < MAX_COLUMNS; j++) {
                grid[i][j] = new EditText(context);
                grid[i][j].setId(i * MAX_COLUMNS + j + 100 * number);
//                if (mutable)
//                    grid[i][j].setInputType(InputType.TYPE_CLASS_PHONE);
                grid[i][j].setSingleLine(false);
                grid[i][j].setBackgroundResource(R.drawable.edit);
                grid[i][j].setTextColor(Color.WHITE);
                grid[i][j].setGravity(Gravity.CENTER);
                grid[i][j].setMinWidth(70);
                grid[i][j].setMinHeight(70);
                final View a = grid[i][j];
                grid[i][j].addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.d("clicked on edittext", " id: " + a.getId());
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        ((EditText) a).setTextColor(Color.WHITE);
                        if (charSequence.toString().equals(""))
                            a.setBackgroundResource(R.drawable.edit);
                        else
                            a.setBackgroundResource(0);
                    }

                    public void afterTextChanged(Editable editable) {
                    }
                });
                grid[i][j].setOnFocusChangeListener(focusChangeListener);
                gridRows[i].addView(grid[i][j], editParams);
            }
            bodyMatrixRows.addView(gridRows[i], wrapWrap);
        }

        grid[0][0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((Activity) context).getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        grid[0][0].requestFocus();    //TODO: klavu align

        bodyMatrix.addView(relativeLayout, wrapWrap);

        bodyMatrixRows.setId(BODY_ID);
        fillFill.addRule(RelativeLayout.ALIGN_RIGHT, bodyMatrixRows.getId());
        fillFill.addRule(RelativeLayout.ALIGN_BOTTOM, bodyMatrixRows.getId());
        canvas = new MatrixCanvas(context);
        relativeLayout.addView(canvas, fillFill);
        relativeLayout.addView(bodyMatrixRows, wrapWrapRel);

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

    public void addSideColumn() {
        isSideColumnVisible = true;
        refreshVisible();
    }

    public void removeSideColumn() {
        isSideColumnVisible = false;
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

    public void fillMatrixFromViews() throws BadSymbolException {
        Log.d("zzzzzzzzzzzz", "start fillGrid" + System.currentTimeMillis());
        elementsFractions = false;
        side = new Double[rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                try {
                    m[i][j] = Double.parseDouble(grid[i][j].getText().toString());
                    grid[i][j].setTextColor(Color.WHITE);
                } catch (NumberFormatException e) {
                    if (grid[i][j].getText().toString().length() == 0) {
                        grid[i][j].setBackgroundResource(R.drawable.red_edit);
                    } else {
                        grid[i][j].setTextColor(Color.RED);
                    }
                    throw new BadSymbolException();
                }
            }
            if (isSideColumnVisible)
                side[i] = Double.parseDouble(sideColumnEdits[i].getText().toString());
        }
        Log.d("zzzzzzzzzzzz", "middle fillGrid" + System.currentTimeMillis());


        /*parse fractions*/
        sideFrac = new Fraction[rows];
        mFrac = new Fraction[rows][];
        for (int i = 0; i < rows; i++) {
            mFrac[i] = new Fraction[columns];
            for (int j = 0; j < columns; j++) {
                try {
                    int integer = Integer.parseInt(grid[i][j].getText().toString());
                    mFrac[i][j] = new Fraction(integer);
                } catch (NumberFormatException e) {
                    sideFrac = null;
                    mFrac = null;
                    return;
                }
            }
            if (isSideColumnVisible) {
                int sideInt = Integer.parseInt(sideColumnEdits[i].getText().toString());
                sideFrac[i] = new Fraction(sideInt);
            }
        }
        Log.d("zzzzzzzzzzzz", "end fillGrid" + System.currentTimeMillis());

        elementsFractions = true;
    }

    public void fillViewsFromMatrix() {
        if (mFrac != null) {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++) {
                    SpannableString text = mFrac[i][j].toSpanString();
                    grid[i][j].setSingleLine(false);
                    grid[i][j].setText(text);
                }
        } else {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++) {
                    double v = m[i][j];
                    if (v % 1 == 0)
                        grid[i][j].setText((int) v + "");
                    else {
                        grid[i][j].setText(v + "");
                    }
                }
        }
    }

    public void onDestroy() {
        canvas.onDestroy();
    }

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

    public int getNextEdit(int direction, int curEditId) {
        int column = curEditId % MAX_COLUMNS;
        int row = (curEditId < SIDE_COL_ID) ?
                curEditId / MAX_COLUMNS : (curEditId - SIDE_COL_ID);
        if (direction == RIGHT) {
            if (column != columns - 1)
                return curEditId + 1;
            else if (isSideColumnVisible && curEditId < SIDE_COL_ID) {
                return SIDE_COL_ID + row;
            }
        } else if (direction == LEFT) {
            if (curEditId >= SIDE_COL_ID) {
                return (row * MAX_COLUMNS + columns - 1);
            } else if (column != 0)
                return curEditId - 1;
        } else if (direction == UP) {
            if (curEditId >= SIDE_COL_ID) {
                if (curEditId - SIDE_COL_ID != 0)
                    return --curEditId;
            } else if (row != 0)
                return curEditId - MAX_COLUMNS;
        } else if (direction == DOWN) {
            if (curEditId >= SIDE_COL_ID) {
                if (curEditId - SIDE_COL_ID != rows - 1)
                    return ++curEditId;
            } else if (row != rows - 1)
                return curEditId + MAX_COLUMNS;
        }
        return -1;
    }
}
