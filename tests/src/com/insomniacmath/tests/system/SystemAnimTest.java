package com.insomniacmath.tests.system;


import com.insomniacmath.Controller;
import com.insomniacmath.tests.GeneralTest;

import java.util.Random;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class SystemAnimTest extends GeneralTest {


    public static final int ADD_COLUMN_START_ID = 100;

    public void testGauss3x3() throws Exception {
//
        Random random = new Random();
        final int MAX_TEXT_DIM = 2;
        int rows = 3;
//        int rows = 2 + random.nextInt(MAX_TEXT_DIM);
//        int columns = 2 + random.nextInt(MAX_TEXT_DIM);
        int columns = rows;
        for (int i = 0; i < columns - 2; i++) {
            clickOnViewByID(PLUS_COLUMN_ID);
        }
        for (int i = 0; i < rows - 2; i++) {
            clickOnViewByID(PLUS_ROW_ID);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                enterEditByXY(j, i, random.nextInt() % 100);
            }
        }

        solo.clickOnText("Action");
        solo.clickOnText("Linear");

        for (int i = 0; i < rows; i++) {
            enterEditByID(100 + i, random.nextInt(10));
        }

        solo.clickOnMenuItem("Solve");
        solo.clickOnMenuItem("Gauss");
//        clickOnViewByID(Controller.EXPLAIN_BUTTON_ID);
        solo.sleep(400200);

    }

}