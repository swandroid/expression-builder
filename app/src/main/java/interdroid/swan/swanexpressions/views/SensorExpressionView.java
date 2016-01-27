package interdroid.swan.swanexpressions.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionInterface;

/**
 * Created by steven on 19/01/16.
 */
public class SensorExpressionView extends LinearLayout implements ExpressionInterface {

    private ImageView mSensorImage;

    public SensorExpressionView(Context context) {
        super(context);
    }

    public SensorExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SensorExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SensorExpressionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mSensorImage = (ImageView) findViewById(R.id.sensor_image);
    }

    @Override
    public String getExpression() {
        return null;
    }

    @Override
    public void parsePartOfExpression(String partOfExpression) {

    }
}
