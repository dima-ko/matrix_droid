package com.insomniacmath.tests.multiply;


import com.insomniacmath.R;
import com.insomniacmath.Solver;
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
        enterEditByXY(0, 0, 2);
        enterEditByXY(1, 0, 5);
        enterEditByXY(0, 1, 8);
        enterEditByXY(1, 1, 12);

        clickOnButtonByID(R.id.multiply);

        enterEditByXY(100, 100, 1);
        enterEditByXY(101, 100, 3);
        enterEditByXY(100, 101, 6);
        enterEditByXY(101, 101, 9);


        solo.sleep(10);
        clickOnButtonByID(Solver.SOLVE_BUTTON_ID);
        solo.sleep(1200);
        clickOnButtonByID(Solver.EXPLAIN_BUTTON_ID);
//        TextView result = (TextView) solo.getView(RESULT_ID);
//        Assert.assertEquals(result.getText(), "Determinant = " + "-16");
    }

}