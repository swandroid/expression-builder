package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

/**
 * Created by steven on 01/04/15.
 */
public class LogicExpression implements ExpressionInterface {

    private String operator;

    public LogicExpression() {

    }

    protected LogicExpression(Parcel in) {
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

    public static final Creator<LogicExpression> CREATOR = new Creator<LogicExpression>() {
        @Override
        public LogicExpression createFromParcel(Parcel in) {
            return new LogicExpression(in);
        }

        @Override
        public LogicExpression[] newArray(int size) {
            return new LogicExpression[size];
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
    }

    public String getOperator() {
        return operator;
    }
}
