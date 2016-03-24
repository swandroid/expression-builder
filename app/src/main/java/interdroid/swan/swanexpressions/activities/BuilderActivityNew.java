package interdroid.swan.swanexpressions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.melnykov.fab.FloatingActionButton;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.adapters.ExpressionCreatorListAdapterNew;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 01/04/15.
 */
public class BuilderActivityNew extends BaseActivity {

    private static final String TAG = BuilderActivityNew.class.getSimpleName();

    public static final int EXPRESSION_REQUEST_ID = 1240;

    public static final String KEY_EXTRA_NAME = "Name";
    public static final String KEY_EXTRA_EXPRESSION = "Expression";

    private EditText mName;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpressionCreatorListAdapterNew mAdapter;

    private FloatingActionButton mAddButton;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        setContentView(R.layout.activity_builder);
        setActionBarIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (mToolbar != null) {
//            setSupportActionBar(mToolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        getViews();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_builder;
    }

    private void getViews() {
        mName = (EditText) findViewById(R.id.builder_activity_name_edittext);
        mRecyclerView = (RecyclerView) findViewById(R.id.builder_activity_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ExpressionCreatorListAdapterNew(this);
        mRecyclerView.setAdapter(mAdapter);

        mAddButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddButton.setOnClickListener(mOnAddClickListener);
        //mAddButton.attachToRecyclerView(mRecyclerView);
    }

    private View.OnClickListener mOnAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addExpressionCreator();
//            mAdapter.addExpressionCreator();
        }
    };

    private void addExpressionCreator() {
        ExpressionCreatorItem expressionCreatorItem = mAdapter.getNextTypeOfExpression();
        Intent intent = new Intent(BuilderActivityNew.this, ExpressionSelectionActivity.class);
        intent.putExtra(Constants.EXTRA_EXPRESSION_CREATOR, expressionCreatorItem);
        startActivityForResult(intent, EXPRESSION_REQUEST_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_builder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                buildExpression();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildExpression() {
//        String name = mName.getText().toString();
//        String expression = mAdapter.buildExpression();
//        Intent intent = new Intent();
//        Log.d(TAG, name);
//        intent.putExtra(KEY_EXTRA_NAME, name);
//        intent.putExtra(KEY_EXTRA_EXPRESSION, expression);
//        setResult(RESULT_OK, intent);
        finish();
        //Toast.makeText(getApplicationContext(), "Expression: " + expression, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EXPRESSION_REQUEST_ID) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ExpressionCreatorItem expressionCreatorItem = data.getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);
                int position = data.getIntExtra(Constants.EXTRA_EXPRESSION_LIST_POSITION, -1);
                if (position >= 0) {
                    mAdapter.updateExpression(expressionCreatorItem, position);
                } else {
                    mAdapter.addExpression(expressionCreatorItem);
                }
            } else {

            }
        }
    }
}
