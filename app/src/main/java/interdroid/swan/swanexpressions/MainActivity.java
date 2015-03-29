package interdroid.swan.swanexpressions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import interdroid.swan.swanexpressions.activities.BaseActivity;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int EXPRESSION_BUILDER_REQUEST = 1234;

    private Spinner mSensorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_ab_drawer);

        getViews();
        //To start the expression builder from swan
        //startExpressionBuilder();

        //Log.d(TAG, "size: " + ExpressionManager.getSensors(MainActivity.this).size());
    }

    private void startExpressionBuilder() {
        Intent sendIntent = new Intent();
        sendIntent.setAction("interdroid.swan.ui.BUILD_EXPRESSION");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(sendIntent, EXPRESSION_BUILDER_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXPRESSION_BUILDER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String expression = data.getStringExtra("Expression");
                Toast.makeText(getApplicationContext(), expression, Toast.LENGTH_LONG).show();
                Log.d(TAG, "expression result: " + expression);
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
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
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_expression_builder:
                startExpressionBuilder();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
