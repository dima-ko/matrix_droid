package com.insomniacmath.Animations;


import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.*;

public class GausAnimation extends Animation {

    int actions;
    TextView[] hints;
    LinearLayout arrowLayout;
    Fraction alpha;

    public GausAnimation(Animator animator, LinearLayout solvationView, MatrixView mW1) {
        super(animator, solvationView, mW1);
        buildHint();
        actions = mW1.rows;
    }

    public void buildHint() {
        LinearLayout hintEditsLayout = new LinearLayout(solvation.getContext());
        hintEditsLayout.setOrientation(LinearLayout.VERTICAL);
        hints = new TextView[mW1.rows];
        for (int i = 0; i < mW1.rows; i++) {
            hints[i] = new TextView(solvation.getContext());
            hints[i].setTextColor(Color.WHITE);
            hints[i].setGravity(Gravity.CENTER);
            hintEditsLayout.addView(hints[i], c70x70);
        }

        arrowLayout = new LinearLayout(solvation.getContext());
        LinearLayout arrow = new LinearLayout(solvation.getContext());      //todo: commit explain speed
        arrow.setBackgroundResource(R.drawable.arrow);
        arrowLayout.addView(arrow, c20Fill);
        mW1.hintLayout.addView(arrowLayout, c20Fill);
        mW1.hintLayout.addView(hintEditsLayout, wrap_wrap);
        mW1.hintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void tic(int t) {

        int curRow = t / 2 + 1;
        if (curRow >= mW1.rows)
            return;
        if (t % 2 == 0) {
            //todo
            alpha = (mW1.mFrac[0][0]).negative().divide(mW1.mFrac[curRow][0]);
            hints[curRow].setText(alpha.toSpanString());
            arrowLayout.setVisibility(View.VISIBLE);
        } else {
            hints[curRow].setText("");
            arrowLayout.setVisibility(View.GONE);
            mW1.sideFrac[curRow] = mW1.sideFrac[curRow].subtract(alpha.multiply(mW1.sideFrac[0]));
            for (int j = 0; j < mW1.columns; j++) {//todo
                mW1.mFrac[curRow][j] = mW1.mFrac[0][j].
                        add(alpha.multiply(mW1.mFrac[curRow][j]));
            }
            mW1.fillViewsFromMatrix();
        }


        if (t == 20)
            animator.stopExplain();


    }
}
