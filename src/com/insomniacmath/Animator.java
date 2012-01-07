package com.insomniacmath;


import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.insomniacmath.Animations.Animation;
import com.insomniacmath.Animations.Det2x2Animation;
import com.insomniacmath.Animations.MatrixCanvas;

public class Animator {



    static final int ANIM_DETERMINANT_2x2 = 0;
    static final int ANIM_DETERMINANT_3x3 = 0;
    static final int ANIM_DETERMINANT_4x4 = 0;

    TicTac tic;

//    MatrixCanvas canvas;
    MatrixCanvas canvas;
    Matrix parent;
    int animType;
    String solvationString;
    private TextView solvationText;
    private LinearLayout solvationView;
    Animation anim;

    public int getAnimType() {
        return animType;
    }

    public void setAnimType(int animType) {
        this.animType = animType;
    }


    public Animator(MatrixCanvas canvas, Matrix matrix) {
        this.canvas = canvas;
        parent=matrix;
    }

    public void startSolvation() {


        tic = new TicTac();
        if (parent.rows == 2 && parent.columns == 2) {
            start2x2Solvation();
        } else if (parent.rows == 3 && parent.columns == 3) {
            start3x3Solvation();
        } else ;
        tic.execute();
    }

    private void start3x3Solvation() {

    }

    private void start2x2Solvation() {
        solvationString = "" + Utils.floToRoundString(parent.m[0][0]) + "*" + Utils.floToRoundString(parent.m[1][1]) + " - "
                + Utils.floToRoundString(parent.m[0][1]) + "*" + Utils.floToRoundString(parent.m[1][0]);

        anim = new Det2x2Animation(canvas);


    }

    public void stopSolvation() {
    }

    public void setView(LinearLayout solvationView) {

        this.solvationView = solvationView;

        solvationText = new TextView(solvationView.getContext());
        solvationText.setTextSize(23);
        solvationText.setGravity(Gravity.CENTER_HORIZONTAL);
        solvationView.addView(solvationText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        solvationView.setVisibility(View.GONE);

    }


    class TicTac extends AsyncTask {

        short counter = 0;

        @Override
        protected Object doInBackground(Object... objects) {
            while (!this.isCancelled()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            Log.d("zzz", "zzz");

            anim.tic(counter);
            counter++;
            super.onProgressUpdate(values);
        }
    }



}
