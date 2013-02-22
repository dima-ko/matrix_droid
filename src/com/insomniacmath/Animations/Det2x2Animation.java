package com.insomniacmath.Animations;


import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.math.solvers.Solver;
import com.insomniacmath.ui.MatrixView;

public class Det2x2Animation extends Animation {


    TextView solvationText;
    TextView solvationText2;

    public Det2x2Animation(LinearLayout solvation, MatrixView parent) {
        super(solvation, parent);

        solvationText = new TextView(solvation.getContext());
        solvationText.setTextColor(0xFF3388FF);
        solvationText2 = new TextView(solvation.getContext());
        solvationText2.setTextColor(0xFF8833FF);
    }

    @Override
    public void animate() {
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvation.setVisibility(View.VISIBLE);
                solvationText.setTextSize(SOLV_TEXT_SIZE);
                solvationText.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationText.setText(mW1.model.mFrac[0][0].toString());
                //        mW1.getCanvas().addCircle(0, 0, 0xFF3388FF);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationText.setText(mW1.model.mFrac[0][0] + "·" + mW1.model.mFrac[1][1]);
                //        mW1.getCanvas().addPath(0, 0, 1, 1, 0xFF3388FF);
                //        mW1.getCanvas().addCircle(1, 1, 0xFF3388FF);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationText2.setTextSize(SOLV_TEXT_SIZE);
                solvationText2.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText2, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationText2.setText("-" + mW1.model.mFrac[0][1]);
                //        mW1.getCanvas().addCircle(1, 0, 0xFF8833FF);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationText2.setText("-" + mW1.model.mFrac[0][1] + "·" + mW1.model.mFrac[1][0]);
                //        mW1.getCanvas().addPath(0, 1, 1, 0, 0xFF8833FF);
                //        mW1.getCanvas().addCircle(0, 1, 0xFF8833FF);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationText.setText(solvationText.getText() + "  (" + mW1.model.mFrac[0][0].multiply(mW1.model.mFrac[1][1]) + ")");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationText2.setText(solvationText2.getText() + "  (" + mW1.model.mFrac[0][1].multiply(mW1.model.mFrac[1][0]).multiply(-1) + ")");
            }
        });
    }

    private void w() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

}
