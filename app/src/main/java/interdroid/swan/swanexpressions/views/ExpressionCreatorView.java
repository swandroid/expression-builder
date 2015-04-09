package interdroid.swan.swanexpressions.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

import interdroid.swan.SensorInfo;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.SwanExpressionsApp;
import interdroid.swan.swanexpressions.adapters.SensorSelectSpinnerAdapter;
import interdroid.swan.swanexpressions.enums.ExpressionType;
import interdroid.swan.swanexpressions.pojos.expressions.ComparisonExpression;
import interdroid.swan.swanexpressions.pojos.expressions.ConstantExpression;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.pojos.expressions.LogicExpression;
import interdroid.swan.swanexpressions.pojos.expressions.MathExpression;
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
    }

    public void setExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem) {
        mExpressionCreatorItem = expressionCreatorItem;

        ExpressionType[] expressionTypes = getPossibleExpressionTypes();
        mExpressionTypeAdapter = new ArrayAdapter<ExpressionType>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, expressionTypes);
        mSpinner.setAdapter(mExpressionTypeAdapter);
        mSpinner.setOnItemSelectedListener(mOnExpressionTypeSelectedListener);

        mSpinner.setSelection(getExpressionSelectionToSet(expressionTypes, expressionCreatorItem.expressionType));
        //inflateCorrectExpression(expressionCreatorItem.expressionType);
        //TODO: set items according to this;
    }

    private ExpressionType[] getPossibleExpressionTypes() {
        int expressionTypeId = mExpressionCreatorItem.possibleExpressionType.getId();
        if (expressionTypeId == ExpressionType.VALUE_EXPRESSION.getId()) {
            return ExpressionType.getValueExpressionTypes();
        } else if (expressionTypeId == ExpressionType.VALUE_OPERATOR_EXPRESSION.getId()) {
            return ExpressionType.getValueOperatorExpressionTypes();
        } else if (expressionTypeId == ExpressionType.TRI_MATH_OPERATOR_EXPRESSION.getId()) {
            return ExpressionType.getTriMathExpressionTypes();
        } else {
            return ExpressionType.getExpressionTypes();
        }
    }

    private int getExpressionSelectionToSet(ExpressionType[] expressionTypes, ExpressionType expressionType) {
        int expressionId = expressionType.getId();
        for (int i = 0; i < expressionTypes.length; i++) {
            if (expressionTypes[i].getId() == expressionId) {
                return i;
            }
        }
        return 0;
    }

    private AdapterView.OnItemSelectedListener mOnExpressionTypeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ExpressionType expressionType = mExpressionTypeAdapter.getItem(position);
            inflateCorrectExpression(expressionType);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void inflateCorrectExpression(ExpressionType expressionType) {
        int expressionTypeId = expressionType.getId();
        removeCurrentExpression();
        if (expressionTypeId == ExpressionType.SENSOR_EXPRESSION.getId()) {
            if (mExpressionCreatorItem.expressionType.getId() != expressionTypeId
                    || mExpressionCreatorItem.expressionInterface == null) {
                mExpressionCreatorItem.expressionType = expressionType;
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new SensorExpression();
            }
            inflateSensorExpression();
        } else if (expressionTypeId == ExpressionType.CONSTANT_EXPRESSION.getId()) {
            if (mExpressionCreatorItem.expressionType.getId() != expressionTypeId
                    || mExpressionCreatorItem.expressionInterface == null) {
                mExpressionCreatorItem.expressionType = expressionType;
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new ConstantExpression();
            }
            inflateConstantExpression();
        } else if (expressionTypeId == ExpressionType.MATH_EXPRESSION.getId()) {
            mExpressionCreatorItem.expressionType = expressionType;
            mExpressionCreatorItem.expressionInterface = null;
            mExpressionCreatorItem.expressionInterface = new MathExpression();
            inflateMathExpression();
        } else if (expressionTypeId == ExpressionType.COMPARISON_EXPRESSION.getId()) {
            mExpressionCreatorItem.expressionType = expressionType;
            mExpressionCreatorItem.expressionInterface = null;
            mExpressionCreatorItem.expressionInterface = new ComparisonExpression();
            inflateComparisonExpression();
        } else if (expressionTypeId == ExpressionType.LOGIC_EXPRESSION.getId()) {
            mExpressionCreatorItem.expressionType = expressionType;
            mExpressionCreatorItem.expressionInterface = null;
            mExpressionCreatorItem.expressionInterface = new LogicExpression();
            inflateLogicExpression();
        }
    }

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
        mSensors = SwanExpressionsApp.getInstance().getSwanSensors();

        SensorSelectSpinnerAdapter adapter = new SensorSelectSpinnerAdapter(getContext(),
                R.layout.spinner_row, mSensors);
        mSensorSpinner.setAdapter(adapter);
        mSensorSpinner.setOnItemSelectedListener(mOnSensorSelectedListener);
        mSensorSpinner.setSelection(getSensorSelectionToSet());

        mValuePathSpinner = (Spinner) findViewById(R.id.sensor_expression_value_path_spinner);
        mValuePathSpinner.setOnItemSelectedListener(mOnValuePathSelectedListener);

        mHistoryWindow = (EditText) findViewById(R.id.sensor_expression_history_window_edittext);
        mHistoryWindow.addTextChangedListener(mOnHistoryWindowChangeListener);

        mHistoryUnit = (Spinner) findViewById(R.id.sensor_expression_history_unit_spinner);
        mHistoryUnit.setOnItemSelectedListener(mOnHistoryUnitChangeListener);

        mHistoryReductionMode = (Spinner) findViewById(R.id.sensor_expression_history_reduction_spinner);
        mHistoryReductionMode.setOnItemSelectedListener(mOnHistoryReductionModeChangeListener);

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
                if (mExpressionCreatorItem.expressionInterface != null
                        && !sensorInfo.getEntity().equals(((SensorExpression)mExpressionCreatorItem.expressionInterface).getSensor())) {
                    mExpressionCreatorItem.expressionInterface = null;
                    mExpressionCreatorItem.expressionInterface = new SensorExpression();
                    ((SensorExpression)mExpressionCreatorItem.expressionInterface).setSensor(sensorInfo.getEntity());
                }
                ArrayList<String> valuePaths = sensorInfo.getValuePaths();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, valuePaths);
                mValuePathSpinner.setAdapter(adapter);

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

    private int getSensorSelectionToSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String sensor = ((SensorExpression)mExpressionCreatorItem.expressionInterface).getSensor();
        if (sensor == null || sensor.equals("")) {
            return 0;
        }
        for (int i = 0; i < mSensors.size(); i++) {
            if (mSensors.get(i).getEntity().equals(sensor)) {
                return i;
            }
        }
        return 0;
    }

    private AdapterView.OnItemSelectedListener mOnValuePathSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mExpressionCreatorItem.expressionInterface != null) {
                ((SensorExpression)mExpressionCreatorItem.expressionInterface).setValuePath(mValuePathSpinner.getSelectedItem().toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private TextWatcher mOnHistoryWindowChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() < 1) {
                ((SensorExpression)mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(0, "");
            } else {
                ((SensorExpression)mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(
                        Integer.parseInt(s.toString()),
                        mHistoryUnit.getSelectedItem().toString());
            }
        }
    };

    private AdapterView.OnItemSelectedListener mOnHistoryReductionModeChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mExpressionCreatorItem.expressionInterface != null) {
                ((SensorExpression)mExpressionCreatorItem.expressionInterface).setHistoryReductionMode(
                        mHistoryReductionMode.getSelectedItem().toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener mOnHistoryUnitChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String window = mHistoryWindow.getText().toString();
            if (mExpressionCreatorItem.expressionInterface != null) {
                if (window.length() > 0) {
                    ((SensorExpression)mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(
                            Integer.parseInt(window),
                            mHistoryUnit.getSelectedItem().toString());
                } else {
                    ((SensorExpression)mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(0, "");
                }
            }
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
        mConstantValue.addTextChangedListener(mOnConstantChangeListener);

        if (mExpressionCreatorItem.expressionInterface != null) {
            mConstantValue.setText(((ConstantExpression)mExpressionCreatorItem.expressionInterface).getConstant());
        }

    }

    private TextWatcher mOnConstantChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                ((ConstantExpression)mExpressionCreatorItem.expressionInterface).setConstant(s.toString());
            } else {
                ((ConstantExpression)mExpressionCreatorItem.expressionInterface).setConstant("");
            }

        }
    };

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
        mMathSpinner.setOnItemSelectedListener(mOnMathOperatorChangeListener);
    }

    private AdapterView.OnItemSelectedListener mOnMathOperatorChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((MathExpression)mExpressionCreatorItem.expressionInterface).setOperator(
                    mMathSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
        mComparisonSpinner.setOnItemSelectedListener(mOnComparisonOperatorChangeListener);
    }

    private AdapterView.OnItemSelectedListener mOnComparisonOperatorChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((ComparisonExpression)mExpressionCreatorItem.expressionInterface).setOperator(
                    mComparisonSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
        mLogicSpinner.setOnItemSelectedListener(mOnLogicOperatorChangeListener);
    }

    private AdapterView.OnItemSelectedListener mOnLogicOperatorChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String operator = getContext().getResources().getStringArray(
                    R.array.logic_operators_values)[mLogicSpinner.getSelectedItemPosition()];
            ((LogicExpression)mExpressionCreatorItem.expressionInterface).setOperator(operator);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
