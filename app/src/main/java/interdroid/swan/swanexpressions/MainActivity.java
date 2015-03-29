package interdroid.swan.swanexpressions;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import interdroid.swan.ExpressionListener;
import interdroid.swan.ExpressionManager;
import interdroid.swan.SwanException;
import interdroid.swan.TriStateExpressionListener;
import interdroid.swan.ValueExpressionListener;
import interdroid.swan.swanexpressions.activities.BaseActivity;
import interdroid.swan.swanexpressions.adapters.SensorSelectSpinnerAdapter;
import interdroid.swan.swansong.ExpressionFactory;
import interdroid.swan.swansong.ExpressionParseException;
import interdroid.swan.swansong.TimestampedValue;
import interdroid.swan.swansong.TriState;
import interdroid.swan.swansong.TriStateExpression;
import interdroid.swan.swansong.ValueExpression;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Spinner mSensorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_ab_drawer);

        getViews();

        //Log.d(TAG, "size: " + ExpressionManager.getSensors(MainActivity.this).size());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void getViews() {
        /*mSensorSpinner = (Spinner) findViewById(R.id.main_sensor_spinner);
        SensorSelectSpinnerAdapter adapter = new SensorSelectSpinnerAdapter(this,
                R.layout.spinner_row, ExpressionManager.getSensors(MainActivity.this));
        mSensorSpinner.setAdapter(adapter);*/
        /*try {
            ExpressionManager.registerValueExpression(this, "1234", (ValueExpression) ExpressionFactory.parse("self@movement:x{MAX, 1000}"), new ValueExpressionListener() {
                @Override
                public void onNewValues(String s, TimestampedValue[] timestampedValues) {
                    Log.d(TAG, "String: " + s + "\nTimeStampedValues: " + timestampedValues.toString());
                }
            });
        } catch (SwanException e) {
            e.printStackTrace();
        } catch (ExpressionParseException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
