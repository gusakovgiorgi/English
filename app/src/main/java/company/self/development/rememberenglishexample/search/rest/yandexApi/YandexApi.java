package company.self.development.rememberenglishexample.search.rest.yandexApi;

import java.util.List;
import java.util.Map;

import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel.YandexApiRresponce;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by notbl on 11/25/2017.
 */

public interface YandexApi {

    ///api/v1/dicservice.json/lookup?key=API-ключ&lang=en-ru&translatedText=time
    @FormUrlEncoded
    @POST("https://api.selfdevelopmentcompany.xyz")
    Observable<Response<YandexApiRresponce>> translate(@FieldMap Map<String,String> params);
}
