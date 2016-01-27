package interdroid.swan.swanexpressions.activities;

import android.os.Bundle;

import interdroid.swan.swanexpressions.R;

/**
 * Created by steven on 27/01/16.
 */
public class ExpressionSelectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getViews();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_expression_selection;
    }

    private void getViews() {

    }
}
