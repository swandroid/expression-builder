package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 01/04/15.
 */
public class TriValueExpression implements ExpressionListInterface {

    private List<ExpressionCreatorItem> mItems;
    private String mName;

    public TriValueExpression() {

    }

    public TriValueExpression(List<ExpressionCreatorItem> items) {
        this.mItems = items;
    }

    protected TriValueExpression(Parcel in) {
        mName = in.readString();
        mItems = new ArrayList<>();
        in.readList(mItems, ExpressionCreatorItem.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeList(mItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TriValueExpression> CREATOR = new Creator<TriValueExpression>() {
        @Override
        public TriValueExpression createFromParcel(Parcel in) {
            return new TriValueExpression(in);
        }

        @Override
        public TriValueExpression[] newArray(int size) {
            return new TriValueExpression[size];
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
