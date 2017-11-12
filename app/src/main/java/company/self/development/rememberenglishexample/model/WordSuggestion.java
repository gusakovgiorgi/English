package company.self.development.rememberenglishexample.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.gson.annotations.SerializedName;

/**
 * Created by notbl on 11/12/2017.
 */

public class WordSuggestion implements SearchSuggestion, Comparable<WordSuggestion> {


    private String word;
    /**
     * priority that represents words popularity in vocabulary
     *
     */
    @SerializedName("score")
    private int priority;

    public WordSuggestion(Parcel in) {
        word=in.readString();
        priority =in.readInt();
    }

    public WordSuggestion(String word) {
        this.word = word;
    }

    @Override
    public String getBody() {
        return word;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(word);
        parcel.writeInt(priority);
    }

    @Override
    public int compareTo(@NonNull WordSuggestion wordSuggestion) {
        return wordSuggestion.priority -this.priority;
    }

    public static final Creator<WordSuggestion> CREATOR = new Creator<WordSuggestion>() {
        @Override
        public WordSuggestion createFromParcel(Parcel in) {
            return new WordSuggestion(in);
        }

        @Override
        public WordSuggestion[] newArray(int size) {
            return new WordSuggestion[size];
        }
    };

}
