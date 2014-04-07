package com.insomniacmath.Animations;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.ui.EditableMatrixView;
import com.insomniacmath.ui.LParams;

public class GausAnimation extends Animation {

    TextView[] hints;
    LinearLayout arrowLayout;
    private LinearLayout hintLayout;

    public GausAnimation(LinearLayout solvationView, EditableMatrixView mW1) {
        super(solvationView, mW1);
        buildHint();
    }

    int multiplic;
    int finalJ;


    @Override
    public void animate() {

        //forward

        for (int i = 0; i < mW1.model.rows; i++) {
            multiplic = i;
            for (int j = i + 1; j < mW1.model.rows; j++) {
                finalJ = j;
                ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Fraction lastAlpha = mW1.model.mFrac[multiplic][multiplic]
                                .divide(mW1.model.mFrac[finalJ][multiplic]);
                        hints[finalJ].setText(lastAlpha.toSpanString());
                    }
                });
                w();
            }
        }

        //backward
//        for (int i = 0; i < mW1.model.rows; i++) {
//            for (int j = 0; j < mW1.model.rows - i; j++) {
//                ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mW1.getCanvas().addCircle(0, 0, 0xFF3388FF);
//                    }
//                });
//                w();
//            }
//        }


//        if (t == (mW1.rows - iter - 1) * 4 + prevIterEnded) {
//            iter++;
//            prevIterEnded = t;
//        }
//
//        int curRow = (t - prevIterEnded) / 4 + 1 + iter;
//        if (curRow >= mW1.rows) {
//            if (t - prevIterEnded == 0) {
//                Fraction lastAlpha = new Fraction(1).divide(mW1.mFrac[iter][iter]);
//                hints[iter].setText(lastAlpha.toSpanString());
//            } else if (t - prevIterEnded == 1) {
//                hints[iter].setVisibility(View.GONE);
//                mW1.sideFrac[iter] = mW1.sideFrac[iter].divide(mW1.mFrac[iter][iter]);
//                mW1.mFrac[iter][iter] = new Fraction(1);
//            } else onReverseIter(t - prevIterEnded - 2);
//            mW1.fillViewsFromMatrix();
//            return;
//        }
//        if (t % 4 == 0) {
//            alpha = (mW1.mFrac[iter][iter]).negative().divide(mW1.mFrac[curRow][iter]);
//            hints[curRow].setText(alpha.toSpanString());
//
//        } else if (t % 4 == 1) {
//            hints[curRow].setText("");
//            mW1.sideFrac[curRow] = mW1.sideFrac[curRow].multiply(alpha);
//            for (int j = 0; j < mW1.columns; j++) {         //todo
//                mW1.mFrac[curRow][j] = alpha.multiply(mW1.mFrac[curRow][j]);
//            }
//        } else if (t % 4 == 2) {
//            arrowLayout.setVisibility(View.VISIBLE);
//        } else {
//            arrowLayout.setVisibility(View.GONE);
//            mW1.sideFrac[curRow] = mW1.sideFrac[curRow].add(mW1.sideFrac[iter]);
//            for (int j = iter; j < mW1.columns; j++) {       //todo
//                mW1.mFrac[curRow][j] = mW1.mFrac[iter][j].add(mW1.mFrac[curRow][j]);
//            }
//        }
//        mW1.fillViewsFromMatrix();
//
//    }
//
//    private void onReverseIter(int i) {
//
//        int c_iter = i - prevRevIterEnded;
//        if (c_iter == (revIter + 1) * 2) {
//            alpha = new Fraction(1).divide(mW1.mFrac[r - 2][revIter]);
//            hints[r - 2].setText(alpha.toSpanString());
//            hints[r - 2].setVisibility(View.VISIBLE);
//
//            return;
//        } else if (c_iter == (revIter + 1) * 2 + 1) {
//            //final recount
//            revIter++;
//            prevRevIterEnded = i + 1;
//            //todo change the row
//            hints[r - 2].setVisibility(View.GONE);
//            mW1.sideFrac[r - 2] = mW1.sideFrac[r - 2].multiply(alpha);
//            for (int j = 0; j < mW1.columns; j++) {       //todo
//                mW1.mFrac[r - 2][j] = mW1.mFrac[r - 2][j].multiply(alpha);
//            }
//            if (revIter == r - 1)
//                animator.stopExplain();
//            return;
//        }
//
//        if (c_iter % 2 == 0) {
//            alpha = mW1.mFrac[r - 2][c - 1 - c_iter / 2].negative();
//            arrowLayout.setVisibility(View.VISIBLE);
//            hints[r - 1].setText(alpha.toSpanString());
//            hints[r - 1].setVisibility(View.VISIBLE);
//        } else {
//            arrowLayout.setVisibility(View.GONE);
//            hints[r - 1].setVisibility(View.GONE);
//            mW1.sideFrac[r - 2] = mW1.sideFrac[r - 2].add(mW1.sideFrac[r - 1].multiply(alpha));
//            for (int j = revIter; j < mW1.columns; j++) {       //todo
//                mW1.mFrac[r - 2][j] = mW1.mFrac[r - 2][j].add(mW1.mFrac[r - 1][j].multiply(alpha));
//            }
//        }
//
//
    }


    public void buildHint() {

        hintLayout = new LinearLayout(solvation.getContext());
        mW1.addView(hintLayout, new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.FILL_PARENT));

        LinearLayout hintEditsLayout = new LinearLayout(solvation.getContext());
        hintEditsLayout.setOrientation(LinearLayout.VERTICAL);
        hints = new TextView[mW1.model.rows];
        for (int i = 0; i < mW1.model.rows; i++) {
            hints[i] = new TextView(solvation.getContext());
            hints[i].setTextColor(Color.WHITE);
            hints[i].setGravity(Gravity.CENTER);
            hintEditsLayout.addView(hints[i], LParams.L_WRAP_70);
        }
        hintLayout.addView(hintEditsLayout, LParams.L_WRAP_WRAP);

        arrowLayout = new LinearLayout(solvation.getContext());
        LinearLayout arrow = new LinearLayout(solvation.getContext());      //todo: commit explain speed
        arrow.setBackgroundResource(R.drawable.arrow);
        arrowLayout.addView(arrow, LParams.L_20_Fill);
        hintLayout.addView(arrowLayout, LParams.L_20_Fill);
        hintLayout.setVisibility(View.VISIBLE);
    }

    public void onDestroy() {
        mW1.removeView(hintLayout);
        hintLayout.removeAllViews();
        hintLayout = null;
    }
}
