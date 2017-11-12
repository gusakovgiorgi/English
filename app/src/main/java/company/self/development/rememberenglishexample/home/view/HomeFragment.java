package company.self.development.rememberenglishexample.home.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.base.SelectProperNavigationInterface;
import company.self.development.rememberenglishexample.home.interfaces.HomeFragmentView;
import company.self.development.rememberenglishexample.home.presenter.HomeFragmentPresenter;
import company.self.development.rememberenglishexample.util.ChangeableBundleFragment;

public class HomeFragment extends MvpAppCompatFragment implements HomeFragmentView,ChangeableBundleFragment{
    
    public static final String TAG = HomeFragment.class.getSimpleName();
    @InjectPresenter
    HomeFragmentPresenter presenter;

    private Bundle mArguments;

    @BindView(R.id.search_bar_text)
    protected EditText mSearchEditText;

    private HomeFragmentListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Bundle args) {
        HomeFragment fragment = new HomeFragment();
        fragment.setChangeableArguments(args);
        return fragment;
    }

    @Override
    public void setChangeableArguments(Bundle args) {
        mArguments=args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mListener!=null){
            mListener.selectNavigate(TAG);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.search_bar_text)
    void onSearchClick(View view){
        presenter.onSearchClick(view);
    }

    @Override
    public void showSearch() {
        if (mListener!=null){
            mListener.showSearchViewClick();
        }
    }

    public interface HomeFragmentListener extends SelectProperNavigationInterface{
        void showSearchViewClick();
    }
}
