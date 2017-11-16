package company.self.development.rememberenglishexample.main.view;

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
import company.self.development.rememberenglishexample.main.classes.router.Router;

public class MainActivity extends MvpAppCompatActivity implements MainActivityInterface, HomeFragment.HomeFragmentListener,SearchFragment.SearchFragmentListener{

    @BindView(R.id.navigation)
    protected BottomNavigationView mNavigation;
    private Router router;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MAIN_ACTIVITY")
    MainActivityPresenter presenter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = menuItem ->
            presenter.selectNavigationItem(menuItem);


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.v("test", mNavigation.getMaxItemCount() + "");
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()==1){
            finish();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void showMessage(int strRes) {

    }

    @Override
    public void goToHome() {
        getRouter().replaceFragment(R.id.frameLayout,HomeFragment.TAG,true,null);
    }

    @Override
    public void goToSearch(Bundle args) {
        getRouter().replaceFragment(R.id.frameLayout,SearchFragment.TAG,true,args);
    }

    @Override
    public void changeNavigationViewProgrammatically(int menuId) {
        mNavigation.setSelectedItemId(menuId);
    }


    private Router getRouter(){
        if (router==null){
            router=new Router(this);
        }
        return router;
    }

    //############################# fragments callbacks ########################################
    @Override
    public void showSearchViewClick() {
        presenter.showSearchEventReceived();
    }

    @Override
    public void selectNavigate(String tag) {
        presenter.selectProperNavigationEventReceived(tag);
    }
}
