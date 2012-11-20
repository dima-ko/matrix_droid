package com.insomniacmath;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animation;
import com.insomniacmath.Animations.Det2x2Animation;
import com.insomniacmath.Animations.Det3x3Animation;
import com.insomniacmath.Animations.MatrixCanvas;

public class Animator {


    static final int ANIM_DETERMINANT = 0;

    static final int ANIM_MULTIPLICATION = 1;

    TicTac tic;

    //    MatrixCanvas canvas;
    MatrixWrapper mainMW;

    public void setSecMW(MatrixWrapper secMW) {
        this.secMW = secMW;
    }

    MatrixWrapper secMW;
    int animType;
    private int rows;
    private int columns;

    private LinearLayout solvationView;
    Animation anim;

    public int getAnimType() {
        return animType;
    }

    public void setAnimType(int animType, int rows, int columns) {
        this.animType = animType;
        this.rows = rows;
        this.columns = columns;
    }

    public Animator(MatrixWrapper MatrixWrapper) {
        mainMW = MatrixWrapper;
        mainMW.getCanvas().setVisibility(View.INVISIBLE);
    }

    public void startExplaining() {
        mainMW.getCanvas().setVisibility(View.VISIBLE);
        tic = new TicTac();
        if (animType == ANIM_DETERMINANT) {
            if (rows == 2)
                anim = new Det2x2Animation(mainMW.getCanvas(), solvationView, mainMW);
            if (rows == 3)
                anim = new Det3x3Animation(mainMW.getCanvas(), solvationView, mainMW);
        } else if (animType == ANIM_MULTIPLICATION) {

        }
        tic.execute();

    }


    public void stopExplain() {
        mainMW.getCanvas().clear();
    }

    public void setView(LinearLayout solvationView) {
        this.solvationView = solvationView;
    }


    class TicTac extends AsyncTask {

        short counter = 0;

        @Override
        protected Object doInBackground(Object... objects) {
            while (!this.isCancelled()) {
                try {
                    int time = 1000;
                    Thread.sleep(time);
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
