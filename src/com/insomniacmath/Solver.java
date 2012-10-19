package com.insomniacmath;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.*;
import com.insomniacmath.roboto.ButtonRoboto;
import org.ejml.simple.SimpleMatrix;


public class Solver implements Constants {

    UIMatrix mainUIMatrix;
    LinearLayout mainMatrixView;
    RelativeLayout resultView;
    TextView resultText;
    ImageView solvationButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapCenterHor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
    RelativeLayout.LayoutParams fillFillRel = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        wrapWrapCenterHor.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
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

        solvationButton = new ImageView(context);
        solvationButton.setImageResource(R.drawable.gear);
        solvationButton.setVisibility(View.INVISIBLE);
        solvationButton.setOnClickListener(new View.OnClickListener() {
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
                    solvationButton.startAnimation(animation);

                } else {
                    solvationView.setVisibility(View.GONE);
                    stopSolvationCast();
                    isShowingSolvation = false;
                    solvationButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_ccw));
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

        resultView = new RelativeLayout(context);
        resultView.setPadding(0, 20, 0, 0);
        resultView.setVisibility(View.GONE);
        resultView.addView(solvationButton, c80x80left100);


        resultText = new TextView(context);
        resultText.setTextSize(20);
        resultText.setId(RESULT_ID);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, wrapWrapCenterHor);
        mainView.addView(resultView, fillFillRel);
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
        ButtonRoboto solveButton = new ButtonRoboto(_context);
        solveButton.setPadding(30, 30, 30, 30);
        solveButton.setTextColor(Color.WHITE);
        solveButton.setText("Solve");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        resultView.addView(solveButton, params);
        solveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                findMultiplication();
            }
        });

        resultView.setVisibility(View.VISIBLE);

    }

    public void findMultiplication() {

        try {
            mainUIMatrix.fillMatrixFromGrid();
        } catch (BadSymbolException e) {
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
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
        UIMatrix resMatrix = new UIMatrix(_context, resultMatrixLay);
        resMatrix.adjustSizeTo(c.numRows(), c.numCols());
        for (int i = 0; i < c.numRows(); i++) {
            for (int j = 0; j < c.numCols(); j++) {
                resMatrix.m[i][j] = c.get(i, j);
            }
        }
        resMatrix.fillGridFromMatrix();
        resMatrix.refreshVisible();
        solvationButton.setVisibility(View.VISIBLE);

    }

    public void findDeterminant() {
        resultView.setVisibility(View.VISIBLE);
        try {
            resultText.setText("Determinant = " + Utils.round(mainUIMatrix.findDeterminant()));
            solvationButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
            // solvationButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));

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
