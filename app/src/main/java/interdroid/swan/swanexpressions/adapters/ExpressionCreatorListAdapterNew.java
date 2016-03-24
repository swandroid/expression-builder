package interdroid.swan.swanexpressions.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.activities.BuilderActivityNew;
import interdroid.swan.swanexpressions.activities.ExpressionSelectionActivity;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;
import interdroid.swan.swanexpressions.views.ConstantExpressionView;
import interdroid.swan.swanexpressions.views.OperatorExpressionView;

/**
 * Created by steven on 01/03/15.
 */
public class ExpressionCreatorListAdapterNew extends RecyclerView.Adapter<ExpressionCreatorListAdapterNew.SimpleViewHolder>
    implements ConstantExpressionView.OnConstantExpressionClickListener, OperatorExpressionView.OnOperatorExpressionClickListener {

    private Context mContext;
    private ArrayList<ExpressionCreatorItem> mExpressionCreators;

    public ExpressionCreatorListAdapterNew(Context context) {
        mContext = context;
        mExpressionCreators = new ArrayList<ExpressionCreatorItem>();
        //mOnExpressionCreatorClickListener = onClickListener;
//        addExpressionCreator();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SimpleViewHolder(View view) {
            super(view);
        }
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public View mRoot;
//        public ExpressionCreatorView mCreatorView;
//        public int mPosition;
//        public ViewHolder(View view) {
//            super(view);
//            mRoot = view;
//            mCreatorView = (ExpressionCreatorView) mRoot.findViewById(R.id.expression_creator_item_view);
//        }
//    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_expression_creator, parent, false);
//
//        // set the view's size, margins, paddings and layout parameters
//
//        ViewHolder viewHolder = new ViewHolder(view);
//        view.setTag(viewHolder);
//        return viewHolder;
//    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        if (viewType == Constants.SENSOR_EXPRESSION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.view_sensor_expression, parent, false);
        } else if (viewType == Constants.CONSTANT_EXPRESSION){
            view = LayoutInflater.from(mContext).inflate(R.layout.view_constant_expression, parent, false);;
        } else if (viewType == Constants.MATH_EXPRESSION){
            view = LayoutInflater.from(mContext).inflate(R.layout.view_operator_expression, parent, false);;
        } else if (viewType == Constants.COMPARISON_EXPRESSION){
            view = LayoutInflater.from(mContext).inflate(R.layout.view_operator_expression, parent, false);;
        } else {
            view = null;
        }
//        else if (viewType == 2) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.spotfooter, parent, false);
//        } else {
//            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_spot, parent, false);
//        }
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        if (holder.getItemViewType() == Constants.SENSOR_EXPRESSION) {

        } else if (holder.getItemViewType() == Constants.CONSTANT_EXPRESSION) {
            ((ConstantExpressionView) holder.itemView).setExpressionCreatorItem(mExpressionCreators.get(position), this);
        } else if (holder.getItemViewType() == Constants.MATH_EXPRESSION) {
            ((OperatorExpressionView) holder.itemView).setExpressionCreatorItem(mExpressionCreators.get(position), this);
        } else if (holder.getItemViewType() == Constants.COMPARISON_EXPRESSION) {
            ((OperatorExpressionView) holder.itemView).setExpressionCreatorItem(mExpressionCreators.get(position), this);
        } else if (holder.getItemViewType() == Constants.LOGIC_EXPRESSION) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mExpressionCreators.get(position).expressionTypeInt;
    }

    @Override
    public int getItemCount() {
        return mExpressionCreators.size();
    }

    public void setExpressions(ArrayList<ExpressionCreatorItem> expressions) {
        mExpressionCreators = expressions;
        notifyDataSetChanged();
    }

    public void addExpression(ExpressionCreatorItem expressionCreatorItem) {
        mExpressionCreators.add(expressionCreatorItem);
        notifyDataSetChanged();
//        notifyItemInserted(mExpressionCreators.size() - 1);
    }

    public void updateExpression(ExpressionCreatorItem expressionCreatorItem, int position) {
        mExpressionCreators.set(position, expressionCreatorItem);
        notifyItemChanged(position);
    }

    public void addExpressionCreator() {
//        mExpressionCreators.add(getNextTypeOfExpression());
//        notifyItemInserted(getItemCount() - 1);
    }
//
//    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            if (mOnExpressionCreatorClickListener != null) {
//                mOnExpressionCreatorClickListener.onExpressionCreatorClicked(mExpressionCreators.get(((SimpleViewHolder)((View)v.getParent()).getTag()).mPosition));
//            }
//        }
//    };
//
//    public String buildExpression() {
//        StringBuilder sb = new StringBuilder();
//        //TODO: check if expression contains "contains" and/or "regex"
//        boolean[] shouldAddQuotes = new boolean[mExpressionCreators.size()];
//        for (int i = 0; i < mExpressionCreators.size(); i++) {
//            ExpressionCreatorItem expressionCreatorItem = mExpressionCreators.get(i);
//            if (expressionCreatorItem.expressionType == ExpressionType.COMPARISON_EXPRESSION
//                    && (expressionCreatorItem.expressionInterface.getExpression().equals("contains")
//                    || expressionCreatorItem.expressionInterface.getExpression().equals("regex"))) {
//                //TODO: errorChecking
//                if (mExpressionCreators.get(i-1).expressionType == ExpressionType.CONSTANT_EXPRESSION) {
//                    shouldAddQuotes[i-1] = true;
//                }
//                if (mExpressionCreators.get(i+1).expressionType == ExpressionType.CONSTANT_EXPRESSION) {
//                    shouldAddQuotes[i+1] = true;
//                }
//            }
//        }
//        for (int i = 0; i < mExpressionCreators.size(); i++) {
//            ExpressionCreatorItem expressionCreatorItem = mExpressionCreators.get(i);
//            if (shouldAddQuotes[i]) {
//                sb.append("'");
//                sb.append(expressionCreatorItem.expressionInterface.getExpression());
//                sb.append("' ");
//            } else {
//                sb.append(expressionCreatorItem.expressionInterface.getExpression());
//                sb.append(" ");
//            }
//        }
//        if (mExpressionCreators.size() > 0) {
//            sb.deleteCharAt(sb.length() - 1);
//        }
//        return sb.toString();
//    }

    public ExpressionCreatorItem getNextTypeOfExpression() {
        ExpressionCreatorItem expressionCreatorItem = new ExpressionCreatorItem();
        if (mExpressionCreators.size() < 1) {
            expressionCreatorItem.possibleExpressionTypeInt = Constants.VALUE_EXPRESSION;
        } else {
            ExpressionCreatorItem previous = mExpressionCreators.get(mExpressionCreators.size() - 1);
            int previousId = previous.possibleExpressionTypeInt;
            if (previousId == Constants.VALUE_OPERATOR_EXPRESSION
                    || previousId == Constants.TRI_STATE_OPERATOR_EXPRESSION
                    || previousId == Constants.TRI_MATH_OPERATOR_EXPRESSION) {
                expressionCreatorItem.possibleExpressionTypeInt = Constants.VALUE_EXPRESSION;
            } else {
                expressionCreatorItem.possibleExpressionTypeInt = checkLastOperator();
            }
        }

        return expressionCreatorItem;
    }

    private int checkLastOperator() {
        for (int i = mExpressionCreators.size() - 2; i > 0; i-=2) {
            ExpressionCreatorItem expressionCreatorItem = mExpressionCreators.get(i);
            int lastOperatorId = expressionCreatorItem.expressionTypeInt;
            if (lastOperatorId == Constants.COMPARISON_EXPRESSION) {
                return Constants.TRI_MATH_OPERATOR_EXPRESSION;
            } else if (lastOperatorId == Constants.LOGIC_EXPRESSION) {
                return Constants.VALUE_OPERATOR_EXPRESSION;
            }
        }
        return Constants.VALUE_OPERATOR_EXPRESSION;
    }

    @Override
    public void onConstantExpressionClicked(ExpressionCreatorItem expressionCreatorItem) {
        startExpresionSelectionActivity(expressionCreatorItem);
    }

    @Override
    public void onOperatorExpressionClicked(ExpressionCreatorItem expressionCreatorItem) {
        startExpresionSelectionActivity(expressionCreatorItem);
    }

    private void startExpresionSelectionActivity(ExpressionCreatorItem expressionCreatorItem) {
        Intent intent = new Intent(mContext, ExpressionSelectionActivity.class);
        intent.putExtra(Constants.EXTRA_EXPRESSION_CREATOR, expressionCreatorItem);
        intent.putExtra(Constants.EXTRA_EXPRESSION_LIST_POSITION, mExpressionCreators.indexOf(expressionCreatorItem));
        ((Activity) mContext).startActivityForResult(intent, BuilderActivityNew.EXPRESSION_REQUEST_ID);
    }
}
