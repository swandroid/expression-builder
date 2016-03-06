package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

/**
 * Created by steven on 01/04/15.
 */
public class MathExpression implements ExpressionInterface {

    private String operator;

    public MathExpression() {

    }

    protected MathExpression(Parcel in) {
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

    public static final Creator<MathExpression> CREATOR = new Creator<MathExpression>() {
        @Override
        public MathExpression createFromParcel(Parcel in) {
            return new MathExpression(in);
        }

        @Override
        public MathExpression[] newArray(int size) {
            return new MathExpression[size];
        }
    };

    @Override
    public String getExpression() {
        return operator;
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
