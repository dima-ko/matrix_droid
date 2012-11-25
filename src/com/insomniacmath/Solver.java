package com.insomniacmath;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import org.ejml.simple.SimpleMatrix;


public class Solver implements Constants {

    public static final int RESULT_MATRIX = 1000;
    public static final int SOLVE_BUTTON_ID = 600;
    public static final int EXPLAIN_BUTTON_ID = 900;
    int state = STATE_INITIAL;
    public static int curEditId = 0;

    private float downY;
    private float downX;

    MatrixWrapper mainMatrixWrapper;
    LinearLayout mainMatrixView;
    LinearLayout resultView;
    TextView resultText;
    Button xplainButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapCenterHor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillFill = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams c80x80left100 = new LinearLayout.LayoutParams(80, 80);
    LinearLayout.LayoutParams c80x80 = new LinearLayout.LayoutParams(80, 80);
    private Context _context;
    private LinearLayout solvationView;

    LinearLayout bottomPlusHolder;
    LinearLayout rightPlusHolder;

    private LinearLayout secondMatrixView;
    private MatrixWrapper secondMatrixWrapper;
    private Animator animator;
    LinearLayout scrollWrapper;
    MatrixWrapper resMatrixWrapper;

    public Solver(Context context, LinearLayout mainView) {

        _context = context;
        wrapWrap.gravity = Gravity.LEFT;
        wrapWrapCenterHor.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        HorizontalScrollView scrollView = new HorizontalScrollView(context);

        scrollWrapper = new LinearLayout(context);
        mainMatrixView = new LinearLayout(context);

        mainMatrixView.setLayoutParams(wrapWrap);
        mainMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        mainMatrixWrapper = new MatrixWrapper(context, mainMatrixView, 0, true);

        scrollWrapper.addView(mainMatrixView, wrapWrap);


        rightPlusHolder = new LinearLayout(context);
        rightPlusHolder.setOrientation(LinearLayout.VERTICAL);

        ImageView plusColumn = new ImageView(context);
        plusColumn.setId(PLUS_COLUMN_ID);
        plusColumn.setImageResource(R.drawable.plus_small);
        rightPlusHolder.addView(plusColumn, c80x80);
        plusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixWrapper.addColumn();
            }
        });

        ImageView minusColumn = new ImageView(context);
        minusColumn.setId(MINUS_COLUMN_ID);
        minusColumn.setImageResource(R.drawable.minus_small);
        rightPlusHolder.addView(minusColumn, c80x80);
        minusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixWrapper.removeColumn();
            }
        });

        scrollWrapper.addView(rightPlusHolder, wrapWrap);

        scrollView.addView(scrollWrapper, wrapWrap);
        mainView.addView(scrollView, wrapWrap);

        bottomPlusHolder = new LinearLayout(context);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(80, 80);

        ImageView plusRow = new ImageView(context);
        plusRow.setId(PLUS_ROW_ID);
        plusRow.setImageResource(R.drawable.plus_small);
        bottomPlusHolder.addView(plusRow, params1);

        ImageView minusRow = new ImageView(context);
        minusRow.setId(MINUS_ROW_ID);
        minusRow.setImageResource(R.drawable.minus_small);
        bottomPlusHolder.addView(minusRow, params1);

        c80x80left100.leftMargin = 100;

        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixWrapper.addRow();
            }
        });

        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixWrapper.removeRow();
            }
        });
        mainView.addView(bottomPlusHolder, fillWrap);


        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
        solvationView = new LinearLayout(context);
        solvationView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    animator.changeSpeed((motionEvent.getX() - downX) > 0);
                return true;
            }
        });

        solvationView.setOrientation(LinearLayout.VERTICAL);
//        solvationView.setPadding(15, 15, 0, 0);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);

        mainView.addView(solvationView);

        resultView = new LinearLayout(context);
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setPadding(0, 5, 0, 0);
        resultView.setBackgroundColor(0x12345678);
        resultView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    float dx = (motionEvent.getX() - downX);
                    float dy = (motionEvent.getY() - downY);
                    if (Math.abs(dx) > Math.abs(dy)) {
                        moveToEdit(dx < 0 ? LEFT : RIGHT);
                    } else {
                        moveToEdit(dy < 0 ? UP : DOWN);
                    }
                }
                return true;
            }
        });

        mainView.addView(resultView, fillFill);

        animator = new Animator(mainMatrixWrapper);
        animator.setView(solvationView);
    }

    public void onDestroy() {
        mainMatrixWrapper.onDestroy();
    }

    // --------------------------------------------compute----------------------------------------------------

    public void findEigenVectors() {

    }

    public void findRang() {
        resultView.removeAllViews();
        addResultText();
        try {
            resultText.setText("Rang = " + Utils.bra(mainMatrixWrapper.findRang(), false));
            resultText.setTextColor(Color.WHITE);
            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);

            state = STATE_RANG_FIND;
            addXplainButton();
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        }
    }

    public void findDeterminant() {
        resultView.removeAllViews();
        addResultText();
        try {
            resultText.setText("det = " + Utils.bra(mainMatrixWrapper.findDeterminant(), false));
            resultText.setTextColor(Color.WHITE);
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);
            state = STATE_DETERMIN_PRESSED;
            addXplainButton();
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        } catch (NotSquareException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        }
    }

    public void findMultiplication() {
        resultView.removeAllViews();
        try {
            mainMatrixWrapper.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            addResultText();
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
            return;
        }
        try {
            secondMatrixWrapper.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            addResultText();
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
            return;
        }

        state = STATE_MULTIPLY_FIND;

        resultText.setVisibility(View.GONE);

        SimpleMatrix a = new SimpleMatrix(mainMatrixWrapper.m);
        SimpleMatrix b = new SimpleMatrix(secondMatrixWrapper.m);

        SimpleMatrix c = a.mult(b);

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultMatrixLay.setId(RESULT_MATRIX);
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);

        resMatrixWrapper = new MatrixWrapper(_context, resultMatrixLay, 2, false);
        resMatrixWrapper.adjustSizeTo(c.numRows(), c.numCols());
        for (int i = 0; i < c.numRows(); i++) {
            for (int j = 0; j < c.numCols(); j++) {
                resMatrixWrapper.m[i][j] = c.get(i, j);
            }
        }
        resMatrixWrapper.fillViewsFromMatrix();
        resMatrixWrapper.refreshVisible();
        animator.setResultMW(resMatrixWrapper);
        xplainButton.setVisibility(View.VISIBLE);
        animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixWrapper.rows, mainMatrixWrapper.columns);
    }

    public void findInverse() {
        resultView.removeAllViews();
        try {
            mainMatrixWrapper.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            addResultText();
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
        }

        if (mainMatrixWrapper.columns != mainMatrixWrapper.rows) {
            addResultText();
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
        }

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultMatrixLay.setId(RESULT_MATRIX);
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
        resMatrixWrapper = new MatrixWrapper(_context, resultMatrixLay, 2, false);

        if (mainMatrixWrapper.elementsFractions) {
            Fraction[][] result = mainMatrixWrapper.findInverseFraction();
            resMatrixWrapper.adjustSizeTo(result[0].length, result.length);
            resMatrixWrapper.mFrac = result;
        } else {
            SimpleMatrix inverse = mainMatrixWrapper.findInverseDouble();
            resMatrixWrapper.adjustSizeTo(inverse.numCols(), inverse.numRows());
            for (int i = 0; i < inverse.numRows(); i++) {
                for (int j = 0; j < inverse.numCols(); j++) {
                    double v = inverse.get(i, j);
                    resMatrixWrapper.m[i][j] = v;
                }
            }
        }

        state = STATE_INVERT_FIND;

        resMatrixWrapper.fillViewsFromMatrix();
        resMatrixWrapper.refreshVisible();
        animator.setResultMW(resMatrixWrapper);
        animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixWrapper.rows, mainMatrixWrapper.columns);
        addXplainButton();
    }

    private void findSystemSolvation() {
        resultView.removeAllViews();
        Log.d("zzzzzzzzzzzz", "start beforefill at" + System.currentTimeMillis());
        try {
            mainMatrixWrapper.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            e.printStackTrace();
        }
        Log.d("zzzzzzzzzzzz", "start afterfill at" + System.currentTimeMillis());

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultMatrixLay.setId(RESULT_MATRIX);
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
        resMatrixWrapper = new MatrixWrapper(_context, resultMatrixLay, 2, false);

        if (mainMatrixWrapper.elementsFractions) {
            Fraction[] result = mainMatrixWrapper.solveSLEFraction();
            resMatrixWrapper.adjustSizeTo(1, result.length);
            resMatrixWrapper.mFrac = new Fraction[result.length][];

            for (int i = 0; i < result.length; i++) {
                resMatrixWrapper.mFrac[i] = new Fraction[1];
                resMatrixWrapper.mFrac[i][0] = result[i];
            }

        } else {
            SimpleMatrix result = mainMatrixWrapper.solveSLEDouble();
            resMatrixWrapper.adjustSizeTo(result.numCols(), result.numRows());
            for (int i = 0; i < result.numRows(); i++) {
                for (int j = 0; j < result.numCols(); j++) {
                    double v = result.get(i, j);
                    resMatrixWrapper.m[i][j] = v;
                }
            }
        }

        resMatrixWrapper.fillViewsFromMatrix();
        resMatrixWrapper.refreshVisible();
        // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);
        state = STATE_INVERT_FIND;
        animator.setResultMW(resMatrixWrapper);

    }

    // -------------------------------------add UI elements----------------------------------------------------

    private void addXplainButton() {
        xplainButton = new Button(_context);
        xplainButton.setId(EXPLAIN_BUTTON_ID);
        xplainButton.setBackgroundResource(R.drawable.xplain_but);
        xplainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                solvationView.setVisibility(View.VISIBLE);
                startExplain();
                xplainButton.setVisibility(View.GONE);
                solvationView.removeAllViews();
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160, 64);
        params.setMargins(15, 0, 0, 0);
        resultView.addView(xplainButton, params);
    }

    private void addResultText() {
        resultText = new TextView(_context);
        resultText.setTextSize(20);
        resultText.setPadding(20, 20, 20, 20);
        resultText.setId(RESULT_ID);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, wrapWrapCenterHor);
    }

    public void addSecondMatrix() {

        state = STATE_MULTIPLY_PRESSED;
        bottomPlusHolder.setVisibility(View.GONE);
        rightPlusHolder.setVisibility(View.GONE);

        secondMatrixView = new LinearLayout(_context);

        secondMatrixView.setLayoutParams(wrapWrap);
        secondMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        secondMatrixWrapper = new MatrixWrapper(_context, secondMatrixView, 1, true);
        secondMatrixWrapper.adjustSizeTo(mainMatrixWrapper.rows, mainMatrixWrapper.columns);
        secondMatrixWrapper.refreshVisible();

        mainMatrixView.addView(secondMatrixView);

        animator.setSecMW(secondMatrixWrapper);
    }

    // -----------------------------------------controls----------------------------------------------------

    public void moveToEdit(int direction) {
        int newId;
        EditText input2;
        if (curEditId < 100) {
            newId = mainMatrixWrapper.getNextEdit(direction, curEditId);
            input2 = (EditText) mainMatrixWrapper.bodyMatrix.findViewById(newId);
        } else {
            newId = secondMatrixWrapper.getNextEdit(direction, curEditId - 100);
            input2 = (EditText) secondMatrixWrapper.bodyMatrix.findViewById(newId);
        }
        if (newId != -1) {
            input2.requestFocus();
            curEditId = newId;
        }
    }

    private void startExplain() {
        animator.startExplaining();
        switch (state) {
            case STATE_DETERMIN_PRESSED:
                state = STATE_DETERMIN_EXPLAINING;
                break;
            case STATE_MULTIPLY_FIND:
                state = STATE_MULTIPLY_EXPLAINING;
                break;
        }
    }

    public void stopExplain() {
        animator.stopExplain();
    }

    public void onMenu(int i) {
        switch (i) {
            case R.id.determinant:
                findDeterminant();
                break;
            case R.id.multiply:
                addSecondMatrix();
                break;
            case R.id.invers:
                findInverse();
                break;
            case R.id.rank:
                findRang();
                break;
            case R.id.solve_sys:
                mainMatrixWrapper.addSideColumn();
                state = STATE_SIDE_COLUMN_ADDED;
                break;
            case R.id.eigen:
                findEigenVectors();
                break;
            case R.id.solve:
                if (state == STATE_SIDE_COLUMN_ADDED) {
                    findSystemSolvation();
                } else if (state == STATE_MULTIPLY_PRESSED)
                    findMultiplication();
                break;
        }
    }

    public boolean onBackPressed() {
        switch (state) {
            case STATE_INITIAL:
                return false;
            case STATE_DETERMIN_PRESSED:
                state = STATE_INITIAL;
                resultText.setVisibility(View.GONE);
                solvationView.setVisibility(View.GONE);
                xplainButton.setVisibility(View.GONE);
                break;
            case STATE_DETERMIN_EXPLAINING:
                animator.stopExplain();
            case STATE_DETERMIN_EXPLAINED:
                state = STATE_DETERMIN_PRESSED;
                solvationView.setVisibility(View.GONE);
                xplainButton.setVisibility(View.VISIBLE);
                break;
            case STATE_MULTIPLY_PRESSED:
            case STATE_MULTIPLY_FIND:
            case STATE_MULTIPLY_EXPLAINING:
            case STATE_MULTIPLY_EXPLAINED:
                mainMatrixView.removeView(secondMatrixView);
                secondMatrixWrapper.onDestroy();

                resultView.removeAllViews();
                resMatrixWrapper.onDestroy();

            case STATE_INVERT_FIND:
            case STATE_RANG_FIND:
            case STATE_SIDE_COLUMN_ADDED:
            case STATE_SYSTEM_SOLVED:

                state = STATE_INITIAL;
                bottomPlusHolder.setVisibility(View.VISIBLE);
                rightPlusHolder.setVisibility(View.VISIBLE);

                solvationView.removeAllViews();

                Thread.yield();
                secondMatrixView = null;
                secondMatrixWrapper = null;
                break;

        }

        return true;
    }
}
