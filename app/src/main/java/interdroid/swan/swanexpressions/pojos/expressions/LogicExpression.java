package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

/**
 * Created by steven on 01/04/15.
 */
public class LogicExpression implements ExpressionInterface {

    private String operator;
    private String humanReadableOperator;

    public LogicExpression() {

    }

    public LogicExpression(String operator, String humanReadableOperator) {
        this.operator = operator;
        this.humanReadableOperator = humanReadableOperator;
    }

    protected LogicExpression(Parcel in) {
        operator = in.readString();
        humanReadableOperator = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operator);
        dest.writeString(humanReadableOperator);
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

    public void setHumanReadableOperator(String operator) {
        humanReadableOperator = operator;
    }

    public String getHumanReadableOperator() {
        return humanReadableOperator;
    }
}
