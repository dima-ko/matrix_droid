package com.insomniacmath.math.solvers;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animations.Animation;
import com.insomniacmath.Animations.Animator;
import com.insomniacmath.Constants;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.ui.LParams;

public abstract class Solver implements Constants {


    protected LinearLayout mainView;
    protected Controller controller;

    protected View backButton;

    protected View explaining;
    protected View xplainButton;
    protected TextView message;
    Context context;

//    TicTac tic;

    protected Solver(LinearLayout mainView, Controller controller) {
        context = mainView.getContext();
        this.mainView = mainView;
        this.controller = controller;
        addResultView();
        xplainButton = mainView.findViewById(R.id.explain);
        backButton = mainView.findViewById(R.id.back_arrow);
        explaining = mainView.findViewById(R.id.explaining);
        message = (TextView) mainView.findViewById(R.id.message);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected Animator animator;

    protected LinearLayout solvationView;
    protected LinearLayout resultView;

    public void addSolvationView() {
        mainView.removeView(resultView);
        solvationView = new LinearLayout(mainView.getContext());
        solvationView.setBackgroundColor(0xff222222);
        //    solvationView.setOnTouchListener(new View.OnTouchListener() {
        //        public boolean onTouch(View view, MotionEvent motionEvent) {
        //            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        //                downX = motionEvent.getX();
        //            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
        //                animator.changeSpeed((motionEvent.getX() - downX) > 0);
        //            return true;
        //        }
        //    });
        solvationView.setOrientation(LinearLayout.VERTICAL);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);
        mainView.addView(solvationView);
        mainView.addView(resultView, LParams.L_FILL_FILL);
    }


    public void addResultView() {
        resultView = new LinearLayout(mainView.getContext());
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setPadding(0, 5, 0, 0);
        resultView.setBackgroundColor(0xff000000);
        mainView.addView(resultView, LParams.L_FILL_FILL);
    }

    public void showExplaining() {

    }

    public void onDestroySolver() {
        controller.actionButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        mainView.removeView(resultView);
        mainView.removeView(solvationView);
        resultView = null;
        solvationView = null;
    }


    protected void showXplainButton() {
        backButton.setVisibility(View.VISIBLE);
        controller.actionButton.setVisibility(View.GONE);
        xplainButton.setVisibility(View.VISIBLE);
        xplainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onExplainClicked();
            }
        });
    }


    protected void onExplainClicked() {
        addSolvationView();
        explaining.setVisibility(View.VISIBLE);
        solvationView.setVisibility(View.VISIBLE);
        solvationView.removeAllViews();
        xplainButton.setVisibility(View.GONE);
    }


    ExplainThread explainThread;

//    public final static String ANIM_MONITOR = "anim";

    Animation animation;

    static int timeout = 1000;

    class ExplainThread extends Thread {

        @Override
        public void run() {
            animation.animate();
        }
    }


//    class TicTac extends Thread {
//
//        @Override
//        public void run() {
//            while (!this.isInterrupted()) {
//                try {
//                    Thread.sleep(timeout);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (ANIM_MONITOR) {
//                    animation.notify();
//                }
//            }
//        }
//
//    }


    public abstract void onBackPressed();


//                solvationView.setVisibility(View.GONE);    //todo decimal is FRACTION

//    resultView.setOnTouchListener(new View.OnTouchListener() {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                downX = motionEvent.getX();
//                downY = motionEvent.getY();
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                float dx = (motionEvent.getX() - downX);
//                float dy = (motionEvent.getY() - downY);
//                if (Math.abs(dx) > Math.abs(dy)) {
//                    moveToEdit(dx < 0 ? LEFT : RIGHT);
//                } else {
//                    moveToEdit(dy < 0 ? UP : DOWN);
//                }
//            }
//            return true;
//        }
//    });
}
