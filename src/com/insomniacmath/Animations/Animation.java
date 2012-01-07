package com.insomniacmath.Animations;


import android.view.Surface;
import android.view.SurfaceView;

abstract public class Animation {

    MatrixCanvas surface;

    Animation(MatrixCanvas surface) {
        this.surface = surface;
    }

    public abstract void tic(int t);


}
