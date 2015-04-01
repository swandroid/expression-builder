package interdroid.swan.swanexpressions.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
}
