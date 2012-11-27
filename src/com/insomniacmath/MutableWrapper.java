package com.insomniacmath;

import android.content.Context;
import android.widget.LinearLayout;

public class MutableWrapper extends MatrixWrapper{
    public MutableWrapper(Context context, LinearLayout view, int number, boolean isMutable) {
        super(context, view, number, isMutable);
    }
}
