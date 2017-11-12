package company.self.development.rememberenglishexample.search.rest;

import java.util.List;

import company.self.development.rememberenglishexample.model.WordSuggestion;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by notbl on 11/12/2017.
 */

public interface SuggestionsApi {

    @GET("/sug")
    Observable<List<WordSuggestion>> requestSuggestions(@Query("s") String word);
}
