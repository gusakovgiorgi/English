package company.self.development.rememberenglishexample.main.classes.router;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import company.self.development.rememberenglishexample.home.view.HomeFragment;
import company.self.development.rememberenglishexample.search.view.SearchFragment;
import company.self.development.rememberenglishexample.util.ChangeableBundleFragment;

/**
 * Created by notbl on 11/4/2017.
 */

public class Router {

    private WeakReference<AppCompatActivity> activityWeakReference;

    public Router(AppCompatActivity appCompatActivity) {
        activityWeakReference = new WeakReference<AppCompatActivity>(appCompatActivity);
    }

    public void goToHome(Context context, int layoutId, String tag, Bundle args) {
        RouterUtils.replaceFragment(context, layoutId, tag, args);
    }


    public void replaceFragment(int layoutId, String tag, Boolean addToBackStack, Bundle args) {
        FragmentManager fragmentManager = getSupportFragmentManager(activityWeakReference.get());
        if (isInBackStack(fragmentManager, tag)) {
            return;
        }
        Fragment fragment = getFragmentByTag(fragmentManager, tag, args);
        if (!fragment.isVisible()) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction()
                    .replace(layoutId, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    private boolean isInBackStack(FragmentManager fragmentManager, String tag) {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(i);
            if (backStackEntry.getName() != null && backStackEntry.getName().equals(tag)) {
                fragmentManager.popBackStack(backStackEntry.getId(), 0);
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
        } else if (args != null) {
            ((ChangeableBundleFragment) fragment).setChangeableArguments(args);
        }
        return fragment;
    }

    private static FragmentManager getSupportFragmentManager(AppCompatActivity appCompatActivity) {
        FragmentManager fragmentManager;
        try {
            fragmentManager = appCompatActivity.getSupportFragmentManager();
        } catch (NullPointerException e) {
            e.printStackTrace();
            fragmentManager = null;
        }
        return fragmentManager;
    }

}
