package company.self.development.rememberenglishexample.search.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.base.SelectProperNavigationInterface;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import company.self.development.rememberenglishexample.search.interfaces.SearchFragmentView;
import company.self.development.rememberenglishexample.search.presenter.SearchFragmentPresenter;
import company.self.development.rememberenglishexample.util.ChangeableBundleFragment;

public class SearchFragment extends MvpAppCompatFragment implements SearchFragmentView, ChangeableBundleFragment {
    public static final String TAG = SearchFragment.class.getSimpleName();

    public static final String PARAM1_FOCUS_SEARCH = "param1_focus_search";

    @BindView(R.id.floating_search_view)
    protected FloatingSearchView mSearchView;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SEARCH_FRAGMENT")
    SearchFragmentPresenter presenter;

    private FloatingSearchView.OnQueryChangeListener queryChangeListener = (oldQuery, newQuery) -> presenter.queryChange(oldQuery, newQuery);

    private SearchSuggestionsAdapter.OnBindSuggestionCallback onBindSuggestionCallback = (suggestionView, leftIcon, textView, item, itemPosition) -> presenter.onBindSuggestion(suggestionView, leftIcon, textView, item, itemPosition);
    private Bundle mArguments;

    private SearchFragmentListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void setChangeableArguments(Bundle args) {
        mArguments = args;
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mSearchView.findViewById(R.id.search_bar_text).requestFocus();
        setupSearchView();
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
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.selectNavigate(TAG);
        }
        presenter.searchFocused();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showSuggestions(List<WordSuggestion> suggestions) {
        mSearchView.swapSuggestions(suggestions);
    }

    @SuppressLint("WrongThread")
    @Override
    @WorkerThread
    @MainThread
    public void showSearchProgress(boolean show) {
        if (Looper.myLooper()!=Looper.getMainLooper()){
            AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
            if (appCompatActivity!=null){
                appCompatActivity.runOnUiThread(() -> executeProgress(show));
            }
        }else{
            executeProgress(show);
        }
    }

    @MainThread
    private void executeProgress(boolean show){
        if (show){
            mSearchView.showProgress();
        }else {
            mSearchView.hideProgress();
        }
    }

    @Override
    public void setSuggestionsHistoryIcon(ImageView leftIcon) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity == null) return;
        leftIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.ic_history_black_24dp));
    }

    private void setupSearchView() {
        mSearchView.setOnQueryChangeListener(queryChangeListener);
        mSearchView.setOnBindSuggestionCallback(onBindSuggestionCallback);
    }

    public interface SearchFragmentListener extends SelectProperNavigationInterface {
        // TODO: Update argument type and name
//        void showSearchViewClick(Uri uri);
    }
}
