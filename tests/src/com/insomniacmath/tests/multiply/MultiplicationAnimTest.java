package com.insomniacmath.tests.multiply;


import com.insomniacmath.Controller;
import com.insomniacmath.tests.GeneralTest;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class MultiplicationAnimTest extends
        GeneralTest {

    public void testDetermin2x2() throws Exception {
//
        clickOnViewByID(PLUS_COLUMN_ID);

        enterEditByXY(0, 0, 2);
        enterEditByXY(1, 0, 5);
        enterEditByXY(2, 0, 6);
        enterEditByXY(0, 1, 8);
        enterEditByXY(1, 1, 9);
        enterEditByXY(2, 1, 13);

        solo.clickOnMenuItem("Multiply");

        enterEditByID(100, 1);
        enterEditByID(101, 3);
        enterEditByID(108, 6);
        enterEditByID(109, 9);
        enterEditByID(116, 6);
        enterEditByID(117, 9);

        solo.clickOnMenuItem("Solve");
//        clickOnViewByID(Controller.EXPLAIN_BUTTON_ID);
        solo.sleep(400200);

    }

}