package company.self.development.rememberenglishexample.search.presenter;

import android.content.Context;
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

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.base.GlobalSettings;
import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.Language;
import company.self.development.rememberenglishexample.model.TranslateRequest;
import company.self.development.rememberenglishexample.model.Translation;
import company.self.development.rememberenglishexample.model.WordHistorySuggestion;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import company.self.development.rememberenglishexample.search.interfaces.SearchFragmentView;
import company.self.development.rememberenglishexample.search.rest.SuggestionsApi;
import company.self.development.rememberenglishexample.search.rest.yandexApi.YandexApi;
import company.self.development.rememberenglishexample.search.rest.yandexApi.YandexApiKey;
import company.self.development.rememberenglishexample.search.rest.yandexApi.YandexApiRequest;
import company.self.development.rememberenglishexample.search.view.SearchFragment;
import company.self.development.rememberenglishexample.util.ToastUtil;
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
    private boolean showSuggestions = true;
    private Context applicationContext;
    private String currentSearchText;


    public SearchFragmentPresenter(Context context) {
        this.applicationContext = context.getApplicationContext();
        currentSearchText="";
        createRestAdapter();
        initFields();
    }

    public void searchStarted(Bundle args) {
        if (args != null && args.getBoolean(SearchFragment.PARAM1_FOCUS_SEARCH)) {
            getViewState().focusOnSearchView(true);
            showSuggestionsHistory();
        }

    }

    public void queryChange(String oldWord, String newWord) {
        disposePreviousSuggetsionsRequest();
        if (newWord.isEmpty()) {
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
        showSuggestions = true;

    }

    private void disposePreviousSuggetsionsRequest() {
        if (suggestionsDisposable != null && !suggestionsDisposable.isDisposed()) {
            suggestionsDisposable.dispose();
        }
    }

    private void showSuggestionsHistory() {
        List<WordHistorySuggestion> wordHistorySuggestions = getSuggestionsHistory();
        getViewState().showSuggestionsHistory(wordHistorySuggestions);
    }

    private List<WordHistorySuggestion> getSuggestionsHistory() {
        List<WordHistorySuggestion> wordHistorySuggestions = new ArrayList<>(selectedSuggestions.size());
        Iterator<WordSuggestion> iterator = selectedSuggestions.iterator();
        while (iterator.hasNext()) {
            wordHistorySuggestions.add(new WordHistorySuggestion(iterator.next().getBody()));
        }
        return wordHistorySuggestions;
    }


    public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
        if (item instanceof WordHistorySuggestion) {
            getViewState().setSuggestionsHistoryIcon(leftIcon);
        }
    }

    private SuggestionsApi getSuggestionsApi() {
        if (suggestionsApi == null) {
            suggestionsApi = suggestionsRestAdapter.create(SuggestionsApi.class);
        }
        return suggestionsApi;
    }

    private YandexApi getSearchApi() {
        if (searchApi == null) {
            searchApi = searchRestAdapter.create(YandexApi.class);
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

        searchRestAdapter = new Retrofit.Builder()
                .baseUrl("https://dictionary.yandex.net") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    private void initFields() {
        selectedSuggestions = new TreeSet<>(new Comparator<WordSuggestion>() {
            @Override
            public int compare(WordSuggestion wordSuggestion, WordSuggestion t1) {
                if (wordSuggestion.getBody().equals(t1.getBody())) {
                    return 0;
                }
                return -Long.compare(wordSuggestion.getSelectedTime(), t1.getSelectedTime());
            }
        });
        // TODO: 11/14/2017 load saved suggestions from db
    }

    public void onSuggestionItemSelected(WordSuggestion item) {
        item.setSelectedTime(System.currentTimeMillis());
        selectedSuggestions.add(item);
        showSuggestions = false;
        getViewState().setSearchText(item.getBody());
        getViewState().clearSuggestions();
        getViewState().showSearchProgress(true);
        translate(item.getBody());
    }

    public void onSearchActionClick(String currentQuery) {
        disposePreviousSuggetsionsRequest();
        getViewState().clearSuggestions();
        getViewState().showSearchProgress(true);
        translate(currentQuery);

    }

    private void translate(String currentQuery) {
        currentSearchText=currentQuery;
        YandexApiKey yandexApiKey = new YandexApiKey(applicationContext);
        TranslateRequest request = new YandexApiRequest(Language.getDefaultDirection(), yandexApiKey, currentQuery);
        getSearchApi().translate(request.getPostParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getViewState().showSearchProgress(false);
                    if (response.headers() != null && response.headers().get("api_key") != null) {
                        yandexApiKey.checkApiKey(response.headers().get("api_key"));
                    }
                    ITranslation responceTranslation = response.body();
                    if (responceTranslation != null && responceTranslation.getTranslations() != null) {
                        getViewState().showTranslations(responceTranslation.getTranslations());
                        for (Translation translation : responceTranslation.getTranslations()) {
                            Log.v("debug", "original=" + translation.getOriginalWord());
                            Log.v("debug", "transription= " + translation.getTranscription());
                            Log.v("debug", "translations = " + Arrays.toString(translation.getTranslationWord().toArray()));
                            Log.v("debug", "examples = " + Arrays.toString(translation.getExample().toArray()));
                        }
                    }
                }, throwable -> {
                    getViewState().showSearchProgress(false);
                    ToastUtil.getInstance().showShortMessage(R.string.something_went_wrong);
                    throwable.printStackTrace();

                });
    }

    public void createOwnCardClicked(View mCreateOwnCardView) {
        getViewState().showEmptyDialog(currentSearchText);
    }
}

