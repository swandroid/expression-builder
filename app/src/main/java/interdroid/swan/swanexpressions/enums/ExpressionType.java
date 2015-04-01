package interdroid.swan.swanexpressions.enums;

import interdroid.swan.swanexpressions.R;

/**
 * Created by steven on 01/04/15.
 */
public enum ExpressionType {

    SENSOR_EXPRESSION(R.string.expression_sensor),
    CONSTANT_EXPRESSION(R.string.expression_constant),
    MATH_EXPRESSION(R.string.expression_math),
    COMPARISON_EXPRESSION(R.string.expression_comparison),
    LOGIC_EXPRESSION(R.string.expression_logic);

    private int nameId;

    ExpressionType(int nameId) {
        this.nameId = nameId;
    }

}
