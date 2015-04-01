package interdroid.swan.swanexpressions.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.ExpressionItem;

/**
 * Created by steven on 26/03/15.
 */
public class ExpressionListAdapter extends RecyclerView.Adapter<ExpressionListAdapter.ViewHolder> {

    private ArrayList<ExpressionItem> mExpressions;
    private OnExpressionClickListener mOnExpressionClickListener;

    public ExpressionListAdapter(OnExpressionClickListener onClickListener) {
        mExpressions = new ArrayList<ExpressionItem>();
        mOnExpressionClickListener = onClickListener;
    }

    public interface OnExpressionClickListener {
        void onExpressionClicked(ExpressionItem expression);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mRoot;
        public LinearLayout mContainer;
        public TextView mExpressionName;
        public TextView mExpressionValuation;
        public int mPosition;
        public ViewHolder(View view) {
            super(view);
            mRoot = view;
            mContainer = (LinearLayout) mRoot.findViewById(R.id.expression_item_linearlayout);
            mExpressionName = (TextView) mRoot.findViewById(R.id.expression_item_name);
            mExpressionValuation = (TextView) mRoot.findViewById(R.id.expression_item_valuation);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expression, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        viewHolder.mContainer.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ExpressionItem expression = mExpressions.get(i);
        viewHolder.mExpressionName.setText(expression.name);
        viewHolder.mPosition = i;
    }

    @Override
    public int getItemCount() {
        return mExpressions.size();
    }

    public void setExpressions(ArrayList<ExpressionItem> expressions) {
        mExpressions = expressions;
        notifyDataSetChanged();
    }

    //TODO: something to update the valuation

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOnExpressionClickListener != null) {
                mOnExpressionClickListener.onExpressionClicked(mExpressions.get(((ViewHolder)((View)v.getParent()).getTag()).mPosition));
            }
        }
    };
}
