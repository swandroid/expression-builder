package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 19/01/16.
 */
public class SensorExpressionView extends CardView implements View.OnClickListener {

    private ImageView mSensorImage;
    private TextView mSensorExpression;

    private ExpressionCreatorItem mExpressionCreatorItem;
    private OnSensorExpressionClickListener mListener;

    public SensorExpressionView(Context context) {
        super(context);
    }

    public SensorExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SensorExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        mSensorImage = (ImageView) findViewById(R.id.sensor_image);
        mSensorExpression = (TextView) findViewById(R.id.sensor_expression);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem,
                                         OnSensorExpressionClickListener listener) {
        mExpressionCreatorItem = expressionCreatorItem;
        mListener = listener;
        mSensorExpression.setText(expressionCreatorItem.expressionInterface.getExpression());
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            if (mListener != null) {
                mListener.onSensorExpressionClicked(mExpressionCreatorItem);
            }
        }
    }

    public interface OnSensorExpressionClickListener {
        void onSensorExpressionClicked(ExpressionCreatorItem expressionCreatorItem);
    }
}