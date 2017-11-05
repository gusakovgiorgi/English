package company.self.development.rememberenglishexample.settings;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import company.self.development.rememberenglishexample.home.view.HomeFragment;
import company.self.development.rememberenglishexample.search.view.SearchFragment;

/**
 * Created by notbl on 11/4/2017.
 */

class FragmentTable {
    static private Map<String,Class> fragmentMap;
    static {
        fragmentMap=new HashMap<>();
        fragmentMap.put(HomeFragment.TAG,HomeFragment.class);
        fragmentMap.put(SearchFragment.TAG,SearchFragment.class);
    }

    Class getFragment(String tag){
        return fragmentMap.get(tag);
    }
}
