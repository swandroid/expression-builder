package interdroid.swan.swanexpressions.pojos;

/**
 * Created by steven on 29/03/15.
 */
public class ExpressionItem {

    public static int currectId = 5000;

    public int id;
    public String name;
    public String stringId;
    public String expression;
    public String valuation;
    public int expressionType;

    public ExpressionItem(String name, String expression, int expressionType) {
        this.id = currectId++;
        this.name = name;
        this.expression = expression;
        this.stringId = name + "_" + id;
        this.expressionType = expressionType;
    }

    public void updateValuation(String valuation) {
        this.valuation = valuation;
    }

    public String getStringId() {
        return stringId;
    }

}
