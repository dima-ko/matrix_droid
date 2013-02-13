package com.insomniacmath.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.insomniacmath.roboto.TextViewRoboto;

public class MinorSolvationView extends LinearLayout {

    public MinorSolvationView(Context context, int row, int column, MatrixModel matrix) {
        super(context);
        setGravity(Gravity.CENTER);

        TextViewRoboto prefix = new TextViewRoboto(context);
        prefix.setText("M" + row + column);
        addView(prefix, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


    }

}
