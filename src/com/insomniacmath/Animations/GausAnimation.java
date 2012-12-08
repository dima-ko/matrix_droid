package com.insomniacmath.Animations;


import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
import com.insomniacmath.Fraction;
import com.insomniacmath.MatrixView;
import com.insomniacmath.R;

public class GausAnimation extends Animation {

    int actions;
    TextView[] hints;
    LinearLayout arrowLayout;
    Fraction alpha;
    int iter = 0;
    int prevIterEnded;

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
        arrowLayout.setVisibility(View.GONE);
    }

    @Override
    public void tic(int t) {

        if (t == (mW1.rows - iter - 1) * 4 + prevIterEnded) {
            iter++;
            prevIterEnded = t;
        }

        int curRow = (t - prevIterEnded) / 4 + 1 + iter;
        if (curRow >= mW1.rows) {
            if (t - prevIterEnded == 0) {
                Fraction lastAlpha = new Fraction(1).divide(mW1.mFrac[iter][iter]);
                hints[iter].setText(lastAlpha.toSpanString());
            } else if (t - prevIterEnded == 1) {
                hints[iter].setVisibility(View.GONE);
                mW1.sideFrac[iter] = mW1.sideFrac[iter].divide(mW1.mFrac[iter][iter]);
                mW1.mFrac[iter][iter] = new Fraction(1);
            } else
                animator.stopExplain();
            mW1.fillViewsFromMatrix();
            return;
        }
        if (t % 4 == 0) {
            alpha = (mW1.mFrac[iter][iter]).negative().divide(mW1.mFrac[curRow][iter]);
            hints[curRow].setText(alpha.toSpanString());

        } else if (t % 4 == 1) {
            hints[curRow].setText("");
            mW1.sideFrac[curRow] = mW1.sideFrac[curRow].multiply(alpha);
            for (int j = 0; j < mW1.columns; j++) {         //todo
                mW1.mFrac[curRow][j] = alpha.multiply(mW1.mFrac[curRow][j]);
            }
        } else if (t % 4 == 2) {
            arrowLayout.setVisibility(View.VISIBLE);
        } else {
            arrowLayout.setVisibility(View.GONE);
            mW1.sideFrac[curRow] = mW1.sideFrac[curRow].add(mW1.sideFrac[iter]);
            for (int j = iter; j < mW1.columns; j++) {       //todo
                mW1.mFrac[curRow][j] = mW1.mFrac[iter][j].add(mW1.mFrac[curRow][j]);
            }
        }
        mW1.fillViewsFromMatrix();


    }
}
