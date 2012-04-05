package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Matrix;
import com.insomniacmath.Utils;

public class Det3x3Animation extends Animation {

    TextView[] solvationTexts = new TextView[6];

    public Det3x3Animation(MatrixCanvas surface, LinearLayout solvationView, Matrix parent) {
        super(surface, solvationView, parent);

        solvationTexts[0] = new TextView(solvation.getContext());
        solvationTexts[0].setTextColor(0xFF3388FF);
        solvationTexts[1] = new TextView(solvation.getContext());
        solvationTexts[1].setTextColor(0xFF8833FF);
        solvationTexts[2] = new TextView(solvation.getContext());
        solvationTexts[2].setTextColor(0xFF8833FF);
    }

    @Override
    public void tic(int t) {

        switch (t) {
            case 0:
                break;
            case 1:
                solvationTexts[0].setTextSize(23);
                solvationTexts[0].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[0], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[0].setText(Utils.round(parent.m[0][0]));
                break;
            case 2:
                solvationTexts[0].setText(Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]));
                surface.addPath(0, 0, 1, 1, 0xFF3388FF);
                break;
            case 3:
//                solvationText2 = new TextView(solvation.getContext());
                solvationTexts[1].setTextSize(23);
                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[1], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[1].setText("-" + Utils.round(parent.m[0][1]));
                break;
            case 4:
                solvationTexts[1].setText("-" + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][0]));
                surface.addPath(0, 1, 1, 0, 0xFF8833FF);
                break;


            default:
                break;

        }
    }
}
