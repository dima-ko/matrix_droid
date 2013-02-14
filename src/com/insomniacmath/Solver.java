package com.insomniacmath;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.insomniacmath.exceptions.BadSymbolException;
import com.insomniacmath.exceptions.NotSquareException;
import com.insomniacmath.exceptions.SingularMatrixException;
import com.insomniacmath.ui.MatrixView;
import org.ejml.simple.SimpleMatrix;


public class Solver implements Constants {

    public static final int RESULT_MATRIX = 1000;
    public static final int SOLVE_BUTTON_ID = 600;
    public static final int EXPLAIN_BUTTON_ID = 900;
    public int state = STATE_INITIAL;
    public static int curEditId = 0;

    private float downY;
    private float downX;

    MatrixView mainMatrixModel;
    LinearLayout mainMatrixLayout;
    LinearLayout resultView;
    TextView resultText;
    Button xplainButton;
    LinearLayout.LayoutParams wrapWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout.LayoutParams wrapWrapCenterHor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams fillFill = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
    LinearLayout.LayoutParams fillWrap = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams c80x80left100 = new LinearLayout.LayoutParams(80, 80);
    LinearLayout.LayoutParams c80x80 = new LinearLayout.LayoutParams(80, 80);
    private Context _context;
    private LinearLayout solvationView;

    LinearLayout bottomPlusHolder;
    LinearLayout rightPlusHolder;

    private LinearLayout secondMatrixView;
    private MatrixView secondMatrixModel;
    LinearLayout resultMatrixLayout;
    private Animator animator;
    LinearLayout scrollWrapper;
    MatrixView resMatrixModel;

    public Solver(Context context, LinearLayout mainView) {

        _context = context;
        wrapWrap.gravity = Gravity.LEFT;
        wrapWrapCenterHor.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        setActionBar(mainView);

        HorizontalScrollView scrollView = new HorizontalScrollView(context);

        scrollWrapper = new LinearLayout(context);
        mainMatrixLayout = new LinearLayout(context);

        mainMatrixLayout.setLayoutParams(wrapWrap);
        mainMatrixLayout.setOrientation(LinearLayout.HORIZONTAL);

        mainMatrixModel = new MatrixView(context, mainMatrixLayout, 0);

        scrollWrapper.addView(mainMatrixLayout, wrapWrap);


        rightPlusHolder = new LinearLayout(context);
        rightPlusHolder.setOrientation(LinearLayout.VERTICAL);

        ImageView plusColumn = new ImageView(context);
        plusColumn.setId(PLUS_COLUMN_ID);
        plusColumn.setImageResource(R.drawable.plus_small);
        rightPlusHolder.addView(plusColumn, c80x80);
        plusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.addColumn();
            }
        });

        ImageView minusColumn = new ImageView(context);
        minusColumn.setId(MINUS_COLUMN_ID);
        minusColumn.setImageResource(R.drawable.minus_small);
        rightPlusHolder.addView(minusColumn, c80x80);
        minusColumn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.removeColumn();
            }
        });

        scrollWrapper.addView(rightPlusHolder, wrapWrap);

        scrollView.addView(scrollWrapper, wrapWrap);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wrapWrap);
        params.setMargins(10, 19, 0, 0);
        mainView.addView(scrollView, params);

        bottomPlusHolder = new LinearLayout(context);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(80, 80);

        ImageView plusRow = new ImageView(context);
        plusRow.setId(PLUS_ROW_ID);
        plusRow.setImageResource(R.drawable.plus_small);
        bottomPlusHolder.addView(plusRow, params1);

        ImageView minusRow = new ImageView(context);
        minusRow.setId(MINUS_ROW_ID);
        minusRow.setImageResource(R.drawable.minus_small);
        bottomPlusHolder.addView(minusRow, params1);

        c80x80left100.leftMargin = 100;

        plusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.addRow();
            }
        });

        minusRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mainMatrixModel.removeRow();
            }
        });
        mainView.addView(bottomPlusHolder, fillWrap);

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
        solvationView = new LinearLayout(context);
        solvationView.setBackgroundColor(0xff222222);
        solvationView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    animator.changeSpeed((motionEvent.getX() - downX) > 0);
                return true;
            }
        });

        solvationView.setOrientation(LinearLayout.VERTICAL);
        solvationView.setGravity(Gravity.CENTER_HORIZONTAL);
        solvationView.setVisibility(View.GONE);
        mainView.addView(solvationView);

        resultView = new LinearLayout(context);
        resultView.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.setOrientation(LinearLayout.HORIZONTAL);
        resultView.setPadding(0, 5, 0, 0);
        resultView.setBackgroundColor(0xff000000);
        resultView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    float dx = (motionEvent.getX() - downX);
                    float dy = (motionEvent.getY() - downY);
                    if (Math.abs(dx) > Math.abs(dy)) {
                        moveToEdit(dx < 0 ? LEFT : RIGHT);
                    } else {
                        moveToEdit(dy < 0 ? UP : DOWN);
                    }
                }
                return true;
            }
        });

        mainView.addView(resultView, fillFill);

        animator = new Animator(mainMatrixModel);
        animator.setView(solvationView);
    }

    private void setActionBar(LinearLayout mainView) {
        RelativeLayout actionBar = (RelativeLayout) ((Activity) _context).getLayoutInflater().inflate(R.layout.top, null);
        mainView.addView(actionBar, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        actionBar.findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(_context);

            }
        });
    }

    public void onDestroy() {
        mainMatrixModel.onDestroy();
    }

    // -----------------------------------------compute----------------------------------------------------

    public void findEigenVectors() {

    }

    public void findRang() {
        resultView.removeAllViews();
        addResultText();
        try {
            resultText.setText("Rang = " + Utils.round(mainMatrixModel.findRang(), false));
            resultText.setTextColor(Color.WHITE);
            // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);

            state = STATE_RANG_FIND;
            addXplainButton();
        } catch (BadSymbolException e) {
            resultText.setText(_context.getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
//            solvationText.setVisibility(View.GONE);
        }
    }

    public void findDeterminant() {
        resultView.removeAllViews();
        addResultText();
        try {
            resultText.setText("det = " + Utils.round(mainMatrixModel.findDeterminant(), false));
            resultText.setTextColor(Color.WHITE);
            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);
            state = STATE_DETERMIN_PRESSED;
            if (mainMatrixModel.rows == 2 || mainMatrixModel.rows == 3)
                addXplainButton();
        } catch (BadSymbolException e) {
            resultText.setText(_context.getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
        } catch (NotSquareException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
        }
    }

    public void findMultiplication() {
        try {
            mainMatrixModel.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            addResultText();
            resultText.setText(_context.getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
            return;
        }
        try {
            secondMatrixModel.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            addResultText();
            resultText.setText(_context.getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
            return;
        }

        state = STATE_MULTIPLY_FIND;

        SimpleMatrix a = new SimpleMatrix(mainMatrixModel.m);
        SimpleMatrix b = new SimpleMatrix(secondMatrixModel.m);

        SimpleMatrix c = a.mult(b);

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultMatrixLay.setId(RESULT_MATRIX);
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);

        resMatrixModel = new MatrixView(_context, resultMatrixLay, 2);
        resMatrixModel.adjustSizeTo(c.numRows(), c.numCols());
        for (int i = 0; i < c.numRows(); i++) {
            for (int j = 0; j < c.numCols(); j++) {
                resMatrixModel.m[i][j] = c.get(i, j);
            }
        }
        resMatrixModel.fillViewsFromMatrix();
        resMatrixModel.refreshVisible();
        animator.setResultMW(resMatrixModel);
        addXplainButton();
        animator.setAnimType(Animator.ANIM_MULTIPLICATION, mainMatrixModel.rows, mainMatrixModel.columns);
    }

    public void findInverse() {
        resultView.removeAllViews();
        try {
            mainMatrixModel.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            addResultText();
            resultText.setText(_context.getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
            return;
        }

        if (mainMatrixModel.columns != mainMatrixModel.rows) {
            addResultText();
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
            return;
        }

        resultMatrixLayout = new LinearLayout(_context);
        resultMatrixLayout.setId(RESULT_MATRIX);
        resultView.addView(resultMatrixLayout, wrapWrapCenterHor);
        resMatrixModel = new MatrixView(_context, resultMatrixLayout, 2);

        if (mainMatrixModel.elementsFractions) {
            Fraction[][] result = mainMatrixModel.findInverseFraction();
            resMatrixModel.adjustSizeTo(result[0].length, result.length);
            resMatrixModel.mFrac = result;
        } else {
            SimpleMatrix inverse = mainMatrixModel.findInverseDouble();
            resMatrixModel.adjustSizeTo(inverse.numCols(), inverse.numRows());
            for (int i = 0; i < inverse.numRows(); i++) {
                for (int j = 0; j < inverse.numCols(); j++) {
                    double v = inverse.get(i, j);
                    resMatrixModel.m[i][j] = v;
                }
            }
        }

        state = STATE_INVERT_FIND;

        resMatrixModel.fillViewsFromMatrix();
        resMatrixModel.refreshVisible();
        animator.setResultMW(resMatrixModel);
        animator.setAnimType(Animator.ANIM_INVERT, mainMatrixModel.rows, mainMatrixModel.columns);
        addXplainButton();
    }

    private void findSystemSolvation() throws SingularMatrixException {
        resultView.removeAllViews();
        Log.d("zzzzzzzzzzzz", "start beforefill at" + System.currentTimeMillis());
        try {
            mainMatrixModel.fillMatrixFromViews();
        } catch (BadSymbolException e) {
            e.printStackTrace();
        }
        Log.d("zzzzzzzzzzzz", "start afterfill at" + System.currentTimeMillis());

        LinearLayout resultMatrixLay = new LinearLayout(_context);
        resultMatrixLay.setId(RESULT_MATRIX);
        resultView.addView(resultMatrixLay, wrapWrapCenterHor);
        resMatrixModel = new MatrixView(_context, resultMatrixLay, 2);

        if (mainMatrixModel.elementsFractions) {
            Fraction[] result = mainMatrixModel.solveSLEFraction();
            resMatrixModel.adjustSizeTo(1, result.length);
            resMatrixModel.mFrac = new Fraction[result.length][];

            for (int i = 0; i < result.length; i++) {
                resMatrixModel.mFrac[i] = new Fraction[1];
                resMatrixModel.mFrac[i][0] = result[i];
            }

        } else {
            SimpleMatrix result = mainMatrixModel.solveSLEDouble();
            resMatrixModel.adjustSizeTo(result.numCols(), result.numRows());
            for (int i = 0; i < result.numRows(); i++) {
                for (int j = 0; j < result.numCols(); j++) {
                    double v = result.get(i, j);
                    resMatrixModel.m[i][j] = v;
                }
            }
        }

        resMatrixModel.fillViewsFromMatrix();
        resMatrixModel.refreshVisible();
        // xplainButton.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.rotate_indefinitely_cw));
        animator.setAnimType(Animator.ANIM_SYSTEM_GAUSS, mainMatrixModel.rows, mainMatrixModel.columns);
        state = STATE_SYSTEM_SOLVED;
        animator.setResultMW(resMatrixModel);
        addXplainButton();
    }

    // -------------------------------------add UI elements----------------------------------------------------

    private void addXplainButton() {
        xplainButton = new Button(_context);
        xplainButton.setId(EXPLAIN_BUTTON_ID);
        xplainButton.setBackgroundResource(R.drawable.xplain_button);
        xplainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                solvationView.setVisibility(View.VISIBLE);
                solvationView.removeAllViews();
                if (state == STATE_SYSTEM_SOLVED) {
                    showSystemDialog();
                } else {
                    xplainButton.setVisibility(View.GONE);
                    startExplain();
                }
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160, 64);
        params.setMargins(15, 0, 0, 0);
        resultView.addView(xplainButton, params);
    }

    private void showSystemDialog() {


    }

//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.context_menu, menu);
//    }


    private void addResultText() {
        resultText = new TextView(_context);
        resultText.setTextSize(20);
        resultText.setPadding(20, 20, 20, 20);
        resultText.setId(RESULT_ID);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, wrapWrapCenterHor);
    }

    public void addSecondMatrix() {

        state = STATE_MULTIPLY_PRESSED;
        bottomPlusHolder.setVisibility(View.GONE);
        rightPlusHolder.setVisibility(View.GONE);

        secondMatrixView = new LinearLayout(_context);

        secondMatrixView.setLayoutParams(wrapWrap);
        secondMatrixView.setOrientation(LinearLayout.HORIZONTAL);

        secondMatrixModel = new MatrixView(_context, secondMatrixView, 1);
        secondMatrixModel.adjustSizeTo(mainMatrixModel.rows, mainMatrixModel.columns);
        secondMatrixModel.refreshVisible();

        mainMatrixLayout.addView(secondMatrixView);

        animator.setSecMW(secondMatrixModel);
    }

    // -----------------------------------------controls----------------------------------------------------

    public void moveToEdit(int direction) {
        int newId;
        EditText input2;
        if (curEditId < 100) {
            newId = mainMatrixModel.getNextEdit(direction, curEditId);
            input2 = (EditText) mainMatrixModel._view.findViewById(newId);
        } else {
            newId = secondMatrixModel.getNextEdit(direction, curEditId - 100);
            input2 = (EditText) secondMatrixModel.bodyMatrix.findViewById(newId);
        }
        if (newId != -1) {
            input2.requestFocus();
            curEditId = newId;
        }
    }

    private void startExplain() {
        switch (state) {
            case STATE_DETERMIN_PRESSED:
                state = STATE_DETERMIN_EXPLAINING;
                break;
            case STATE_MULTIPLY_FIND:
                state = STATE_MULTIPLY_EXPLAINING;
                break;
            case STATE_SYSTEM_SOLVED:
                state = STATE_SYSTEM_EXPLAINING_GAUS;
                break;
            case STATE_INVERT_FIND:
                state = STATE_INVERT_EXPLAINING;
                break;
            default:
                return;
        }
        solvationView.setVisibility(View.VISIBLE);
        animator.startExplaining();
    }

    public void stopExplain() {
        animator.stopExplain();

    }

    public void onMenu(int i) {
        switch (i) {
            case R.id.determinant:
                findDeterminant();
                break;
            case R.id.multiply:
                addSecondMatrix();
                break;
            case R.id.invers:
                findInverse();
                break;
            case R.id.rank:
                findRang();
                break;
            case R.id.solve_sys:
                mainMatrixModel.addSideColumn();
                state = STATE_SIDE_COLUMN_ADDED;
                break;
//            case R.id.eigen:
//                findEigenVectors();
//                break;
            case Menu.FIRST:
                if (state == STATE_SIDE_COLUMN_ADDED) {
                    try {
                        findSystemSolvation();
                    } catch (SingularMatrixException e) {
                        e.printStackTrace();  //Todo
                    }
                } else if (state == STATE_MULTIPLY_PRESSED)
                    findMultiplication();
                break;
        }
    }

    public boolean onBackPressed() {
        switch (state) {
            case STATE_INITIAL:
                return false;
            case STATE_DETERMIN_PRESSED:
                state = STATE_INITIAL;
                resultText.setVisibility(View.GONE);
                solvationView.setVisibility(View.GONE);
                xplainButton.setVisibility(View.GONE);
                break;
            case STATE_DETERMIN_EXPLAINING:
                animator.stopExplain();
            case STATE_DETERMIN_EXPLAINED:
                state = STATE_DETERMIN_PRESSED;
                solvationView.setVisibility(View.GONE);
                xplainButton.setVisibility(View.VISIBLE);
                break;
            case STATE_MULTIPLY_EXPLAINED:
                resultView.removeView(resultMatrixLayout);
                resMatrixModel.onDestroy();
            case STATE_MULTIPLY_PRESSED:
            case STATE_MULTIPLY_FIND:
            case STATE_MULTIPLY_EXPLAINING:
                mainMatrixLayout.removeView(secondMatrixView);
                secondMatrixModel.onDestroy();
                resultView.removeAllViews();
            case STATE_SYSTEM_EXPLAINING_GAUS:
                resultView.removeView(resultMatrixLayout);
                if (resMatrixModel != null)
                    resMatrixModel.onDestroy();
            case STATE_INVERT_FIND:
            case STATE_RANG_FIND:
            case STATE_SIDE_COLUMN_ADDED:
            case STATE_SYSTEM_SOLVED:
                state = STATE_INITIAL;
                bottomPlusHolder.setVisibility(View.VISIBLE);
                rightPlusHolder.setVisibility(View.VISIBLE);

                solvationView.removeAllViews();
                Thread.yield();
                secondMatrixView = null;
                secondMatrixModel = null;
                break;

        }

        return true;
    }
}
