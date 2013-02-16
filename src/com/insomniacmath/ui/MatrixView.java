package com.insomniacmath.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.insomniacmath.Animations.MatrixCanvas;
import com.insomniacmath.Constants;
import com.insomniacmath.R;

public abstract class MatrixView extends LinearLayout implements Constants {

    public RelativeLayout bodyMatrix;
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
        addView(leftBraket, new LayoutParams(35, ViewGroup.LayoutParams.FILL_PARENT));

        bodyMatrix = new RelativeLayout(getContext());
        addView(bodyMatrix, LParams.L_WRAP_WRAP);

        rightBraket = new ImageView(getContext());
        rightBraket.setImageResource(R.drawable.right_braket);
        addView(rightBraket, new LayoutParams(35, ViewGroup.LayoutParams.FILL_PARENT));

    }

    protected abstract void updateUI();

    public void setCanvas(MatrixCanvas canvas) {
        this.canvas = canvas;
        bodyMatrix.addView(canvas, LParams.R_FILL_FILL);
    }

    public MatrixCanvas getCanvas() {
        return canvas;
    }

    public void onDestroy() {
        canvas.onDestroy();
    }

}
