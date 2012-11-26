package com.insomniacmath.Animations;


import android.widget.LinearLayout;
import com.insomniacmath.Animator;
import com.insomniacmath.MatrixWrapper;

public class GausAnimation extends Animation {  //todo tests!!!


    public GausAnimation(Animator animator, LinearLayout solvationView, MatrixWrapper mW1) {
        super(animator, solvationView, mW1);

    }

    @Override
    public void tic(int t) {

        if (t == 10)
            animator.stopExplain();
    }
}
