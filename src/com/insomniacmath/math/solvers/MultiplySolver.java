package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.ui.MatrixView;
import org.ejml.simple.SimpleMatrix;

public class MultiplySolver extends Solver {


    private LinearLayout secondMatrixView;
    LinearLayout resultMatrixLayout;

    MatrixView resMatrixModel;
    private View solveButton;
    public MultiplySolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
    }

    @Override
    void onBack() {


    }
//
//    public void findMultiplication() {
//        try {
//            mainMatrixModel.fillMatrixFromViews();
//        } catch (BadSymbolException e) {
//            addResultText();
//            resultText.setText(_context.getString(R.string.bad_elements));
//            resultText.setTextColor(Color.RED);
//            return;
//        }
//        try {
//            secondMatrixModel.fillMatrixFromViews();
//        } catch (BadSymbolException e) {
//            addResultText();
//            resultText.setText(_context.getString(R.string.bad_elements));
//            resultText.setTextColor(Color.RED);
//            return;
//        }
//
//        state = STATE_MULTIPLY_FIND;
//
//        SimpleMatrix a = new SimpleMatrix(mainMatrixModel.m);
//        SimpleMatrix b = new SimpleMatrix(secondMatrixModel.m);
//
//        SimpleMatrix c = a.mult(b);
//
//        LinearLayout resultMatrixLay = new LinearLayout(_context);
//        resultMatrixLay.setId(RESULT_MATRIX);
//        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
//
//        resMatrixModel = new MatrixView(_context, resultMatrixLay, 2);
//        resMatrixModel.adjustSizeTo(c.numRows(), c.numCols());
//        for (int i = 0; i < c.numRows(); i++) {
//            for (int j = 0; j < c.numCols(); j++) {
//                resMatrixModel.m[i][j] = c.get(i, j);
//            }
//        }
//        resMatrixModel.fillViewsFromMatrix();
//        resMatrixModel.refreshVisible();
//        animator.setResultMW(resMatrixModel);
//        showXplainButton();
//        animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixModel.rows, mainMatrixModel.columns);
//    }
//


}
