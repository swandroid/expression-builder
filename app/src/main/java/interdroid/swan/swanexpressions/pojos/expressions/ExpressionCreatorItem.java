package interdroid.swan.swanexpressions.pojos.expressions;

import interdroid.swan.swanexpressions.enums.ExpressionType;

/**
 * Created by steven on 01/04/15.
 */
public class ExpressionCreatorItem {

    public String name;
    public ExpressionType possibleExpressionType;
    public ExpressionType expressionType;
    public ExpressionInterface expressionInterface;

    public ExpressionCreatorItem() {
        this.name = "";
        this.possibleExpressionType = ExpressionType.VALUE_EXPRESSION;
        this.expressionType = ExpressionType.SENSOR_EXPRESSION;
        this.expressionInterface = null;
    }

    public ExpressionCreatorItem(ExpressionType expressionType) {
        this.name = "";
        this.possibleExpressionType = ExpressionType.VALUE_EXPRESSION;
        this.expressionType = expressionType;
        this.expressionInterface = null;
    }
}
