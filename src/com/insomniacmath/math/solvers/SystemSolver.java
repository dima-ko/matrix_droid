package com.insomniacmath.math.solvers;

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

    public static final int GAUSS_ID = 1523;
    public static final int CRAMER_ID = 1524;
    public static final int INV_ID = 1525;
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

    Button gaussButton;
    Button cramerButton;
    Button invButton;

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

    @Override
    protected void showXplainButton() {
        backButton.setVisibility(View.VISIBLE);
        controller.actionButton.setVisibility(View.GONE);
        gaussButton = new ButtonRoboto(mainView.getContext());
        gaussButton.setText("Gauss");
        gaussButton.setId(GAUSS_ID);
        gaussButton.setTextSize(21);
        gaussButton.setOnClickListener(xplainClickList);
        gaussButton.setPadding(40, 40, 40, 40);
        gaussButton.setBackgroundResource(R.drawable.roboto_under_bl);
        RelativeLayout.LayoutParams gausparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gausparams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        topPanel.addView(
                gaussButton, gausparams
        );

        cramerButton = new ButtonRoboto(mainView.getContext());
        cramerButton.setText("Cramer");
        cramerButton.setId(CRAMER_ID);
        cramerButton.setOnClickListener(xplainClickList);
        cramerButton.setTextSize(21);
        cramerButton.setPadding(40, 40, 40, 40);
        cramerButton.setBackgroundResource(R.drawable.roboto_under_bl);
        RelativeLayout.LayoutParams cramerparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cramerparams.addRule(RelativeLayout.LEFT_OF, GAUSS_ID);
        cramerparams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        topPanel.addView(
                cramerButton, cramerparams
        );

        invButton = new ButtonRoboto(mainView.getContext());
        invButton.setText("Invert");
        invButton.setId(INV_ID);
        invButton.setTextSize(21);
        invButton.setOnClickListener(xplainClickList);
        invButton.setPadding(40, 40, 40, 40);
        invButton.setBackgroundResource(R.drawable.roboto_under_bl);
        RelativeLayout.LayoutParams invparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        invparams.addRule(RelativeLayout.RIGHT_OF, GAUSS_ID);
        invparams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        topPanel.addView(
                invButton, invparams
        );
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
        controller.state = STATE_MULTIPLY_EXPLAINING;
        mainMatrixView.setCanvas(new MatrixCanvas(context));

        if (method == GAUSS_ID) {
            animation = new GausAnimation(solvationView, mainMatrixView);
        } else if (method == CRAMER_ID) {
            animation = new CramerAnimation(solvationView, mainMatrixView);
        } else {
            animation = new InverseAnimation(solvationView, mainMatrixView);
        }
        explainThread = new ExplainThread();
        explainThread.start();
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
