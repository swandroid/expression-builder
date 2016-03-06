package interdroid.swan.swanexpressions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.views.ValueSelectionView;

/**
 * Created by steven on 27/01/16.
 */
public class ExpressionSelectionActivity extends BaseActivity {

    public static final int EXPRESSION_REQUEST_ID = 1241;

    private ValueSelectionView mValueSelectionView;

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
        return R.layout.activity_expression_selection;
    }

    private void getViews() {
        mValueSelectionView = (ValueSelectionView) findViewById(R.id.expression_selection_value);
        ExpressionCreatorItem expressionCreatorItem = getIntent().getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);
        switch (mExpressionCreatorItem.possibleExpressionTypeInt) {
            case Constants.VALUE_EXPRESSION:
                mValueSelectionView.setVisibility(View.VISIBLE);
                mValueSelectionView.setExpressionCreatorItem(expressionCreatorItem);
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EXPRESSION_REQUEST_ID) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

            } else {

            }
        }
    }
}
