package company.self.development.rememberenglishexample.main.main.presenter;

import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.main.main.view.MainActivityInterface;

/**
 * Created by Giorgi on 10/24/2017.
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityInterface> {



    public boolean handleNavigationItemClick(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                getViewState().showMessage(R.string.title_home);
                return true;
            case R.id.navigation_search:
                getViewState().showMessage(R.string.title_search);
                return true;
            case R.id.navigation_card:
                getViewState().showMessage(R.string.title_card);
                return true;
            case R.id.navigation_settings:
                getViewState().showMessage(R.string.title_settings);
                return true;
            case R.id.navigation_learner:
                getViewState().showMessage(R.string.title_learner);
                return true;
        }
        return false;
    }
}
