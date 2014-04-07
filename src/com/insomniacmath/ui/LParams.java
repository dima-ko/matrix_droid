package com.insomniacmath.ui;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LParams {

    public final static LinearLayout.LayoutParams L_WRAP_70 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 70);
    public final static LinearLayout.LayoutParams L_WRAP_WRAP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public final static LinearLayout.LayoutParams L_FILL_FILL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
    public final static RelativeLayout.LayoutParams R_FILL_FILL = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
    public final static RelativeLayout.LayoutParams R_WRAP_WRAP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    public final static RelativeLayout.LayoutParams R_WRAP_WRAP_CENTER_HOR = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    public final static LinearLayout.LayoutParams L_FILL_WRAP = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    public final static LinearLayout.LayoutParams L_80_80_LEFT_100 = new LinearLayout.LayoutParams(80, 80);
    public final static LinearLayout.LayoutParams L_80_80 = new LinearLayout.LayoutParams(80, 80);


    public static LinearLayout.LayoutParams L_20_Fill = new LinearLayout.LayoutParams(20, LinearLayout.LayoutParams.FILL_PARENT);
    public static LinearLayout.LayoutParams L_70_70 = new LinearLayout.LayoutParams(70, 70);
    //todo
    public final static LinearLayout.LayoutParams wrap_80 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 80);
    public final static LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    static {
        R_WRAP_WRAP_CENTER_HOR.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        L_80_80_LEFT_100.leftMargin = 100;

    }


}
