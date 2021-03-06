package interdroid.swan.swanexpressions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import interdroid.swan.ExpressionListener;
import interdroid.swan.ExpressionManager;
import interdroid.swan.SwanException;
import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.SwanExpressionsSettings;
import interdroid.swan.swanexpressions.adapters.ExpressionListAdapter;
import interdroid.swan.swanexpressions.pojos.ExpressionItem;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swansong.ExpressionFactory;
import interdroid.swan.swansong.ExpressionParseException;
import interdroid.swan.swansong.TimestampedValue;
import interdroid.swan.swansong.TriState;


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
        setResult(RESULT_CANCELED);
        setActionBarIcon(R.drawable.ic_ab_drawer);

        getViews();
        getExpressions();
    }

    private void getExpressions() {
        List<ExpressionItem> expressions = SwanExpressionsSettings.getInstance().getExpressionItems();
        if (expressions != null) {
            mAdapter.setExpressions(expressions);
        }
    }

    private void startExpressionBuilder() {
        Intent sendIntent = new Intent();
        sendIntent.setAction("interdroid.swan.ui.BUILD_EXPRESSION");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(sendIntent, EXPRESSION_BUILDER_OLD_REQUEST);
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXPRESSION_BUILDER_OLD_REQUEST) {
            if (resultCode == RESULT_OK) {
                String expression = data.getStringExtra("Expression");
                Toast.makeText(getApplicationContext(), expression, Toast.LENGTH_LONG).show();
                Log.d(TAG, "expression result: " + expression);
            }
        } else if (requestCode == EXPRESSION_BUILDER_REQUEST) {
            if (resultCode == RESULT_OK) {
                ExpressionCreatorItem expressionCreatorItem = data.getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);
                handleExpressionCreatorItem(expressionCreatorItem);
                ExpressionItem expressionItem = new ExpressionItem(expressionCreatorItem.name,
                        expressionCreatorItem.expressionInterface.getExpression(), expressionCreatorItem.expressionTypeInt);
                mAdapter.addExpression(expressionItem);
                registerExpression(expressionItem);
                SwanExpressionsSettings.getInstance().setExpressionItems(mAdapter.getExpressions());
            }
        }
    }

    private void handleExpressionCreatorItem(ExpressionCreatorItem expressionCreatorItem) {
        switch (expressionCreatorItem.expressionTypeInt) {
            case Constants.VALUE_EXPRESSION:
                Toast.makeText(getApplicationContext(), "Value expression", Toast.LENGTH_SHORT).show();
                break;
            case Constants.COMPARISON_VALUE_EXPRESSION:
                Toast.makeText(getApplicationContext(), "Comparison expression", Toast.LENGTH_SHORT).show();
                break;
            case Constants.TRI_VALUE_EXPRESSION:
                Toast.makeText(getApplicationContext(), "Tri value expression", Toast.LENGTH_SHORT).show();
                break;
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
            setResult(RESULT_OK, new Intent().putExtra("Expression", expression.expression));
            finish();
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
