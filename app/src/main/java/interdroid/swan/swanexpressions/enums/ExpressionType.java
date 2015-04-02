package interdroid.swan.swanexpressions.enums;

import java.util.ArrayList;

import interdroid.swan.swanexpressions.R;

/**
 * Created by steven on 01/04/15.
 */
public enum ExpressionType {

    SENSOR_EXPRESSION(0, R.string.expression_sensor),
    CONSTANT_EXPRESSION(1, R.string.expression_constant),
    MATH_EXPRESSION(2, R.string.expression_math),
    COMPARISON_EXPRESSION(3, R.string.expression_comparison),
    LOGIC_EXPRESSION(4, R.string.expression_logic);

    private int id;
    private int nameId;

    ExpressionType(int id, int nameId) {
        this.id = id;
        this.nameId = nameId;
    }

    //TODO: make multiple methods to get different arrays
    public static ExpressionType[] getExpressionTypes() {
        ExpressionType[] expressionTypes = {SENSOR_EXPRESSION, CONSTANT_EXPRESSION, MATH_EXPRESSION, COMPARISON_EXPRESSION, LOGIC_EXPRESSION};
        return expressionTypes;
    }

    public int getId() {
       return id;
    }

    public int getNameId() {
        return nameId;
    }
}
