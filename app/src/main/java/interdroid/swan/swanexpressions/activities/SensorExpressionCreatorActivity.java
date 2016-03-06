package interdroid.swan.swanexpressions.activities;

import android.os.Bundle;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 27/01/16.
 */
public class SensorExpressionCreatorActivity extends BaseActivity {

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
        return R.layout.activity_sensor_expression_creator;
    }

    private void getViews() {

    }
}
