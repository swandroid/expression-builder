package interdroid.swan.swanexpressions.pojos.expressions;

import interdroid.swan.swanexpressions.enums.ExpressionType;

/**
 * Created by steven on 01/04/15.
 */
public class SensorExpression implements ExpressionInterface{

    private String sensor;
    private String valuePath;
    private int historyWindow;
    private String historyUnit;
    private String historyReductionMode;

    public SensorExpression() {
        this.sensor = "";
        this.valuePath = "";
        this.historyWindow = 0;
        this.historyUnit = "";
        this.historyReductionMode = "";
    }

    @Override
    public String getExpression() {
        return String.format("self@%s:%s{%s,%d%s}", sensor, valuePath, historyReductionMode, historyWindow, historyUnit);
    }

    @Override
    public void parsePartOfExpression(String partOfExpression) {
        //TODO: for future expression to expression building blocks
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public void setValuePath(String valuePath) {
        this.valuePath = valuePath;
    }

    public void setHistoryWindow(int historyWindow) {
        this.historyWindow = historyWindow;
    }

    public void setHistoryUnit(String historyUnit) {
        this.historyUnit = historyUnit;
    }

    public void setHistoryWindowAndUnit(int historyWindow, String historyUnit) {
        this.historyWindow = historyWindow;
        if (historyWindow > 0) {
            this.historyUnit = historyUnit;
        } else {
            this.historyUnit = "";
        }
    }

    public void setHistoryReductionMode(String historyReductionMode) {
        this.historyReductionMode = historyReductionMode;
    }

    public String getSensor() {
        return sensor;
    }

    public String getValuePath() {
        return valuePath;
    }

    public int getHistoryWindow() {
        return historyWindow;
    }

    public String getHistoryUnit() {
        return historyUnit;
    }

    public String getHistoryReductionMode() {
        return historyReductionMode;
    }
}
