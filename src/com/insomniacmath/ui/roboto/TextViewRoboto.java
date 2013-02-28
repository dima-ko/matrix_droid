package com.insomniacmath.ui.roboto;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.insomniacmath.R;
import com.insomniacmath.etc.Utils;

/**
 * this class was made
 * by insomniac and angryded
 * for their purposes
 */
public class TextViewRoboto extends TextView {
    public TextViewRoboto(Context context) {
        super(context);
        setTypeface(Utils.roboto_light);
        setTextColor(0xffffffff);
    }

    public TextViewRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Utils.roboto_light);
        setTextColor(0xffffffff);
    }

    public TextViewRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Utils.roboto_light);
        setTextColor(0xffffffff);
    }
}
