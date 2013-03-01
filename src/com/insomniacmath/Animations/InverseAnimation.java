package com.insomniacmath.Animations;


import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.ui.MatrixView;
import com.insomniacmath.ui.MinorSolvationView;
import com.insomniacmath.ui.roboto.TextViewRoboto;

public class InverseAnimation extends Animation {

    private MatrixView resultMW;


    public InverseAnimation(LinearLayout solvationView, MatrixView mW1, MatrixView resMW) {
        super(solvationView, mW1);
        resultMW = resMW;
    }

    @Override
    public void animate() {

        MatrixModel wholemodel = new MatrixModel(mW1.model.mFrac);
        final TextView det = new TextView(solvation.getContext());
        det.setGravity(Gravity.CENTER);
        det.setTextSize(18);
        det.setTextColor(0xffffffff);
        det.setText("det = " + MatrixUtils.determin(wholemodel.mFrac).toString());
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvation.addView(det, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        });
        w();

        for (int i = 0; i < mW1.model.rows; i++) {
            for (int j = 0; j < mW1.model.columns; j++) {
                Fraction[][] fractions = MatrixUtils.removeRowAndColumn(mW1.model.mFrac, i, j);
                MatrixModel model = new MatrixModel(fractions);
                final MinorSolvationView minor = new MinorSolvationView(solvation.getContext(), model);
                String prefix1 = "M" + i + j + "=det";
                SpannableString ss = new SpannableString(prefix1);
                ss.setSpan(new UnderlineSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                minor.setPrefix(prefix1);

                minor.setPostfix("=" + MatrixUtils.determin(model.mFrac).toString());
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
