package com.insomniacmath.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.insomniacmath.Constants;
import com.insomniacmath.MatrixActivity;
import com.jayway.android.robotium.solo.Solo;

import java.util.List;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class GeneralTest extends
        ActivityInstrumentationTestCase2<MatrixActivity>  implements Constants {

    protected Solo solo;

    public GeneralTest() {
        super("com.insomniacmath",
                MatrixActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    protected void enterEditByXY(int x, int y, float number) {
        int id = y * MAX_COLUMNS + x;
        enterEditByID(id, number);
    }

    private void enterEditByID(int id, float number) {
        EditText numberEdit = (EditText) solo.getView(id);
        solo.clickOnView(numberEdit);
        solo.enterText(numberEdit, Float.toString(number));
    }

    protected void enterEditByXY(int x, int y, int number) {
        int id = y * MAX_COLUMNS + x;
        enterEditByID(id, number);
    }

    private void enterEditByID(int id, int number) {
        EditText numberEdit = (EditText) solo.getView(id);
        solo.clickOnView(numberEdit);
        solo.enterText(numberEdit, Integer.toString(number));
    }

    protected void clickOnButtonByID(int ID) {
        // get a list of all ImageButtons on the current activity
        List<Button> btnList = solo.getCurrentButtons();
        for (int i = 0; i < btnList.size(); i++) {
            Button btn = btnList.get(i);
            // find button by id
            if (btn.getId() == ID) {
                // click on the button using index (not id !!!)
                solo.clickOnButton(i);
                // check if new activity is the 'About'
            } else {
                // other code
            }
        }
    }

    protected void clickOnViewByID(int ID) {
        // get a list of all ImageButtons on the current activity
        List<View> views = solo.getCurrentViews();
        for (View view : views) {
            // find button by id
            if (view.getId() == ID) {
                // click on the button using index (not id !!!)
                solo.clickOnView(view);
                // check if new activity is the 'About'
            } else {
                // other code
            }
        }
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}