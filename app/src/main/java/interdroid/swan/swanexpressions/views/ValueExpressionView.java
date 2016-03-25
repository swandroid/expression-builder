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
public class ValueExpressionView extends FrameLayout {

    private LinearLayout mExpressionContainer;
    private TextView mExpressionName;

    private ExpressionCreatorItem mExpressionCreatorItem;

    public ValueExpressionView(Context context) {
        super(context);
    }

    public ValueExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        mExpressionContainer = (LinearLayout) findViewById(R.id.value_expression_container);
        mExpressionName = (TextView) findViewById(R.id.value_expression_name);
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
            } else if (items.get(i).expressionTypeInt == Constants.MATH_EXPRESSION) {
                MathOperatorExpressionView view =
                        (MathOperatorExpressionView) inflater.inflate(R.layout.view_math_operator_expression, null);
                view.setExpressionCreatorItem(items.get(i), null);
                mExpressionContainer.addView(view);
            }
        }
    }
}
