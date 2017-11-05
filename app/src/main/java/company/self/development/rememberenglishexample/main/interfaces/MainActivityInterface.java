package company.self.development.rememberenglishexample.main.interfaces;

import android.content.Context;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Giorgi on 10/24/2017.
 */

public interface MainActivityInterface extends MvpView {
    void showMessage(int strRes);
    void goToHome();
    void goToSearch();
    void changeNavigationViewProgrammatically(int menuId);
}
