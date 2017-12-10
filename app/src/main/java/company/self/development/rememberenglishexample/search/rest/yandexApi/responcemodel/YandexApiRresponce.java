package company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.Translation;

/**
 * Created by notbl on 11/25/2017.
 */

public class YandexApiRresponce implements ITranslation {

    @SerializedName("def")
    private List<DefinitionModel> definitionModel;


    @Override
    public List<Translation> getTranslations() {
        List<Translation> translationList = new ArrayList<>();
        if (definitionModel == null) return translationList;
        for (DefinitionModel particularDefModel : definitionModel) {
            String originalWord=particularDefModel.originalText;
            String transcription=particularDefModel.transcription;
            if (particularDefModel.translations != null && particularDefModel.translations.size() > 0) {
                for (TranslationModel translationModel : particularDefModel.translations) {
                    Translation translation = new Translation();
                    translation.setOriginalWord(originalWord);
                    translation.setTranscription(transcription);
                    translation.addTranslationWord(translationModel.getTranslationsList());
                    translation.addExamples(translationModel.getExamples());
                    translationList.add(translation);
                }
            }
        }
        return translationList;
    }
}
