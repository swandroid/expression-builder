package interdroid.swan.swanexpressions.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import interdroid.swan.SensorInfo;
import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.SwanExpressionsApp;
import interdroid.swan.swanexpressions.adapters.SensorSelectSpinnerAdapter;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.pojos.expressions.SensorExpression;

/**
 * Created by steven on 27/01/16.
 */
public class SensorExpressionCreatorActivity extends BaseActivity {


    private Spinner mSensorSpinner;
    private Spinner mValuePathSpinner;
    private EditText mHistoryWindowInput;
    private Spinner mHitoryWindowUnitSpinner;
    private Spinner mHistoryWindowReductionSpinner;
    private View mSplitView;
    private LinearLayout mSpecificContainer;

    private ExpressionCreatorItem mExpressionCreatorItem;

    private ArrayList<SensorInfo> mSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        mExpressionCreatorItem = getIntent().getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);

        getViews();
        getData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sensor_expression_creator;
    }

    private void getViews() {
        mSensorSpinner = (Spinner) findViewById(R.id.sensor_expression_sensor_spinner);
        mValuePathSpinner = (Spinner) findViewById(R.id.sensor_expression_value_path_spinner);
        mHistoryWindowInput = (EditText) findViewById(R.id.sensor_expression_history_window_edittext);
        mHitoryWindowUnitSpinner = (Spinner) findViewById(R.id.sensor_expression_history_unit_spinner);
        mHistoryWindowReductionSpinner = (Spinner) findViewById(R.id.sensor_expression_history_reduction_spinner);
        mSplitView = findViewById(R.id.sensor_expression_split_view);
        mSpecificContainer = (LinearLayout) findViewById(R.id.sensor_expression_specific_container);
    }

    private void getData() {
        mSensors = SwanExpressionsApp.getInstance().getSwanSensors();

        SensorSelectSpinnerAdapter adapter = new SensorSelectSpinnerAdapter(this,
                R.layout.item_sensor_spinner, mSensors);
        mSensorSpinner.setAdapter(adapter);
        mSensorSpinner.setOnItemSelectedListener(mOnSensorSelectedListener);
        mSensorSpinner.setSelection(getSensorSelectionToSet());

        mValuePathSpinner.setOnItemSelectedListener(mOnValuePathSelectedListener);

//        int historySelectionToSet = getHistoryUnitSelectionToSet();

        mHistoryWindowInput.addTextChangedListener(mOnHistoryWindowChangeListener);
        mHistoryWindowInput.setText(getHistoryWindowToSet());

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.time_units_values));
        mHitoryWindowUnitSpinner.setAdapter(unitAdapter);
        mHitoryWindowUnitSpinner.setOnItemSelectedListener(mOnHistoryUnitChangeListener);
        mHitoryWindowUnitSpinner.setSelection(getHistoryUnitSelectionToSet());

        mHistoryWindowReductionSpinner.setOnItemSelectedListener(mOnHistoryReductionModeChangeListener);

        ArrayAdapter<String> reductionModeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.history_reduction_modes));
        mHistoryWindowReductionSpinner.setAdapter(reductionModeAdapter);
        mHistoryWindowReductionSpinner.setSelection(getHistoryReductionModeSelectionToSet());
    }

    private int getSensorSelectionToSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String sensor = ((SensorExpression) mExpressionCreatorItem.expressionInterface).getSensor();
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
        String valuePath = ((SensorExpression) mExpressionCreatorItem.expressionInterface).getValuePath();
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
        int historyWindow = ((SensorExpression) mExpressionCreatorItem.expressionInterface).getHistoryWindow();
        if (historyWindow == 0) {
            return "0";
        }
        return "" + historyWindow;
    }

    private int getHistoryUnitSelectionToSet() {
        if (mExpressionCreatorItem.expressionInterface == null) {
            return 0;
        }
        String historyUnit = ((SensorExpression) mExpressionCreatorItem.expressionInterface).getHistoryUnit();
        if (historyUnit == null || historyUnit.equals("")) {
            return 0;
        }
        String[] historyUnits = getResources().getStringArray(R.array.time_units_values);

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
        String historyReductionMode = ((SensorExpression) mExpressionCreatorItem.expressionInterface).getHistoryReductionMode();
        if (historyReductionMode == null || historyReductionMode.equals("")) {
            return 0;
        }
        String[] historyReductionModes = getResources().getStringArray(R.array.history_reduction_modes);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SensorExpressionCreatorActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, valuePaths);
                mValuePathSpinner.setAdapter(adapter);
                mValuePathSpinner.setSelection(getValuePathSelectionToSet());

                Set<String> keys = new HashSet<String>();
                keys.addAll(sensorInfo.getConfiguration().keySet());
                mSpecificContainer.removeAllViews();
                if (keys.size() > 0) {
                    addViewsForKeys(keys, sensorInfo.getConfiguration());
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void addViewsForKeys(Set<String> keys, Bundle bundle) {
        for (String key: keys) {
            if (key.contains("configuration_full")) {
                //TODO: add a button for further configuration
            } else {
                TextInputLayout textInputLayout = (TextInputLayout) getLayoutInflater()
                        .inflate(R.layout.sensor_specific_item, mSpecificContainer, false);
                EditText editText = (EditText) textInputLayout.findViewById(R.id.sensor_specific_edittext);
                editText.setText(bundle.get(key).toString());
                //TODO: possible set correct editText value
                textInputLayout.setHint(key);
                mSpecificContainer.addView(textInputLayout);
            }
        }
    }


    private AdapterView.OnItemSelectedListener mOnValuePathSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mExpressionCreatorItem.expressionInterface != null) {
                ((SensorExpression) mExpressionCreatorItem.expressionInterface).setValuePath(mValuePathSpinner.getSelectedItem().toString());
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
                ((SensorExpression) mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(0, "");
            } else {
                if (mHitoryWindowUnitSpinner != null && mHitoryWindowUnitSpinner.getSelectedItemPosition() >= 0) {
                    ((SensorExpression) mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(
                            Integer.parseInt(s.toString()),
                            mHitoryWindowUnitSpinner.getSelectedItem().toString());
                }
            }
        }
    };


    private AdapterView.OnItemSelectedListener mOnHistoryUnitChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String window = mHistoryWindowInput.getText().toString();
            if (mExpressionCreatorItem.expressionInterface != null) {
                if (window.length() > 0) {
                    ((SensorExpression) mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(
                            Integer.parseInt(window),
                            mHitoryWindowUnitSpinner.getSelectedItem().toString());
                } else {
                    ((SensorExpression) mExpressionCreatorItem.expressionInterface).setHistoryWindowAndUnit(0, "");
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
                ((SensorExpression) mExpressionCreatorItem.expressionInterface).setHistoryReductionMode(
                        mHistoryWindowReductionSpinner.getSelectedItem().toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Constants.EXTRA_EXPRESSION_CREATOR, mExpressionCreatorItem);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
