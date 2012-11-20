package com.insomniacmath.tests.determinant;


import android.widget.TextView;
import com.insomniacmath.tests.GeneralTest;
import junit.framework.Assert;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class Determinant3x4Test extends
        GeneralTest {

    public void testDetermin3x4() throws Exception {

        clickOnViewByID(PLUS_COLUMN_ID);
        clickOnViewByID(PLUS_COLUMN_ID);
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


        solo.clickOnMenuItem("Determinant");
        solo.sleep(10);
        TextView result = (TextView) solo.getView(RESULT_ID);
        Assert.assertTrue(((String)result.getText()).contains("must be square"));
    }
}