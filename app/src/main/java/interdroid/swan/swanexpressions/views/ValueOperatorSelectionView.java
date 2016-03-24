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
public class ValueOperatorSelectionView extends LinearLayout implements View.OnClickListener {

    private View mMathPlus;
    private View mMathMinus;
    private View mMathTimes;
    private View mMathDivide;
    private View mMathModulo;

    private View mComparisonEquals;
    private View mComparisonGreaterEquals;
    private View mComparisonLessEquals;
    private View mComparisonGreater;
    private View mComparisonLess;
    private View mComparisonNotEqual;
    private View mComparisonContains;

    private ExpressionCreatorItem mExpressionCreatorItem;

    private OnOperatorClickListener mListener;

    public ValueOperatorSelectionView(Context context) {
        super(context);
    }

    public ValueOperatorSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueOperatorSelectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ValueOperatorSelectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        mMathPlus = findViewById(R.id.value_operator_math_plus);
        mMathMinus = findViewById(R.id.value_operator_math_minus);
        mMathTimes = findViewById(R.id.value_operator_math_times);
        mMathDivide = findViewById(R.id.value_operator_math_divide);
        mMathModulo = findViewById(R.id.value_operator_math_modulo);

        mComparisonEquals = findViewById(R.id.value_operator_comparison_equals);
        mComparisonGreaterEquals = findViewById(R.id.value_operator_comparison_greater_equals);
        mComparisonLessEquals = findViewById(R.id.value_operator_comparison_less_equals);
        mComparisonGreater = findViewById(R.id.value_operator_comparison_greater);
        mComparisonLess = findViewById(R.id.value_operator_comparison_less);
        mComparisonNotEqual = findViewById(R.id.value_operator_comparison_not_equals);
        mComparisonContains = findViewById(R.id.value_operator_comparison_contains);

        mMathPlus.setOnClickListener(this);
        mMathMinus.setOnClickListener(this);
        mMathTimes.setOnClickListener(this);
        mMathDivide.setOnClickListener(this);
        mMathModulo.setOnClickListener(this);

        mComparisonEquals.setOnClickListener(this);
        mComparisonGreaterEquals.setOnClickListener(this);
        mComparisonLessEquals.setOnClickListener(this);
        mComparisonGreater.setOnClickListener(this);
        mComparisonLess.setOnClickListener(this);
        mComparisonNotEqual.setOnClickListener(this);
        mComparisonContains.setOnClickListener(this);
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
        } else if (v == mComparisonEquals) {
            setComparisonExpression(R.string.comparison_operator_equals);
        } else if (v == mComparisonGreaterEquals) {
            setComparisonExpression(R.string.comparison_operator_greater_equals);
        } else if (v == mComparisonLessEquals) {
            setComparisonExpression(R.string.comparison_operator_less_equals);
        } else if (v == mComparisonGreater) {
            setComparisonExpression(R.string.comparison_operator_greater);
        } else if (v == mComparisonLess) {
            setComparisonExpression(R.string.comparison_operator_less);
        } else if (v == mComparisonNotEqual) {
            setComparisonExpression(R.string.comparison_operator_not_equals);
        } else if (v == mComparisonContains) {
            setComparisonExpression(R.string.comparison_operator_contains);
        }
    }

    private void setMathExpression(int operatorResourceId) {
        mExpressionCreatorItem.expressionTypeInt = Constants.MATH_EXPRESSION;
        mExpressionCreatorItem.expressionInterface = new MathExpression(getContext().getString(operatorResourceId));
        if (mListener != null) {
            mListener.onOperatorClicked(mExpressionCreatorItem);
        }
    }

    private void setComparisonExpression(int operatorResourceId) {
        mExpressionCreatorItem.expressionTypeInt = Constants.COMPARISON_EXPRESSION;
        mExpressionCreatorItem.expressionInterface = new ComparisonExpression(getContext().getString(operatorResourceId));
        if (mListener != null) {
            mListener.onOperatorClicked(mExpressionCreatorItem);
        }
    }

    public interface OnOperatorClickListener {
        void onOperatorClicked(ExpressionCreatorItem mExpressionCreatorItem);
    }
}
