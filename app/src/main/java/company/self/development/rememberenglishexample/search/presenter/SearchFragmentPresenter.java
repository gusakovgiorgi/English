package company.self.development.rememberenglishexample.search.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import company.self.development.rememberenglishexample.base.GlobalSettings;
import company.self.development.rememberenglishexample.model.WordHistorySuggestion;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import company.self.development.rememberenglishexample.search.interfaces.SearchFragmentView;
import company.self.development.rememberenglishexample.search.rest.SuggestionsApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by notbl on 11/12/2017.
 */
@InjectViewState
public class SearchFragmentPresenter extends MvpPresenter<SearchFragmentView> {

    private Retrofit suggestionsRestAdapter;
    private SuggestionsApi suggestionsApi;
    private Disposable suggestionsDisposable;


    public SearchFragmentPresenter() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        suggestionsRestAdapter = new Retrofit.Builder()
                .baseUrl("https://api.datamuse.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public void queryChange(String oldWord, String newWord){
        if (suggestionsDisposable!=null && !suggestionsDisposable.isDisposed()){
            suggestionsDisposable.dispose();
        }
        suggestionsDisposable=getSuggestionsApi().requestSuggestions(newWord)
                .doOnSubscribe(disposable -> getViewState().showSearchProgress(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(GlobalSettings.SUGGESTION_REQUEST_DELAY_SEC,TimeUnit.SECONDS)
                .subscribe(suggestionList ->{
                    getViewState().showSearchProgress(false);
                    Collections.sort(suggestionList);
                    getViewState().showSuggestions(suggestionList);});
    }

    public void searchFocused(){
        List<WordSuggestion> wordHistorySuggestions=new ArrayList<>();
        wordHistorySuggestions.add(new WordHistorySuggestion("hello"));
        wordHistorySuggestions.add(new WordHistorySuggestion("hellios"));
        wordHistorySuggestions.add(new WordHistorySuggestion("henry"));
        wordHistorySuggestions.add(new WordHistorySuggestion("hell"));
        wordHistorySuggestions.add(new WordHistorySuggestion("heisenberg"));
        getViewState().showSuggestions(wordHistorySuggestions);
    }



    public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
        if (item instanceof WordHistorySuggestion){
            getViewState().setSuggestionsHistoryIcon(leftIcon);
        }
    }

    private SuggestionsApi getSuggestionsApi(){
        return suggestionsRestAdapter.create(SuggestionsApi.class);
    }
}
