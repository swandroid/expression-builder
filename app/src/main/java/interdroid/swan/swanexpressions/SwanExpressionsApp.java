package interdroid.swan.swanexpressions;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import interdroid.swan.ExpressionManager;
import interdroid.swan.SensorInfo;

/**
 * Created by steven on 03/04/15.
 */
public class SwanExpressionsApp extends Application {

    private static final String TAG = SwanExpressionsApp.class.getSimpleName();

    private static SwanExpressionsApp sInstance;

    private ArrayList<SensorInfo> mSensors;

    public static SwanExpressionsApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSensors = (ArrayList) ExpressionManager.getSensors(getApplicationContext());

        sInstance = this;
    }

    public ArrayList<SensorInfo> getSwanSensors() {
        return mSensors;
    }

}
