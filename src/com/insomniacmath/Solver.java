package com.insomniacmath;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

public class Solver {

    Matrix mainMatrix;
    LinearLayout mainMatrixView;
    LinearLayout resultView;
    TextView resultText;
    ImageView solv;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams c100x100 = new LinearLayout.LayoutParams(80, 80);
    private Context _context;
    private LinearLayout solvationView;
    private TextView solvationText;
    LinearLayout bottomHolder;
    String solvationString;
    TicTac tic;
    boolean isShowingSolvation=false;


    public Solver(Context context, LinearLayout mainView) {

        _context = context;

        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        LinearLayout scrollWrapper = new LinearLayout(context);
        scrollWrapper.setOrientation(LinearLayout.VERTICAL);
        mainMatrixView = new LinearLayout(context);

        mainMatrixView.setLayoutParams(wrapWrap);
        mainMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        mainMatrix = new Matrix(context, mainMatrixView);

        scrollWrapper.addView(mainMatrixView);

        bottomHolder = new LinearLayout(context);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(80, 80);

        ImageView plusRow = new ImageView(context);
        plusRow.setImageResource(R.drawable.plus_small);
        bottomHolder.addView(plusRow, params1);

        ImageView minusRow = new ImageView(context);
        minusRow.setImageResource(R.drawable.minus_small);
        bottomHolder.addView(minusRow, params1);


        solv = new ImageView(context);
        solv.setImageResource(R.drawable.vortex_out);
        solv.setVisibility(View.INVISIBLE);
        solv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isShowingSolvation)  {
                    solvationView.setVisibility(View.VISIBLE);
                    solv.setImageResource(R.drawable.vortex_in);
                    startSolvationCast();
                    isShowingSolvation=true;
                }
                else{
                    solvationView.setVisibility(View.GONE);
                    stopSolvationCast();
                    solv.setImageResource(R.drawable.vortex_out);
                    isShowingSolvation=false;
                }
            }
        });
        c100x100.leftMargin=100;
        bottomHolder.addView(solv,c100x100);



        scrollWrapper.addView(bottomHolder, fillWrap);

        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrix.addRow();
                mainMatrix.makeVisible();
            }
        });

        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrix.removeRow();
                mainMatrix.makeVisible();
            }
        });

        scrollView.addView(scrollWrapper, wrapWrap);
        mainView.addView(scrollView, wrapWrap);

        wrapWrap.gravity = Gravity.CENTER_HORIZONTAL;

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
        solvationView = new LinearLayout(context);
        solvationView.setPadding(15, 15, 0, 0);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);

        solvationText = new TextView(context);
        solvationText.setTextSize(23);
        solvationText.setGravity(Gravity.CENTER_HORIZONTAL);
        solvationView.addView(solvationText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        solvationView.setVisibility(View.GONE);
        mainView.addView(solvationView);
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------



        resultView = new LinearLayout(context);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setGravity(Gravity.RIGHT);
        resultView.setPadding(0, 20, 0, 0);
        resultView.setVisibility(View.GONE);


        resultText = new TextView(context);
        resultText.setTextSize(20);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText,fillWrap);



        mainView.addView(resultView);
    }


    private void stopSolvationCast() {


    }

    private void startSolvationCast() {
        tic = new TicTac();
        if (mainMatrix.rows == 2 && mainMatrix.columns == 2) {
            start2x2Solvation();
        } else if (mainMatrix.rows == 3 && mainMatrix.columns == 3) {
            start3x3Solvation();
        } else ;
        tic.execute();
    }

    private void start3x3Solvation() {

    }

    private void start2x2Solvation() {
        solvationString = "" + round(mainMatrix.m[0][0]) + "*" + round(mainMatrix.m[1][1]) + " - " + round(mainMatrix.m[0][1]) + "*" + round(mainMatrix.m[1][0]);
    }

    private String round(float i) {
        if (i % 1 == 0)
            return Integer.toString((int) i);
        else
            return Float.toString(i);
    }

    Dialog d;




    public void findProperVectors() {

    }

    public void findRang() {


    }

    public void findInverse() {


    }

    public void findMultiplication(Matrix secondMatrix) {

    }


    public void findDeterminant() {
        try {
            resultText.setText("Determinant = " + round(mainMatrix.findDeterminant()));
            resultView.setVisibility(View.VISIBLE);
            solv.setVisibility(View.VISIBLE);
            resultText.setTextColor(Color.WHITE);
        } catch (BadSymbolException e) {
            resultText.setText("Some elements are unsiutable");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        } catch (BadMatrixException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);

        }
//        Toast.makeText(_context, mainMatrix.findDeterminant() + "", 2000).show();
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

            if (counter <= solvationString.length()) {
                solvationText.setText(solvationString.substring(0, counter));
            }
            counter++;
            super.onProgressUpdate(values);
        }
    }


}
