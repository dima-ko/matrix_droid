package com.insomniacmath.Animations;


import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.ui.MatrixView;

public class Det3x3Animation extends Animation {

    TextView[] solvationTexts = new TextView[6];
    final static int cyan = 0xAA3388FF;
    final static int viol = 0xAA8833FF;
    final static int ros = 0xAAFF3388;
    final static int blu = 0xAA2233FF;
    final static int gree = 0xAA22FF22;
    final static int yel = 0xAAFF8800;

    public Det3x3Animation(LinearLayout solvationView, MatrixView parent) {
        super(solvationView, parent);

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
    public void animate() {
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[0].setTextSize(SOLV_TEXT_SIZE);
                solvationTexts[0].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[0], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[0].setText("+ ");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[0].setText("+ " + mW1.model.mFrac[0][0]);
                mW1.getCanvas().addCircle(0, 0, cyan);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[0].setText("+ " + mW1.model.mFrac[0][0] + "·" + mW1.model.mFrac[1][1]);
                mW1.getCanvas().addPath(0, 0, 1, 1, cyan);
                mW1.getCanvas().addCircle(1, 1, cyan);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[0].setText("+ " + mW1.model.mFrac[0][0] + "·" + mW1.model.mFrac[1][1] + "·" + mW1.model.mFrac[2][2]);
                mW1.getCanvas().addPath(1, 1, 2, 2, cyan);
                mW1.getCanvas().addCircle(2, 2, cyan);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[1].setTextSize(SOLV_TEXT_SIZE);
                solvationTexts[1].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[1], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[1].setText("+ ");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[1].setText("+ " + mW1.model.mFrac[0][1]);
                mW1.getCanvas().addCircle(1, 0, viol);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[1].setText("+ " + mW1.model.mFrac[0][1] + "·" + mW1.model.mFrac[1][2]);
                mW1.getCanvas().addPath(1, 0, 2, 1, viol);
                mW1.getCanvas().addCircle(2, 1, viol);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[1].setText("+ " + mW1.model.mFrac[0][1] + "·" + mW1.model.mFrac[1][2] + "·" + mW1.model.mFrac[2][0]);
                mW1.getCanvas().addPath(2, 1, 0, 2, viol);
                mW1.getCanvas().addCircle(0, 2, viol);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mW1.getCanvas().addPath(0, 2, 1, 0, viol);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[2].setTextSize(SOLV_TEXT_SIZE);
                solvationTexts[2].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[2], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[2].setText("+ ");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[2].setText("+ " + mW1.model.mFrac[1][0]);
                mW1.getCanvas().addCircle(0, 1, ros);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[2].setText("+ " + mW1.model.mFrac[1][0] + "·" + mW1.model.mFrac[2][1]);
                mW1.getCanvas().addPath(0, 1, 1, 2, ros);
                mW1.getCanvas().addCircle(1, 2, ros);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[2].setText("+ " + mW1.model.mFrac[1][0] + "·" + mW1.model.mFrac[2][1] + "·" + mW1.model.mFrac[0][2]);
                mW1.getCanvas().addPath(1, 2, 2, 0, ros);
                mW1.getCanvas().addCircle(2, 0, ros);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mW1.getCanvas().addPath(2, 0, 0, 1, ros);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mW1.getCanvas().clear();
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[3].setTextSize(SOLV_TEXT_SIZE);
                solvationTexts[3].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[3], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[3].setText("- ");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[3].setText("- " + mW1.model.mFrac[0][2]);
                mW1.getCanvas().addCircle(2, 0, blu);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[3].setText("- " + mW1.model.mFrac[0][2] + "·" + mW1.model.mFrac[1][1]);
                mW1.getCanvas().addPath(2, 0, 1, 1, blu);
                mW1.getCanvas().addCircle(1, 1, blu);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[3].setText("- " + mW1.model.mFrac[0][2] + "·" + mW1.model.mFrac[1][1] + "·" + mW1.model.mFrac[2][0]);
                mW1.getCanvas().addPath(1, 1, 0, 2, blu);
                mW1.getCanvas().addCircle(0, 2, blu);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[4].setTextSize(SOLV_TEXT_SIZE);
                solvationTexts[4].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[4], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[4].setText("- ");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[4].setText("- " + mW1.model.mFrac[0][1]);
                mW1.getCanvas().addCircle(1, 0, gree);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[4].setText("- " + mW1.model.mFrac[0][1] + "·" + mW1.model.mFrac[1][0]);
                mW1.getCanvas().addPath(1, 0, 0, 1, gree);
                mW1.getCanvas().addCircle(0, 1, gree);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[4].setText("- " + mW1.model.mFrac[0][1] + "·" + mW1.model.mFrac[1][0] + "·" + mW1.model.mFrac[2][2]);
                mW1.getCanvas().addPath(0, 1, 2, 2, gree);
                mW1.getCanvas().addCircle(2, 2, gree);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mW1.getCanvas().addPath(2, 2, 1, 0, gree);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[5].setTextSize(SOLV_TEXT_SIZE);
                solvationTexts[5].setGravity(Gravity.CENTER_HORIZONTAL);
                solvation.addView(solvationTexts[5], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                solvationTexts[5].setText("- ");
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[5].setText("- " + mW1.model.mFrac[1][2]);
                mW1.getCanvas().addCircle(2, 1, yel);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[5].setText("- " + mW1.model.mFrac[1][2] + "·" + mW1.model.mFrac[2][1]);
                mW1.getCanvas().addPath(2, 1, 1, 2, yel);
                mW1.getCanvas().addCircle(1, 2, yel);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[5].setText("- " + mW1.model.mFrac[1][2] + "·" + mW1.model.mFrac[2][1] + "·" + mW1.model.mFrac[0][0]);
                mW1.getCanvas().addPath(1, 2, 0, 0, yel);
                mW1.getCanvas().addCircle(0, 0, yel);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mW1.getCanvas().addPath(0, 0, 2, 1, yel);
            }
        });
        w();
        ((Activity) solvation.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                solvationTexts[0].setText(solvationTexts[0].getText() + "  (" + mW1.model.mFrac[0][0].multiply(mW1.model.mFrac[1][1]).multiply(mW1.model.mFrac[2][2]) + ")");
            }
        });
        w();

    }

}
