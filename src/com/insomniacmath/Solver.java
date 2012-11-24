package com.insomniacmath;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.insomniacmath.roboto.ButtonRoboto;
import org.apache.commons.math.fraction.Fraction;
import org.ejml.simple.SimpleMatrix;


public class Solver implements Constants {

    public static final int RESULT_MATRIX = 1000;
    public static final int SOLVE_BUTTON_ID = 600;
    public static final int EXPLAIN_BUTTON_ID = 900;
    int state = STATE_INITIAL;

    MatrixWrapper mainMatrixWrapper;
    LinearLayout mainMatrixView;
    LinearLayout resultView;
    TextView resultText;
    Button xplainButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapCenterHor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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

    public void onDestroy() {
        mainMatrixWrapper.onDestroy();
    }

    LinearLayout scrollWrapper;
    View solveVariants;

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

        xplainButton = new Button(context);
        xplainButton.setVisibility(View.GONE);
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
        resultView.setOrientation(LinearLayout.VERTICAL);
        resultView.setPadding(0, 20, 0, 0);
        resultView.addView(xplainButton, new LinearLayout.LayoutParams(240, 96));
        resultView.setBackgroundColor(0x12345678);


        resultText = new TextView(context);
        resultText.setVisibility(View.GONE);
        resultText.setTextSize(20);
        resultText.setPadding(20, 20, 20, 20);
        resultText.setId(RESULT_ID);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, wrapWrapCenterHor);
        mainView.addView(resultView, fillWrap);


        solveButton = new ButtonRoboto(_context);
        solveButton.setPadding(30, 30, 30, 30);
        solveButton.setTextColor(Color.WHITE);
        solveButton.setText("Solve");
        solveButton.setVisibility(View.GONE);
        solveButton.setId(SOLVE_BUTTON_ID);
        resultView.addView(solveButton, fillWrap);
        solveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                solveButton.setVisibility(View.GONE);
                if (state == STATE_SIDE_COLUMN_ADDED) {
                    findSystemSolvation();
                } else if (state == STATE_MULTIPLY_PRESSED)
                    findMultiplication();
            }
        });

        solveVariants = (((Activity) _context).getLayoutInflater()).inflate(R.layout.solves, null);
        resultView.addView(solveVariants, fillWrap);

        solveVariants.findViewById(R.id.determinant).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                findDeterminant();
            }
        });
        solveVariants.findViewById(R.id.multiply).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addSecondMatrix();
            }
        });
        solveVariants.findViewById(R.id.inverse).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                findInverse();
            }
        });
        solveVariants.findViewById(R.id.rang).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                findRang();
            }
        });
        solveVariants.findViewById(R.id.solve_sys).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixWrapper.addSideColumn();
                solveVariants.setVisibility(View.GONE);
                solveButton.setVisibility(View.VISIBLE);
                state = STATE_SIDE_COLUMN_ADDED;
            }
        });
        solveVariants.findViewById(R.id.eigen).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                findEigenVectors();
            }
        });

        animator = new Animator(mainMatrixWrapper);
        animator.setView(solvationView);
    }

    private void findSystemSolvation() {

        Log.d("zzzzzzzzzzzz", "start beforefill at" + System.currentTimeMillis());
        try {
            mainMatrixWrapper.fillMatrixFromGrid();
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

        resMatrixWrapper.fillGridFromMatrix();
        resMatrixWrapper.refreshVisible();

        resultView.setVisibility(View.VISIBLE);
        resultText.setVisibility(View.VISIBLE);

        xplainButton.setVisibility(View.VISIBLE);
        resultText.setTextColor(Color.WHITE);
        // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);
        state = STATE_INVERT_FIND;
        solveVariants.setVisibility(View.GONE);

        animator.setResultMW(resMatrixWrapper);
        xplainButton.setVisibility(View.VISIBLE);

    }

    ButtonRoboto solveButton;

    public void stopExplain() {
        animator.stopExplain();
    }

    float downX;

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

    public void findEigenVectors() {

    }

    public void findRang() {
        try {
            resultView.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            resultText.setText("Rang = " + Utils.bra(mainMatrixWrapper.findRang(), false));
            xplainButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);

            state = STATE_RANG_FIND;
            solveVariants.setVisibility(View.GONE);

        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        }
    }

    public void findInverse() {
        try {
            resultView.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);

            SimpleMatrix inverse = mainMatrixWrapper.findInverse();

            xplainButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);

            state = STATE_INVERT_FIND;
            solveVariants.setVisibility(View.GONE);

            LinearLayout resultMatrixLay = new LinearLayout(_context);
            resultMatrixLay.setId(RESULT_MATRIX);
            resultView.addView(resultMatrixLay, wrapWrapCenterHor);

            resMatrixWrapper = new MatrixWrapper(_context, resultMatrixLay, 2, false);
            resMatrixWrapper.adjustSizeTo(inverse.numRows(), inverse.numCols());
            for (int i = 0; i < inverse.numRows(); i++) {
                for (int j = 0; j < inverse.numCols(); j++) {
                    resMatrixWrapper.m[i][j] = inverse.get(i, j);
                }
            }
            resMatrixWrapper.fillGridFromMatrix();
            resMatrixWrapper.refreshVisible();
            animator.setResultMW(resMatrixWrapper);
            xplainButton.setVisibility(View.VISIBLE);
            animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixWrapper.rows, mainMatrixWrapper.columns);

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

        solveVariants.setVisibility(View.GONE);
        solveButton.setVisibility(View.VISIBLE);

        animator.setSecMW(secondMatrixWrapper);
    }

    public void findMultiplication() {

        try {
            mainMatrixWrapper.fillMatrixFromGrid();
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
            return;
        }
        try {
            secondMatrixWrapper.fillMatrixFromGrid();
        } catch (BadSymbolException e) {
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
        resMatrixWrapper.fillGridFromMatrix();
        resMatrixWrapper.refreshVisible();
        animator.setResultMW(resMatrixWrapper);
        xplainButton.setVisibility(View.VISIBLE);
        animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixWrapper.rows, mainMatrixWrapper.columns);
    }

    MatrixWrapper resMatrixWrapper;

    public void findDeterminant() {
        try {
            resultView.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            resultText.setText("Determinant = " + Utils.bra(mainMatrixWrapper.findDeterminant(), false));
            xplainButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixWrapper.rows, mainMatrixWrapper.columns);

            state = STATE_DETERMIN_PRESSED;
            solveVariants.setVisibility(View.GONE);

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

    public boolean onBackPressed() {
        switch (state) {
            case STATE_INITIAL:
                return false;
            case STATE_DETERMIN_PRESSED:
                state = STATE_INITIAL;
                resultText.setVisibility(View.GONE);
                solvationView.setVisibility(View.GONE);
                xplainButton.setVisibility(View.GONE);
                solveVariants.setVisibility(View.VISIBLE);
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

                solveVariants.setVisibility(View.VISIBLE);
                solveButton.setVisibility(View.GONE);
                break;

        }

        return true;
    }
}
