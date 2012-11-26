package com.insomniacmath.Animations;


import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
import com.insomniacmath.MatrixWrapper;

public class InverseAnimation extends Animation {

     private MatrixWrapper resultMW;

    public InverseAnimation(Animator animator, LinearLayout solvationView, MatrixWrapper mW1, MatrixWrapper resMW) {
    }

    StringBuilder builder = new StringBuilder();  //todo: gaus, kramer etc

    @Override
    public void tic(int t) {
    }
}
