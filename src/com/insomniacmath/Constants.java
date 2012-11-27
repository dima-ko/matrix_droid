package com.insomniacmath;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public interface Constants {

    int STATE_INITIAL = 0;
    int STATE_DETERMIN_PRESSED = 1;
    int STATE_DETERMIN_EXPLAINING = 2;
    int STATE_DETERMIN_EXPLAINED = 3;
    int STATE_MULTIPLY_PRESSED = 4;
    int STATE_MULTIPLY_FIND = 5;
    int STATE_MULTIPLY_EXPLAINING = 6;
    int STATE_MULTIPLY_EXPLAINED = 7;
    int STATE_INVERT_FIND = 8;
    int STATE_INVERT_EXPLAINING = 9;
    int STATE_INVERT_EXPLAINED = 10;
    int STATE_RANG_FIND = 11;
    int STATE_RANG_EXPLAINING = 12;
    int STATE_RANG_EXPLAINED = 13;
    int STATE_SIDE_COLUMN_ADDED = 14;
    int STATE_SYSTEM_SOLVED = 15;
    int STATE_SYSTEM_EXPLAINING = 16;
    int STATE_SYSTEM_EXPLAINED = 17;

    int MAX_ROWS = 8;
    int MAX_COLUMNS = 8;

    int DOWN = 0;
    int UP = 1;
    int LEFT = 2;
    int RIGHT = 3;

    final static int cyan = 0xAA3388FF;
    final static int viol = 0xAA8833FF;
    final static int ros = 0xAAFF3388;
    final static int blu = 0xAA2233FF;
    final static int gree = 0xAA22FF22;
    final static int yel = 0xAAFF8800;


    int VIEW_IDS = 500;
    int BODY_ID = VIEW_IDS + 1;
    int RESULT_ID = VIEW_IDS + 2;

    int PLUS_COLUMN_ID = VIEW_IDS + 12;
    int MINUS_COLUMN_ID = VIEW_IDS + 13;
    int PLUS_ROW_ID = VIEW_IDS + 14;
    int MINUS_ROW_ID = VIEW_IDS + 15;

    int[] colors = new int[]{cyan, viol, ros, blu, gree, yel};

    public static final int EDIT_HEIGHT = 60;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapRel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapCenterHor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillFill = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams wrapFill = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.FILL_PARENT);
    LinearLayout.LayoutParams c20Fill = new LinearLayout.LayoutParams(20, RelativeLayout.LayoutParams.FILL_PARENT);
    LinearLayout.LayoutParams c80x80left100 = new LinearLayout.LayoutParams(80, 80);
    LinearLayout.LayoutParams c80x80 = new LinearLayout.LayoutParams(80, 80);
    LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, EDIT_HEIGHT);


    final static LinearLayout.LayoutParams wrap_80 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 80);
    final static LinearLayout.LayoutParams wrap_wrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


}
