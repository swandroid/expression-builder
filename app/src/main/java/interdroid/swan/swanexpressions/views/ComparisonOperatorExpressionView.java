package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 27/01/16.
 */
public class ComparisonOperatorExpressionView extends FrameLayout implements View.OnClickListener {

    private TextView mOperatorValue;

    private ExpressionCreatorItem mExpressionCreatorItem;
    private OnComparisonOperatorExpressionClickListener mListener;

    public ComparisonOperatorExpressionView(Context context) {
        super(context);
    }

    public ComparisonOperatorExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComparisonOperatorExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        this.setOnClickListener(this);
        mOperatorValue = (TextView) findViewById(R.id.operator_value);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem,
                                         OnComparisonOperatorExpressionClickListener listener) {
        mExpressionCreatorItem = expressionCreatorItem;
        mListener = listener;
        mOperatorValue.setText(expressionCreatorItem.expressionInterface.getExpression());
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            if (mListener != null) {
                mListener.onComparisonOperatorExpressionClicked(mExpressionCreatorItem);
            }
        }
    }

    public interface OnComparisonOperatorExpressionClickListener {
        void onComparisonOperatorExpressionClicked(ExpressionCreatorItem expressionCreatorItem);
    }
}
