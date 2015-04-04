package interdroid.swan.swanexpressions.pojos.expressions;

/**
 * Created by steven on 01/04/15.
 */
public class MathExpression implements ExpressionInterface{

    private String operator;

    @Override
    public String getExpression() {
        return operator;
    }

    @Override
    public void parsePartOfExpression(String partOfExpression) {
        //TODO: for future expression to expression building blocks
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
