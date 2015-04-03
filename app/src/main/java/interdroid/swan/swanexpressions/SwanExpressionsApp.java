package interdroid.swan.swanexpressions;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import interdroid.swan.ExpressionManager;
import interdroid.swan.SensorInfo;

/**
 * Created by steven on 03/04/15.
 */
public class SwanExpressionsApp extends Application {

    private static SwanExpressionsApp sInstance;

    public static SwanExpressionsApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}
