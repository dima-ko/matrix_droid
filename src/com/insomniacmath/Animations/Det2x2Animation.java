package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Matrix;

public class Det2x2Animation extends Animation {


    public Det2x2Animation(MatrixCanvas surface, LinearLayout solvation, Matrix parent) {
        super(surface, solvation, parent);
    }

    @Override
    public void tic(int t) {
        switch (t) {
            case 0:
                break;
            case 1:
                TextView solvationText = new TextView(solvation.getContext());
                solvationText.setTextSize(23);
                solvationText.setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvation.setVisibility(View.GONE);
                break;


        }
    }

}
