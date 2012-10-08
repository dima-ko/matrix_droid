package com.insomniacmath.tests;

import android.opengl.GLSurfaceView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.SeekBar;
import com.insomniacmath.MatrixActivity;
import com.jayway.android.robotium.solo.Solo;
import junit.framework.Assert;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class DeterminantTest extends
        ActivityInstrumentationTestCase2<MatrixActivity> {

    private Solo solo;

    public DeterminantTest() {
        super("com.insomniacmath",
                MatrixActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

//     solo.sendKey(Solo.MENU);
//    solo.clickOnText("More");
//    solo.clickOnText("Preferences");
//    solo.clickOnText("Edit File Extensions");
//    Assert.assertTrue(solo.searchText("rtf"));
//
//    solo.clickOnText("txt");
//    solo.clearEditText(2);
//    solo.enterText(2, "robotium");
//    solo.clickOnButton("Save");
//    solo.goBack();
//    solo.clickOnText("Edit File Extensions");
//    Assert.assertTrue(solo.searchText("application/robotium"));

    public void testLifeCycle() throws Exception {


    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}