package com.insomniacmath.Animations;


import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
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
            hintEditsLayout.addView(hints[i], c80x80);
        }
        hintEditsLayout.setBackgroundColor(Color.GREEN);

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

        if (t == 10)
            animator.stopExplain();
    }
}
