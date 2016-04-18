package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 01/04/15.
 */
public class ComparisonValueExpression implements ExpressionListInterface {

    private List<ExpressionCreatorItem> mItems;
    private String mName;

    public ComparisonValueExpression() {

    }

    public ComparisonValueExpression(List<ExpressionCreatorItem> items) {
        this.mItems = items;
    }

    protected ComparisonValueExpression(Parcel in) {
        mItems = new ArrayList<>();
        in.readList(mItems, ExpressionCreatorItem.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ComparisonValueExpression> CREATOR = new Creator<ComparisonValueExpression>() {
        @Override
        public ComparisonValueExpression createFromParcel(Parcel in) {
            return new ComparisonValueExpression(in);
        }

        @Override
        public ComparisonValueExpression[] newArray(int size) {
            return new ComparisonValueExpression[size];
        }
    };

    @Override
    public String getExpression() {
        String expression = "";
        for (int i = 0; i < mItems.size(); i++) {
            expression += " " + mItems.get(i).expressionInterface.getExpression();
        }
        return expression;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void parsePartOfExpression(String partOfExpression) {
        //TODO: for future expression to expression building blocks
    }

    public void setExpressionCreatorItems(List<ExpressionCreatorItem> items) {
        this.mItems = items;
    }

    public List<ExpressionCreatorItem> getExpressionCreatorItems() {
        return mItems;
    }

    public void setName(String name) {
        mName = name;
    }

}
