package company.self.development.rememberenglishexample.settings;

import android.support.v4.app.Fragment;

import java.util.Map;

import company.self.development.rememberenglishexample.home.view.HomeFragment;

/**
 * Created by notbl on 11/4/2017.
 */

public class Settings {
    static FragmentTable fragmentTable=new FragmentTable();

    public static Class getFragmentClass(String tag){
        return fragmentTable.getFragment(tag);
    }

    public static Fragment getDefaultFragment() {
        return new HomeFragment();
    }
}
