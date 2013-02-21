package com.insomniacmath.Animations;


import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.insomniacmath.etc.Utils;
import com.insomniacmath.ui.MatrixView;
import com.insomniacmath.ui.MinorSolvationView;

public class InverseAnimation extends Animation {

    private MatrixView resultMW;

    int dim;

    public InverseAnimation(LinearLayout solvationView, MatrixView mW1, MatrixView resMW) {
        super( solvationView, mW1);

    }

    public void tic(int t) {
        if (t >= dim * dim) {
//            animator.stopExplain();
            return;
        }

        int column = t % dim;
        int row = t / dim;

//        MinorSolvationView minor = new MinorSolvationView(solvation.getContext(), row, column,
//                Utils.removeRowAndColumn(mW1.mFrac, row, column));
//        solvation.addView(minor, new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void animate() {


    }
}
