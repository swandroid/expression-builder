package interdroid.swan.swanexpressions.pojos;

import interdroid.swan.swanexpressions.enums.ExpressionType;

/**
 * Created by steven on 01/04/15.
 */
public class ExpressionCreatorItem {

    public String name;
    public ExpressionType expressionType;
    public Object expression;   //TODO: maybe extending some interface

    public ExpressionCreatorItem() {
        this.name = "";
        this.expressionType = ExpressionType.SENSOR_EXPRESSION;
        this.expression = null;
    }

}
