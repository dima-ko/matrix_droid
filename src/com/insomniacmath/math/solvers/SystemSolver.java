package com.insomniacmath.math.solvers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.insomniacmath.Animations.CramerAnimation;
import com.insomniacmath.Animations.GausAnimation;
import com.insomniacmath.Animations.InverseAnimation;
import com.insomniacmath.Animations.MatrixCanvas;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.SingularMatrixException;
import com.insomniacmath.ui.ConstMatrixView;
import com.insomniacmath.ui.roboto.ButtonRoboto;

public class SystemSolver extends Solver {

    private View solveButton;
    private ConstMatrixView resultMatrixView;
    RelativeLayout topPanel;

    public SystemSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);

        topPanel = (RelativeLayout) mainView.findViewById(R.id.top_panel);
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
        if (controller.state == STATE_SYSTEM_SOLVED
            || controller.state == STATE_SYSTEM_EXPLAINING_INVERT
            || controller.state == STATE_SYSTEM_EXPLAINING_CRAMER
            || controller.state == STATE_SYSTEM_EXPLAINING_GAUS

        ) {
            explainThread.interrupt();
            explaining.setVisibility(View.GONE);
            controller.state = STATE_SIDE_COLUMN_ADDED;
            xplainButton.setVisibility(View.GONE);
            controller.bottomPlusHolder.setVisibility(View.VISIBLE);
            controller.rightPlusHolder.setVisibility(View.VISIBLE);
            resultView.removeAllViews();
            topPanel.removeView(buttonHolder);
            ((GausAnimation)animation).onDestroy();
            showSolveButton();
        } else if (controller.state == STATE_SIDE_COLUMN_ADDED) {
            controller.state = STATE_INITIAL;
            controller.mainMatrixView.removeSideMatrix();
            solveButton.setVisibility(View.GONE);
            onDestroySolver();
        }
    }

    LinearLayout buttonHolder;

    @Override
    protected void showXplainButton() {

        buttonHolder = (LinearLayout) ((Activity) context).getLayoutInflater().inflate(R.layout.system_buttons, null);

        backButton.setVisibility(View.VISIBLE);
        controller.actionButton.setVisibility(View.GONE);

        RelativeLayout.LayoutParams invparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        invparams.addRule(RelativeLayout.RIGHT_OF, R.id.back_arrow);
        topPanel.addView(buttonHolder, invparams);

        for (int i = 0; i < buttonHolder.getChildCount(); i++) {
            buttonHolder.getChildAt(i).setOnClickListener(xplainClickList);
        }
    }

    int method;

    View.OnClickListener xplainClickList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            method = view.getId();
            onExplainClicked();
        }
    };

    @Override
    protected void onExplainClicked() {
        super.onExplainClicked();

        mainMatrixView.setCanvas(new MatrixCanvas(context));

        if (method == R.id.cramer) {
            animation = new CramerAnimation(solvationView, mainMatrixView);
            controller.state = STATE_SYSTEM_EXPLAINING_CRAMER;
        } else if (method == R.id.gauss) {
            animation = new GausAnimation(solvationView, mainMatrixView);
            controller.state = STATE_SYSTEM_EXPLAINING_GAUS;
        } else {
            animation = new InverseAnimation(solvationView, mainMatrixView);
            controller.state = STATE_SYSTEM_EXPLAINING_INVERT;
        }
        explainThread = new ExplainThread();
        explainThread.start();
    }

    private void findSystemSolvation() throws SingularMatrixException {

        resultView.removeAllViews();
        Fraction[] result = MatrixUtils.gauss(controller.mainMatrixView.model.mFrac, controller.mainMatrixView.sideFrac);

        resultMatrixView = new ConstMatrixView(mainView.getContext(), result, 1);
        resultView.addView(resultMatrixView);

    }

}
