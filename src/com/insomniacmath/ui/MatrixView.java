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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.insomniacmath.Animations.MatrixCanvas;
import com.insomniacmath.Constants;
import com.insomniacmath.Fraction;
import com.insomniacmath.R;
import com.insomniacmath.exceptions.BadSymbolException;

public abstract class MatrixView extends LinearLayout implements Constants {


    public LinearLayout bodyMatrix;
    public MatrixCanvas canvas;
    ImageView rightBraket, leftBraket;

    MatrixModel model;

    public MatrixView(Context context, int number) {
        super(context);
        model = new MatrixModel();
        buildBricks();
    }

    protected void buildBricks() {
        leftBraket = new ImageView(getContext());
        leftBraket.setImageResource(R.drawable.left_braket);

        bodyMatrix = new LinearLayout(getContext());
        bodyMatrix.setOrientation(LinearLayout.VERTICAL);

        rightBraket = new ImageView(getContext());
        rightBraket.setImageResource(R.drawable.right_braket);
    }

    protected abstract void updateUI();

    public MatrixCanvas getCanvas() {
        return canvas;
    }

    public void onDestroy() {
        canvas.onDestroy();
    }

}
