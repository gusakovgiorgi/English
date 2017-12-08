package company.self.development.rememberenglishexample.search.rest.yandexApi;

import android.content.Context;

import com.google.gson.GsonBuilder;

/**
 * Created by notbl on 12/7/2017.
 */

public class YandexApiKey {
    private static final String YANDEX_API_SHARED_PREF_NAME="yandex_api_shared_pref_name";
    private static final String YANDEX_API_KEY="yandex_api_key";

    private String yandexApiKey;
    private Context applicatonContext;

    public YandexApiKey(Context context){
        init(context);
    }

    public void checkApiKey(String yandexApiKey){
        if (this.yandexApiKey.isEmpty() || !this.yandexApiKey.equals(yandexApiKey)){
            this.yandexApiKey=yandexApiKey;
            saveYandexApiKey();
        }
    }

    private void saveYandexApiKey() {
        applicatonContext.getSharedPreferences(YANDEX_API_SHARED_PREF_NAME,Context.MODE_PRIVATE)
                .edit()
                .putString(YANDEX_API_KEY,this.yandexApiKey)
                .apply();
    }

    private void init(Context context) {
        applicatonContext=context.getApplicationContext();
        String apiKey=applicatonContext.getSharedPreferences(YANDEX_API_SHARED_PREF_NAME,Context.MODE_PRIVATE)
                .getString(YANDEX_API_KEY,"");
    }

    public String getYandexApiKey() {
        return yandexApiKey;
    }
}
