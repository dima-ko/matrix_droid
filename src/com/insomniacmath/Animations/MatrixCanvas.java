package com.insomniacmath.Animations;


import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class MatrixCanvas extends SurfaceView{


    public MatrixCanvas(Context context) {
        super(context);
        this.setVisibility(GONE);
    }

    public MatrixCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setVisibility(GONE);
    }

    public MatrixCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setVisibility(GONE);
    }
}
