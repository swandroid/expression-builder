package interdroid.swan.swanexpressions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import interdroid.swan.ExpressionListener;
import interdroid.swan.ExpressionManager;
import interdroid.swan.SwanException;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.adapters.ExpressionListAdapter;
import interdroid.swan.swanexpressions.pojos.ExpressionItem;
import interdroid.swan.swansong.ExpressionFactory;
import interdroid.swan.swansong.ExpressionParseException;
import interdroid.swan.swansong.TimestampedValue;
import interdroid.swan.swansong.TriState;
import interdroid.swan.swansong.TriStateExpression;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int EXPRESSION_BUILDER_OLD_REQUEST = 1234;
    private static final int EXPRESSION_BUILDER_REQUEST = 23847;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpressionListAdapter mAdapter;

    private FloatingActionButton mAddButton;

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
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void getViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.main_activity_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ExpressionListAdapter(mOnExpressionCLickListener);
        mRecyclerView.setAdapter(mAdapter);

        mAddButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddButton.setOnClickListener(mOnAddClickListener);

        //registerExpression(null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXPRESSION_BUILDER_OLD_REQUEST) {
            if (resultCode == RESULT_OK) {
                String expression = data.getStringExtra("Expression");
                Toast.makeText(getApplicationContext(), expression, Toast.LENGTH_LONG).show();
                Log.d(TAG, "expression result: " + expression);
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        } else if (requestCode == EXPRESSION_BUILDER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(BuilderActivity.KEY_EXTRA_NAME);
                String expression = data.getStringExtra(BuilderActivity.KEY_EXTRA_EXPRESSION);
                Toast.makeText(getApplicationContext(), name + ": " + expression, Toast.LENGTH_LONG).show();
                Log.d(TAG, name + ": " + expression);

                ExpressionItem expressionItem = new ExpressionItem(name, expression);
                mAdapter.addExpression(expressionItem);
                registerExpression(expressionItem);
            }
        }
    }

    private void registerExpression(ExpressionItem expressionItem) {
        try {
            ExpressionManager.registerExpression(this, expressionItem.getStringId(), ExpressionFactory.parse(expressionItem.expression), mExpressionListener);
        } catch (ExpressionParseException e) {
            e.printStackTrace();
        } catch (SwanException e) {
            e.printStackTrace();
        }

        /*try {
            Log.d(TAG, "register");
            ExpressionManager.registerExpression(this, "test3", ExpressionFactory.parse("2 * self@movement:x{MAX, 1000} + self@movement:y{MIN,1000}"), mExpressionListener);
        } catch (ExpressionParseException e) {
            Log.e(TAG, "ExpressionParseException");
            e.printStackTrace();
        } catch (SwanException e) {
            Log.e(TAG, "SwanException");
            e.printStackTrace();
        }*/
    }

    private ExpressionListener mExpressionListener = new ExpressionListener() {
        @Override
        public void onNewState(String stringId, long l, TriState triState) {
            Log.d(TAG, "newState: " + triState.toString());
            mAdapter.updateExpression(stringId, triState.toString());
        }

        @Override
        public void onNewValues(String stringId, TimestampedValue[] timestampedValues) {
            if (timestampedValues != null && timestampedValues.length > 0) {
                Log.d(TAG, "newValues: " + timestampedValues[0].getValue().toString());
                mAdapter.updateExpression(stringId, timestampedValues[0].getValue().toString());
            }
        }
    };

    private ExpressionListAdapter.OnExpressionClickListener mOnExpressionCLickListener = new ExpressionListAdapter.OnExpressionClickListener() {

        @Override
        public void onExpressionClicked(ExpressionItem expression) {

        }
    };

    private View.OnClickListener mOnAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, BuilderActivity.class);
            startActivityForResult(intent, EXPRESSION_BUILDER_REQUEST);
            overridePendingTransition(R.anim.right_slide_in, R.anim.fade_out);
        }
    };

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

    @Override
    public void onBackPressed() {
        unregisterSensors();
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregisterSensors();
    }

    @Override
    public void onDestroy() {
        unregisterSensors();
        super.onDestroy();
    }

    private void unregisterSensors() {
        mAdapter.unregisterExpressions(this);
    }
}
