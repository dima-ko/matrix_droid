package com.insomniacmath.math.solvers;

import android.util.Log;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.Controller;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.SingularMatrixException;
import com.insomniacmath.ui.MatrixView;
import org.ejml.simple.SimpleMatrix;

public class SystemSolver extends Solver {


    public SystemSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
    }

    @Override
    public void onBackPressed() {


    }

//
//    private void findSystemSolvation() throws SingularMatrixException {
//        resultView.removeAllViews();
//        Log.d("zzzzzzzzzzzz", "start beforefill at" + System.currentTimeMillis());
//        try {
//            mainMatrixModel.fillMatrixFromViews();
//        } catch (BadSymbolException e) {
//            e.printStackTrace();
//        }
//        Log.d("zzzzzzzzzzzz", "start afterfill at" + System.currentTimeMillis());
//
//        LinearLayout resultMatrixLay = new LinearLayout(_context);
//        resultMatrixLay.setId(RESULT_MATRIX);
//        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
//        resMatrixModel = new MatrixView(_context, resultMatrixLay, 2);
//
//        if (mainMatrixModel.elementsFractions) {
//            Fraction[] result = mainMatrixModel.solveSLEFraction();
//            resMatrixModel.adjustSizeTo(1, result.length);
//            resMatrixModel.mFrac = new Fraction[result.length][];
//
//            for (int i = 0; i < result.length; i++) {
//                resMatrixModel.mFrac[i] = new Fraction[1];
//                resMatrixModel.mFrac[i][0] = result[i];
//            }
//
//        } else {
//            SimpleMatrix result = mainMatrixModel.solveSLEDouble();
//            resMatrixModel.adjustSizeTo(result.numCols(), result.numRows());
//            for (int i = 0; i < result.numRows(); i++) {
//                for (int j = 0; j < result.numCols(); j++) {
//                    double v = result.get(i, j);
//                    resMatrixModel.m[i][j] = v;
//                }
//            }
//        }
//
//        resMatrixModel.fillViewsFromMatrix();
//        resMatrixModel.refreshVisible();
//        // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
//        animator.setAnimType(Animator.ANIM_SYSTEM_GAUSS, mainMatrixModel.rows, mainMatrixModel.columns);
//        state = STATE_SYSTEM_SOLVED;
//        animator.setResultMW(resMatrixModel);
//        showXplainButton();
//    }
//

}
