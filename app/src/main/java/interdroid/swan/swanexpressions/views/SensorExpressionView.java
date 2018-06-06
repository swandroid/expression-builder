package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.ConfigurationItem;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.pojos.expressions.SensorExpression;

/**
 * Created by steven on 19/01/16.
 */
public class SensorExpressionView extends FrameLayout implements View.OnClickListener {

    private TextView mSensorTitle;
    private TextView mValuePath;
    private LinearLayout mConfigurationContainer;
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
        mSensorTitle = (TextView) findViewById(R.id.sensor_title);
        mValuePath = (TextView) findViewById(R.id.sensor_value_path);
        mConfigurationContainer = (LinearLayout) findViewById(R.id.sensor_configuration_container);
        mSensorExpression = (TextView) findViewById(R.id.sensor_expression);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem,
                                         OnSensorExpressionClickListener listener) {
        mExpressionCreatorItem = expressionCreatorItem;
        mListener = listener;
        SensorExpression sensorExpression = (SensorExpression) expressionCreatorItem.expressionInterface;
        mSensorTitle.setText(sensorExpression.getSensor());
        mValuePath.setText(sensorExpression.getValuePath());
        List<ConfigurationItem> configurtionItems = sensorExpression.getConfigurationList();
        mConfigurationContainer.removeAllViews();
        for (int i = 0; i < configurtionItems.size(); i++) {
            ConfigurationItem configurationItem = configurtionItems.get(i);
            if (!configurationItem.key.contains("configuration_full")) {
                TextView textView = new TextView(mConfigurationContainer.getContext());
                textView.setText(configurationItem.key + ": " + configurationItem.value);
                mConfigurationContainer.addView(textView);
            } else {
                //TODO: improve for RSS
                String[] configurationFullParts = configurationItem.value.split("name");
                String configurationName = configurationFullParts[1].replace("\":\"", "");
                configurationName = configurationName.substring(0, configurationName.indexOf('"'));
                if (configurationItem.key.equals("rss_configuration_full")) {
                    String[] configurationFullPartsRss = configurationItem.value.split("word");
                    String valueName = configurationFullPartsRss[1].replace("\":\"", "").replace("\"}", "");
                    TextView textView = new TextView(mConfigurationContainer.getContext());
                    textView.setText(configurationName + ", " + valueName);
                    mConfigurationContainer.addView(textView);
                } else {
                    String valueName = configurationFullParts[configurationFullParts.length - 1].replace("\":\"", "");
                    valueName = valueName.substring(0, valueName.indexOf('"'));
                    TextView textView = new TextView(mConfigurationContainer.getContext());
                    textView.setText(configurationName + ", " + valueName);
                    mConfigurationContainer.addView(textView);
                }

            }

        }
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