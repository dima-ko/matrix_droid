package com.insomniacmath;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.insomniacmath.math.solvers.*;
import com.insomniacmath.ui.EditableMatrixView;
import com.insomniacmath.ui.LParams;


public class Controller implements Constants {

    public int state = STATE_INITIAL;

//    private float downY;
//    private float downX;

    public EditableMatrixView mainMatrixView;

    private Context _context;
    private LinearLayout mainView;
    public LinearLayout bottomPlusHolder;
    public LinearLayout rightPlusHolder;
    public LinearLayout scrollWrapper;

    Dialog dialog;
    Solver solver = null;


    public Controller(Context context, LinearLayout mainView) {

        _context = context;
        this.mainView = mainView;

        setActionBar(mainView);

        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        scrollWrapper = new LinearLayout(context);
        mainMatrixView = new EditableMatrixView(context, 0);
        scrollWrapper.addView(mainMatrixView, LParams.L_WRAP_WRAP);

        //right plus-minus
        rightPlusHolder = new LinearLayout(context);
        rightPlusHolder.setOrientation(LinearLayout.VERTICAL);

        ImageView plusColumn = new ImageView(context);
        plusColumn.setId(PLUS_COLUMN_ID);
        plusColumn.setImageResource(R.drawable.plus_small);
        rightPlusHolder.addView(plusColumn, LParams.L_80_80);
        plusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixView.addColumn();
            }
        });

        ImageView minusColumn = new ImageView(context);
        minusColumn.setId(MINUS_COLUMN_ID);
        minusColumn.setImageResource(R.drawable.minus_small);
        rightPlusHolder.addView(minusColumn, LParams.L_80_80);
        minusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixView.removeColumn();
            }
        });

        scrollWrapper.addView(rightPlusHolder, LParams.L_WRAP_WRAP);

        scrollView.addView(scrollWrapper, LParams.L_WRAP_WRAP);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LParams.L_WRAP_WRAP);
        params.setMargins(10, 19, 0, 0);
        mainView.addView(scrollView, params);


        //bottom plus-minus
        bottomPlusHolder = new LinearLayout(context);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(80, 80);

        ImageView plusRow = new ImageView(context);
        plusRow.setId(PLUS_ROW_ID);
        plusRow.setImageResource(R.drawable.plus_small);
        bottomPlusHolder.addView(plusRow, params1);

        ImageView minusRow = new ImageView(context);
        minusRow.setId(MINUS_ROW_ID);
        minusRow.setImageResource(R.drawable.minus_small);
        bottomPlusHolder.addView(minusRow, params1);


        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixView.addRow();
            }
        });
        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixView.removeRow();
            }
        });
        mainView.addView(bottomPlusHolder, LParams.L_FILL_WRAP);

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------

    }

    public View actionButton;

    private void setActionBar(LinearLayout mainView) {
        RelativeLayout actionBar = (RelativeLayout) ((Activity) _context).getLayoutInflater().inflate(R.layout.top, null);
        mainView.addView(actionBar, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.actions_menu);
        dialog.findViewById(R.id.determinant).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.multiply).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.invers).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.solve_sys).setOnClickListener(actionsClickListener);
        dialog.findViewById(R.id.rank).setOnClickListener(actionsClickListener);


        actionButton = actionBar.findViewById(R.id.action);
        actionButton.setOnClickListener(actionsClickListener);
    }

    View.OnClickListener actionsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.action) {
                ((Activity) _context).getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.show();
                return;
            }
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
            onMenu(id);
        }
    };

    public void onDestroy() {
        mainMatrixView.onDestroy();
    }

    public void onMenu(int i) {
        switch (i) {
            case R.id.determinant:
                solver = new DeterminantSolver(mainView, this);
                break;
            case R.id.multiply:
                solver = new MultiplySolver(mainView, this);
                break;
            case R.id.invers:
                solver = new InvertSolver(mainView, this);
                break;
            case R.id.rank:
                solver = new RangSolver(mainView, this);
                break;
            case R.id.solve_sys:
                solver = new SystemSolver(mainView, this);
                break;
            case R.id.eigen:
                solver = new EigenSolver(mainView, this);
                break;

        }
    }

    public boolean onBackPressed() {
        boolean b = state != STATE_INITIAL;
        if (solver == null)
            return false;
        solver.onBackPressed();
        return b;
    }
}
