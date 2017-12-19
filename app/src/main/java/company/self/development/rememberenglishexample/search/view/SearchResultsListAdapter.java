package company.self.development.rememberenglishexample.search.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.model.Translation;
import company.self.development.rememberenglishexample.search.utils.SearchUtils;

/**
 * Created by notbl on 12/9/2017.
 */

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {

    private DisplayMetrics mDisplayMetrics;
    private WindowManager mWindowManager;
    private List<Translation> mDataSet;
    private OnAddToCardListener mListener;

    public SearchResultsListAdapter() {
        mDataSet = new ArrayList<>();
    }

    public void setOnAddCardListener(OnAddToCardListener listener) {
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.translate_word_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextContainer.setTag(mDataSet.get(position));
        holder.mTextContainer.setOnClickListener(containerClickListener);
        holder.mTranslation.setText(SearchUtils.getStringFromTranslationWords(mDataSet.get(position).getTranslationWord()));
        String examplesString = SearchUtils.getStringFromExamples(mDataSet.get(position).getExample());
        if (examplesString.isEmpty()) {
            holder.mDivider.setVisibility(View.INVISIBLE);
            holder.mExample.setText("");
        } else {
            setDividerWidth(holder);
            holder.mDivider.setVisibility(View.VISIBLE);
            holder.mExample.setText(examplesString);
        }
    }

    private void setDividerWidth(ViewHolder holder) {
        holder.mTranslation.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        ViewGroup.LayoutParams params = holder.mDivider.getLayoutParams();
        params.width = holder.mTranslation.getMeasuredWidth();
        holder.mDivider.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void swapData(List<Translation> translations) {
        mDataSet = translations;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTranslation;
        final TextView mExample;
        final View mTextContainer;
        final View mDivider;

        public ViewHolder(View view) {
            super(view);
            mTranslation = (TextView) view.findViewById(R.id.color_name);
            mExample = (TextView) view.findViewById(R.id.example);
            mTextContainer = view.findViewById(R.id.text_container);
            mDivider = view.findViewById(R.id.translate_card_divider_above_example);
        }

    }


    private View.OnClickListener containerClickListener = v -> {
        dispatchEvent((Translation) v.getTag());
    };

    private void dispatchEvent(Translation translationModel) {
        if (mListener != null) {
            mListener.addToCard(translationModel);
        }
    }

    interface OnAddToCardListener {
        void addToCard(Translation translationModel);
    }

}
