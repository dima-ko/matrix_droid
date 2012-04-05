package com.insomniacmath;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MatrixActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    Solver solver;
    LinearLayout mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainView = new LinearLayout(this);
        mainView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        mainView.setOrientation(LinearLayout.VERTICAL);
        solver = new Solver(this, mainView);
        setContentView(mainView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.matrix_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.Determinant:
                solver.findDeterminant();
                return true;
            case R.id.Multiply:
                solver.findMultiplication(null);
                return true;
            case R.id.Invert:
                solver.findInverse();
                return true;
            case R.id.Rang:
                solver.findRang();
                return true;
            case R.id.Vectors:
                solver.findProperVectors();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    protected void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        radiusSurfaceView.onResumeMySurfaceView();
//    }
//
//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        radiusSurfaceView.onPauseMySurfaceView();
//    }
}
