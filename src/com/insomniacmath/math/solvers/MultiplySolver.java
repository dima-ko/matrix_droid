package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.ui.EditableMatrixView;
import com.insomniacmath.ui.MatrixView;

public class MultiplySolver extends Solver {

    private EditableMatrixView secondMatrixView;

    protected View solveButton;

    LinearLayout resultMatrixLayout;
    MatrixView resMatrixModel;

    public MultiplySolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);

        solveButton = mainView.findViewById(R.id.solve);

        controller.bottomPlusHolder.setVisibility(View.GONE);
        controller.rightPlusHolder.setVisibility(View.GONE);
        Controller.state = STATE_MULTIPLY_PRESSED;

        secondMatrixView = new EditableMatrixView(mainView.getContext(), 1);
        controller.mainMatrixView.addView(secondMatrixView);
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
                    if (model.mFrac[i][j] == null)
                        throw new BadSymbolException();
                }
            }
            for (int i = 0; i < model2.rows; i++) {
                for (int j = 0; j < model2.columns; j++) {
                    if (model2.mFrac[i][j] == null)
                        throw new BadSymbolException();
                }
            }
            message.setVisibility(View.GONE);
        } catch (BadSymbolException e) {
            e.printStackTrace();
            message.setText(mainView.getContext().getString(R.string.bad_elements));
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        }

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
