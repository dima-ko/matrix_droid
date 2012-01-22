package com.insomniacmath;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.Animation;
import com.insomniacmath.Animations.Det2x2Animation;
import com.insomniacmath.Animations.Det3x3Animation;
import com.insomniacmath.Animations.MatrixCanvas;

public class Animator {


    static final int ANIM_DETERMINANT_2x2 = 0;
    static final int ANIM_DETERMINANT_3x3 = 1;
    static final int ANIM_DETERMINANT_4x4 = 2;

    TicTac tic;

    //    MatrixCanvas canvas;
    MatrixCanvas canvas;
    Matrix parent;
    int animType;

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
        parent = matrix;
    }

    public void startSolvation() {

        tic = new TicTac();
        if (animType == ANIM_DETERMINANT_2x2) {
            anim = new Det2x2Animation(canvas, solvationView, parent);
        } else if (animType == ANIM_DETERMINANT_3x3) {
            anim = new Det3x3Animation(canvas, solvationView, parent);
        } else ;
        tic.execute();

    }


    public void stopSolvation() {

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
                    int time = 500;
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
