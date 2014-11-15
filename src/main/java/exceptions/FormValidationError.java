package exceptions;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
public class FormValidationError extends Exception {

    public FormValidationError(ErrorMessages error) {
        super(error.name());
    }

}
