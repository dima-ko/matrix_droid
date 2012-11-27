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
        enterEditByXY(0, 0, 2);
        enterEditByXY(1, 0, 5);
        enterEditByXY(0, 1, 8);
        enterEditByXY(1, 1, 12);

        solo.clickOnMenuItem("Linear system");

        enterEditByID(80, 1);
        enterEditByID(81, 1);

        solo.clickOnMenuItem("Solve");
        clickOnViewByID(Solver.EXPLAIN_BUTTON_ID);
        solo.sleep(400200);

    }

}