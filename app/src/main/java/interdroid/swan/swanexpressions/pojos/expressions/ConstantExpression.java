package interdroid.swan.swanexpressions.pojos.expressions;

import interdroid.swan.swanexpressions.enums.ExpressionType;

/**
 * Created by steven on 01/04/15.
 */
public class ConstantExpression implements ExpressionInterface{

    private String constant;

    public ConstantExpression() {
        this.constant = "";
    }

    @Override
    public String getExpression() {
        return constant;
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
}
