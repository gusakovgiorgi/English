package company.self.development.rememberenglishexample.search.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import company.self.development.rememberenglishexample.R;
import company.self.development.rememberenglishexample.model.Translation;

/**
 * Created by notbl on 12/9/2017.
 */

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {

    private DisplayMetrics mDisplayMetrics;
    private WindowManager mWindowManager;
    private List<Translation> mDataSet;

    public SearchResultsListAdapter() {
        mDataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.translate_word_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTranslation.setText(getStringFromTranslationWords(mDataSet.get(position).getTranslationWord()));
        String examplesString=getStringFromExamples(mDataSet.get(position).getExample());
        if (examplesString.isEmpty()) {
            holder.mDivider.setVisibility(View.INVISIBLE);
            holder.mExample.setText("");
        }else {
            setDividerWidth(holder);
            holder.mDivider.setVisibility(View.VISIBLE);
            holder.mExample.setText(examplesString);
        }
    }

    private void setDividerWidth(ViewHolder holder) {
        // TODO: 12/10/2017 if we refresh search view measure increase
        measureView(holder.mTextContainer,holder.mTextContainer.getContext());
        holder.mDivider.getLayoutParams().width=holder.mTextContainer.getMeasuredWidth();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void swapData(List<Translation> translations) {
        mDataSet = translations;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTranslation;
        public final TextView mExample;
        public final View mTextContainer;
        public final View mDivider;

        public ViewHolder(View view) {
            super(view);
            mTranslation = (TextView) view.findViewById(R.id.color_name);
            mExample = (TextView) view.findViewById(R.id.example);
            mTextContainer = view.findViewById(R.id.text_container);
            mDivider=view.findViewById(R.id.translate_card_divider_above_example);
        }
    }


    private static String getStringFromTranslationWords(List<String> translationStrings) {
        StringBuilder result = new StringBuilder();
        for (String translationString : translationStrings) {
            result.append(translationString).append(",").append(" ");
        }
        deleteLastCharacters(2,result);

        return result.toString();
    }

    private static String getStringFromExamples(List<String> examples) {
        StringBuilder result = new StringBuilder();
        for (String example : examples) {
            result.append(example).append(".").append("\n");
        }
        deleteLastCharacters(1,result);
        return result.toString();
    }


    private static void deleteLastCharacters(int length,StringBuilder result) {
        if (result!=null && result.length()>2){
            result.delete(result.length()-length,result.length());
        }
    }

    private void measureView(View view, Context context){
        DisplayMetrics displayMetrics=getDisplayMetrics(context);
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    private DisplayMetrics getDisplayMetrics(Context context){
        if (mDisplayMetrics==null){
            mDisplayMetrics=new DisplayMetrics();
            getWindowManager(context).getDefaultDisplay().getMetrics(mDisplayMetrics);
        }
        return mDisplayMetrics;
    }

    private WindowManager getWindowManager(Context context){
        if (mWindowManager==null){
            mWindowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

}
