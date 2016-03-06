package interdroid.swan.swanexpressions.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.R;
import interdroid.swan.swanexpressions.enums.ExpressionType;
import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 01/03/15.
 */
public class ExpressionCreatorListAdapterNew extends RecyclerView.Adapter<ExpressionCreatorListAdapterNew.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<ExpressionCreatorItem> mExpressionCreators;
    private OnExpressionCreatorClickListener mOnExpressionCreatorClickListener;

    public ExpressionCreatorListAdapterNew(Context context) {
        mContext = context;
        mExpressionCreators = new ArrayList<ExpressionCreatorItem>();
        //mOnExpressionCreatorClickListener = onClickListener;
//        addExpressionCreator();
    }

    public interface OnExpressionCreatorClickListener {
        void onExpressionCreatorClicked(ExpressionCreatorItem expression);
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

        } else if (holder.getItemViewType() == 2) {

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
//
    public ExpressionCreatorItem getNextTypeOfExpression() {
        ExpressionCreatorItem expressionCreatorItem = new ExpressionCreatorItem();
        if (mExpressionCreators.size() < 1) {
            expressionCreatorItem.possibleExpressionType = ExpressionType.VALUE_EXPRESSION;
        } else {
            ExpressionCreatorItem previous = mExpressionCreators.get(mExpressionCreators.size() - 1);
            int previousId = previous.possibleExpressionType.getId();
            if (previousId == ExpressionType.VALUE_OPERATOR_EXPRESSION.getId()
                    || previousId == ExpressionType.TRI_STATE_OPERATOR_EXPRESSION.getId()
                    || previousId == ExpressionType.TRI_MATH_OPERATOR_EXPRESSION.getId()) {
                expressionCreatorItem.possibleExpressionType = ExpressionType.VALUE_EXPRESSION;
            } else {
                expressionCreatorItem.possibleExpressionType = checkLastOperator();
            }
        }

        return expressionCreatorItem;
    }

    private ExpressionType checkLastOperator() {
        for (int i = mExpressionCreators.size() - 2; i > 0; i-=2) {
            ExpressionCreatorItem expressionCreatorItem = mExpressionCreators.get(i);
            int lastOperatorId = expressionCreatorItem.expressionType.getId();
            if (lastOperatorId == ExpressionType.COMPARISON_EXPRESSION.getId()) {
                return ExpressionType.TRI_MATH_OPERATOR_EXPRESSION;
            } else if (lastOperatorId == ExpressionType.LOGIC_EXPRESSION.getId()) {
                return ExpressionType.VALUE_OPERATOR_EXPRESSION;
            }
        }
        return ExpressionType.VALUE_OPERATOR_EXPRESSION;
    }
}
