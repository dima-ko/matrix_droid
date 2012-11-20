package com.insomniacmath.tests.determinant;


import android.widget.TextView;
import com.insomniacmath.tests.GeneralTest;
import junit.framework.Assert;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class Determinan2x2tTest extends
        GeneralTest {

    public void testDetermin2x2() throws Exception {

        enterEditByXY(0, 0, 2);
        enterEditByXY(1, 0, 5);
        enterEditByXY(0, 1, 8);
        enterEditByXY(1, 1, 12);
        solo.clickOnMenuItem("Determinant");
        solo.sleep(10);
        TextView result = (TextView) solo.getView(RESULT_ID);
        Assert.assertEquals(result.getText(), "Determinant = " + "-16");
    }

}