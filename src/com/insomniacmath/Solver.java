package com.insomniacmath;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.*;
import com.insomniacmath.roboto.ButtonRoboto;
import org.ejml.simple.SimpleMatrix;


public class Solver implements Constants {

    int state = STATE_INITIAL;

    UIMatrix mainUIMatrix;
    LinearLayout mainMatrixView;
    LinearLayout resultView;
    TextView resultText;
    ImageView xplainButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapCenterHor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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

        mainUIMatrix = new UIMatrix(context, mainMatrixView, 0);

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

        xplainButton = new ImageView(context);
        xplainButton.setVisibility(View.GONE);
        xplainButton.setImageResource(R.drawable.xplain_but);
        xplainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isShowingSolvation) {
                    solvationView.setVisibility(View.VISIBLE);
                    startExplain();
                    isShowingSolvation = true;
                    Animation animation = AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw);
                    animation.setInterpolator(new Interpolator() {
                        public float getInterpolation(float v) {
                            return v;
                        }
                    });
                    xplainButton.startAnimation(animation);

                } else {
                    solvationView.setVisibility(View.GONE);
                    stopSolvationCast();
                    isShowingSolvation = false;
                    xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_ccw));
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
//        solvationView.setPadding(15, 15, 0, 0);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);

        mainUIMatrix.animator.setView(solvationView);
        mainView.addView(solvationView);

        resultView = new LinearLayout(context);
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setOrientation(LinearLayout.VERTICAL);
        resultView.setPadding(0, 20, 0, 0);
        resultView.addView(xplainButton, new LinearLayout.LayoutParams(213,81));
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
        resultView.addView(solveButton, fillWrap);
        solveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
        solveVariants.findViewById(R.id.eigen).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                findeigenVectors();
            }
        });
    }

    ButtonRoboto solveButton;


    private void stopSolvationCast() {

        mainUIMatrix.animator.stopSolvation();

    }

    private void startExplain() {

        mainUIMatrix.animator.startExplaining(state);

    }


//    Dialog d;


    public void findeigenVectors() {

    }

    public void findRang() {


    }

    public void findInverse() {


    }

    public void addSecondMatrix() {

        state = STATE_MULTIPLY_PRESSED;
        bottomPlusHolder.setVisibility(View.GONE);
        rightPlusHolder.setVisibility(View.GONE);

        secondMatrixView = new LinearLayout(_context);

        secondMatrixView.setLayoutParams(wrapWrap);
        secondMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        secondUIMatrix = new UIMatrix(_context, secondMatrixView, 1);
        secondUIMatrix.adjustSizeTo(mainUIMatrix.rows, mainUIMatrix.columns);
        secondUIMatrix.refreshVisible();

        mainMatrixView.addView(secondMatrixView);

        solveVariants.setVisibility(View.GONE);
        solveButton.setVisibility(View.VISIBLE);

    }

    public void findMultiplication() {

        try {
            mainUIMatrix.fillMatrixFromGrid();
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
            return;
        }
        try {
            secondUIMatrix.fillMatrixFromGrid();
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsuitable");
            resultText.setTextColor(Color.RED);
            return;
        }

        state = STATE_MULTIPLY_FIND;

        resultText.setVisibility(View.GONE);

        SimpleMatrix a = new SimpleMatrix(mainUIMatrix.m);
        SimpleMatrix b = new SimpleMatrix(secondUIMatrix.m);

        SimpleMatrix c = a.mult(b);

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
        UIMatrix resMatrix = new UIMatrix(_context, resultMatrixLay, 2);
        resMatrix.adjustSizeTo(c.numRows(), c.numCols());
        for (int i = 0; i < c.numRows(); i++) {
            for (int j = 0; j < c.numCols(); j++) {
                resMatrix.m[i][j] = c.get(i, j);
            }
        }
        resMatrix.fillGridFromMatrix();
        resMatrix.refreshVisible();
        xplainButton.setVisibility(View.VISIBLE);

    }

    public void findDeterminant() {

        try {
            resultView.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            resultText.setText("Determinant = " + Utils.round(mainUIMatrix.findDeterminant()));
            xplainButton.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));

            if (mainUIMatrix.rows == 2 && mainUIMatrix.columns == 2) {
                mainUIMatrix.animator.setAnimType(Animator.ANIM_DETERMINANT_2x2);
            } else if (mainUIMatrix.rows == 3 && mainUIMatrix.columns == 3) {
                mainUIMatrix.animator.setAnimType(Animator.ANIM_DETERMINANT_3x3);
            }

            state = STATE_DETERMIN_PRESSED;
            solveVariants.setVisibility(View.GONE);

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
