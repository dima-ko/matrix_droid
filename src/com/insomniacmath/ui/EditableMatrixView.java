package com.insomniacmath.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.insomniacmath.Constants;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;

public class EditableMatrixView extends MatrixView implements Constants {


    public EditText[][] grid;

//    public LinearLayout hintLayout;

    public MatrixModel sideMatrix;
    boolean isSideColumnVisible = false;
    public EditText[] sideColumnEdits;
    LinearLayout sideColumn;
    LinearLayout divider;

    public EditableMatrixView(Context context, int number) {
        super(context, number);
        this.number = number;
        updateBody();

//        hintLayout = new LinearLayout(context);      todo hint layout
//        _view.addView(hintLayout, new LayoutParams(100, ViewGroup.LayoutParams.FILL_PARENT));
//        hintLayout.setVisibility(View.GONE);
    }

    public EditableMatrixView(Context context, MatrixModel model, int number) {
        super(context, number);
        this.model = model;
        this.number = number;
        updateBody();
    }

    public void updateBody() {
        bodyMatrix.removeAllViews();

        LinearLayout bodyMatrixRows = new LinearLayout(getContext());
        bodyMatrixRows.setOrientation(LinearLayout.VERTICAL);
        grid = new EditText[model.rows][];
        gridRows = new LinearLayout[model.rows];

        for (int i = 0; i < model.rows; i++) {
            gridRows[i] = new LinearLayout(getContext());
            gridRows[i].setOrientation(LinearLayout.HORIZONTAL);
            grid[i] = new EditText[model.columns];
            for (int j = 0; j < model.columns; j++) {
                grid[i][j] = new EditText(getContext());
                grid[i][j].setId(i * MAX_SIZE + j + 100 * number);
                grid[i][j].setInputType(InputType.TYPE_CLASS_PHONE);
                grid[i][j].setSingleLine(false);
                grid[i][j].setBackgroundResource(R.drawable.edit);
                grid[i][j].setTextColor(Color.WHITE);
                grid[i][j].setGravity(Gravity.CENTER);
                grid[i][j].setMinWidth(70);
                grid[i][j].setMinHeight(70);
                final EditText a = grid[i][j];
                grid[i][j].addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        a.setTextColor(Color.WHITE);
                        int row = (a.getId() - 100 * number) / MAX_SIZE;
                        int column = (a.getId() - 100 * number) - row * MAX_SIZE;
                        if (charSequence.toString().equals("")) {
                            a.setBackgroundResource(R.drawable.edit);
                            model.mFrac[row][column] = null;
                        } else {
                            Integer in = Integer.parseInt(charSequence.toString());
                            a.setBackgroundResource(0);
                            model.mFrac[row][column] = new Fraction(in);   //todo double
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                gridRows[i].addView(grid[i][j], LParams.L_WRAP_70);
            }
            bodyMatrixRows.addView(gridRows[i], LParams.L_WRAP_WRAP);
        }
        bodyMatrix.addView(bodyMatrixRows, LParams.R_WRAP_WRAP);

        for (int i = 0; i < model.rows; i++) {
            for (int j = 0; j < model.columns; j++) {
                Fraction fraction = model.mFrac[i][j];
                if (fraction == null) {
                    grid[i][j].setText("");
                    grid[i][j].setBackgroundResource(R.drawable.edit);
                    break;
                }
                SpannableString text = fraction.toSpanString();
                grid[i][j].setSingleLine(false);
                grid[i][j].setText(text);
                if (text.toString().contains("\n"))
                    grid[i][j].setTextSize(12);
                else
                    grid[i][j].setTextSize(18);

            }
        }

        grid[0][0].setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((Activity) getContext()).getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        grid[0][0].requestFocus();
    }


    public void addSideMatrix() {
        divider = new LinearLayout(getContext());
        divider.setBackgroundColor(Color.WHITE);
        bodyMatrix.addView(divider, new LayoutParams(5, ViewGroup.LayoutParams.FILL_PARENT));

        sideColumn = new LinearLayout(getContext());
        sideColumn.setOrientation(LinearLayout.VERTICAL);
        sideColumn.setGravity(Gravity.CENTER);
        bodyMatrix.addView(sideColumn, LParams.R_WRAP_WRAP);

        for (int i = 0; i < model.rows; i++) {
            sideColumnEdits[i] = new EditText(getContext());
            sideColumnEdits[i].setInputType(InputType.TYPE_CLASS_PHONE);
            sideColumnEdits[i].setBackgroundResource(R.drawable.edit);
            sideColumnEdits[i].setTextColor(Color.WHITE);
            sideColumnEdits[i].setGravity(Gravity.CENTER);
            sideColumnEdits[i].setMinWidth(70);
            sideColumnEdits[i].setMinHeight(70);
            final View a = sideColumnEdits[i];
            sideColumnEdits[i].addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d("clicked on edittext", " id: " + a.getId());
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((EditText) a).setTextColor(Color.WHITE);
                    if (charSequence.toString().equals(""))
                        a.setBackgroundResource(R.drawable.edit);
                    else
                        a.setBackgroundResource(0);
                }

                public void afterTextChanged(Editable editable) {
                }
            });
            sideColumn.addView(sideColumnEdits[i], LParams.L_WRAP_70);
        }
    }

    public void removeSideMatrix() {
        removeView(divider);
        divider = null;

        removeView(sideColumn);
        sideColumn = null;
    }


    public void onDestroy() {
//        canvas.onDestroy();
    }

    public void addRow() {
        model.addRow();
        updateBody();
    }

    public void removeRow() {
        model.removeRow();
        updateBody();
    }

    public void addColumn() {
        model.addColumn();
        updateBody();
    }

    public void removeColumn() {
        model.removeColumn();
        updateBody();
    }


}
