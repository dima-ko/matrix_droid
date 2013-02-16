package com.insomniacmath.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Constants;
import com.insomniacmath.math.MatrixModel;


public class ConstMatrixView extends MatrixView implements Constants {

    public TextView[][] grid;

    public ConstMatrixView(Context context, MatrixModel matrixModel, int number) {
        super(context, number);
        model = matrixModel;
        updateBody();
    }

    public void onDestroy() {
        canvas.onDestroy();
    }

    protected void updateBody() {
        bodyMatrix.removeAllViews();

        LinearLayout bodyMatrixRows = new LinearLayout(getContext());
        bodyMatrixRows.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < MAX_ROWS; i++) {
            gridRows[i] = new LinearLayout(getContext());
            gridRows[i].setOrientation(LinearLayout.HORIZONTAL);
            grid[i] = new EditText[MAX_COLUMNS];
            for (int j = 0; j < MAX_COLUMNS; j++) {
                grid[i][j] = new TextView(getContext());
                grid[i][j].setTextColor(Color.WHITE);
                grid[i][j].setGravity(Gravity.CENTER);
                grid[i][j].setMinHeight(70);
                gridRows[i].addView(grid[i][j], LParams.L_WRAP_70);
            }
            bodyMatrixRows.addView(gridRows[i], LParams.L_WRAP_WRAP);
        }
        bodyMatrix.addView(bodyMatrixRows, LParams.R_WRAP_WRAP);

        for (int i = 0; i < model.rows; i++) {
            for (int j = 0; j < model.columns; j++) {
                SpannableString text = model.mFrac[i][j].toSpanString();
                grid[i][j].setSingleLine(false);
                grid[i][j].setText(text);
                if (text.toString().contains("\n"))
                    grid[i][j].setTextSize(12);
                else
                    grid[i][j].setTextSize(18);

            }
        }
    }


}
