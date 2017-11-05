package company.self.development.rememberenglishexample.util.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by notbl on 11/4/2017.
 */

public class Router implements RouterInterface{
    private static final Router ourInstance = new Router();

    public static Router getInstance() {
        return ourInstance;
    }

    private Router() {
    }

    @Override
    public void goToHome(Context context, int layoutId, String tag,Bundle args) {
        RouterUtils.replaceFragment(context, layoutId, tag,args);
    }

    @Override
    public void goToSearch(Context context, int layoutId, String tag, Bundle args) {
        RouterUtils.replaceFragment(context,layoutId,tag,args);
    }

    @Override
    public void goToCard(Context context, int layoutId,String tag,Bundle args) {

    }

    @Override
    public void goToLearning(Context context, int layoutId,String tag,Bundle args) {

    }

    @Override
    public void goToSettings(Context context) {

    }

}
