package interdroid.swan.swanexpressions.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.adapters.ExpressionCreatorListAdapter;
import interdroid.swan.swanexpressions.adapters.ExpressionListAdapter;

/**
 * Created by steven on 01/04/15.
 */
public class BuilderActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpressionCreatorListAdapter mAdapter;

    private FloatingActionButton mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        getViews();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_builder;
    }

    private void getViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.builder_activity_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ExpressionCreatorListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAddButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddButton.setOnClickListener(mOnAddClickListener);
    }

    private View.OnClickListener mOnAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAdapter.addExpressionCreator();
        }
    };

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
        String expression = mAdapter.buildExpression();
        Toast.makeText(getApplicationContext(), "Expression: " + expression, Toast.LENGTH_LONG).show();
    }
}
