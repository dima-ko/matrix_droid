package com.insomniacmath;

import android.content.Context;
import android.widget.LinearLayout;

public class ImmutableWrapper extends MatrixWrapper{
    public ImmutableWrapper(Context context, LinearLayout view, int number, boolean isMutable) {
        super(context, view, number, isMutable);
    }
}
