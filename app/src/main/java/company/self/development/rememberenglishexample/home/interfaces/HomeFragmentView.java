package company.self.development.rememberenglishexample.home.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by notbl on 11/5/2017.
 */

public interface HomeFragmentView extends MvpView{

    @StateStrategyType(value = SkipStrategy.class)
    void showSearch();
}
