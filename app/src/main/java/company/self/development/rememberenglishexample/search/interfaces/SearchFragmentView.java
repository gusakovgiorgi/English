package company.self.development.rememberenglishexample.search.interfaces;

import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import company.self.development.rememberenglishexample.model.WordHistorySuggestion;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import io.reactivex.Single;

/**
 * Created by notbl on 11/12/2017.
 */

public interface SearchFragmentView extends MvpView{

    @StateStrategyType(SingleStateStrategy.class)
    void showSuggestions(List<WordSuggestion> suggestions);
    void setSuggestionsHistoryIcon(ImageView leftIcon);
    @StateStrategyType(SingleStateStrategy.class)
    void showSearchProgress(boolean show);
}
