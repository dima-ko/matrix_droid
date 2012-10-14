package com.insomniacmath.Animations;


import android.widget.LinearLayout;
import com.insomniacmath.UIMatrix;

abstract public class Animation {

    public MatrixCanvas surface;
    public LinearLayout solvation;
    public UIMatrix parent;

    Animation(MatrixCanvas surface, LinearLayout solvation, UIMatrix parent) {
        this.surface = surface;
        this.solvation = solvation;
        this.parent = parent;
    }

    public abstract void tic(int t);

//    solvationString = "" + Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]) + " - "
//            + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][0]);

}
