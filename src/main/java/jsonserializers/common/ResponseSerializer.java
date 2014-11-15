package jsonserializers.common;

import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
public class ResponseSerializer<T> {

    private ResponseStatus status;
    private String errorMessage;
    private T result;

    public ResponseSerializer() {
    }

    public ResponseSerializer(T result) {
        this.result = result;
    }

    public ResponseSerializer(ResponseStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ResponseSerializer(ResponseStatus status, String errorMessage, T result) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.result = result;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
