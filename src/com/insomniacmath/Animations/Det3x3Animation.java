package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Matrix;
import com.insomniacmath.Utils;

public class Det3x3Animation extends Animation {

    TextView[] solvationTexts = new TextView[6];
    final static int cyan = 0xAA3388FF;
    final static int viol = 0xAA8833FF;
    final static int ros = 0xAAFF3388;
    final static int blu = 0xAA2233FF;
    final static int gree = 0xAA22FF22;

    public Det3x3Animation(MatrixCanvas surface, LinearLayout solvationView, Matrix parent) {
        super(surface, solvationView, parent);

        solvationTexts[0] = new TextView(solvation.getContext());
        solvationTexts[0].setTextColor(cyan);
        solvationTexts[1] = new TextView(solvation.getContext());
        solvationTexts[1].setTextColor(viol);
        solvationTexts[2] = new TextView(solvation.getContext());
        solvationTexts[2].setTextColor(ros);
        solvationTexts[3] = new TextView(solvation.getContext());
        solvationTexts[3].setTextColor(blu);
        solvationTexts[4] = new TextView(solvation.getContext());
        solvationTexts[4].setTextColor(gree);
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
                surface.addCircle(0, 0, cyan);
                break;
            case 2:
                solvationTexts[0].setText("+ " + Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]));
                surface.addPath(0, 0, 1, 1, cyan);
                surface.addCircle(1, 1, cyan);
                break;
            case 3:
                solvationTexts[0].setText("+ " + Utils.round(parent.m[0][0]) + "*" + Utils.round(parent.m[1][1]) + "*" + Utils.round(parent.m[2][2]));
                surface.addPath(1, 1, 2, 2, cyan);
                surface.addCircle(2, 2, cyan);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 5:
                solvationTexts[1].setTextSize(23);
                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[1], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[1].setText("+ ");
                break;

            case 6:
                solvationTexts[1].setText("+ " + Utils.round(parent.m[0][1]));
                surface.addCircle(1, 0, viol);
                break;
            case 7:
                solvationTexts[1].setText("+ " + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][2]));
                surface.addPath(1, 0, 2, 1, viol);
                surface.addCircle(2, 1, viol);
                break;
            case 8:
                solvationTexts[1].setText("+ " + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][2]) + "*" + Utils.round(parent.m[2][0]));
                surface.addPath(2, 1, 0, 2, viol);
                surface.addCircle(0, 2, viol);
                break;
            case 9:
                surface.addPath(0, 2, 1, 0, viol);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 10:
                solvationTexts[2].setTextSize(23);
                solvationTexts[2].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[2], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[2].setText("+ ");
                break;

            case 11:
                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]));
                surface.addCircle(0, 1, ros);
                break;
            case 12:
                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][1]));
                surface.addPath(0, 1, 1, 2, ros);
                surface.addCircle(1, 2, ros);
                break;
            case 13:
                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][1]) + "*" + Utils.round(parent.m[0][2]));
                surface.addPath(1, 2, 2, 0, ros);
                surface.addCircle(2, 0, ros);
                break;
            case 14:
            surface.addPath(2, 0, 0, 1, ros);
                break;
            case 15:
                surface.clearPaths();
                break;
            //----------------------------------------------------------------------------------------------------------------------------------------
            case 16:
                solvationTexts[3].setTextSize(23);
                solvationTexts[3].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[3], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[3].setText("+ ");
                break;
            case 17:
                solvationTexts[3].setText("+ " + Utils.round(parent.m[0][2]));
                surface.addCircle(2, 0, blu);
                break;
            case 18:
                solvationTexts[3].setText("+ " + Utils.round(parent.m[0][2]) + "*" + Utils.round(parent.m[1][1]));
                surface.addPath(2, 0, 1, 1, blu);
                surface.addCircle(1, 1, blu);
                break;
            case 19:
                solvationTexts[3].setText("+ " + Utils.round(parent.m[0][2]) + "*" + Utils.round(parent.m[1][1]) + "*" + Utils.round(parent.m[2][0]));
                surface.addPath(1, 1, 0, 2, blu);
                surface.addCircle(0, 2, blu);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 20:
                solvationTexts[4].setTextSize(23);
                solvationTexts[4].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[4], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[4].setText("+ ");
                break;

            case 21:
                solvationTexts[4].setText("+ " + Utils.round(parent.m[0][1]));
                surface.addCircle(1, 0, gree);
                break;
            case 22:
                solvationTexts[4].setText("+ " + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][0]));
                surface.addPath(1, 0, 2, 1, gree);
                surface.addCircle(2, 1, gree);
                break;
            case 23:
                solvationTexts[4].setText("+ " + Utils.round(parent.m[0][1]) + "*" + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][2]));
                surface.addPath(2, 1, 0, 2, gree);
                surface.addCircle(0, 2, gree);
                break;
            case 24:
                surface.addPath(0, 2, 1, 0, gree);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
//            case 25:
//                solvationTexts[2].setTextSize(23);
//                solvationTexts[2].setGravity(Gravity.CENTER_HORIZONTAL);
//                solvation.addView(solvationTexts[2], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                solvationTexts[2].setText("+ ");
//                break;
//
//            case 26:
//                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]));
//                surface.addCircle(0, 1, ros);
//                break;
//            case 27:
//                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][1]));
//                surface.addPath(0, 1, 1, 2, ros);
//                surface.addCircle(1, 2, ros);
//                break;
//            case 28:
//                solvationTexts[2].setText("+ " + Utils.round(parent.m[1][0]) + "*" + Utils.round(parent.m[2][1]) + "*" + Utils.round(parent.m[0][2]));
//                surface.addPath(1, 2, 2, 0, ros);
//                surface.addCircle(2, 0, ros);
//                break;
//            case 29:
//                surface.addPath(2, 0, 0, 1, ros);
//                break;


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
