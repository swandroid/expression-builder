package interdroid.swan.swanexpressions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import interdroid.swan.swanexpressions.pojos.expressions.ExpressionCreatorItem;

/**
 * Created by steven on 17/04/16.
 */
public class SwanExpressionsSettings {

    private static final String KEY_EXPRESSION_CREATOR_ITEMS = "key_expression_creator_items";

    private static SwanExpressionsSettings sInstance;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private SharedPreferences mSharedPreferences;

    private ArrayList<ExpressionCreatorItem> mExpressionCreatorItems;

    private SwanExpressionsSettings(Context context) {
        mExecutor = Executors.newSingleThreadExecutor();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SwanExpressionsApp.getInstance());
//        mSharedPreferences = context.getSharedPreferences("JsonSensor", Context.MODE_PRIVATE);

        loadData();
    }

    public static SwanExpressionsSettings getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SwanExpressionsSettings(context);
        }
        return sInstance;
    }

    private void loadData() {
        String expressionCreatorItemsString = mSharedPreferences.getString(KEY_EXPRESSION_CREATOR_ITEMS, null);
        Type typeOfObjectsList = new TypeToken<ArrayList<ExpressionCreatorItem>>() {}.getType();
        mExpressionCreatorItems = new Gson().fromJson(expressionCreatorItemsString, typeOfObjectsList);

    }

    private void persistString(final String key, final String value) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(key, value);
                editor.commit();
            }
        });
    }

    public void setExpressionCreatorItems(ArrayList<ExpressionCreatorItem> expressionCreatorItems) {
        mExpressionCreatorItems = expressionCreatorItems;
        persistString(KEY_EXPRESSION_CREATOR_ITEMS, new Gson().toJson(mExpressionCreatorItems));
    }

    public ArrayList<ExpressionCreatorItem> getExpressionCreatorItems() {
        return mExpressionCreatorItems;
    }

}
