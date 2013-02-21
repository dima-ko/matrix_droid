package com.insomniacmath.math.solvers;

import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.SingularMatrixException;
import com.insomniacmath.ui.ConstMatrixView;

public class SystemSolver extends Solver {

    private View solveButton;
    private ConstMatrixView resultMatrixView;

    public SystemSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
        solveButton = mainView.findViewById(R.id.solve);
        controller.mainMatrixView.addSideMatrix();
        showSolveButton();
    }

    protected void showSolveButton() {
        backButton.setVisibility(View.VISIBLE);
        controller.actionButton.setVisibility(View.GONE);
        solveButton.setVisibility(View.VISIBLE);
        solveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSolveClicked();
            }
        });
    }

    private void onSolveClicked() {
        MatrixModel model = controller.mainMatrixView.model;
        try {
            for (int i = 0; i < model.rows; i++) {
                for (int j = 0; j < model.columns; j++) {
                    if (model.mFrac[i][j] == null) {
                        controller.mainMatrixView.grid[i][j].setBackgroundResource(R.drawable.red_edit);
                        throw new BadSymbolException();
                    }
                }
            }

            for (int i = 0; i < model.rows; i++) {
                if (controller.mainMatrixView.sideFrac[i] == null) {
                    controller.mainMatrixView.sideColumnEdits[i].setBackgroundResource(R.drawable.red_edit);
                    throw new BadSymbolException();
                }
            }

        } catch (BadSymbolException e) {
            message.setText(mainView.getContext().getString(R.string.bad_elements));
            message.setVisibility(View.VISIBLE);
            return;
        }

        controller.state = STATE_SYSTEM_SOLVED;
        message.setVisibility(View.GONE);

        try {
            findSystemSolvation();
        } catch (SingularMatrixException e) {
            message.setText(mainView.getContext().getString(R.string.sing));
            message.setVisibility(View.VISIBLE);
            return;
        }

        controller.bottomPlusHolder.setVisibility(View.GONE);
        controller.rightPlusHolder.setVisibility(View.GONE);

        showXplainButton();
        solveButton.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (controller.state == STATE_SYSTEM_SOLVED) {
            controller.state = STATE_SIDE_COLUMN_ADDED;
            xplainButton.setVisibility(View.GONE);
            controller.bottomPlusHolder.setVisibility(View.VISIBLE);
            controller.rightPlusHolder.setVisibility(View.VISIBLE);
            resultView.removeAllViews();
            showSolveButton();
        } else if (controller.state == STATE_SIDE_COLUMN_ADDED) {
            controller.state = STATE_INITIAL;
            controller.mainMatrixView.removeSideMatrix();
            solveButton.setVisibility(View.GONE);
            onDestroySolver();
        }
    }

    private void findSystemSolvation() throws SingularMatrixException {

        resultView.removeAllViews();
        Fraction[] result = MatrixUtils.gauss(controller.mainMatrixView.model.mFrac, controller.mainMatrixView.sideFrac);

        resultMatrixView = new ConstMatrixView(mainView.getContext(), result, 1);
        resultView.addView(resultMatrixView);

//        animator.setAnimType(Animator.ANIM_SYSTEM_GAUSS, mainMatrixModel.rows, mainMatrixModel.columns);
//        animator.setResultMW(resMatrixModel);
    }

}
