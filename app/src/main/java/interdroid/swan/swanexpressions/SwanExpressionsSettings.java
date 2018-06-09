package interdroid.swan.swanexpressions;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import interdroid.swan.swanexpressions.pojos.ExpressionItem;

/**
 * Created by steven on 17/04/16.
 */
public class SwanExpressionsSettings {

    private static final String KEY_EXPRESSIONS = "key_expressions";

    private static SwanExpressionsSettings sInstance;
    private ExecutorService mExecutor;
    private SharedPreferences mSharedPreferences;

    private List<ExpressionItem> mExpressions;

    private SwanExpressionsSettings() {
        mExecutor = Executors.newSingleThreadExecutor();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SwanExpressionsApp.getInstance());

        loadData();
    }

    public static SwanExpressionsSettings getInstance() {
        if (sInstance == null) {
            sInstance = new SwanExpressionsSettings();
        }
        return sInstance;
    }

    private void loadData() {
        String expressionItemsString = mSharedPreferences.getString(KEY_EXPRESSIONS, null);
        Type typeOfObjectsList = new TypeToken<List<ExpressionItem>>() {}.getType();
        mExpressions = new Gson().fromJson(expressionItemsString, typeOfObjectsList);

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

    public void setExpressionItems(List<ExpressionItem> expressionItems) {
        mExpressions = expressionItems;
        persistString(KEY_EXPRESSIONS, new Gson().toJson(mExpressions));
    }

    public List<ExpressionItem> getExpressionItems() {
        return mExpressions;
    }

}
