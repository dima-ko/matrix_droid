package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.ui.LParams;

public class RangSolver extends Solver {

    private TextView resultText;

    public RangSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);

        controller.bottomPlusHolder.setVisibility(View.GONE);
        controller.rightPlusHolder.setVisibility(View.GONE);

        try {
            MatrixModel model = controller.mainMatrixView.model;
            for (int i = 0; i < model.rows; i++) {
                for (int j = 0; j < model.columns; j++) {
                    if (model.mFrac[i][j] == null)
                        throw new BadSymbolException();
                }
            }

            int R = (int) MatrixUtils.findRang(model);

            resultText = new TextView(mainView.getContext());
            resultText.setTextSize(20);
            resultText.setPadding(20, 20, 20, 20);
            resultText.setGravity(Gravity.CENTER_HORIZONTAL);
            resultView.addView(resultText, LParams.L_WRAP_WRAP);
            resultText.setText("Rank = " + R);
            resultText.setTextColor(Color.WHITE);
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);
            controller.state = STATE_RANG_FIND;
            showXplainButton();
            controller.bottomPlusHolder.setVisibility(View.GONE);
            controller.rightPlusHolder.setVisibility(View.GONE);
        } catch (BadSymbolException e) {
            message.setText(mainView.getContext().getString(R.string.bad_elements));
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {

        if (controller.state == STATE_RANG_EXPLAINED||
                controller.state == STATE_RANG_EXPLAINING) {

            controller.state = STATE_RANG_FIND;
            explaining.setVisibility(View.GONE);
            xplainButton.setVisibility(View.VISIBLE);
            if (solvationView != null)
                mainView.removeView(solvationView);

        } else if (controller.state == STATE_RANG_FIND) {
            xplainButton.setVisibility(View.GONE);
            controller.state = STATE_INITIAL;
            mainView.removeView(resultView);
            resultView = null;
            controller.bottomPlusHolder.setVisibility(View.VISIBLE);
            controller.rightPlusHolder.setVisibility(View.VISIBLE);
            onDestroySolver();
        }

    }

    @Override
    protected void onExplainClicked() {
        super.onExplainClicked();
        controller.state = STATE_RANG_EXPLAINING;

    }


}
