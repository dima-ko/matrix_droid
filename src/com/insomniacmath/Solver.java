package com.insomniacmath;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.*;

public class Solver {

    Matrix mainMatrix;
    LinearLayout mainMatrixView;
    LinearLayout resultView;
    TextView resultText;
    ImageView solveButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams c80x80 = new LinearLayout.LayoutParams(80, 80);
    private Context _context;
    private LinearLayout solvationView;

    LinearLayout bottomHolder;


    boolean isShowingSolvation = false;


    public Solver(Context context, LinearLayout mainView) {

        _context = context;

        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        LinearLayout scrollWrapper = new LinearLayout(context);
        scrollWrapper.setOrientation(LinearLayout.VERTICAL);
        mainMatrixView = new LinearLayout(context);

        mainMatrixView.setLayoutParams(wrapWrap);
        mainMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        mainMatrix = new Matrix(context, mainMatrixView);

        scrollWrapper.addView(mainMatrixView);

        bottomHolder = new LinearLayout(context);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(80, 80);

        ImageView plusRow = new ImageView(context);
        plusRow.setImageResource(R.drawable.plus_small);
        bottomHolder.addView(plusRow, params1);

        ImageView minusRow = new ImageView(context);
        minusRow.setImageResource(R.drawable.minus_small);
        bottomHolder.addView(minusRow, params1);


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
        c80x80.leftMargin = 100;
        bottomHolder.addView(solveButton, c80x80);


        scrollWrapper.addView(bottomHolder, fillWrap);

        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrix.addRow();
                mainMatrix.makeVisible();
            }
        });

        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrix.removeRow();
                mainMatrix.makeVisible();
            }
        });

        scrollView.addView(scrollWrapper, wrapWrap);
        mainView.addView(scrollView, wrapWrap);

        wrapWrap.gravity = Gravity.CENTER_HORIZONTAL;

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
        solvationView = new LinearLayout(context);
        solvationView.setOrientation(LinearLayout.VERTICAL);
        solvationView.setPadding(15, 15, 0, 0);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);

        mainMatrix.animator.setView(solvationView);
        mainView.addView(solvationView);

        resultView = new LinearLayout(context);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setGravity(Gravity.RIGHT);
        resultView.setPadding(0, 20, 0, 0);
        resultView.setVisibility(View.GONE);


        resultText = new TextView(context);
        resultText.setTextSize(20);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, fillWrap);


        mainView.addView(resultView);
    }


    private void stopSolvationCast() {

        mainMatrix.animator.stopSolvation();

    }

    private void startSolvationCast() {

        mainMatrix.animator.startSolvation();

    }


    Dialog d;


    public void findProperVectors() {

    }

    public void findRang() {


    }

    public void findInverse() {


    }

    public void findMultiplication(Matrix secondMatrix) {

    }


    public void findDeterminant() {
        try {
            resultText.setText("Determinant = " + Utils.round(mainMatrix.findDeterminant()));
            resultView.setVisibility(View.VISIBLE);
            solveButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
           // solveButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));

            if (mainMatrix.rows == 2 && mainMatrix.columns == 2) {
                mainMatrix.animator.setAnimType(Animator.ANIM_DETERMINANT_2x2);
            } else if (mainMatrix.rows == 3 && mainMatrix.columns == 3) {
                mainMatrix.animator.setAnimType(Animator.ANIM_DETERMINANT_3x3);
            } else ;


        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsiutable");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        } catch (BadMatrixException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);

        }
//        Toast.makeText(_context, mainMatrix.findDeterminant() + "", 2000).show();
    }


}
