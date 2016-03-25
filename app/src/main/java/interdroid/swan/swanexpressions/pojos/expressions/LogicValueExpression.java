package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 01/04/15.
 */
public class LogicValueExpression implements ExpressionInterface {

    private List<ExpressionCreatorItem> mItems;
    private String mName;

    public LogicValueExpression() {

    }

    public LogicValueExpression(List<ExpressionCreatorItem> items) {
        this.mItems = items;
    }

    protected LogicValueExpression(Parcel in) {
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

    public static final Creator<LogicValueExpression> CREATOR = new Creator<LogicValueExpression>() {
        @Override
        public LogicValueExpression createFromParcel(Parcel in) {
            return new LogicValueExpression(in);
        }

        @Override
        public LogicValueExpression[] newArray(int size) {
            return new LogicValueExpression[size];
        }
    };

    @Override
    public String getExpression() {
        return "Hier moet de expression komen";
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
