package com.insomniacmath.Animations;


import android.view.Surface;
import android.view.SurfaceView;

abstract public class Animation {

    SurfaceView surface;

    Animation(SurfaceView surface) {
        this.surface = surface;
    }

    public abstract void tic(int t);


}
