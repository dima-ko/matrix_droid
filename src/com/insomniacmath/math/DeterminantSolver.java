package com.insomniacmath.math;

import android.graphics.Color;
import android.view.View;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.R;
import com.insomniacmath.etc.Utils;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.NotSquareException;

public class DeterminantSolver extends Solver {


    @Override
    void onBack() {


    }

    public void findDeterminant() {
        resultView.removeAllViews();
        addResultText();
        try {
            resultText.setText("Determinant = " + Utils.round(mainMatrixModel.findDeterminant(), false));
            resultText.setTextColor(Color.WHITE);
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);
            state = STATE_DETERMIN_PRESSED;
            if (mainMatrixModel.rows == 2 || mainMatrixModel.rows == 3)
                showXplainButton();
            bottomPlusHolder.setVisibility(View.GONE);
            rightPlusHolder.setVisibility(View.GONE);
        } catch (BadSymbolException e) {
            resultText.setText(_context.getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
        } catch (NotSquareException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
        }
    }

}
