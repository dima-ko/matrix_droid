package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.R;
import com.insomniacmath.etc.Utils;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.NotSquareException;
import com.insomniacmath.ui.MatrixView;
import org.ejml.simple.SimpleMatrix;

public class InvertSolver extends Solver {


    @Override
    void onBack() {


    }

//     public void findInverse() {
//        resultView.removeAllViews();
//        try {
//            mainMatrixModel.fillMatrixFromViews();
//        } catch (BadSymbolException e) {
//            addResultText();
//            resultText.setText(_context.getString(R.string.bad_elements));
//            resultText.setTextColor(Color.RED);
//            return;
//        }
//
//        if (mainMatrixModel.columns != mainMatrixModel.rows) {
//            addResultText();
//            resultText.setText("Matrix must be square");
//            resultText.setTextColor(Color.RED);
//            return;
//        }
//
//        resultMatrixLayout = new LinearLayout(_context);
//        resultMatrixLayout.setId(RESULT_MATRIX);
//        resultView.addView(resultMatrixLayout, wrapWrapCenterHor);
//        resMatrixModel = new MatrixView(_context, resultMatrixLayout, 2);
//
//        if (mainMatrixModel.elementsFractions) {
//            Fraction[][] result = mainMatrixModel.findInverseFraction();
//            resMatrixModel.adjustSizeTo(result[0].length, result.length);
//            resMatrixModel.mFrac = result;
//        } else {
//            SimpleMatrix inverse = mainMatrixModel.findInverseDouble();
//            resMatrixModel.adjustSizeTo(inverse.numCols(), inverse.numRows());
//            for (int i = 0; i < inverse.numRows(); i++) {
//                for (int j = 0; j < inverse.numCols(); j++) {
//                    double v = inverse.get(i, j);
//                    resMatrixModel.m[i][j] = v;
//                }
//            }
//        }
//
//        state = STATE_INVERT_FIND;
//
//        resMatrixModel.fillViewsFromMatrix();
//        resMatrixModel.refreshVisible();
//        animator.setResultMW(resMatrixModel);
//        animator.setAnimType(Animator.ANIM_INVERT, mainMatrixModel.rows, mainMatrixModel.columns);
//        showXplainButton();
//        bottomPlusHolder.setVisibility(View.GONE);
//        rightPlusHolder.setVisibility(View.GONE);
//    }

}
