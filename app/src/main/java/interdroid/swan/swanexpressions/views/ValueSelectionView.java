package interdroid.swan.swanexpressions.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.activities.ConstantExpressionCreatorActivity;
import interdroid.swan.swanexpressions.activities.ExpressionSelectionActivity;
import interdroid.swan.swanexpressions.activities.SensorExpressionCreatorActivity;
import interdroid.swan.swanexpressions.pojos.expressions.ConstantExpression;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 27/01/16.
 */
public class ValueSelectionView extends LinearLayout {

    private View mSensorExpression;
    private View mConstantExpression;

    private ExpressionCreatorItem mExpressionCreatorItem;

    public ValueSelectionView(Context context) {
        super(context);
    }

    public ValueSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueSelectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ValueSelectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        getViews();
    }

    private void getViews() {
        mSensorExpression = findViewById(R.id.selection_value_sensor);
        mSensorExpression.setOnClickListener(mSensorExpressionClickListener);
        mConstantExpression = findViewById(R.id.selection_value_constant);
        mConstantExpression.setOnClickListener(mConstantExpressionClickListener);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem) {
        mExpressionCreatorItem = expressionCreatorItem;
    }

    private View.OnClickListener mSensorExpressionClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), SensorExpressionCreatorActivity.class);
            intent.putExtra(Constants.EXTRA_EXPRESSION_CREATOR, mExpressionCreatorItem);
            ((Activity) getContext()).startActivityForResult(intent, ExpressionSelectionActivity.EXPRESSION_REQUEST_ID);
        }
    };

    private View.OnClickListener mConstantExpressionClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mExpressionCreatorItem.expressionInterface == null) {
                mExpressionCreatorItem.expressionInterface = new ConstantExpression();
            }
            Intent intent = new Intent(getContext(), ConstantExpressionCreatorActivity.class);
            intent.putExtra(Constants.EXTRA_EXPRESSION_CREATOR, mExpressionCreatorItem);
            ((Activity) getContext()).startActivityForResult(intent, ExpressionSelectionActivity.EXPRESSION_REQUEST_ID);
        }
    };

}
