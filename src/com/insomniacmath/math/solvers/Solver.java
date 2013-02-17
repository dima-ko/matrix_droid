package com.insomniacmath.math.solvers;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.Constants;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.ui.LParams;

public abstract class Solver implements Constants {


    protected LinearLayout mainView;
    protected Controller controller;

    private View backButton;

    private View explaining;
    private View xplainButton;

    protected Solver(LinearLayout mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
        addResultView();
        xplainButton = mainView.findViewById(R.id.explain);
        backButton = mainView.findViewById(R.id.back_arrow);
        explaining = mainView.findViewById(R.id.explaining);

    }

    protected Animator animator;

    protected LinearLayout solvationView;
    protected LinearLayout resultView;

    public void addSolvationView() {
        solvationView = new LinearLayout(mainView.getContext());
        solvationView.setBackgroundColor(0xff222222);
        //    solvationView.setOnTouchListener(new View.OnTouchListener() {
        //        public boolean onTouch(View view, MotionEvent motionEvent) {
        //            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        //                downX = motionEvent.getX();
        //            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
        //                animator.changeSpeed((motionEvent.getX() - downX) > 0);
        //            return true;
        //        }
        //    });
        solvationView.setOrientation(LinearLayout.VERTICAL);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);
        mainView.addView(solvationView);
    }


    public void addResultView() {
        resultView = new LinearLayout(mainView.getContext());
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setPadding(0, 5, 0, 0);
        resultView.setBackgroundColor(0xff000000);
        mainView.addView(resultView, LParams.L_FILL_FILL);
    }

    public void showInitial() {

    }

    public void showSolve() {

    }

    public void showExplaine() {

    }

    public void showExplaining() {

    }

    public void onDestroySolver() {
        mainView.removeView(resultView);
        mainView.removeView(solvationView);
        resultView = null;
        solvationView = null;
    }


    protected void showXplainButton() {
        backButton.setVisibility(View.VISIBLE);
        controller.actionButton.setVisibility(View.GONE);
        xplainButton.setVisibility(View.VISIBLE);
        xplainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onExplainClicked();
            }
        });
    }

    protected void onExplainClicked() {
        addSolvationView();
        explaining.setVisibility(View.VISIBLE);
        solvationView.setVisibility(View.VISIBLE);
        solvationView.removeAllViews();
        xplainButton.setVisibility(View.GONE);
        startExplain();
    }

    private void startExplain() {


    }

    private void showSystemDialog() {


    }

    //            if (id == R.id.action) {
//                ((Activity) _context).getWindow().setSoftInputMode(
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                dialog.show();
//            } else if (id == R.id.back_arrow) {
//                onBackPressed();
//            } else if (id == R.id.solve) {
//                if (state == STATE_SIDE_COLUMN_ADDED) {
//                    try {
//                        findSystemSolvation();
//                        solveButton.setVisibility(View.GONE);
//                    } catch (SingularMatrixException e) {
//                        e.printStackTrace();  //Todo
//                    }
//                } else if (state == STATE_MULTIPLY_PRESSED) {
//                    findMultiplication();
//                    solveButton.setVisibility(View.GONE);
//                }
//            } else {

//    public void addSecondMatrix() {
//
//        state = STATE_MULTIPLY_PRESSED;
//        bottomPlusHolder.setVisibility(View.GONE);
//        rightPlusHolder.setVisibility(View.GONE);
//        actionButton.setVisibility(View.GONE);
//        solveButton.setVisibility(View.VISIBLE);
//        backButton.setVisibility(View.VISIBLE);
//
//        secondMatrixView = new LinearLayout(_context);
//
//        secondMatrixView.setLayoutParams(wrapWrap);
//        secondMatrixView.setOrientation(LinearLayout.HORIZONTAL);
//
//        secondMatrixModel = new MatrixView(_context, secondMatrixView, 1);
//        secondMatrixModel.adjustSizeTo(mainMatrixView.rows, mainMatrixView.columns);
//        secondMatrixModel.refreshVisible();
//
//        mainMatrixLayout.addView(secondMatrixView);
//
//        animator.setSecMW(secondMatrixModel);
//    }


//
//    private void startExplain() {
//        ((Activity) _context).getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        switch (state) {
//            case STATE_DETERMIN_PRESSED:
//                state = STATE_DETERMIN_EXPLAINING;
//                break;
//            case STATE_MULTIPLY_FIND:
//                state = STATE_MULTIPLY_EXPLAINING;
//                break;
//            case STATE_SYSTEM_SOLVED:
//                state = STATE_SYSTEM_EXPLAINING_GAUS;
//                break;
//            case STATE_INVERT_FIND:
//                state = STATE_INVERT_EXPLAINING;
//                break;
//            default:
//                return;
//        }
//        solvationView.setVisibility(View.VISIBLE);
//        animator.startExplaining();
//    }

    public void stopExplain() {
        animator.stopExplain();

    }

    abstract void onBack();


    public boolean onBackPressed() {
//        switch (state) {
//            case STATE_INITIAL:
//                return false;
//            case STATE_DETERMIN_PRESSED:
//                state = STATE_INITIAL;
//                resultText.setVisibility(View.GONE);
//                solvationView.setVisibility(View.GONE);
//                xplainButton.setVisibility(View.GONE);
//                backButton.setVisibility(View.GONE);
//                actionButton.setVisibility(View.VISIBLE);
//                bottomPlusHolder.setVisibility(View.VISIBLE);
//                rightPlusHolder.setVisibility(View.VISIBLE);
//                break;
//            case STATE_DETERMIN_EXPLAINING:
//                animator.stopExplain();
//            case STATE_DETERMIN_EXPLAINED:
//                explaining.setVisibility(View.GONE);
//                state = STATE_DETERMIN_PRESSED;
//                solvationView.setVisibility(View.GONE);
//                xplainButton.setVisibility(View.VISIBLE);
//                backButton.setVisibility(View.VISIBLE);
//                break;
//            case STATE_MULTIPLY_EXPLAINED:
//                resultView.removeView(resultMatrixLayout);
//                resMatrixModel.onDestroy();
//            case STATE_MULTIPLY_PRESSED:
//            case STATE_MULTIPLY_FIND:
//            case STATE_MULTIPLY_EXPLAINING:
//                mainMatrixLayout.removeView(secondMatrixView);
//                secondMatrixModel.onDestroy();
//                resultView.removeAllViews();
//            case STATE_SYSTEM_EXPLAINING_GAUS:
//                resultView.removeView(resultMatrixLayout);
//                if (resMatrixModel != null)
//                    resMatrixModel.onDestroy();
//                break;
//            case STATE_INVERT_EXPLAINING:
//                animator.stopExplain();
//            case STATE_INVERT_EXPLAINED:
//                solvationView.setVisibility(View.GONE);    //todo decimal is FRACTION
//                explaining.setVisibility(View.GONE);
//                xplainButton.setVisibility(View.VISIBLE);
//                state = STATE_INVERT_FIND;
//                break;
//            case STATE_INVERT_FIND:
//            case STATE_RANG_FIND:
//            case STATE_SIDE_COLUMN_ADDED:
//            case STATE_SYSTEM_SOLVED:
//                state = STATE_INITIAL;
//                bottomPlusHolder.setVisibility(View.VISIBLE);
//                rightPlusHolder.setVisibility(View.VISIBLE);
//
//                solvationView.removeAllViews();
//                Thread.yield();
//                secondMatrixView = null;
//                secondMatrixModel = null;
//                break;
//
//        }
//
        return true;
    }


//    resultView.setOnTouchListener(new View.OnTouchListener() {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                downX = motionEvent.getX();
//                downY = motionEvent.getY();
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                float dx = (motionEvent.getX() - downX);
//                float dy = (motionEvent.getY() - downY);
//                if (Math.abs(dx) > Math.abs(dy)) {
//                    moveToEdit(dx < 0 ? LEFT : RIGHT);
//                } else {
//                    moveToEdit(dy < 0 ? UP : DOWN);
//                }
//            }
//            return true;
//        }
//    });
}
