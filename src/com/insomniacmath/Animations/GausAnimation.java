package com.insomniacmath.Animations;


import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
import com.insomniacmath.Fraction;
import com.insomniacmath.MatrixWrapper;
import com.insomniacmath.R;

public class GausAnimation extends Animation {

    int actions;

    public GausAnimation(Animator animator, LinearLayout solvationView, MatrixWrapper mW1) {
        super(animator, solvationView, mW1);
        buildHint();
        actions = mW1.rows;
    }

    TextView[] hints;

    public void buildHint() {
        LinearLayout hintEditsLayout = new LinearLayout(solvation.getContext());
        hintEditsLayout.setOrientation(LinearLayout.VERTICAL);
        hints = new TextView[mW1.rows];
        for (int i = 0; i < mW1.rows; i++) {
            hints[i] = new TextView(solvation.getContext());
            hints[i].setTextColor(Color.WHITE);
            hints[i].setGravity(Gravity.CENTER);
            hintEditsLayout.addView(hints[i], c80x80);
        }

        LinearLayout arrowLayout = new LinearLayout(solvation.getContext());
        LinearLayout arrow = new LinearLayout(solvation.getContext());
        arrow.setBackgroundResource(R.drawable.arrow);
        arrowLayout.addView(arrow, c20Fill);
        mW1.hintLayout.addView(arrowLayout, c20Fill);
        mW1.hintLayout.addView(hintEditsLayout, wrap_wrap);
        mW1.hintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void tic(int t) {
        for (int i = 1; i < mW1.rows; i++) {
            Fraction alpha = mW1.mFrac[0][0].divide(mW1.mFrac[i][0]); //todo
            hints[i].setText("·" + alpha.toString());
        }

        if (t == 20)
            animator.stopExplain();


    }
}
