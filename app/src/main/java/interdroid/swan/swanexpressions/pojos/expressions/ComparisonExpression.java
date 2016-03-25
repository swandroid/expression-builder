package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

/**
 * Created by steven on 01/04/15.
 */
public class ComparisonExpression implements ExpressionInterface {

    private String operator;

    public ComparisonExpression() {

    }

    public ComparisonExpression(String operator) {
        this.operator = operator;
    }

    protected ComparisonExpression(Parcel in) {
        operator = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operator);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ComparisonExpression> CREATOR = new Creator<ComparisonExpression>() {
        @Override
        public ComparisonExpression createFromParcel(Parcel in) {
            return new ComparisonExpression(in);
        }

        @Override
        public ComparisonExpression[] newArray(int size) {
            return new ComparisonExpression[size];
        }
    };

    @Override
    public String getExpression() {
        return operator;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void parsePartOfExpression(String partOfExpression) {
        //TODO: for future expression to expression building blocks
    }

    public void setOperator(String operator) {
        this.operator = operator;
        //TODO: check for regex/contains/startsWith/endswith
    }

    public String getOperator() {
        return operator;
    }
}
