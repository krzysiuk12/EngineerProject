package jsonserializers.google.geocoding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodingSerializer {

    private List<GoogleGeocodingResult> results;
    private String status;

    public GoogleGeocodingSerializer() {
    }

    public GoogleGeocodingSerializer(List<GoogleGeocodingResult> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<GoogleGeocodingResult> getResults() {
        return results;
    }

    public void setResults(List<GoogleGeocodingResult> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
