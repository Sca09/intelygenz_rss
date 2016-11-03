package es.intelygenz.rss.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public class PreferencesService {

    public static void setSource(Context context, String sourceId) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        mySharedPreferences.edit().putString(Constants.PREFERENCES_KEY_SOURCE, sourceId).apply();
    }

    public static String getSource(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        return mySharedPreferences.getString(Constants.PREFERENCES_KEY_SOURCE, Constants.WEB_SERVICE_DEFAULT_SOURCE);
    }

}
