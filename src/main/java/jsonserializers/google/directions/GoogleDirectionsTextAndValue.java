package jsonserializers.google.directions;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDirectionsTextAndValue {

    private String text;
    private int value;

    public GoogleDirectionsTextAndValue() {
    }

    public GoogleDirectionsTextAndValue(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
