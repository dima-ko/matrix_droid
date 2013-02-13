package com.insomniacmath.Animations;


import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
import com.insomniacmath.ui.MatrixView;
import com.insomniacmath.Utils;

public class MultiplyAnimation extends Animation {

    public static final LinearLayout.LayoutParams FILL_WRAP =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    TextView[] solvationTexts;
    private MatrixView mW2;
    private MatrixView resultMW;
    int actions;
    int resMax;

    int[] colors = new int[]{cyan, viol, ros, blu, gree, yel};

    public MultiplyAnimation(Animator animator, LinearLayout solvationView, MatrixView mW1, MatrixView mW2, MatrixView resMW) {
        super(animator, solvationView, mW1);
        this.mW2 = mW2;
        resultMW = resMW;

        resMax = mW1.rows * mW1.rows;
        solvationTexts = new TextView[resMax];
        for (int i = 0; i < resMax; i++) {
            solvationTexts[i] = new TextView(solvation.getContext());
        }
        actions = 4 * mW1.columns + 1;
    }

    /**
     * converts number to string, wraps with brakets , if number is negative
     *
     * @param number
     * @return
     */


    StringBuilder builder = new StringBuilder();

    @Override
    public void tic(int t) {

        if (t == actions * resMax) {
            animator.stopExplain();
            mW1.getCanvas().clear();
            mW2.getCanvas().clear();
            return;
        }

        int resCount = t / actions;
        int rowIter = t % actions;

        if (rowIter == 0) {
            builder = new StringBuilder();
            solvationTexts[resCount].setTextSize(23);
            solvationTexts[resCount].setGravity(Gravity.CENTER_HORIZONTAL);
            solvationTexts[resCount].setTextColor(colors[resCount % 6]);
            solvation.addView(solvationTexts[resCount], FILL_WRAP);
            mW1.getCanvas().clear();
            mW2.getCanvas().clear();
        }

        int elementIter = rowIter / 4;
        int ro = resCount / mW1.rows;
        int col = resCount % mW1.rows;

        Log.d("ticzzzzz", " resCount " + resCount + "  mW1.columns " + mW1.columns);
        Log.d("ticzzzzz", rowIter + " col " + elementIter + "  row " + ro);

        if (rowIter == actions - 2) {
            builder.append(" = ");
        } else if (rowIter == actions - 1) {
            builder.append(Utils.round(resultMW.m[ro][col], false));
            resultMW.getCanvas().addCircle(col, ro, colors[resCount % 6]);
        } else
            switch (rowIter % 4) {
                case 0:
                    builder.append(Utils.round(mW1.m[ro][elementIter], true));
                    mW1.getCanvas().addCircle(elementIter, ro, colors[resCount % 6]);
                    if (elementIter != 0)
                        mW1.getCanvas().addPath(elementIter, ro, elementIter - 1, ro, colors[resCount % 6]);
                    break;
                case 1:
                    builder.append("·");
                    break;
                case 2:
                    builder.append(Utils.round(mW2.m[elementIter][col], true));
                    mW2.getCanvas().addCircle(col, elementIter, colors[resCount % 6]);
                    if (elementIter != 0)
                        mW2.getCanvas().addPath(col, elementIter, col, elementIter - 1, colors[resCount % 6]);
                    break;
                case 3:
                    builder.append("+");
                    break;
            }
        if (resCount < solvationTexts.length )
            solvationTexts[resCount].setText(builder.toString());

    }
}
