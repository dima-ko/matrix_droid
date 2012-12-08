package com.insomniacmath.tests.system;


import com.insomniacmath.Solver;
import com.insomniacmath.tests.GeneralTest;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class SystemAnimTest extends GeneralTest {

    public void testSolveSystem() throws Exception {
//
        clickOnViewByID(PLUS_COLUMN_ID);
        clickOnViewByID(PLUS_ROW_ID);

        enterEditByXY(0, 0, 2);
        enterEditByXY(1, 0, 5);
        enterEditByXY(2, 0, 6);
        enterEditByXY(0, 1, 8);
        enterEditByXY(1, 1, 9);
        enterEditByXY(2, 1, 13);
        enterEditByXY(0, 2, -7);
        enterEditByXY(1, 2, 4);
        enterEditByXY(2, 2, -1);

        solo.clickOnMenuItem("Linear system");

        enterEditByID(80, 1);
        enterEditByID(81, 1);
        enterEditByID(82, 2);

        solo.clickOnMenuItem("Solve");
        clickOnViewByID(Solver.EXPLAIN_BUTTON_ID);
        solo.sleep(400200);

    }

}