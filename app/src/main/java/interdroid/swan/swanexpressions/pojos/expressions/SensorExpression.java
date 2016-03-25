package interdroid.swan.swanexpressions.pojos.expressions;

import android.os.Parcel;

/**
 * Created by steven on 01/04/15.
 */
public class SensorExpression implements ExpressionInterface {

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

    protected SensorExpression(Parcel in) {
        sensor = in.readString();
        valuePath = in.readString();
        historyWindow = in.readInt();
        historyUnit = in.readString();
        historyReductionMode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sensor);
        dest.writeString(valuePath);
        dest.writeInt(historyWindow);
        dest.writeString(historyUnit);
        dest.writeString(historyReductionMode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SensorExpression> CREATOR = new Creator<SensorExpression>() {
        @Override
        public SensorExpression createFromParcel(Parcel in) {
            return new SensorExpression(in);
        }

        @Override
        public SensorExpression[] newArray(int size) {
            return new SensorExpression[size];
        }
    };

    @Override
    public String getExpression() {
        if (sensor.equals("time")) {
            return String.format("self@%s:%s", sensor, valuePath);
        }
        return String.format("self@%s:%s{%s,%d%s}", sensor, valuePath, historyReductionMode, historyWindow, historyUnit);
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
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
