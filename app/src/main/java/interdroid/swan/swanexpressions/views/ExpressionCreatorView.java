package interdroid.swan.swanexpressions.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
import java.util.HashSet;
import java.util.Set;

import interdroid.swan.SensorInfo;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.SwanExpressionsApp;
import interdroid.swan.swanexpressions.adapters.ExpressionTypeSpinnerAdapter;
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
    private ExpressionTypeSpinnerAdapter mExpressionTypeAdapter;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
        mExpressionTypeAdapter = new ExpressionTypeSpinnerAdapter(getContext(),
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
            if (mExpressionCreatorItem.expressionType.getId() != expressionTypeId
                    || mExpressionCreatorItem.expressionInterface == null) {
                mExpressionCreatorItem.expressionType = expressionType;
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new MathExpression();
            }
            inflateMathExpression();
        } else if (expressionTypeId == ExpressionType.COMPARISON_EXPRESSION.getId()) {
            if (mExpressionCreatorItem.expressionType.getId() != expressionTypeId
                || mExpressionCreatorItem.expressionInterface == null) {
                mExpressionCreatorItem.expressionType = expressionType;
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new ComparisonExpression();
            }
            inflateComparisonExpression();
        } else if (expressionTypeId == ExpressionType.LOGIC_EXPRESSION.getId()) {
            if (mExpressionCreatorItem.expressionType.getId() != expressionTypeId
                    || mExpressionCreatorItem.expressionInterface == null) {
                mExpressionCreatorItem.expressionType = expressionType;
                mExpressionCreatorItem.expressionInterface = null;
                mExpressionCreatorItem.expressionInterface = new LogicExpression();
            }
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
                R.layout.item_sensor_spinner, mSensors);
        mSensorSpinner.setAdapter(adapter);
        mSensorSpinner.setOnItemSelectedListener(mOnSensorSelectedListener);
        mSensorSpinner.setSelection(getSensorSelectionToSet());

        mValuePathSpinner = (Spinner) findViewById(R.id.sensor_expression_value_path_spinner);
        mValuePathSpinner.setOnItemSelectedListener(mOnValuePathSelectedListener);

        int historySelectionToSet = getHistoryUnitSelectionToSet();

        mHistoryWindow = (EditText) findViewById(R.id.sensor_expression_history_window_edittext);
        mHistoryWindow.addTextChangedListener(mOnHistoryWindowChangeListener);
        mHistoryWindow.setText(getHistoryWindowToSet());

        mHistoryUnit = (Spinner) findViewById(R.id.sensor_expression_history_unit_spinner);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.time_units_values));
        mHistoryUnit.setAdapter(unitAdapter);
        mHistoryUnit.setOnItemSelectedListener(mOnHistoryUnitChangeListener);
        mHistoryUnit.setSelection(historySelectionToSet);

        mHistoryReductionMode = (Spinner) findViewById(R.id.sensor_expression_history_reduction_spinner);
        mHistoryReductionMode.setOnItemSelectedListener(mOnHistoryReductionModeChangeListener);

        ArrayAdapter<String> reductionModeAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getContext().getResources().getStringArray(R.array.history_reduction_modes));
        mHistoryReductionMode.setAdapter(reductionModeAdapter);
        mHistoryReductionMode.setSelection(getHistoryReductionModeSelectionToSet());
    }

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

    private int getValuePathSelectionToSet() {
        String valuePath = ((SensorExpression)mExpressionCreatorItem.expressionInterface).getValuePath();
        if (valuePath == null || valuePath.equals("")) {
            return 0;
        }
        SensorInfo sensorInfo = mSensors.get(mSensorSpinner.getSelectedItemPosition());
        ArrayList<String> valuePaths = sensorInfo.getValuePaths();
        for (int i = 0; i < valuePaths.size(); i++) {
            if (valuePaths.get(i).equals(valuePath)) {
                return i;
            }
        }
        return 0;
    }

    private String getHistoryWindowToSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return "0";
        }
        int historyWindow = ((SensorExpression)mExpressionCreatorItem.expressionInterface).getHistoryWindow();
        if (historyWindow == 0) {
            return "0";
        }
        return "" + historyWindow;
    }

    private int getHistoryUnitSelectionToSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String historyUnit = ((SensorExpression)mExpressionCreatorItem.expressionInterface).getHistoryUnit();
        if (historyUnit == null || historyUnit.equals("")) {
            return 0;
        }
        String[] historyUnits = getContext().getResources().getStringArray(R.array.time_units_values);

        for (int i = 0; i < historyUnits.length; i++) {
            if (historyUnits[i].equals(historyUnit)) {
                return i;
            }
        }
        return 0;
    }

    private int getHistoryReductionModeSelectionToSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String historyReductionMode = ((SensorExpression)mExpressionCreatorItem.expressionInterface).getHistoryReductionMode();
        if (historyReductionMode == null || historyReductionMode.equals("")) {
            return 0;
        }
        String[] historyReductionModes = getContext().getResources().getStringArray(R.array.history_reduction_modes);

        for (int i = 0; i < historyReductionModes.length; i++) {
            if (historyReductionModes[i].equals(historyReductionMode)) {
                return i;
            }
        }
        return 0;
    }

    private AdapterView.OnItemSelectedListener mOnSensorSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mSensors != null) {
                SensorInfo sensorInfo = mSensors.get(position);
                if (mExpressionCreatorItem.expressionInterface != null
                        && !sensorInfo.getEntity().equals(((SensorExpression) mExpressionCreatorItem.expressionInterface).getSensor())) {
                    ((SensorExpression) mExpressionCreatorItem.expressionInterface).setSensor(sensorInfo.getEntity());
                    ((SensorExpression) mExpressionCreatorItem.expressionInterface).setValuePath("");
                }
                ArrayList<String> valuePaths = sensorInfo.getValuePaths();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, valuePaths);
                mValuePathSpinner.setAdapter(adapter);
                mValuePathSpinner.setSelection(getValuePathSelectionToSet());

                Set<String> keys = new HashSet<String>();
                keys.addAll(sensorInfo.getConfiguration().keySet());
                if (keys.size() > 0) {
                    //TODO: add a button to show there are extra options to set
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
                if (mHistoryUnit != null) {
                    ((SensorExpression)mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(
                            Integer.parseInt(s.toString()),
                            mHistoryUnit.getSelectedItem().toString());
                }
            }
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
        mMathSpinner.setSelection(getMathSelectiontoSet());
    }

    private int getMathSelectiontoSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String mathOperator = ((MathExpression)mExpressionCreatorItem.expressionInterface).getOperator();
        if (mathOperator == null || mathOperator.equals("")) {
            return 0;
        }
        String[] mathOperators = getContext().getResources().getStringArray(R.array.math_operators);
        for (int i = 0; i < mathOperators.length; i++) {
            if (mathOperators[i].equals(mathOperator)) {
                return i;
            }
        }
        return 0;
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
        mComparisonSpinner.setSelection(getComparisonSelectiontoSet());
    }

    private int getComparisonSelectiontoSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String comparisonOperator = ((ComparisonExpression)mExpressionCreatorItem.expressionInterface).getOperator();
        if (comparisonOperator == null || comparisonOperator.equals("")) {
            return 0;
        }
        String[] comparisonOperators = getContext().getResources().getStringArray(R.array.comparison_operators);
        for (int i = 0; i < comparisonOperators.length; i++) {
            if (comparisonOperators[i].equals(comparisonOperator)) {
                return i;
            }
        }
        return 0;
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
        mLogicSpinner.setSelection(getLogicSelectiontoSet());
    }

    private int getLogicSelectiontoSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String logicOperator = ((LogicExpression)mExpressionCreatorItem.expressionInterface).getOperator();
        if (logicOperator == null || logicOperator.equals("")) {
            return 0;
        }
        String[] logicOperators = getContext().getResources().getStringArray(R.array.logic_operators_values);
        for (int i = 0; i < logicOperators.length; i++) {
            if (logicOperators[i].equals(logicOperator)) {
                return i;
            }
        }
        return 0;
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
