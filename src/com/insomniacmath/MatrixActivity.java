package com.insomniacmath;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MatrixActivity extends Activity implements Constants {
    /**
     * Called when the activity is first created.
     */
    Solver solver;
    LinearLayout mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.resolvePlatform(this);

        mainView = new LinearLayout(this);
        mainView.setBackgroundColor(0xff000000);
        mainView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        mainView.setOrientation(LinearLayout.VERTICAL);
        solver = new Solver(this, mainView);
        setContentView(mainView);

    }

    private boolean isChangedStat = false;
    private static final int SOLVE_ITEM = Menu.FIRST;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (solver.state == STATE_MULTIPLY_PRESSED ||
                solver.state == STATE_SIDE_COLUMN_ADDED) {
            menu.clear();
            menu.add(0, SOLVE_ITEM, 0, "Solve");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        solver.onMenu(item.getItemId());
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!solver.onBackPressed())
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        solver.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
