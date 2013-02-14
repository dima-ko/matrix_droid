package com.insomniacmath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MatrixActivity extends Activity implements Constants {
    private static final int MENU_EDIT = 3;
    private static final int MENU_DELETE = 4;
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

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (solver.state == STATE_MULTIPLY_PRESSED ||
//                solver.state == STATE_SIDE_COLUMN_ADDED) {
//            menu.clear();
//            menu.add(0, SOLVE_ITEM, 0, "Solve");
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, MENU_EDIT, 0, R.string.determinant);
        menu.add(Menu.NONE, MENU_DELETE, 1, R.string.multiply);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
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
