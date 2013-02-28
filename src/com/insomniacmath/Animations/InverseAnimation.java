package com.insomniacmath.Animations;


import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.ui.MatrixView;
import com.insomniacmath.ui.MinorSolvationView;

public class InverseAnimation extends Animation {

    private MatrixView resultMW;


    public InverseAnimation(LinearLayout solvationView, MatrixView mW1, MatrixView resMW) {
        super(solvationView, mW1);
        resultMW = resMW;
    }

    @Override
    public void animate() {
        for (int i = 0; i < mW1.model.rows; i++) {
            for (int j = 0; j < mW1.model.columns; j++) {
                Fraction[][] fractions = MatrixUtils.removeRowAndColumn(mW1.model.mFrac, i, j);
                MatrixModel model = new MatrixModel(fractions);
                final MinorSolvationView minor = new MinorSolvationView(solvation.getContext(), model);
                minor.setPrefix("M(" + i + ")(" + j + ") = det");
                minor.setPostfix(" =");
                ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        solvation.addView(minor, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                });
                w();
            }
        }
    }
}
