package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import interdroid.swan.ExpressionManager;
import interdroid.swan.SensorInfo;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.adapters.SensorSelectSpinnerAdapter;

/**
 * Created by steven on 25/03/15.
 */
public class ExpressionCreatorView extends FrameLayout {

    private static final String TAG = ExpressionCreatorView.class.getSimpleName();

    private ViewGroup mRoot;
    private LinearLayout mLinearLayout;
    private Spinner mSpinner;

    //TODO: move to custom view or something
    private ArrayList<SensorInfo> mSensors;
    private Spinner mSensorSpinner;


    public ExpressionCreatorView(Context context) {
        super(context);
        Log.d(TAG, "constructor 1");
        init();
    }

    public ExpressionCreatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "constructor 2");
        init();
    }

    public ExpressionCreatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "constructor 3");
        init();
    }

    public ExpressionCreatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "constructor 4");
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = (ViewGroup) inflater.inflate(R.layout.view_expression_creator, this, true);
        mLinearLayout = (LinearLayout) mRoot.findViewById(R.id.expression_create_linearlayout);
        mSpinner = (Spinner) mRoot.findViewById(R.id.expression_create_spinner);

        inflateSensorExpression();
    }

    private void inflateSensorExpression() {
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_sensor_expression, null);
        mLinearLayout.addView(viewGroup);
        mSensorSpinner = (Spinner) findViewById(R.id.sensor_expression_sensor_spinner);
        SensorSelectSpinnerAdapter adapter = new SensorSelectSpinnerAdapter(getContext(),
                R.layout.spinner_row, ExpressionManager.getSensors(getContext()));
        mSensorSpinner.setAdapter(adapter);
        mSensorSpinner.setOnItemSelectedListener(mOnSensorSelectedListener);
    }

    private AdapterView.OnItemSelectedListener mOnSensorSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };




}
