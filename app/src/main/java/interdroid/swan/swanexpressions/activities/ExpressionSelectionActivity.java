package interdroid.swan.swanexpressions.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.views.TriMathOperatorSelectionView;
import interdroid.swan.swanexpressions.views.ValueOperatorSelectionView;
import interdroid.swan.swanexpressions.views.ValueSelectionView;

/**
 * Created by steven on 27/01/16.
 */
public class ExpressionSelectionActivity extends BaseActivity
        implements ValueOperatorSelectionView.OnOperatorClickListener,
        TriMathOperatorSelectionView.OnOperatorClickListener {

    public static final int EXPRESSION_REQUEST_ID = 1241;

    private ValueSelectionView mValueSelectionView;
    private ValueOperatorSelectionView mValueOperatorSelectionView;
    private TriMathOperatorSelectionView mTriMathOperatorSelectionView;

    private ExpressionCreatorItem mExpressionCreatorItem;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        mExpressionCreatorItem = getIntent().getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);
        mPosition = getIntent().getIntExtra(Constants.EXTRA_EXPRESSION_LIST_POSITION, -1);

        getViews();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_expression_selection;
    }

    private void getViews() {
        mValueSelectionView = (ValueSelectionView) findViewById(R.id.expression_selection_value);
        mValueOperatorSelectionView = (ValueOperatorSelectionView) findViewById(R.id.expression_selection_value_operator);
        mTriMathOperatorSelectionView = (TriMathOperatorSelectionView) findViewById(R.id.expression_selection_tri_math_operator);
        ExpressionCreatorItem expressionCreatorItem = getIntent().getParcelableExtra(Constants.EXTRA_EXPRESSION_CREATOR);
        switch (mExpressionCreatorItem.possibleExpressionTypeInt) {
            case Constants.VALUE_EXPRESSION:
                mValueSelectionView.setVisibility(View.VISIBLE);
                mValueSelectionView.setExpressionCreatorItem(expressionCreatorItem);
                break;
            case Constants.VALUE_OPERATOR_EXPRESSION:
                mValueOperatorSelectionView.setVisibility(View.VISIBLE);
                mValueOperatorSelectionView.setExpressionCreatorItem(expressionCreatorItem, this);
                break;
            case Constants.TRI_MATH_OPERATOR_EXPRESSION:
                mTriMathOperatorSelectionView.setVisibility(View.VISIBLE);
                mTriMathOperatorSelectionView.setExpressionCreatorItem(expressionCreatorItem, this);
                break;
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
                sendResult(data);
            } else {

            }
        }
    }

    @Override
    public void onOperatorClicked(ExpressionCreatorItem mExpressionCreatorItem) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.EXTRA_EXPRESSION_CREATOR, mExpressionCreatorItem);
        sendResult(resultIntent);
    }

    private void sendResult(Intent intent) {
        setResult(Activity.RESULT_OK, intent);
        intent.putExtra(Constants.EXTRA_EXPRESSION_LIST_POSITION, mPosition);
        finish();
    }
}
