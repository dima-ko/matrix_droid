package com.insomniacmath.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.insomniacmath.Fraction;
import com.insomniacmath.exceptions.BadSymbolException;
import com.insomniacmath.exceptions.NotSquareException;
import com.insomniacmath.roboto.TextViewRoboto;

public class MinorSolvationView extends LinearLayout {


    private final MatrixView matrixView;
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
    public MinorSolvationView(Context context, int row, int column, Fraction[][] fraction) {
        super(context);
        setGravity(Gravity.CENTER);

        prefix = new TextViewRoboto(context);
        prefix.setText("M" + row + column);
        addView(prefix, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout matrixLayout = new LinearLayout(context);
        matrixView = new MatrixView(context, matrixLayout, 0);
        matrixView.mFrac = Fraction.deepCopy(fraction);
        matrixView.buildBricks();
        matrixView.refreshVisible();
        addView(matrixLayout, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        postfix = new TextViewRoboto(context);
        try {
            postfix.setText("=" + matrixView.findDeterminant());
        } catch (NotSquareException e) {
            e.printStackTrace();
        } catch (BadSymbolException e) {
            e.printStackTrace();
        }
        addView(postfix, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


    }

}
