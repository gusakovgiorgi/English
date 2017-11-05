package company.self.development.rememberenglishexample.search.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arlib.floatingsearchview.FloatingSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.util.ChangeableBundleFragment;

public class SearchFragment extends MvpAppCompatFragment implements ChangeableBundleFragment {
    public static final String TAG = SearchFragment.class.getSimpleName();

    public static final String PARAM1_FOCUS_SEARCH = "param1_focus_search";

    @BindView(R.id.floating_search_view)
    protected FloatingSearchView mSearchView;

    private Bundle mArguments;

    private SearchFragmentListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void setChangeableArguments(Bundle args) {
        mArguments=args;
    }

    public static SearchFragment newInstance(Bundle args) {
        SearchFragment fragment = new SearchFragment();
        fragment.setChangeableArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mFocusSearch = getArguments().getBoolean(PARAM1_FOCUS_SEARCH);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        mSearchView.requestFocus();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            mListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface SearchFragmentListener {
        // TODO: Update argument type and name
//        void showSearchViewClick(Uri uri);
    }
}
