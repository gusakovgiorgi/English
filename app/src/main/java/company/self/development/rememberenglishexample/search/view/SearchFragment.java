package company.self.development.rememberenglishexample.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.add_to_card.AddToCardDialogFragment;
import company.self.development.rememberenglishexample.base.SelectProperNavigationInterface;
import company.self.development.rememberenglishexample.model.Translation;
import company.self.development.rememberenglishexample.model.WordHistorySuggestion;
import company.self.development.rememberenglishexample.model.WordSuggestion;
import company.self.development.rememberenglishexample.search.interfaces.SearchFragmentView;
import company.self.development.rememberenglishexample.search.presenter.SearchFragmentPresenter;
import company.self.development.rememberenglishexample.util.ChangeableBundleFragment;
import company.self.development.rememberenglishexample.util.ToastUtil;

public class SearchFragment extends MvpAppCompatFragment implements SearchFragmentView, ChangeableBundleFragment {
    public static final String TAG = SearchFragment.class.getSimpleName();

    public static final String PARAM1_FOCUS_SEARCH = "param1_focus_search";

    @BindView(R.id.floating_search_view)
    protected FloatingSearchView mSearchView;
    @BindView(R.id.search_bar_text)
    protected View mSearchViewField;
    @BindView(R.id .search_results_list)
    protected RecyclerView mSearchResultsList;
    private SearchResultsListAdapter adapter;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SEARCH_FRAGMENT")
    SearchFragmentPresenter presenter;

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "SEARCH_FRAGMENT")
    SearchFragmentPresenter providePresenterWithContext(){
        return new SearchFragmentPresenter(getContext());
    }

    private FloatingSearchView.OnQueryChangeListener queryChangeListener = (oldQuery, newQuery) -> presenter.queryChange(oldQuery, newQuery);

    private SearchSuggestionsAdapter.OnBindSuggestionCallback onBindSuggestionCallback = (suggestionView, leftIcon, textView, item, itemPosition) -> presenter.onBindSuggestion(suggestionView, leftIcon, textView, item, itemPosition);

    private FloatingSearchView.OnSearchListener searchListener= new FloatingSearchView.OnSearchListener() {
        @Override
        public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            presenter.onSuggestionItemSelected((WordSuggestion) searchSuggestion);
        }

        @Override
        public void onSearchAction(String currentQuery) {
            presenter.onSearchActionClick(currentQuery);
        }
    };

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
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
        presenter.searchStarted(mArguments);
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

    @Override
    public void showSuggestionsHistory(List<WordHistorySuggestion> historySuggestions) {
        mSearchView.swapSuggestions(historySuggestions);
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
    public void focusOnSearchView(boolean focus) {
        if (focus){
            mSearchViewField.requestFocus();
        }else {
            mSearchViewField.clearFocus();
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
        mSearchView.setOnSearchListener(searchListener);
        mSearchView.setShowMoveUpSuggestion(true);
        adapter=new SearchResultsListAdapter();
        adapter.setOnAddCardListener(onAddToCardListener);
        mSearchResultsList.setAdapter(adapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void clearSuggestions() {
        mSearchView.clearSuggestions();
    }

    @Override
    public void setSearchText(String text) {
        mSearchView.setSearchText(text);
    }

    @Override
    public void showTranslations(List<Translation> translations) {
        adapter.swapData(translations);
    }

    private AddToCardDialogFragment.OnSaveListener onSaveListener=result -> {ToastUtil.getInstance().showShortMessage("saved");};
    private AddToCardDialogFragment.OnDiscardListener onDiscardListener=() -> {ToastUtil.getInstance().showShortMessage("discarted");};

    // TODO: 12/14/2017 carry out resources
    private SearchResultsListAdapter.OnAddToCardListener onAddToCardListener=translationModel -> {
        new AddToCardDialogFragment.Builder(getContext())
                .setConfirmButton(R.string.add)
                .setTitle("Дабавить карточку")
                .setOnSaveListener(onSaveListener)
                .setOnDiscardListener(onDiscardListener)
                .build().show(getChildFragmentManager(),"tag");
     };


    public interface SearchFragmentListener extends SelectProperNavigationInterface {
        // TODO: Update argument type and name
//        void showSearchViewClick(Uri uri);
    }
}
