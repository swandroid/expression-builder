package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;
import android.os.Parcelable;

import interdroid.swan.swanexpressions.Constants;
import interdroid.swan.swanexpressions.enums.ExpressionType;

/**
 * Created by steven on 01/04/15.
 */
public class ExpressionCreatorItem implements Parcelable {

    public String name;
    public ExpressionType possibleExpressionType;
    public ExpressionType expressionType;
    public int possibleExpressionTypeInt;
    public int expressionTypeInt;
    public ExpressionInterface expressionInterface;

    public ExpressionCreatorItem() {
        this.name = "";
        this.possibleExpressionType = ExpressionType.VALUE_EXPRESSION;
        this.expressionType = ExpressionType.SENSOR_EXPRESSION;
        this.possibleExpressionTypeInt = Constants.VALUE_EXPRESSION;
        this.expressionTypeInt = Constants.SENSOR_EXPRESSION;
        this.expressionInterface = null;
    }

    public ExpressionCreatorItem(ExpressionType expressionType) {
        this.name = "";
        this.possibleExpressionType = ExpressionType.VALUE_EXPRESSION;
        this.expressionType = expressionType;
        this.expressionInterface = null;
    }

    public ExpressionCreatorItem(int expressionTypeInt) {
        this.name = "";
        this.possibleExpressionTypeInt = Constants.VALUE_EXPRESSION;
        this.expressionTypeInt = expressionTypeInt;
        this.expressionInterface = null;
    }

    protected ExpressionCreatorItem(Parcel in) {
        expressionInterface = in.readParcelable(ExpressionInterface.class.getClassLoader());
        name = in.readString();
        possibleExpressionTypeInt = in.readInt();
        expressionTypeInt = in.readInt();
    }

    public static final Creator<ExpressionCreatorItem> CREATOR = new Creator<ExpressionCreatorItem>() {
        @Override
        public ExpressionCreatorItem createFromParcel(Parcel in) {
            return new ExpressionCreatorItem(in);
        }

        @Override
        public ExpressionCreatorItem[] newArray(int size) {
            return new ExpressionCreatorItem[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or {@link
     *              #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(expressionInterface, flags);
        dest.writeString(name);
        dest.writeInt(possibleExpressionTypeInt);
        dest.writeInt(expressionTypeInt);
    }
}
