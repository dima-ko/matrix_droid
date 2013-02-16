package com.insomniacmath;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.insomniacmath.ui.EditableMatrixView;
import com.insomniacmath.ui.MatrixView;


public class Controller implements Constants {

    public static int state = STATE_INITIAL;

    private float downY;
    private float downX;

    EditableMatrixView mainMatrixView;

    LinearLayout mainMatrixLayout;

    private Context _context;
    private LinearLayout mainView;
    LinearLayout bottomPlusHolder;
    LinearLayout rightPlusHolder;
    LinearLayout scrollWrapper;

    Dialog dialog;


    public Controller(Context context, LinearLayout mainView) {

        _context = context;
        this.mainView = mainView;

        setActionBar(mainView);

        HorizontalScrollView scrollView = new HorizontalScrollView(context);

        scrollWrapper = new LinearLayout(context);
        mainMatrixLayout = new LinearLayout(context);

        mainMatrixLayout.setLayoutParams(wrapWrap);
        mainMatrixLayout.setOrientation(LinearLayout.HORIZONTAL);

        mainMatrixView = new MatrixView(context, mainMatrixLayout, 0);

        scrollWrapper.addView(mainMatrixLayout, wrapWrap);


        rightPlusHolder = new LinearLayout(context);
        rightPlusHolder.setOrientation(LinearLayout.VERTICAL);

        ImageView plusColumn = new ImageView(context);
        plusColumn.setId(PLUS_COLUMN_ID);
        plusColumn.setImageResource(R.drawable.plus_small);
        rightPlusHolder.addView(plusColumn, c80x80);
        plusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixView.addColumn();
            }
        });

        ImageView minusColumn = new ImageView(context);
        minusColumn.setId(MINUS_COLUMN_ID);
        minusColumn.setImageResource(R.drawable.minus_small);
        rightPlusHolder.addView(minusColumn, c80x80);
        minusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixView.removeColumn();
            }
        });

        scrollWrapper.addView(rightPlusHolder, wrapWrap);

        scrollView.addView(scrollWrapper, wrapWrap);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrapWrap);
        params.setMargins(10, 19, 0, 0);
        mainView.addView(scrollView, params);

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
        mainView.addView(bottomPlusHolder, fillWrap);

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------

    }

    private View actionButton;

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
                findDeterminant();
                break;
            case R.id.multiply:
                addSecondMatrix();
                break;
            case R.id.invers:
                findInverse();
                break;
            case R.id.rank:
                findRang();
                break;
            case R.id.solve_sys:
                mainMatrixView.addSideColumn();
                state = STATE_SIDE_COLUMN_ADDED;
                break;
//            case R.id.eigen:
//                findEigenVectors();
//                break;

        }
    }

}
