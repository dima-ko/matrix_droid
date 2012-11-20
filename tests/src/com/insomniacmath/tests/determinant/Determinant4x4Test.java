package com.insomniacmath.tests.determinant;


import android.widget.TextView;
import com.insomniacmath.tests.GeneralTest;
import junit.framework.Assert;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class Determinant4x4Test extends
        GeneralTest {

    public void testDetermin4x4() throws Exception {

        clickOnViewByID(PLUS_COLUMN_ID);
        clickOnViewByID(PLUS_COLUMN_ID);
        clickOnViewByID(PLUS_ROW_ID);
        clickOnViewByID(PLUS_ROW_ID);

        enterEditByXY(0, 0, 1);
        enterEditByXY(1, 0, 2);
        enterEditByXY(2, 0, 0);
        enterEditByXY(3, 0, 8);

        enterEditByXY(0, 1, 3);
        enterEditByXY(1, 1, 4);
        enterEditByXY(2, 1, 2);
        enterEditByXY(3, 1, -7);

        enterEditByXY(0, 2, 3);
        enterEditByXY(1, 2, 8);
        enterEditByXY(2, 2, 1);
        enterEditByXY(3, 2, -5);

        enterEditByXY(0, 3, 9);
        enterEditByXY(1, 3, 4);
        enterEditByXY(2, 3, 7);
        enterEditByXY(3, 3, -6);

        solo.clickOnMenuItem("Determinant");
        solo.sleep(10);
        TextView result = (TextView) solo.getView(RESULT_ID);
        Assert.assertEquals(result.getText(), "Determinant = " + "6");
    }
}