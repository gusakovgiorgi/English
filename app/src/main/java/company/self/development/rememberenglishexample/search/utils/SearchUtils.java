package company.self.development.rememberenglishexample.search.utils;

import java.util.List;

/**
 * Created by Giorgi on 12/19/2017.
 */

public class SearchUtils {

    public static String getStringFromTranslationWords(List<String> translationStrings) {
        StringBuilder result = new StringBuilder();
        for (String translationString : translationStrings) {
            result.append(translationString).append(",").append(" ");
        }
        deleteLastCharacters(2, result);
        return result.toString();
    }

    public static String getStringFromExamples(List<String> examples) {
        StringBuilder result = new StringBuilder();
        for (String example : examples) {
            result.append(example).append(".").append("\n");
        }
        deleteLastCharacters(1, result);
        return result.toString();
    }


    public static void deleteLastCharacters(int length, StringBuilder result) {
        if (result != null && result.length() > 2) {
            result.delete(result.length() - length, result.length());
        }
    }
}
