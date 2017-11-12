package company.self.development.rememberenglishexample;

import android.app.Application;

import company.self.development.rememberenglishexample.util.ToastUtil;
import retrofit2.Retrofit;

/**
 * Created by notbl on 11/4/2017.
 */

public class RememberEnglishApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.getInstance().init(this);
    }
}
