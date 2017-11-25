package company.self.development.rememberenglishexample.search.rest.yandexApi.responcemodel;

import java.util.ArrayList;
import java.util.List;

import company.self.development.rememberenglishexample.model.ITranslation;
import company.self.development.rememberenglishexample.model.Translation;

/**
 * Created by notbl on 11/25/2017.
 */

public class YandexApiRresponce implements ITranslation {

    private List<DefinitionModel> definitionModel;


    @Override
    public List<Translation> getTranslations() {
        List<Translation> translationList = new ArrayList<>();
        if (definitionModel == null) return translationList;
        for (DefinitionModel particularDefModel : definitionModel) {
            Translation translation = new Translation();
            translation.setOriginalWord(particularDefModel.originalText);
            translation.setTranscription(particularDefModel.transcription);
            if (particularDefModel.translations != null && particularDefModel.translations.size() > 0) {
                for (TranslationModel translationModel : particularDefModel.translations) {
                    translation.addTranslationWord(translationModel.getTranslationsList());
                    translation.addExamples(translationModel.getExamples());
                }
            }
            translationList.add(translation);
        }
        return translationList;
    }
}
