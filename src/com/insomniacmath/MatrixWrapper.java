package com.insomniacmath;
//0xFF234563

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.insomniacmath.Animations.MatrixCanvas;


public class MatrixWrapper implements Constants {


    public double[][] m;
    EditText[][] grid = new EditText[MAX_ROWS][];
    LinearLayout[] gridRows = new LinearLayout[MAX_ROWS];
    public int number; // made for testing purposes

    int columns = 2, rows = 2;
    private final Context context;
    private LinearLayout _view;
    LinearLayout bodyMatrix;
    LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 80);
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapRel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams fillFill = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

    ImageView rightBraket, leftBraket;
    MatrixCanvas canvas;
    Animator animator;
    RelativeLayout relativeLayout;

    public MatrixWrapper(Context context, LinearLayout view, int number) {    //todo: clear editbox on longclick
        this.context = context;
        _view = view;
        m = new double[2][];
        for (int i = 0; i < 2; i++) {
            m[i] = new double[2];
        }
        this.number = number;
        buildView();
    }

    private void buildView() {


        leftBraket = new ImageView(context);
        leftBraket.setImageResource(R.drawable.left_braket);
        _view.addView(leftBraket, new LinearLayout.LayoutParams(60, ViewGroup.LayoutParams.FILL_PARENT));


        bodyMatrix = new LinearLayout(context);
        bodyMatrix.setOrientation(LinearLayout.VERTICAL);
        fillGrid();

        _view.addView(bodyMatrix, /*new LinearLayout.LayoutParams(200, 200)*/wrapWrap);


        rightBraket = new ImageView(context);
        rightBraket.setImageResource(R.drawable.right_braket);
        _view.addView(rightBraket, new LinearLayout.LayoutParams(60, ViewGroup.LayoutParams.FILL_PARENT));

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
    }

    private void fillGrid() {

        bodyMatrix.addView(new LinearLayout(context), new LinearLayout.LayoutParams(20, 15));

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
                grid[i][j].setInputType(InputType.TYPE_CLASS_PHONE);
                grid[i][j].setBackgroundResource(R.drawable.edit);
                grid[i][j].setTextColor(Color.WHITE);
                grid[i][j].setGravity(Gravity.CENTER);
                grid[i][j].setMinWidth(80);
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
                grid[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                    }
                });
                gridRows[i].addView(grid[i][j], editParams);
            }
            bodyMatrixRows.addView(gridRows[i], wrapWrap);
        }


//        relativeLayout.setBackgroundColor(0x22FFFFFF);

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

        bodyMatrix.addView(new LinearLayout(context), new LinearLayout.LayoutParams(20, 15));

        bodyMatrixRows.setId(BODY_ID);
        fillFill.addRule(RelativeLayout.ALIGN_RIGHT, bodyMatrixRows.getId());
        fillFill.addRule(RelativeLayout.ALIGN_BOTTOM, bodyMatrixRows.getId());
        canvas = new MatrixCanvas(context);
        relativeLayout.addView(canvas, fillFill);
        relativeLayout.addView(bodyMatrixRows, wrapWrapRel);

        animator = new Animator(canvas, this);

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

    }

    public double findDeterminant() throws BadMatrixException, BadSymbolException {
        fillMatrixFromGrid();
        if (columns != rows) {
//            Toast.makeText(context, "no square", 2000).show();
            throw new BadMatrixException();
        } else {
            return determin(m);
        }


    }

    //    1  2  3
    private double determin(double[][] m) {                                                                                    //    4  5  6
        int size = m.length;                                                                                                 //    7  8  9
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

    public void fillMatrixFromGrid() throws BadSymbolException {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                try {
                    m[i][j] = Double.parseDouble(grid[i][j].getText().toString());
                    grid[i][j].setTextColor(Color.WHITE);
                } catch (NumberFormatException e) {
//                    Toast.makeText(context, "badsymbol", 2000).show();
                    if (grid[i][j].getText().toString().length() == 0) {
                        grid[i][j].setBackgroundResource(R.drawable.red_edit);
                    } else {
                        grid[i][j].setTextColor(Color.RED);
                    }
                    throw new BadSymbolException();
                }

            }

    }

    public void fillGridFromMatrix() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                double v = m[i][j];
                if (v % 1 == 0)
                    grid[i][j].setText((int) v + "");
                else
                    grid[i][j].setText(v + "");
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
}
