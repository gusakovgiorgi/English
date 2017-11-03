package company.self.development.rememberenglishexample.main.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.main.main.presenter.MainActivityPresenter;
import company.self.development.rememberenglishexample.main.main.view.MainActivityInterface;

public class MainActivity extends MvpAppCompatActivity implements MainActivityInterface {

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

        SearchView searchView=null;

        searchView.setSuggestionsAdapter(adapter);

        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.v("test", mNavigation.getMaxItemCount() + "");
    }

    @Override
    public void showMessage(int strRes) {
        mTextMessage.setText(getString(strRes));
    }
}
