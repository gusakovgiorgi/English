package company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by notbl on 11/25/2017.
 */

public class SynonymModel {
    @SerializedName("text")
    public String translatedSynonymText;
    @SerializedName("pos")
    public String partOfSpeech;
    @SerializedName("gen")
    public String gender;
    @SerializedName("asp")
    //don't know this abbreviation it's mean the form are perfect or indefinite, maybe use only in words for translated tu Russian
    public String sovershennіjNesovershennіj;
}
