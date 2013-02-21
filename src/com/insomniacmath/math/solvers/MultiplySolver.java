package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.ui.ConstMatrixView;
import com.insomniacmath.ui.EditableMatrixView;

public class MultiplySolver extends Solver {

    private EditableMatrixView secondMatrixView;
    protected View solveButton;

    ConstMatrixView resultMatrixView;

    public MultiplySolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);

        solveButton = mainView.findViewById(R.id.solve);

        controller.bottomPlusHolder.setVisibility(View.GONE);
        controller.rightPlusHolder.setVisibility(View.GONE);
        controller.state = STATE_MULTIPLY_PRESSED;

        MatrixModel model = controller.mainMatrixView.model;
        MatrixModel secMatrix = new MatrixModel(model.columns, model.rows);

        secondMatrixView = new EditableMatrixView(mainView.getContext(), secMatrix, 1);
        controller.scrollWrapper.addView(secondMatrixView);

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
        MatrixModel model2 = secondMatrixView.model;
        try {
            for (int i = 0; i < model.rows; i++) {
                for (int j = 0; j < model.columns; j++) {
                    if (model.mFrac[i][j] == null) {
                        controller.mainMatrixView.grid[i][j].setBackgroundResource(R.drawable.red_edit);
                        throw new BadSymbolException();
                    }
                }
            }

            for (int i = 0; i < model2.rows; i++) {
                for (int j = 0; j < model2.columns; j++) {
                    if (model2.mFrac[i][j] == null) {
                        secondMatrixView.grid[i][j].setBackgroundResource(R.drawable.red_edit);
                        throw new BadSymbolException();
                    }
                }
            }
            controller.state = STATE_MULTIPLY_FIND;
            message.setVisibility(View.GONE);

            findMultiplication();
            showXplainButton();

        } catch (BadSymbolException e) {
            e.printStackTrace();
            message.setText(mainView.getContext().getString(R.string.bad_elements));
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        if (controller.state == STATE_MULTIPLY_EXPLAINED ||
                controller.state == STATE_MULTIPLY_EXPLAINING) {
            controller.state = STATE_MULTIPLY_FIND;
            xplainButton.setVisibility(View.VISIBLE);
            explaining.setVisibility(View.GONE);
            if (solvationView != null)
                mainView.removeView(solvationView);
        } else if (controller.state == STATE_MULTIPLY_FIND) {
            controller.state = STATE_MULTIPLY_PRESSED;
            xplainButton.setVisibility(View.GONE);
            mainView.removeView(resultView);
            solveButton.setVisibility(View.VISIBLE);
        } else if (controller.state == STATE_MULTIPLY_PRESSED) {
            controller.state = STATE_INITIAL;
            controller.scrollWrapper.removeView(secondMatrixView);
            controller.bottomPlusHolder.setVisibility(View.VISIBLE);
            controller.rightPlusHolder.setVisibility(View.VISIBLE);
            onDestroySolver();
        }
    }

    @Override
    public void onDestroySolver() {
        super.onDestroySolver();
        solveButton.setVisibility(View.GONE);
    }

    public void findMultiplication() {

        controller.state = STATE_MULTIPLY_FIND;
        Fraction[][] a = controller.mainMatrixView.model.mFrac;
        Fraction[][] b = secondMatrixView.model.mFrac;

        int aRows = a.length,
                aColumns = a[0].length,
                bRows = b.length,
                bColumns = b[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Fraction[][] resultant = new Fraction[aRows][bColumns];

        for (int i = 0; i < aRows; i++) { // aRow
            resultant[i] = new Fraction[bColumns];
            for (int j = 0; j < bColumns; j++) { // bColumn
                resultant[i][j] = new Fraction(0);
                for (int k = 0; k < aColumns; k++) { // aColumn
                    Fraction multiply = a[i][k].multiply(b[k][j]);
                    resultant[i][j] = resultant[i][j].add(multiply);
                }
            }
        }

        MatrixModel resMatrix = new MatrixModel();
        resMatrix.mFrac = resultant;  //todo rows

        resultMatrixView = new ConstMatrixView(mainView.getContext(), resMatrix, 1);
        resultView.addView(resultMatrixView);

        showXplainButton();
//        animator.setResultMW(resMatrixModel);
//        animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixModel.rows, mainMatrixModel.columns);

    }

    @Override
    protected void onExplainClicked() {
        super.onExplainClicked();
        controller.state = STATE_MULTIPLY_EXPLAINING;
    }

    @Override
    protected void showXplainButton() {
        super.showXplainButton();
        solveButton.setVisibility(View.GONE);
    }
}
