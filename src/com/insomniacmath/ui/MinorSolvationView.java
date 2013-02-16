package com.insomniacmath.ui;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.insomniacmath.roboto.TextViewRoboto;

public class MinorSolvationView extends LinearLayout {


    private final ConstMatrixView matrixView;
    TextViewRoboto postfix;
    TextViewRoboto prefix;

    //todo package!!!!

    public void setPostfix(String postfix1) {
        postfix.setText(postfix1);
    }

    public void setPrefix(String prefix1) {
        prefix.setText(prefix1);
    }

    //todo : arrow - next step, long press -> automatic
    public MinorSolvationView(Context context, MatrixModel model) {
        super(context);
        setGravity(Gravity.CENTER);

        prefix = new TextViewRoboto(context);
        addView(prefix, LParams.L_WRAP_WRAP);

        LinearLayout matrixLayout = new LinearLayout(context);
        matrixView = new ConstMatrixView(context, model, 0);
        addView(matrixLayout, LParams.L_WRAP_WRAP);

        postfix = new TextViewRoboto(context);
        addView(postfix, LParams.L_WRAP_WRAP);


    }

}
