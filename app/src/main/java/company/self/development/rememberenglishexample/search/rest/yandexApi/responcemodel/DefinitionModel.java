package company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by notbl on 11/25/2017.
 */

public class DefinitionModel {

    @SerializedName("text")
    public String originalText;
    @SerializedName("pos")
    public String partOfSpeech;
    @SerializedName("ts")
    public String transcription;
    @SerializedName("tr")
    public List<TranslationModel> translations = null;

}
