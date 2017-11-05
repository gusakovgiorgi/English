package company.self.development.rememberenglishexample.main.presenter;

import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.main.interfaces.MainActivityInterface;

/**
 * Created by Giorgi on 10/24/2017.
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityInterface> {

    private boolean onlySelectNavigationView =true;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().goToHome();
    }

    public boolean handleNavigationItemClick(MenuItem menuItem){
        if (!onlySelectNavigationView){
            return true;
        }
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                getViewState().goToHome();
                return true;
            case R.id.navigation_search:
                getViewState().showMessage(R.string.title_search);
                getViewState().goToSearch();
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

    public void showSearchEventReceived() {
        getViewState().goToSearch();
        onlySelectNavigationView =false;
        getViewState().changeNavigationViewProgrammatically(R.id.navigation_search);
        onlySelectNavigationView =true;
    }
}
