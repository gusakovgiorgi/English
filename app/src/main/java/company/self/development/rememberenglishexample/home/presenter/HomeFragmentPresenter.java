package company.self.development.rememberenglishexample.home.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import company.self.development.rememberenglishexample.home.interfaces.HomeFragmentView;

/**
 * Created by notbl on 11/5/2017.
 */
@InjectViewState
public class HomeFragmentPresenter extends MvpPresenter<HomeFragmentView> {


    public void onSearchClick(View v){
        v.clearFocus();
        getViewState().showSearch();
    }
}
