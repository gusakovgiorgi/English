package company.self.development.rememberenglishexample.main.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.home.view.HomeFragment;
import company.self.development.rememberenglishexample.main.interfaces.MainActivityInterface;
import company.self.development.rememberenglishexample.main.presenter.MainActivityPresenter;
import company.self.development.rememberenglishexample.search.view.SearchFragment;
import company.self.development.rememberenglishexample.util.router.Router;

public class MainActivity extends MvpAppCompatActivity implements MainActivityInterface, HomeFragment.HomeFragmentListener, SearchFragment.SearchFragmentListener {

    @BindView(R.id.message)
    protected TextView mTextMessage;
    @BindView(R.id.navigation)
    protected BottomNavigationView mNavigation;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MAIN_ACTIVITY")
    MainActivityPresenter presenter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = menuItem ->
            presenter.handleNavigationItemClick(menuItem);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.v("test", mNavigation.getMaxItemCount() + "");
    }

    @Override
    public void showMessage(int strRes) {
        mTextMessage.setText(getString(strRes));
    }

    @Override
    public void goToHome() {
        Router.getInstance().goToHome(this, R.id.frameLayout, HomeFragment.TAG,null);
    }

    @Override
    public void goToSearch() {
        Router.getInstance().goToSearch(this, R.id.frameLayout, SearchFragment.TAG,null);
    }

    @Override
    public void changeNavigationViewProgrammatically(int menuId) {
        mNavigation.setSelectedItemId(menuId);
    }

    //############################## interfaces callback#######################################################
    @Override
    public void showSearchViewClick() {
        presenter.showSearchEventReceived();
    }
}
