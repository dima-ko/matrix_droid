package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.etc.Utils;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.ui.MatrixView;
import org.ejml.simple.SimpleMatrix;

public class RangSolver extends Solver {


    public RangSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
    }

    @Override
    void onBack() {


    }
//
//    public void findRang() {
//        resultView.removeAllViews();
//        addResultText();
//        try {
//            resultText.setText("Rang = " + Utils.round(mainMatrixModel.findRang(), false));
//            resultText.setTextColor(Color.WHITE);
//            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);
//
//            state = STATE_RANG_FIND;
//            showXplainButton();
//        } catch (BadSymbolException e) {
//            resultText.setText(_context.getString(R.string.bad_elements));
//            resultText.setTextColor(Color.RED);
////            solvationText.setVisibility(View.GONE);
//        }
//    }
//
}
