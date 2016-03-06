package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;

import interdroid.swan.swanexpressions.R;

/**
 * Created by steven on 19/01/16.
 */
public class SensorExpressionView extends CardView {

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

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mSensorImage = (ImageView) findViewById(R.id.sensor_image);
    }
}