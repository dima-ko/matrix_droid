package com.insomniacmath;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.etc.Utils;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.SingularMatrixException;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.ui.EditableMatrixView;
import com.insomniacmath.ui.MatrixView;
import org.ejml.simple.SimpleMatrix;


public class Controller implements Constants {

    public static int state = STATE_INITIAL;

    private float downY;
    private float downX;

    EditableMatrixView mainMatrixModel;

    LinearLayout mainMatrixLayout;

    private Context _context;
    private LinearLayout mainView;
    LinearLayout bottomPlusHolder;
    LinearLayout rightPlusHolder;
    LinearLayout scrollWrapper;

    Dialog dialog;


    public Controller(Context context, LinearLayout mainView) {

        _context = context;
        this.mainView = mainView;

        setActionBar(mainView);

        HorizontalScrollView scrollView = new HorizontalScrollView(context);

        scrollWrapper = new LinearLayout(context);
        mainMatrixLayout = new LinearLayout(context);

        mainMatrixLayout.setLayoutParams(wrapWrap);
        mainMatrixLayout.setOrientation(LinearLayout.HORIZONTAL);

        mainMatrixModel = new MatrixView(context, mainMatrixLayout, 0);

        scrollWrapper.addView(mainMatrixLayout, wrapWrap);


        rightPlusHolder = new LinearLayout(context);
        rightPlusHolder.setOrientation(LinearLayout.VERTICAL);

        ImageView plusColumn = new ImageView(context);
        plusColumn.setId(PLUS_COLUMN_ID);
        plusColumn.setImageResource(R.drawable.plus_small);
        rightPlusHolder.addView(plusColumn, c80x80);
        plusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.addColumn();
            }
        });

        ImageView minusColumn = new ImageView(context);
        minusColumn.setId(MINUS_COLUMN_ID);
        minusColumn.setImageResource(R.drawable.minus_small);
        rightPlusHolder.addView(minusColumn, c80x80);
        minusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.removeColumn();
            }
        });

        scrollWrapper.addView(rightPlusHolder, wrapWrap);

        scrollView.addView(scrollWrapper, wrapWrap);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrapWrap);
        params.setMargins(10, 19, 0, 0);
        mainView.addView(scrollView, params);

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


        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.addRow();
            }
        });

        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.removeRow();
            }
        });
        mainView.addView(bottomPlusHolder, fillWrap);

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------

    }

    private void setActionBar(LinearLayout mainView) {
        RelativeLayout actionBar = (RelativeLayout) ((Activity) _context).getLayoutInflater().inflate(R.layout.top, null);
        mainView.addView(actionBar, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.actions_menu);
        dialog.findViewById(R.id.determinant).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.multiply).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.invers).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.solve_sys).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.rank).setOnClickListener(actionsClickListener);

        explaining = actionBar.findViewById(R.id.explaining);

        solveButton = actionBar.findViewById(R.id.solve);
        solveButton.setOnClickListener(actionsClickListener);

        backButton = actionBar.findViewById(R.id.back_arrow);
        backButton.setOnClickListener(actionsClickListener);

        actionButton = actionBar.findViewById(R.id.action);
        actionButton.setOnClickListener(actionsClickListener);
    }

    View.OnClickListener actionsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.action) {
                ((Activity) _context).getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.show();
            } else if (id == R.id.back_arrow) {
                onBackPressed();
            } else if (id == R.id.solve) {
                if (state == STATE_SIDE_COLUMN_ADDED) {
                    try {
                        findSystemSolvation();
                        solveButton.setVisibility(View.GONE);
                    } catch (SingularMatrixException e) {
                        e.printStackTrace();  //Todo
                    }
                } else if (state == STATE_MULTIPLY_PRESSED) {
                    findMultiplication();
                    solveButton.setVisibility(View.GONE);
                }
            } else {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                onMenu(id);
            }
        }
    };

    public void onDestroy() {
        stopExplain();
        mainMatrixModel.onDestroy();
    }

    private void showXplainButton() {
        backButton.setVisibility(View.VISIBLE);
        actionButton.setVisibility(View.GONE);
        xplainButton = (Button) mainView.findViewById(R.id.explain);
        xplainButton.setVisibility(View.VISIBLE);
        xplainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                explaining.setVisibility(View.VISIBLE);
                solvationView.setVisibility(View.VISIBLE);
                solvationView.removeAllViews();
                if (state == STATE_SYSTEM_SOLVED) {
                    showSystemDialog();
                } else {
                    xplainButton.setVisibility(View.GONE);
                    startExplain();
                }
            }
        });
    }

    private void showSystemDialog() {


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
        actionButton.setVisibility(View.GONE);
        solveButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);

        secondMatrixView = new LinearLayout(_context);

        secondMatrixView.setLayoutParams(wrapWrap);
        secondMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        secondMatrixModel = new MatrixView(_context, secondMatrixView, 1);
        secondMatrixModel.adjustSizeTo(mainMatrixModel.rows, mainMatrixModel.columns);
        secondMatrixModel.refreshVisible();

        mainMatrixLayout.addView(secondMatrixView);

        animator.setSecMW(secondMatrixModel);
    }

    // -----------------------------------------controls----------------------------------------------------

//    public void moveToEdit(int direction) {
//        int newId;
//        EditText input2;
//        if (curEditId < 100) {
//            newId = mainMatrixModel.getNextEdit(direction, curEditId);
//            input2 = (EditText) mainMatrixModel._view.findViewById(newId);
//        } else {
//            newId = secondMatrixModel.getNextEdit(direction, curEditId - 100);
//            input2 = (EditText) secondMatrixModel.bodyMatrix.findViewById(newId);
//        }
//        if (newId != -1) {
//            input2.requestFocus();
//            curEditId = newId;
//        }
//    }



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
                mainMatrixModel.addSideColumn();
                state = STATE_SIDE_COLUMN_ADDED;
                break;
//            case R.id.eigen:
//                findEigenVectors();
//                break;

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
                backButton.setVisibility(View.GONE);
                actionButton.setVisibility(View.VISIBLE);
                bottomPlusHolder.setVisibility(View.VISIBLE);
                rightPlusHolder.setVisibility(View.VISIBLE);
                break;
            case STATE_DETERMIN_EXPLAINING:
                animator.stopExplain();
            case STATE_DETERMIN_EXPLAINED:
                explaining.setVisibility(View.GONE);
                state = STATE_DETERMIN_PRESSED;
                solvationView.setVisibility(View.GONE);
                xplainButton.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
                break;
            case STATE_MULTIPLY_EXPLAINED:
                resultView.removeView(resultMatrixLayout);
                resMatrixModel.onDestroy();
            case STATE_MULTIPLY_PRESSED:
            case STATE_MULTIPLY_FIND:
            case STATE_MULTIPLY_EXPLAINING:
                mainMatrixLayout.removeView(secondMatrixView);
                secondMatrixModel.onDestroy();
                resultView.removeAllViews();
            case STATE_SYSTEM_EXPLAINING_GAUS:
                resultView.removeView(resultMatrixLayout);
                if (resMatrixModel != null)
                    resMatrixModel.onDestroy();
                break;
            case STATE_INVERT_EXPLAINING:
                animator.stopExplain();
            case STATE_INVERT_EXPLAINED:
                solvationView.setVisibility(View.GONE);    //todo decimal is FRACTION
                explaining.setVisibility(View.GONE);
                xplainButton.setVisibility(View.VISIBLE);
                state = STATE_INVERT_FIND;
                break;
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
                secondMatrixModel = null;
                break;

        }

        return true;
    }
}
