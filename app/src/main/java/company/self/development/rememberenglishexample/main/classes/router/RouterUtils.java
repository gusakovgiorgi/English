package company.self.development.rememberenglishexample.main.classes.router;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.home.view.HomeFragment;
import company.self.development.rememberenglishexample.search.view.SearchFragment;
import company.self.development.rememberenglishexample.settings.Settings;
import company.self.development.rememberenglishexample.util.ChangeableBundleFragment;
import company.self.development.rememberenglishexample.util.ToastUtil;

/**
 * Created by notbl on 11/4/2017.
 */

class RouterUtils {
    private static FragmentManager getSupportFragmentManager(Context context) {
        FragmentManager fragmentManager;
        try {
            fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
            fragmentManager = null;
        }
        return fragmentManager;
    }

    public static void replaceFragment(Context context, int layoutId, String tag,Bundle args) {
        FragmentManager fragmentManager = getSupportFragmentManager(context);
        if (isInBackStack(fragmentManager,tag)){return;}
        Fragment fragment = getFragmentByTag(fragmentManager, tag,args);
        if (!fragment.isVisible()) {
            fragmentManager
                    .beginTransaction()
                    .replace(layoutId, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    private static boolean isInBackStack(FragmentManager fragmentManager, String tag) {
        int backStackCount=fragmentManager.getBackStackEntryCount();
        for (int i=0; i<backStackCount;i++){
            FragmentManager.BackStackEntry backStackEntry=fragmentManager.getBackStackEntryAt(i);
            if (backStackEntry.getName()!=null && backStackEntry.getName().equals(tag)){
                fragmentManager.popBackStack(backStackEntry.getId(),0);
                return true;
            }
        }
        return false;
    }


    private static Fragment getFragmentByTag(FragmentManager fragmentManager, String tag, Bundle args) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (tag == HomeFragment.TAG) {
                fragment = HomeFragment.newInstance(args);
            } else if (tag == SearchFragment.TAG) {
                fragment = SearchFragment.newInstance(args);
            }
        }else if (args!=null){
            ((ChangeableBundleFragment)fragment).setChangeableArguments(args);
        }
        return fragment;
    }

    private static void getDefaultFragment() {
        Fragment fragment;
        fragment = Settings.getDefaultFragment();
        ToastUtil.getInstance().showLongMessage(R.string.show_fragment_error_string);
    }

}
