package company.self.development.rememberenglishexample.main.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.home.view.HomeFragment;
import company.self.development.rememberenglishexample.main.classes.router.Router;
import company.self.development.rememberenglishexample.main.interfaces.MainActivityInterface;
import company.self.development.rememberenglishexample.search.view.SearchFragment;

/**
 * Created by Giorgi on 10/24/2017.
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityInterface> {

    private boolean onlySelectNavigationView =true;


    @Override
    public void attachView(MainActivityInterface view) {
        super.attachView(view);
        getViewState().goToHome();
    }

    public boolean selectNavigationItem(MenuItem menuItem){
        if (!onlySelectNavigationView || menuItem.isChecked()){
            return true;
        }
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                getViewState().goToHome();
                return true;
            case R.id.navigation_search:
                getViewState().showMessage(R.string.title_search);
                getViewState().goToSearch(null);
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
        Bundle args=new Bundle(1);
        args.putBoolean(SearchFragment.PARAM1_FOCUS_SEARCH,true);
        getViewState().goToSearch(args);
        onlySelectNavigationView =false;
        getViewState().changeNavigationViewProgrammatically(R.id.navigation_search);
        onlySelectNavigationView =true;
    }

    public void selectProperNavigationEventReceived(String tag) {
        if (tag==null) return;
        if (tag.equals(HomeFragment.TAG)){
            getViewState().changeNavigationViewProgrammatically(R.id.navigation_home);
        }else if (tag.equals(SearchFragment.TAG)){
            getViewState().changeNavigationViewProgrammatically(R.id.navigation_search);
        }
    }
}
