package company.self.development.rememberenglishexample.main.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Giorgi on 10/24/2017.
 */

@StateStrategyType(value = SkipStrategy.class)
public interface MainActivityInterface extends MvpView {
    void showMessage(int strRes);
    void goToHome();
    void goToSearch(Bundle args);
    void changeNavigationViewProgrammatically(int menuId);
}
