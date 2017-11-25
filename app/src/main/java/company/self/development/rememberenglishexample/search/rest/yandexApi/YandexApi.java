package company.self.development.rememberenglishexample.search.rest.yandexApi;

import java.util.List;

import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by notbl on 11/25/2017.
 */

public interface YandexApi {

    ///api/v1/dicservice.json/lookup?key=API-ключ&lang=en-ru&translatedText=time

    @GET("/api/v1/dicservice.json/lookup")
    Observable<List<ITranslation>> translate(@Query("key") String APIKey, @Query("lang") String lang, @Query("translatedText") String translatedText);
}
