package company.self.development.rememberenglishexample.util;

import android.content.Context;
import android.widget.Toast;

import company.self.development.rememberenglishexample.exceptions.ToastUtilNotInitializedException;

/**
 * Created by notbl on 11/4/2017.
 */

public class ToastUtil {
    private Context mApplicationContext;

    private static final ToastUtil ourInstance = new ToastUtil();

    public static ToastUtil getInstance() {
        return ourInstance;
    }

    public void init(Context context){
        mApplicationContext=context.getApplicationContext();
    }

    private ToastUtil() {
    }

    public void showShortMessage(String msg){;
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public void showLongMessage(String msg){
        showMessage(msg, Toast.LENGTH_LONG);
    }

    public void showShortMessage(int msgResId){;
        showMessage(getStringFromResourse(msgResId), Toast.LENGTH_SHORT);
    }

    public void showLongMessage(int msgResId){
        showMessage(getStringFromResourse(msgResId), Toast.LENGTH_LONG);
    }

    private void showMessage(String msg,int duration){
        checkToastUtilInitiation();
        Toast.makeText(mApplicationContext,msg,duration).show();
    }

    private void checkToastUtilInitiation() {
        if (mApplicationContext==null){
            throw new ToastUtilNotInitializedException("should ToastUtil.getIntstance.init(context) before using this class");
        }
    }
    private String getStringFromResourse(int msgResId) {
        checkToastUtilInitiation();
        return mApplicationContext.getString(msgResId);
    }
}
