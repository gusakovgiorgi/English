package company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by notbl on 11/25/2017.
 */

public class TranslationModel {

    @SerializedName("text")
    public String translatedText;
    @SerializedName("pos")
    public String partOfSpeech;
    @SerializedName("gen")
    public String gender;
    @SerializedName("syn")
    public List<SynonymModel> synonyms = null;
    @SerializedName("mean")
    public List<MeaningModel> meanings = null;
    @SerializedName("ex")
    public List<ExampleModel> examples = null;
    @SerializedName("asp")
    //don't know this abbreviation it's mean the form are perfect or indefinite, maybe use only in words for translated tu Russian
    public String sovershennіjNesovershennіj;

    public List<String> getTranslationsList(){
        List<String> translationsList=new ArrayList<>();
        if (translatedText!=null) translationsList.add(translatedText);
//        if (synonyms!=null && synonyms.size()>0){
//            for (SynonymModel synonym:synonyms){
//                translationsList.add(synonym.translatedSynonymText);
//            }
//        }
        return translationsList;
    }

    public List<String> getExamples(){
        List<String> examplesList=new ArrayList<>();
        if (examples!=null && examples.size()>0){
            for (ExampleModel example:examples){
                examplesList.add(example.exampleText);
            }
        }
        return examplesList;
    }
}
