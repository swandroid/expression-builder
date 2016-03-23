package interdroid.swan.swanexpressions.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ConstantExpression;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.views.ConstantExpressionView;

/**
 * Created by steven on 27/01/16.
 */
public class ConstantExpressionCreatorActivity extends BaseActivity {

    private EditText mConstantValue;
    private ConstantExpressionView mConstantExpressionView;

    private ExpressionCreatorItem mExpressionCreatorItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        mExpressionCreatorItem = getIntent().getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);

        getViews();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_constant_expression_creator;
    }

    private void getViews() {
        mConstantValue = (EditText) findViewById(R.id.constant_creator_value);
        mConstantExpressionView = (ConstantExpressionView) findViewById(R.id.constant_expression_view);
        mConstantValue.addTextChangedListener(mConstantValueTextWatcher);
        mConstantExpressionView.setExpressionCreatorItem(mExpressionCreatorItem, null);

        if (mExpressionCreatorItem.expressionInterface != null) {
            mConstantValue.setText(((ConstantExpression) mExpressionCreatorItem.expressionInterface).getConstant());
            mConstantValue.setSelection(mConstantValue.getText().length());
        }
    }

    private TextWatcher mConstantValueTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ConstantExpression) mExpressionCreatorItem.expressionInterface).setConstant(s.toString());
            mConstantExpressionView.setExpressionCreatorItem(mExpressionCreatorItem, null);
        }

        @Override
        public void afterTextChanged(Editable s) {

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
