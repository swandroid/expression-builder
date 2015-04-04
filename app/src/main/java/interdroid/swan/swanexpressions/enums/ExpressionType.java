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
    LOGIC_EXPRESSION(4, R.string.expression_logic),
    VALUE_EXPRESSION(5, R.string.expression_value),
    TRI_STATE_EXPRESSION(6, R.string.expression_tri_state),
    VALUE_OPERATOR_EXPRESSION(7, R.string.expression_value_operator),
    TRI_STATE_OPERATOR_EXPRESSION(8, R.string.expression_tri_state_operator),
    TRI_MATH_OPERATOR_EXPRESSION(9, R.string.expression_tri_math_operator);

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

    public static ExpressionType[] getValueExpressionTypes() {
        ExpressionType[] expressionTypes = {SENSOR_EXPRESSION, CONSTANT_EXPRESSION};
        return expressionTypes;
    }

    public static ExpressionType[] getValueOperatorExpressionTypes() {
        ExpressionType[] expressionTypes = {COMPARISON_EXPRESSION, MATH_EXPRESSION};
        return expressionTypes;
    }

    public static ExpressionType[] getTriMathExpressionTypes() {
        ExpressionType[] expressionTypes = {LOGIC_EXPRESSION, MATH_EXPRESSION};
        return expressionTypes;
    }

    public int getId() {
       return id;
    }

    public int getNameId() {
        return nameId;
    }
}
