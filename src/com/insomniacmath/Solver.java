package com.insomniacmath;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.*;
import org.ejml.simple.SimpleMatrix;


public class Solver implements Constants {

    UIMatrix mainUIMatrix;
    LinearLayout mainMatrixView;
    LinearLayout resultView;
    TextView resultText;
    ImageView solveButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams c80x80left100 = new LinearLayout.LayoutParams(80, 80);
    LinearLayout.LayoutParams c80x80 = new LinearLayout.LayoutParams(80, 80);
    private Context _context;
    private LinearLayout solvationView;

    LinearLayout bottomPlusHolder;
    LinearLayout rightPlusHolder;


    boolean isShowingSolvation = false;
    private LinearLayout secondMatrixView;
    private UIMatrix secondUIMatrix;


    public void onDestroy() {
        mainUIMatrix.onDestroy();
    }

    LinearLayout scrollWrapper;

    public Solver(Context context, LinearLayout mainView) {

        _context = context;
        wrapWrap.gravity = Gravity.LEFT;
        HorizontalScrollView scrollView = new HorizontalScrollView(context);

        scrollWrapper = new LinearLayout(context);
        mainMatrixView = new LinearLayout(context);

        mainMatrixView.setLayoutParams(wrapWrap);
        mainMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        mainUIMatrix = new UIMatrix(context, mainMatrixView);

        scrollWrapper.addView(mainMatrixView, wrapWrap);


        rightPlusHolder = new LinearLayout(context);
        rightPlusHolder.setOrientation(LinearLayout.VERTICAL);

        ImageView plusColumn = new ImageView(context);
        plusColumn.setId(PLUS_COLUMN_ID);
        plusColumn.setImageResource(R.drawable.plus_small);
        rightPlusHolder.addView(plusColumn, c80x80);
        plusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainUIMatrix.addColumn();
                mainUIMatrix.refreshVisible();
            }
        });

        ImageView minusColumn = new ImageView(context);
        minusColumn.setId(MINUS_COLUMN_ID);
        minusColumn.setImageResource(R.drawable.minus_small);
        rightPlusHolder.addView(minusColumn, c80x80);
        minusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainUIMatrix.removeColumn();
                mainUIMatrix.refreshVisible();
            }
        });

        scrollWrapper.addView(rightPlusHolder, wrapWrap);

        scrollView.addView(scrollWrapper, wrapWrap);
        mainView.addView(scrollView, wrapWrap);

        solveButton = new ImageView(context);
        solveButton.setImageResource(R.drawable.gear);
        solveButton.setVisibility(View.INVISIBLE);
        solveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isShowingSolvation) {
                    solvationView.setVisibility(View.VISIBLE);
                    startSolvationCast();
                    isShowingSolvation = true;
                    Animation animation = AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw);
                    animation.setInterpolator(new Interpolator() {
                        public float getInterpolation(float v) {
                            return v;
                        }
                    });
                    solveButton.startAnimation(animation);

                } else {
                    solvationView.setVisibility(View.GONE);
                    stopSolvationCast();
                    isShowingSolvation = false;
                    solveButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_ccw));
                }
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
        bottomPlusHolder.addView(solveButton, c80x80left100);

        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainUIMatrix.addRow();
                mainUIMatrix.refreshVisible();
            }
        });

        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainUIMatrix.removeRow();
                mainUIMatrix.refreshVisible();
            }
        });

        mainView.addView(bottomPlusHolder, fillWrap);


        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
        solvationView = new LinearLayout(context);
        solvationView.setOrientation(LinearLayout.VERTICAL);
        solvationView.setPadding(15, 15, 0, 0);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);

        mainUIMatrix.animator.setView(solvationView);
        mainView.addView(solvationView);

        resultView = new LinearLayout(context);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setPadding(0, 20, 0, 0);
        resultView.setVisibility(View.GONE);


        resultText = new TextView(context);
        resultText.setTextSize(20);
        resultText.setId(RESULT_ID);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, wrapWrap);
        mainView.addView(resultView);
    }


    private void stopSolvationCast() {

        mainUIMatrix.animator.stopSolvation();

    }

    private void startSolvationCast() {

        mainUIMatrix.animator.startSolvation();

    }


//    Dialog d;


    public void findProperVectors() {

    }

    public void findRang() {


    }

    public void findInverse() {


    }

    public void addSecondMatrix() {

        bottomPlusHolder.setVisibility(View.GONE);
        rightPlusHolder.setVisibility(View.GONE);

        secondMatrixView = new LinearLayout(_context);

        secondMatrixView.setLayoutParams(wrapWrap);
        secondMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        secondUIMatrix = new UIMatrix(_context, secondMatrixView);
        secondUIMatrix.adjustSizeTo(mainUIMatrix.rows, mainUIMatrix.columns);
        secondUIMatrix.refreshVisible();

        mainMatrixView.addView(secondMatrixView);

    }

    public void findMultiplication() {
        resultView.setVisibility(View.VISIBLE);


        try {
            mainUIMatrix.fillMatrixFromGrid();
        }  catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
        }
        try {
            secondUIMatrix.fillMatrixFromGrid();
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
        }

        SimpleMatrix a = new SimpleMatrix(mainUIMatrix.m);
        SimpleMatrix b = new SimpleMatrix(secondUIMatrix.m);

        SimpleMatrix c = a.mult(b);

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultView.addView(resultMatrixLay, wrapWrap);
        UIMatrix resMatrix = new UIMatrix(_context, resultMatrixLay);
        resMatrix.adjustSizeTo(c.numRows(), c.numCols());
        for (int i = 0; i < c.numRows(); i++) {
            for (int j = 0; j < c.numCols(); j++) {
                resMatrix.m[i][j] = c.get(i, j);
            }
        }
        resMatrix.fillGridFromMatrix();
        resMatrix.refreshVisible();
    }


    public void findDeterminant() {
        resultView.setVisibility(View.VISIBLE);
        try {
            resultText.setText("Determinant = " + Utils.round(mainUIMatrix.findDeterminant()));
            solveButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
            // solveButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));

            if (mainUIMatrix.rows == 2 && mainUIMatrix.columns == 2) {
                mainUIMatrix.animator.setAnimType(Animator.ANIM_DETERMINANT_2x2);
            } else if (mainUIMatrix.rows == 3 && mainUIMatrix.columns == 3) {
                mainUIMatrix.animator.setAnimType(Animator.ANIM_DETERMINANT_3x3);
            }


        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        } catch (BadMatrixException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);

        }
//        Toast.makeText(_context, mainUIMatrix.findDeterminant() + "", 2000).show();
    }


}
