package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.UIMatrix;
import com.insomniacmath.Utils;

public class Det2x2Animation extends Animation {


    TextView solvationText;
    TextView solvationText2;

    public Det2x2Animation(MatrixCanvas surface, LinearLayout solvation, UIMatrix parent) {
        super(surface, solvation, parent);

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
                solvationText.setTextSize(23);
                solvationText.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationText.setText(Utils.round(parent.m[0][0]));
                break;
            case 2:
                solvationText.setText(Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]));
                surface.addPath(0, 0, 1, 1, 0xFF3388FF);
                break;
            case 3:
//                solvationText2 = new TextView(solvation.getContext());
                solvationText2.setTextSize(23);
                solvationText2.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText2, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationText2.setText("-" + Utils.round(parent.m[0][1]));
                break;
            case 4:
                solvationText2.setText("-" + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][0]));
                surface.addPath(0, 1, 1, 0, 0xFF8833FF);
                break;


            default:
                break;

        }
    }

}
