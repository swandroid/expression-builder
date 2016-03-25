package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcelable;

/**
 * Created by steven on 03/04/15.
 */
public interface ExpressionInterface extends Parcelable {

    String getExpression();

    void setName(String name);

    String getName();

    void parsePartOfExpression(String partOfExpression);
}
