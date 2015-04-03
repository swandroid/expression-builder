package interdroid.swan.swanexpressions.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.views.ExpressionCreatorView;

/**
 * Created by steven on 01/03/15.
 */
public class ExpressionCreatorListAdapter extends RecyclerView.Adapter<ExpressionCreatorListAdapter.ViewHolder> {

    private ArrayList<ExpressionCreatorItem> mExpressionCreators;
    private OnExpressionCreatorClickListener mOnExpressionCreatorClickListener;

    public ExpressionCreatorListAdapter() {
        mExpressionCreators = new ArrayList<ExpressionCreatorItem>();
        //mOnExpressionCreatorClickListener = onClickListener;
    }

    public interface OnExpressionCreatorClickListener {
        void onExpressionCreatorClicked(ExpressionCreatorItem expression);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mRoot;
        public ExpressionCreatorView mCreatorView;
        public int mPosition;
        public ViewHolder(View view) {
            super(view);
            mRoot = view;
            mCreatorView = (ExpressionCreatorView) mRoot.findViewById(R.id.expression_creator_item_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expression_creator, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ExpressionCreatorItem expression = mExpressionCreators.get(i);
        viewHolder.mPosition = i;
    }

    @Override
    public int getItemCount() {
        return mExpressionCreators.size();
    }

    public void setExpressions(ArrayList<ExpressionCreatorItem> expressions) {
        mExpressionCreators = expressions;
        notifyDataSetChanged();
    }

    public void addExpressionCreator() {
        mExpressionCreators.add(new ExpressionCreatorItem());
        notifyItemInserted(getItemCount() - 1);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOnExpressionCreatorClickListener != null) {
                mOnExpressionCreatorClickListener.onExpressionCreatorClicked(mExpressionCreators.get(((ViewHolder)((View)v.getParent()).getTag()).mPosition));
            }
        }
    };
}
