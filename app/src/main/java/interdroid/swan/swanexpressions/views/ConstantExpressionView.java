package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 27/01/16.
 */
public class ConstantExpressionView extends CardView implements View.OnClickListener {

    private TextView mConstantValue;

    private ExpressionCreatorItem mExpressionCreatorItem;
    private OnConstantExpressionClickListener mListener;

    public ConstantExpressionView(Context context) {
        super(context);
    }

    public ConstantExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConstantExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        this.setOnClickListener(this);
        mConstantValue = (TextView) findViewById(R.id.constant_value);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem,
                                         OnConstantExpressionClickListener listener) {
        mExpressionCreatorItem = expressionCreatorItem;
        mListener = listener;
        mConstantValue.setText(expressionCreatorItem.expressionInterface.getExpression());
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            if (mListener != null) {
                mListener.onConstantExpressionClicked(mExpressionCreatorItem);
            }
        }
    }

    public interface OnConstantExpressionClickListener {
        void onConstantExpressionClicked(ExpressionCreatorItem expressionCreatorItem);
    }
}
