package company.self.development.rememberenglishexample.search.rest.yandexApi;

import java.util.HashMap;
import java.util.Map;

import company.self.development.rememberenglishexample.model.TranslateRequest;

/**
 * Created by notbl on 12/7/2017.
 */

public class YandexApiRequest extends TranslateRequest{
    private String languageDirection;
    private YandexApiKey apiKey;

    private static final String TEXT_KEY="text";
    private static final String LANG_KEY="lang";
    private static final String API_KEY="key";

    @Override
    public Map<String, String> getPostParams() {
        Map<String,String> postParams=new HashMap<>();
        postParams.put(TEXT_KEY,getText());
        postParams.put(LANG_KEY,languageDirection);
        if (!apiKey.getYandexApiKey().isEmpty()){
            postParams.put(API_KEY,apiKey.getYandexApiKey());
        }
        return postParams;
    }

    public YandexApiRequest(String languageDirection, YandexApiKey apiKey, String text) {
        super(text);
        this.languageDirection = languageDirection;
        this.apiKey = apiKey;
    }

    public void setLanguageDirection(String languageDirection) {
        this.languageDirection = languageDirection;
    }

    public void setApiKey(YandexApiKey apiKey) {
        this.apiKey = apiKey;
    }

}
