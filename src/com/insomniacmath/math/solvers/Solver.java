package com.insomniacmath.math.solvers;

import android.app.Activity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.ui.MatrixView;

public abstract class Solver {

    int state;
    protected Animator animator;

    public void showInitial() {

    }

    public void showSolve() {

    }

    public void showExplaine() {

    }

    public void showExplaining() {

    }



    private LinearLayout secondMatrixView;
    private LinearLayout solvationView;
    LinearLayout resultMatrixLayout;

    MatrixView resMatrixModel;


    private View actionButton;
    private View backButton;
    private View solveButton;
    private View explaining;
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


//    solvationView = new LinearLayout(context);
//    solvationView.setBackgroundColor(0xff222222);
//    solvationView.setOnTouchListener(new View.OnTouchListener() {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                downX = motionEvent.getX();
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
//                animator.changeSpeed((motionEvent.getX() - downX) > 0);
//            return true;
//        }
//    });
//
//    solvationView.setOrientation(LinearLayout.VERTICAL);
//    solvationView.setGravity(Gravity.CENTER_HORIZONTAL);
//    solvationView.setVisibility(View.GONE);     //todo add margin
//    mainView.addView(solvationView);
//
//    resultView = new LinearLayout(context);
//    resultView.setGravity(Gravity.CENTER_HORIZONTAL);
//    resultView.setOrientation(LinearLayout.HORIZONTAL);
//    resultView.setPadding(0, 5, 0, 0);
//    resultView.setBackgroundColor(0xff000000);
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
//
//    mainView.addView(resultView, fillFill);
//
//    animator = new Animator(mainMatrixModel);
//    animator.setView(solvationView);
}
