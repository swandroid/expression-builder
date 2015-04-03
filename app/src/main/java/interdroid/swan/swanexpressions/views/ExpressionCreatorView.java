package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import interdroid.swan.ExpressionManager;
import interdroid.swan.SensorInfo;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.SwanExpressionsApp;
import interdroid.swan.swanexpressions.adapters.SensorSelectSpinnerAdapter;
import interdroid.swan.swanexpressions.enums.ExpressionType;
import interdroid.swan.swanexpressions.pojos.expressions.ConstantExpression;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.pojos.expressions.SensorExpression;

/**
 * Created by steven on 25/03/15.
 */
public class ExpressionCreatorView extends FrameLayout {

    private static final String TAG = ExpressionCreatorView.class.getSimpleName();

    private ViewGroup mRoot;
    private LinearLayout mLinearLayout;
    private Spinner mSpinner;
    //TODO: make a custom adapter (maybe with images to show what a specific type of expression is
    private ArrayAdapter<ExpressionType> mExpressionTypeAdapter;

    //TODO: maybe move to custom view or something
    //SENSOR EXPRESSION
    private static ArrayList<SensorInfo> mSensors;
    private Spinner mSensorSpinner;
    private Spinner mValuePathSpinner;
    private EditText mHistoryWindow;
    private Spinner mHistoryUnit;
    private Spinner mHistoryReductionMode;

    //CONSTANT EXPRESSION
    private EditText mConstantValue;
    private Spinner mConstantSpinner;

    //MATH EXPRESSION
    private Spinner mMathSpinner;

    //COMPARISON EXPRESSION
    private Spinner mComparisonSpinner;

    //LOGIC EXPRESSION
    private Spinner mLogicSpinner;

    private ExpressionCreatorItem mExpressionCreatorItem;

    public ExpressionCreatorView(Context context) {
        super(context);
        init();
    }

    public ExpressionCreatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpressionCreatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ExpressionCreatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = (ViewGroup) inflater.inflate(R.layout.view_expression_creator, this, true);
        mLinearLayout = (LinearLayout) mRoot.findViewById(R.id.expression_create_linearlayout);
        mSpinner = (Spinner) mRoot.findViewById(R.id.expression_create_spinner);
        mExpressionTypeAdapter = new ArrayAdapter<ExpressionType>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, ExpressionType.getExpressionTypes());
        mSpinner.setAdapter(mExpressionTypeAdapter);
        mSpinner.setOnItemSelectedListener(mOnExpressionTypeSelectedListener);

        inflateSensorExpression();
    }

    private AdapterView.OnItemSelectedListener mOnExpressionTypeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ExpressionType expressionType = mExpressionTypeAdapter.getItem(position);
            int expressionTypeId = expressionType.getId();
            removeCurrentExpression();
            mExpressionCreatorItem.expressionType = expressionType;
            if (expressionTypeId == ExpressionType.SENSOR_EXPRESSION.getId()) {
                inflateSensorExpression();
            } else if (expressionTypeId == ExpressionType.CONSTANT_EXPRESSION.getId()) {
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new ConstantExpression();
                inflateConstantExpression();
            } else if (expressionTypeId == ExpressionType.MATH_EXPRESSION.getId()) {
                inflateMathExpression();
            } else if (expressionTypeId == ExpressionType.COMPARISON_EXPRESSION.getId()) {
                inflateComparisonExpression();
            } else if (expressionTypeId == ExpressionType.LOGIC_EXPRESSION.getId()) {
                inflateLogicExpression();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void removeCurrentExpression() {
        if (mLinearLayout.getChildCount() > 2) {
            mLinearLayout.removeViewAt(2);
        }
    }

    private void inflateSensorExpression() {
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_sensor_expression, null);
        mLinearLayout.addView(viewGroup);
        mSensorSpinner = (Spinner) findViewById(R.id.sensor_expression_sensor_spinner);
        //TODO: try to make faster or save somewhere. In Application class doesn't looks to work
        mSensors = (ArrayList) ExpressionManager.getSensors(getContext());

        SensorSelectSpinnerAdapter adapter = new SensorSelectSpinnerAdapter(getContext(),
                R.layout.spinner_row, mSensors);
        mSensorSpinner.setAdapter(adapter);
        mSensorSpinner.setOnItemSelectedListener(mOnSensorSelectedListener);

        mValuePathSpinner = (Spinner) findViewById(R.id.sensor_expression_value_path_spinner);

        mHistoryWindow = (EditText) findViewById(R.id.sensor_expression_history_window_edittext);
        mHistoryUnit = (Spinner) findViewById(R.id.sensor_expression_history_unit_spinner);
        mHistoryReductionMode = (Spinner) findViewById(R.id.sensor_expression_history_reduction_spinner);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.time_units_values));
        mHistoryUnit.setAdapter(unitAdapter);

        ArrayAdapter<String> reductionModeAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.history_reduction_modes));
        mHistoryReductionMode.setAdapter(reductionModeAdapter);
    }

    private AdapterView.OnItemSelectedListener mOnSensorSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mSensors != null) {
                SensorInfo sensorInfo = mSensors.get(position);
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new SensorExpression();
                ((SensorExpression)mExpressionCreatorItem.expressionInterface).setSensor(sensorInfo.getEntity());
                ArrayList<String> valuePaths = sensorInfo.getValuePaths();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item, valuePaths);
                mValuePathSpinner.setAdapter(adapter);
                mValuePathSpinner.setOnItemSelectedListener(mOnValuePathSelectedListener);

                //sensorInfo.getIntent()

               /*for (int i = 0; i < valuePaths.size(); i++) {
                    Log.d(TAG, "ValuePath: " + i + " " + valuePaths.get(i));
                }*/
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener mOnValuePathSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((SensorExpression)mExpressionCreatorItem.expressionInterface).setValuePath(mValuePathSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void inflateConstantExpression() {
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_constant_expression, null);
        mLinearLayout.addView(viewGroup);

        mConstantValue = (EditText) findViewById(R.id.constant_expression_value_edittext);
        /*mConstantSpinner = (Spinner) findViewById(R.id.constant_expression_constant_spinner);

        ArrayAdapter<String> constantAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.constant_types));
        mConstantSpinner.setAdapter(constantAdapter);*/
    }

    private void inflateMathExpression() {
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_math_expression, null);
        mLinearLayout.addView(viewGroup);

        mMathSpinner = (Spinner) findViewById(R.id.math_expression_math_spinner);

        ArrayAdapter<String> mathAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.math_operators));
        mMathSpinner.setAdapter(mathAdapter);
    }

    private void inflateComparisonExpression() {
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_comparison_expression, null);
        mLinearLayout.addView(viewGroup);

        mComparisonSpinner = (Spinner) findViewById(R.id.comparison_expression_comparison_spinner);

        ArrayAdapter<String> logicAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.comparison_operators));
        mComparisonSpinner.setAdapter(logicAdapter);
    }

    private void inflateLogicExpression() {
        LayoutInflater inflater = LayoutInflater
                .from(getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_logic_expression, null);
        mLinearLayout.addView(viewGroup);

        mLogicSpinner = (Spinner) findViewById(R.id.logic_expression_logic_spinner);

        ArrayAdapter<String> logicAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.logic_operators));
        mLogicSpinner.setAdapter(logicAdapter);
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem) {
        mExpressionCreatorItem = expressionCreatorItem;
        //TODO: set items according to this;
    }

   /* public ExpressionCreatorItem getExpressionCreatorItem() {
        ExpressionType expressionType = (ExpressionType) mSpinner.getSelectedItem();
        int expressionTypeId = expressionType.getId();
        removeCurrentExpression();
        if (expressionTypeId == ExpressionType.SENSOR_EXPRESSION.getId()) {
            return updateSensorExpression();
        } else if (expressionTypeId == ExpressionType.CONSTANT_EXPRESSION.getId()) {
            //return getConstantExpression();
        } else if (expressionTypeId == ExpressionType.MATH_EXPRESSION.getId()) {
            //return getMathExpression();
        } else if (expressionTypeId == ExpressionType.COMPARISON_EXPRESSION.getId()) {
            //return getComparisonExpression();
        } else if (expressionTypeId == ExpressionType.LOGIC_EXPRESSION.getId()) {
            //return getLogicExpression();
        }
        return null;
    }



    private SensorExpression updateSensorExpression() {
        SensorInfo sensorInfo = (SensorInfo) mSensorSpinner.getSelectedItem();
        sensorExpression.setSensor(sensorInfo.getEntity());

        sensorExpression.setValuePath(mValuePathSpinner.getSelectedItem().toString());

        sensorExpression.setHistoryWindowAndUnit(Integer.parseInt(mHistoryWindow.getText().toString()),
                mHistoryUnit.getSelectedItem().toString());

        sensorExpression.setHistoryReductionMode(mHistoryReductionMode.getSelectedItem().toString());

        return sensorExpression;
    }*/
}
