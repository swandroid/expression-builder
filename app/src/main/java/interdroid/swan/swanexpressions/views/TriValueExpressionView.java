package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.pojos.expressions.ValueExpression;

/**
 * Created by steven on 27/01/16.
 */
public class TriValueExpressionView extends FrameLayout {

    private LinearLayout mExpressionContainer;
    private TextView mExpressionName;

    private ExpressionCreatorItem mExpressionCreatorItem;

    public TriValueExpressionView(Context context) {
        super(context);
    }

    public TriValueExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TriValueExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        mExpressionContainer = (LinearLayout) findViewById(R.id.tri_value_expression_container);
        mExpressionName = (TextView) findViewById(R.id.tri_value_expression_name);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem) {
        mExpressionCreatorItem = expressionCreatorItem;
        addExpressionItems();
    }

    private void addExpressionItems() {
        mExpressionContainer.removeViews(1, mExpressionContainer.getChildCount() - 1);
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        List<ExpressionCreatorItem> items = ((ValueExpression) mExpressionCreatorItem.expressionInterface).getExpressionCreatorItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).expressionTypeInt == Constants.CONSTANT_EXPRESSION) {
                ConstantExpressionView view = (ConstantExpressionView) inflater.inflate(R.layout.view_constant_expression, null);
                view.setExpressionCreatorItem(items.get(i), null);
                mExpressionContainer.addView(view);
            } else if (items.get(i).expressionTypeInt == Constants.SENSOR_EXPRESSION) {
                SensorExpressionView view = (SensorExpressionView) inflater.inflate(R.layout.view_sensor_expression, null);
//                view.setExpressionCreatorItem(mExpressionCreatorItems.get(i), null);
                mExpressionContainer.addView(view);
            } else if (items.get(i).expressionTypeInt == Constants.VALUE_EXPRESSION) {
                ValueExpressionView view =
                        (ValueExpressionView) inflater.inflate(R.layout.view_value_expression, null);
                view.setExpressionCreatorItem(items.get(i));
                mExpressionContainer.addView(view);
            } else if (items.get(i).expressionTypeInt == Constants.COMPARISON_EXPRESSION) {
                ComparisonOperatorExpressionView view = (ComparisonOperatorExpressionView) inflater.inflate(R.layout.view_comparison_operator_expression, null);
                view.setExpressionCreatorItem(items.get(i), null);
                mExpressionContainer.addView(view);
            }
        }
    }
}
