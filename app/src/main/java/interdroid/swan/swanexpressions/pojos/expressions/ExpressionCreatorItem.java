package interdroid.swan.swanexpressions.pojos.expressions;

import interdroid.swan.swanexpressions.enums.ExpressionType;

/**
 * Created by steven on 01/04/15.
 */
public abstract class ExpressionCreatorItem {

    public String name;
    public ExpressionType expressionType;

    public ExpressionCreatorItem() {
        this.name = "";
        this.expressionType = ExpressionType.SENSOR_EXPRESSION;
    }

    public ExpressionCreatorItem(ExpressionType expressionType) {
        this.name = "";
        this.expressionType = expressionType;
    }


    public abstract String getExpression();

    public abstract void parsePartOfExpression(String partOfExpression);

}
