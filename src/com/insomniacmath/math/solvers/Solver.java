package com.insomniacmath.math.solvers;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import com.insomniacmath.Animations.Animator;

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

}
