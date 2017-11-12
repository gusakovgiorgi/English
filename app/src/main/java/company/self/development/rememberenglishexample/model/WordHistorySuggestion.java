package company.self.development.rememberenglishexample.model;

import android.os.Parcel;

/**
 * Created by notbl on 11/12/2017.
 */

public class WordHistorySuggestion extends WordSuggestion {

    public WordHistorySuggestion(Parcel in) {
        super(in);
    }

    public WordHistorySuggestion(String word) {
        super(word);
    }
}
