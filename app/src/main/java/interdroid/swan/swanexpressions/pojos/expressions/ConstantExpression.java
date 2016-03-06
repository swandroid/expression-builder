package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

/**
 * Created by steven on 01/04/15.
 */
public class ConstantExpression implements ExpressionInterface {

    private String constant;

    public ConstantExpression() {
        this.constant = "";
    }

    protected ConstantExpression(Parcel in) {
        constant = in.readString();
    }

    public static final Creator<ConstantExpression> CREATOR = new Creator<ConstantExpression>() {
        @Override
        public ConstantExpression createFromParcel(Parcel in) {
            return new ConstantExpression(in);
        }

        @Override
        public ConstantExpression[] newArray(int size) {
            return new ConstantExpression[size];
        }
    };

    @Override
    public String getExpression() {
        try {
            Long.parseLong(constant);
            return constant;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(constant);
                return constant;
            } catch (NumberFormatException e2) {
                return "'" + constant + "'";
            }
        }
    }

    @Override
    public void parsePartOfExpression(String partOfExpression) {
        //TODO: for future expression to expression building blocks
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(constant);
    }
}
