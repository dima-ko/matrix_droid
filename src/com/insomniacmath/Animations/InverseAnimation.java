package com.insomniacmath.Animations;


import android.widget.LinearLayout;
import com.insomniacmath.Animator;
import com.insomniacmath.ui.MatrixView;

public class InverseAnimation extends Animation {

    private MatrixView resultMW;

    public InverseAnimation(Animator animator, LinearLayout solvationView, MatrixView mW1, MatrixView resMW) {
    }

    StringBuilder builder = new StringBuilder();  //todo: gaus, kramer etc

    @Override
    public void tic(int t) {
    }
}
