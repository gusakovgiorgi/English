package company.self.development.rememberenglishexample.model;

/**
 * Created by notbl on 12/7/2017.
 */

public enum Language {
    EN,
    RU;

    public static String getDefaultDirection(){
        return EN.name()+"-"+RU.name();
    }
}
