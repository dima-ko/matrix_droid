package com.insomniacmath.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.insomniacmath.Animations.MatrixCanvas;
import com.insomniacmath.Constants;
import com.insomniacmath.R;
import com.insomniacmath.math.MatrixModel;

public abstract class MatrixView extends LinearLayout implements Constants {

    public RelativeLayout bodyMatrix;
    LinearLayout[] gridRows;
    public MatrixCanvas canvas;

    MatrixModel model;
    protected int number;

    public MatrixView(Context context, int number) {
        super(context);
        this.number = number;
        model = new MatrixModel();
        buildBricks();
    }

    protected void buildBricks() {
        ImageView leftBraket = new ImageView(getContext());
        leftBraket.setImageResource(R.drawable.left_braket);
        addView(leftBraket, new LayoutParams(35, ViewGroup.LayoutParams.FILL_PARENT));

        bodyMatrix = new RelativeLayout(getContext());
        addView(bodyMatrix, LParams.L_WRAP_WRAP);

        ImageView rightBraket = new ImageView(getContext());
        rightBraket.setImageResource(R.drawable.right_braket);
        addView(rightBraket, new LayoutParams(35, ViewGroup.LayoutParams.FILL_PARENT));

    }

    protected abstract void updateBody();

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
