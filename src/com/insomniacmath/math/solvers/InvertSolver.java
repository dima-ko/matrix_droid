package com.insomniacmath.math.solvers;

import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Controller;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.ui.ConstMatrixView;

public class InvertSolver extends Solver {


    public InvertSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
        controller.bottomPlusHolder.setVisibility(View.GONE);
        controller.rightPlusHolder.setVisibility(View.GONE);
        controller.state = STATE_MULTIPLY_PRESSED;

        findInverse();
    }

    @Override
    public void onBackPressed() {
        if (controller.state == STATE_MULTIPLY_PRESSED) {
            controller.state = STATE_INITIAL;
            mainView.removeView(resultView);
            controller.bottomPlusHolder.setVisibility(View.VISIBLE);
            controller.rightPlusHolder.setVisibility(View.VISIBLE);
            onDestroySolver();
        }

    }

    ConstMatrixView resultMatrixView;

    public void findInverse() {

        controller.state = STATE_INVERT_FIND;

        MatrixModel model = controller.mainMatrixView.model;
        Fraction[][] result = MatrixUtils.inverse(model.mFrac);

        MatrixModel resMatrix = new MatrixModel();
        resMatrix.mFrac = result;  //todo rows

        resultMatrixView = new ConstMatrixView(mainView.getContext(), resMatrix, 1);
        resultView.addView(resultMatrixView);

//        animator.setResultMW(resMatrixModel);
//        animator.setAnimType(Animator.ANIM_INVERT, mainMatrixModel.rows, mainMatrixModel.columns);
        showXplainButton();
    }

}
