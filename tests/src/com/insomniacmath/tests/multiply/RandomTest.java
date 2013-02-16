package com.insomniacmath.tests.multiply;


import com.insomniacmath.Controller;
import com.insomniacmath.tests.GeneralTest;

import java.util.Random;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class RandomTest extends
        GeneralTest {

    public void testRandomMultiply() throws Exception {

        Random random = new Random();
        final int MAX_TEXT_DIM = 2;
        int columns = 2 + random.nextInt(MAX_TEXT_DIM);
        int rows = 2 + random.nextInt(MAX_TEXT_DIM);
        for (int i = 0; i < columns - 2; i++) {
            clickOnViewByID(PLUS_COLUMN_ID);
        }
        for (int i = 0; i < rows -2; i++) {
            clickOnViewByID(PLUS_ROW_ID);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                enterEditByXY(j, i, random.nextInt() % 100);
            }
        }

        solo.clickOnMenuItem("Multiply");

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                enterEditByID(100 + i * MAX_COLUMNS + j, random.nextInt() % 100);
            }
        }

        solo.clickOnMenuItem("Solve");
        clickOnViewByID(Controller.EXPLAIN_BUTTON_ID);
        solo.sleep(400200);

    }

}