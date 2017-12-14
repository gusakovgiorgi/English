package company.self.development.rememberenglishexample.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by notbl on 11/19/2017.
 */

public class Translation {
    String originalWord;
    List<String> translationWord;
    String transcription;
    List<String> example;

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public List<String> getTranslationWord() {
        return translationWord;
    }

    public void setTranslationWord(List<String> translationWord) {
        this.translationWord = translationWord;
    }
    public void addTranslationWord(List<String> addedTranslationsWord){
        if (this.translationWord==null){
            this.translationWord=new ArrayList<>();
        }
        this.translationWord.addAll(addedTranslationsWord);
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }
    public void addExamples(List<String> addedExamples){
        if (this.example==null){
            this.example=new ArrayList<>();
        }
        this.example.addAll(addedExamples);
    }

    @Override
    public String toString() {
        return "Translation{" +
                "originalWord='" + originalWord + '\'' +
                ", translationWord=" + translationWord +
                ", transcription='" + transcription + '\'' +
                ", example=" + example +
                '}';
    }
}
