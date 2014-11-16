package exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
public class FormValidationError extends Exception {

    private List<ErrorMessages> errorMessages;

    public FormValidationError(ErrorMessages error) {
        this.errorMessages = new ArrayList<>(Arrays.asList(error));
    }

    public FormValidationError(List<ErrorMessages> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<ErrorMessages> getErrorMessages() {
        return errorMessages;
    }
}
