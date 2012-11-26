package com.insomniacmath.Animations;


import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
import com.insomniacmath.MatrixWrapper;
import com.insomniacmath.R;

public class GausAnimation extends Animation {

    TextView[] hints;

    public GausAnimation(Animator animator, LinearLayout solvationView, MatrixWrapper mW1) {
        super(animator, solvationView, mW1);
//        LinearLayout hintLayout = new LinearLayout(solvationView.getContext());
//        hintLayout.setOrientation(LinearLayout.VERTICAL);
//        hints = new TextView[mW1.rows];
//        for (int i = 0; i < mW1.rows; i++) {
//            hints[i] = new TextView(solvationView.getContext());
//            hintLayout.addView(hints[i], wrap_80);
//        }
//        hintLayout.setBackgroundColor(Color.GREEN);
//
//        LinearLayout arrowLayout = new LinearLayout(solvationView.getContext());
//        arrowLayout.setBackgroundColor(Color.RED);
//        LinearLayout arrow = new LinearLayout(solvationView.getContext());
//        arrow.setBackgroundResource(R.drawable.arrow);
//        arrowLayout.addView(arrow, c20Fill);
//        mW1.hintLayout.addView(hintLayout, wrap_wrap);
//        mW1.hintLayout.addView(arrowLayout, c20Fill);
//        mW1.hintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void tic(int t) {

    }
}
