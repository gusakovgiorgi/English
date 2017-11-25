package company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import company.self.development.rememberenglishexample.model.Translation;

/**
 * Created by notbl on 11/25/2017.
 */

public class ExampleModel {
    @SerializedName("text")
    public String exampleText;
    @SerializedName("tr")
    public List<TranslationModel> tr = null;
}
