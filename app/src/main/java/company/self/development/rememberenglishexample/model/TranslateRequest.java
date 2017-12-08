package company.self.development.rememberenglishexample.model;

import java.util.Map;

/**
 * Created by notbl on 12/7/2017.
 */

public abstract class TranslateRequest {
    protected String text;

    public abstract Map<String,String> getPostParams();`

    public TranslateRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
