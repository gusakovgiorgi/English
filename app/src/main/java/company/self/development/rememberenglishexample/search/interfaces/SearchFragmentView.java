package company.self.development.rememberenglishexample.search.interfaces;

import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.Translation;
import company.self.development.rememberenglishexample.model.WordHistorySuggestion;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import io.reactivex.Single;

/**
 * Created by notbl on 11/12/2017.
 */
@StateStrategyType(SkipStrategy.class)
public interface SearchFragmentView extends MvpView{
    void showSuggestions(List<WordSuggestion> suggestions);
    @StateStrategyType(SkipStrategy.class)
    void showSuggestionsHistory(List<WordHistorySuggestion> historySuggestions);
    void setSuggestionsHistoryIcon(ImageView leftIcon);
    void showSearchProgress(boolean show);
    void clearSuggestions();
    void setSearchText(String text);
    @StateStrategyType(SkipStrategy.class)
    void focusOnSearchView(boolean focus);
    void showTranslations(List<Translation> translations);
}
