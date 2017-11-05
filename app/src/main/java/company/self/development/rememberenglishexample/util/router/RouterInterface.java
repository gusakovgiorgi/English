package company.self.development.rememberenglishexample.util.router;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by notbl on 11/4/2017.
 */

interface RouterInterface {
    void goToHome(Context context, int layoutId, String TAG,Bundle args);
    public void goToSearch(Context context, int layoutId, String tag, Bundle args);
    void goToCard(Context context, int layoutId,String TAG,Bundle args);
    void goToLearning(Context context, int layoutId,String TAG,Bundle args);
    void goToSettings(Context context);
}
