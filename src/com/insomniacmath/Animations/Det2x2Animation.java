package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.ui.MatrixView;
import com.insomniacmath.etc.Utils;

public class Det2x2Animation extends Animation {


    TextView solvationText;
    TextView solvationText2;

    public Det2x2Animation(Animator animator, LinearLayout solvation, MatrixView parent) {
        super(animator, solvation, parent);

        solvationText = new TextView(solvation.getContext());
        solvationText.setTextColor(0xFF3388FF);
        solvationText2 = new TextView(solvation.getContext());
        solvationText2.setTextColor(0xFF8833FF);
    }

    @Override
    public void tic(int t) {
        switch (t) {
            case 0:
                break;
            case 1:
                solvationText.setTextSize(SOLV_TEXT_SIZE);
                solvationText.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationText.setText(Utils.round(mW1.m[0][0], true));
                mW1.getCanvas().addCircle(0, 0, 0xFF3388FF);
                break;
            case 2:
                solvationText.setText(Utils.round(mW1.m[0][0], true) + "·" + Utils.round(mW1.m[1][1], true));
                mW1.getCanvas().addPath(0, 0, 1, 1, 0xFF3388FF);
                mW1.getCanvas().addCircle(1, 1, 0xFF3388FF);
                break;
            case 3:
                solvationText2.setTextSize(SOLV_TEXT_SIZE);
                solvationText2.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText2, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationText2.setText("-" + Utils.round(mW1.m[0][1], true));
                mW1.getCanvas().addCircle(1, 0, 0xFF8833FF);
                break;
            case 4:
                solvationText2.setText("-" + Utils.round(mW1.m[0][1], true) + "·" + Utils.round(mW1.m[1][0], true));
                mW1.getCanvas().addPath(0, 1, 1, 0, 0xFF8833FF);
                mW1.getCanvas().addCircle(0, 1, 0xFF8833FF);
                break;
            case 5:
                solvationText.setText(solvationText.getText() + "  (" + Utils.round(mW1.m[0][0] * mW1.m[1][1], false) + ")");
                break;
            case 6:
                solvationText2.setText(solvationText2.getText() + "  (" + Utils.round(-mW1.m[0][1] * mW1.m[1][0], false) + ")");
                break;

            default:
                animator.stopExplain();
                break;

        }
    }

}
