package company.self.development.rememberenglishexample.search.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import company.self.development.rememberenglishexample.base.GlobalSettings;
import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.Translation;
import company.self.development.rememberenglishexample.model.WordHistorySuggestion;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import company.self.development.rememberenglishexample.search.interfaces.SearchFragmentView;
import company.self.development.rememberenglishexample.search.rest.SuggestionsApi;
import company.self.development.rememberenglishexample.search.rest.yandexApi.YandexApi;
import company.self.development.rememberenglishexample.search.view.SearchFragment;
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
    private Retrofit searchRestAdapter;
    private SuggestionsApi suggestionsApi;
    private YandexApi searchApi;
    private Disposable suggestionsDisposable;
    private Set<WordSuggestion> selectedSuggestions;
    private boolean showSuggestions=true;


    public SearchFragmentPresenter() {
        createRestAdapter();
        initFields();
    }

    public void searchStarted(Bundle args){
        if (args!=null && args.getBoolean(SearchFragment.PARAM1_FOCUS_SEARCH)){
            getViewState().focusOnSearchView(true);
            showSuggestionsHistory();
        }

    }
    public void queryChange(String oldWord, String newWord){
        if (suggestionsDisposable!=null && !suggestionsDisposable.isDisposed()){
            suggestionsDisposable.dispose();
        }
        if (newWord.isEmpty()){
            showSuggestionsHistory();
            return;
        }
        if (showSuggestions) {
            suggestionsDisposable = getSuggestionsApi().requestSuggestions(newWord)
                    .doOnSubscribe(disposable -> getViewState().showSearchProgress(true))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .delaySubscription(GlobalSettings.SUGGESTION_REQUEST_DELAY_SEC, TimeUnit.SECONDS)
                    .subscribe(suggestionList -> {
                        getViewState().showSearchProgress(false);
                        Collections.sort(suggestionList);
                        getViewState().showSuggestions(suggestionList);
                    });
        }
        showSuggestions=true;

    }

    private void showSuggestionsHistory() {
        showSuggestions=false;
        List<WordHistorySuggestion> wordHistorySuggestions=getSuggestionsHistory();
        getViewState().showSuggestionsHistory(wordHistorySuggestions);
    }

    private List<WordHistorySuggestion> getSuggestionsHistory() {
        List<WordHistorySuggestion> wordHistorySuggestions=new ArrayList<>(selectedSuggestions.size());
        Iterator<WordSuggestion> iterator=selectedSuggestions.iterator();
        while (iterator.hasNext()){
            wordHistorySuggestions.add(new WordHistorySuggestion(iterator.next().getBody()));
        }
        return wordHistorySuggestions;
    }


    public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
        if (item instanceof WordHistorySuggestion){
            getViewState().setSuggestionsHistoryIcon(leftIcon);
        }
    }

    private SuggestionsApi getSuggestionsApi(){
        if (suggestionsApi==null) {
            suggestionsApi= suggestionsRestAdapter.create(SuggestionsApi.class);
        }
        return suggestionsApi;
    }

    private YandexApi getSearchApi(){
        if (searchApi==null){
            searchApi=searchRestAdapter.create(YandexApi.class);
        }
        return searchApi;
    }

    private void createRestAdapter() {
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

        searchRestAdapter=new Retrofit.Builder()
                .baseUrl("https://dictionary.yandex.net") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    private void initFields() {
        selectedSuggestions=new TreeSet<>(new Comparator<WordSuggestion>() {
            @Override
            public int compare(WordSuggestion wordSuggestion, WordSuggestion t1) {
                if (wordSuggestion.getBody().equals(t1.getBody())){
                    return 0;
                }
                return -Long.compare(wordSuggestion.getSelectedTime(),t1.getSelectedTime());
            }
        });
        // TODO: 11/14/2017 load saved suggestions from db
    }

    public void suggestionItemSelected(WordSuggestion item) {
        item.setSelectedTime(System.currentTimeMillis());
        selectedSuggestions.add(item);
        showSuggestions =false;
        getViewState().setSearchText(item.getBody());
        getViewState().clearSuggestions();
        getViewState().showSearchProgress(true);

    }

    public void onSearchActionClick(String currentQuery) {
        getSearchApi().translate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    ITranslation responceTranslation=(ITranslation)response;
                    if (responceTranslation!=null && responceTranslation.getTranslations()!=null){
                        for (Translation translation: responceTranslation.getTranslations()){
                            Log.v("debug","original="+translation.getOriginalWord());
                            Log.v("debug","transription= "+translation.getTranscription());
                            Log.v("debug","translations = "+ Arrays.toString(translation.getTranslationWord().toArray()));
                            Log.v("debug","examples = "+ Arrays.toString(translation.getExample().toArray()));
                        }
                    }
                });
    }
}

