package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Matrix;
import com.insomniacmath.Utils;

public class Det3x3Animation extends Animation {

    TextView[] solvationTexts = new TextView[6];
    final static int cyan = 0xFF3388FF;
    final static int viol = 0xFF8833FF;
    final static int ros = 0xFFFF3388;

    public Det3x3Animation(MatrixCanvas surface, LinearLayout solvationView, Matrix parent) {
        super(surface, solvationView, parent);

        solvationTexts[0] = new TextView(solvation.getContext());
        solvationTexts[0].setTextColor(cyan);
        solvationTexts[1] = new TextView(solvation.getContext());
        solvationTexts[1].setTextColor(viol);
        solvationTexts[2] = new TextView(solvation.getContext());
        solvationTexts[2].setTextColor(ros);
    }

    @Override
    public void tic(int t) {


        switch (t) {
            case 0:
                solvationTexts[0].setTextSize(23);
                solvationTexts[0].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[0], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[0].setText("+ ");
                break;
            case 1:
                solvationTexts[0].setText("+ " + Utils.round(parent.m[0][0]));
                break;
            case 2:
                solvationTexts[0].setText("+ " + Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]));
                surface.addPath(0, 0, 1, 1, cyan);
                break;
            case 3:
                solvationTexts[0].setText("+ " + Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]) + "*" + Utils.round(parent.m[2][2]));
                surface.addPath(1, 1, 2, 2, cyan);
                break;

            case 5:
                solvationTexts[1].setTextSize(23);
                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[1], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[1].setText("+ ");
                break;

            case 6:
                solvationTexts[1].setText("+ " + Utils.round(parent.m[0][1]));
                break;
            case 7:
                solvationTexts[1].setText("+ " + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][2]));
                surface.addPath(1, 0, 2, 1, viol);
                break;
            case 8:
                solvationTexts[1].setText("+ " + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][2]) + "*" + Utils.round(parent.m[2][0]));
                surface.addPath(2, 1, 0, 2, viol);
                break;
            case 9:
                surface.addPath(0, 2, 1, 0, viol);
                break;
            case 10:
                solvationTexts[2].setTextSize(23);
                solvationTexts[2].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[2], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[2].setText("+ ");
                break;

            case 11:
                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]));
                break;
            case 12:
                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][1]));
                surface.addPath(0, 1, 1, 2, ros);
                break;
            case 13:
                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][1]) + "*" + Utils.round(parent.m[0][2]));
                surface.addPath(1, 2, 2, 0, ros);
                break;
            case 14:
                surface.addPath(2, 0, 0, 1, ros);
                break;


//            case 3:
////                solvationText2 = new TextView(solvation.getContext());
//                solvationTexts[1].setTextSize(23);
//                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
//                solvation.addView(solvationTexts[1], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                solvationTexts[1].setText("-" + Utils.round(parent.m[0][1]));
//                break;
//            case 4:
//                solvationTexts[1].setText("-" + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][0]));
//                surface.addPath(0, 1, 1, 0, 0xFF8833FF);
//                break;


            default:
                break;

        }
    }
}
