package com.insomniacmath.roboto;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import com.insomniacmath.Utils;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class ButtonRoboto extends Button {
    public ButtonRoboto(Context context) {
        super(context);
        setTypeface(Utils.roboto_light);
        setBackgroundColor(0xff333333);
        setTextColor(Color.WHITE);
    }

    public ButtonRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Utils.roboto_light);
        setBackgroundColor(0xff333333);
        setTextColor(Color.WHITE);
    }

    public ButtonRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Utils.roboto_light);
        setBackgroundColor(0xff333333);
        setTextColor(Color.WHITE);
    }
}
