package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcelable;

/**
 * Created by steven on 03/04/15.
 */
public interface ExpressionInterface extends Parcelable {

    public String getExpression();

    public void parsePartOfExpression(String partOfExpression);
}
