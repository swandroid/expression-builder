package interdroid.swan.swanexpressions.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ComparisonExpression;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.pojos.expressions.MathExpression;

/**
 * Created by steven on 27/01/16.
 */
public class TriMathOperatorSelectionView extends LinearLayout implements View.OnClickListener {

    private View mMathPlus;
    private View mMathMinus;
    private View mMathTimes;
    private View mMathDivide;
    private View mMathModulo;

    private View mTriAnd;
    private View mTriOr;

    private ExpressionCreatorItem mExpressionCreatorItem;

    private OnOperatorClickListener mListener;

    public TriMathOperatorSelectionView(Context context) {
        super(context);
    }

    public TriMathOperatorSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TriMathOperatorSelectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TriMathOperatorSelectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        mMathPlus = findViewById(R.id.tri_math_operator_math_plus);
        mMathMinus = findViewById(R.id.tri_math_operator_math_minus);
        mMathTimes = findViewById(R.id.tri_math_operator_math_times);
        mMathDivide = findViewById(R.id.tri_math_operator_math_divide);
        mMathModulo = findViewById(R.id.tri_math_operator_math_modulo);

        mTriAnd = findViewById(R.id.tri_math_operator_tri_and);
        mTriOr = findViewById(R.id.tri_math_operator_tri_or);

        mMathPlus.setOnClickListener(this);
        mMathMinus.setOnClickListener(this);
        mMathTimes.setOnClickListener(this);
        mMathDivide.setOnClickListener(this);
        mMathModulo.setOnClickListener(this);

        mTriAnd.setOnClickListener(this);
        mTriOr.setOnClickListener(this);
        //TODO: check if input is a string or number
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem,
                                         OnOperatorClickListener onOperatorClickListener) {
        mExpressionCreatorItem = expressionCreatorItem;
        mListener = onOperatorClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v == mMathPlus) {
            setMathExpression(R.string.math_operator_plus);
        } else if (v == mMathMinus) {
            setMathExpression(R.string.math_operator_minus);
        } else if (v == mMathTimes) {
            setMathExpression(R.string.math_operator_times);
        } else if (v == mMathDivide) {
            setMathExpression(R.string.math_operator_divide);
        } else if (v == mMathModulo) {
            setMathExpression(R.string.math_operator_modulo);
        } else if (v == mTriAnd) {
            setTriExpression(R.string.tri_operator_and_value);
        } else if (v == mTriOr) {
            setTriExpression(R.string.tri_operator_or_value);
        }
    }

    private void setMathExpression(int operatorResourceId) {
        mExpressionCreatorItem.expressionTypeInt = Constants.MATH_EXPRESSION;
        mExpressionCreatorItem.expressionInterface = new MathExpression(getContext().getString(operatorResourceId));
        if (mListener != null) {
            mListener.onOperatorClicked(mExpressionCreatorItem);
        }
    }

    private void setTriExpression(int operatorResourceId) {
        mExpressionCreatorItem.expressionTypeInt = Constants.LOGIC_EXPRESSION;
        mExpressionCreatorItem.expressionInterface = new ComparisonExpression(getContext().getString(operatorResourceId));
        if (mListener != null) {
            mListener.onOperatorClicked(mExpressionCreatorItem);
        }
    }

    public interface OnOperatorClickListener {
        void onOperatorClicked(ExpressionCreatorItem mExpressionCreatorItem);
    }
}
