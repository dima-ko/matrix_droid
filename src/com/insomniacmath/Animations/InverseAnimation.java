package com.insomniacmath.Animations;


import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.insomniacmath.Animator;
import com.insomniacmath.Utils;
import com.insomniacmath.ui.MatrixView;
import com.insomniacmath.ui.MinorSolvationView;

public class InverseAnimation extends Animation {

    private MatrixView resultMW;

    int dim;

    public InverseAnimation(Animator animator, LinearLayout solvationView, MatrixView mW1, MatrixView resMW) {
        super(animator, solvationView, mW1);
        dim = mW1.columns;
    }

    @Override
    public void tic(int t) {
        if (t == dim * dim) {
            animator.stopExplain();
            return;
        }

        int column = t % dim;
        int row = t / dim;

        MinorSolvationView minor = new MinorSolvationView(solvation.getContext(), row, column,
                Utils.removeRowAndColumn(mW1.mFrac, row, column));
        solvation.addView(minor, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
