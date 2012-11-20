package com.insomniacmath.Animations;


import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.MatrixWrapper;
import com.insomniacmath.Utils;

public class MultiplyAnimation extends Animation {

    public static final LinearLayout.LayoutParams FILL_WRAP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    TextView[] solvationTexts = new TextView[6];

    public MultiplyAnimation(LinearLayout solvationView, MatrixWrapper mW1) {
        super( solvationView, mW1);

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
        solvationTexts[5] = new TextView(solvation.getContext());
        solvationTexts[5].setTextColor(yel);
    }


    @Override
    public void tic(int t) {
        switch (t) {
            case 0:
                solvationTexts[0].setTextSize(23);
                solvationTexts[0].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[0], FILL_WRAP);
                solvationTexts[0].setText("+ ");
                break;
            case 1:
                solvationTexts[0].setText("+ " + Utils.round(mW1.m[0][0]));
                mW1.getCanvas().addCircle(0, 0, cyan);
                break;
            case 2:
                solvationTexts[0].setText("+ " + Utils.round(mW1.m[0][0]) + "*" + Utils.round(mW1.m[1][1]));
                mW1.getCanvas().addPath(0, 0, 1, 1, cyan);
                mW1.getCanvas().addCircle(1, 1, cyan);
                break;
            case 3:
                solvationTexts[0].setText("+ " + Utils.round(mW1.m[0][0]) + "*" + Utils.round(mW1.m[1][1]) + "*" + Utils.round(mW1.m[2][2]));
                mW1.getCanvas().addPath(1, 1, 2, 2, cyan);
                mW1.getCanvas().addCircle(2, 2, cyan);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 5:
                solvationTexts[1].setTextSize(23);
                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[1], FILL_WRAP);
                solvationTexts[1].setText("+ ");
                break;

            case 6:
                solvationTexts[1].setText("+ " + Utils.round(mW1.m[0][1]));
                mW1.getCanvas().addCircle(1, 0, viol);
                break;
            case 7:
                solvationTexts[1].setText("+ " + Utils.round(mW1.m[0][1]) + "*" + Utils.round(mW1.m[1][2]));
                mW1.getCanvas().addPath(1, 0, 2, 1, viol);
                mW1.getCanvas().addCircle(2, 1, viol);
                break;
            case 8:
                solvationTexts[1].setText("+ " + Utils.round(mW1.m[0][1]) + "*" + Utils.round(mW1.m[1][2]) + "*" + Utils.round(mW1.m[2][0]));
                mW1.getCanvas().addPath(2, 1, 0, 2, viol);
                mW1.getCanvas().addCircle(0, 2, viol);
                break;
            case 9:
                mW1.getCanvas().addPath(0, 2, 1, 0, viol);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 10:
                solvationTexts[2].setTextSize(23);
                solvationTexts[2].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[2], FILL_WRAP);
                solvationTexts[2].setText("+ ");
                break;

            case 11:
                solvationTexts[2].setText("+ " + Utils.round(mW1.m[1][0]));
                mW1.getCanvas().addCircle(0, 1, ros);
                break;
            case 12:
                solvationTexts[2].setText("+ " + Utils.round(mW1.m[1][0]) + "*" + Utils.round(mW1.m[2][1]));
                mW1.getCanvas().addPath(0, 1, 1, 2, ros);
                mW1.getCanvas().addCircle(1, 2, ros);
                break;
            case 13:
                solvationTexts[2].setText("+ " + Utils.round(mW1.m[1][0]) + "*" + Utils.round(mW1.m[2][1]) + "*" + Utils.round(mW1.m[0][2]));
                mW1.getCanvas().addPath(1, 2, 2, 0, ros);
                mW1.getCanvas().addCircle(2, 0, ros);
                break;
            case 14:
            mW1.getCanvas().addPath(2, 0, 0, 1, ros);
                break;
            case 15:
                mW1.getCanvas().clear();
                break;
            //----------------------------------------------------------------------------------------------------------------------------------------
            case 16:
                solvationTexts[3].setTextSize(23);
                solvationTexts[3].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[3], FILL_WRAP);
                solvationTexts[3].setText("- ");
                break;
            case 17:
                solvationTexts[3].setText("- " + Utils.round(mW1.m[0][2]));
                mW1.getCanvas().addCircle(2, 0, blu);
                break;
            case 18:
                solvationTexts[3].setText("- " + Utils.round(mW1.m[0][2]) + "*" + Utils.round(mW1.m[1][1]));
                mW1.getCanvas().addPath(2, 0, 1, 1, blu);
                mW1.getCanvas().addCircle(1, 1, blu);
                break;
            case 19:
                solvationTexts[3].setText("- " + Utils.round(mW1.m[0][2]) + "*" + Utils.round(mW1.m[1][1]) + "*" + Utils.round(mW1.m[2][0]));
                mW1.getCanvas().addPath(1, 1, 0, 2, blu);
                mW1.getCanvas().addCircle(0, 2, blu);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 20:
                solvationTexts[4].setTextSize(23);
                solvationTexts[4].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[4], FILL_WRAP);
                solvationTexts[4].setText("- ");
                break;

            case 21:
                solvationTexts[4].setText("- " + Utils.round(mW1.m[0][1]));
                mW1.getCanvas().addCircle(1, 0, gree);
                break;
            case 22:
                solvationTexts[4].setText("- " + Utils.round(mW1.m[0][1]) + "*" + Utils.round(mW1.m[1][0]));
                mW1.getCanvas().addPath(1, 0, 0, 1, gree);
                mW1.getCanvas().addCircle(0, 1, gree);
                break;
            case 23:
                solvationTexts[4].setText("- " + Utils.round(mW1.m[0][1]) + "*" + Utils.round(mW1.m[1][0]) + "*" + Utils.round(mW1.m[2][2]));
                mW1.getCanvas().addPath(0, 1, 2, 2, gree);
                mW1.getCanvas().addCircle(2, 2, gree);
                break;
            case 24:
                mW1.getCanvas().addPath(2, 2, 1, 0, gree);
                break;
            //-------------------------------------------------------------------------------------------------------------------------------
            case 25:
                solvationTexts[5].setTextSize(23);
                solvationTexts[5].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[5], FILL_WRAP);
                solvationTexts[5].setText("- ");
                break;

            case 26:
                solvationTexts[5].setText("- " + Utils.round(mW1.m[1][2]));
                mW1.getCanvas().addCircle(2, 1, yel);
                break;
            case 27:
                solvationTexts[5].setText("- " + Utils.round(mW1.m[1][2]) + "*" + Utils.round(mW1.m[2][1]));
                mW1.getCanvas().addPath(2, 1, 1, 2, yel);
                mW1.getCanvas().addCircle(1, 2, yel);
                break;
            case 28:
                solvationTexts[5].setText("- " + Utils.round(mW1.m[1][2]) + "*" + Utils.round(mW1.m[2][1]) + "*" + Utils.round(mW1.m[0][0]));
                mW1.getCanvas().addPath(1, 2, 0, 0, yel);
                mW1.getCanvas().addCircle(0, 0, yel);
                break;
            case 29:
                mW1.getCanvas().addPath(0, 0, 2, 1, yel);
                break;


//            case 3:
////                solvationText2 = new TextView(solvation.getContext());
//                solvationTexts[1].setTextSize(23);
//                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
//                solvation.addView(solvationTexts[1], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_mW1, ViewGroup.LayoutParams.WRAP_CONTENT));
//                solvationTexts[1].setText("-" + Utils.round(mW1.m[0][1]));
//                break;
//            case 4:
//                solvationTexts[1].setText("-" + Utils.round(mW1.m[0][1]) + "*" + Utils.round(mW1.m[1][0]));
//                surface.addPath(0, 1, 1, 0, 0xFF8833FF);
//                break;


            default:
                break;

        }
    }
}
