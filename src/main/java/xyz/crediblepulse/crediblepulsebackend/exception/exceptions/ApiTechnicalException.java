package xyz.crediblepulse.crediblepulsebackend.exception.exceptions;

import java.io.Serial;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.ErrorMessage;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.KeyValueError;

public class ApiTechnicalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessage errorMessage;

    public ApiTechnicalException(String message) {
        super(message);

        errorMessage = new ErrorMessage(message, 0);
    }

    public ApiTechnicalException(Exception exception) {
        super(exception);

        errorMessage = new ErrorMessage(exception.getMessage(), 0);
    }

    public ApiTechnicalException(Exception exception, KeyValueError keyValueError, String... args) {
        super(exception);

        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getCode(), args);
    }

    public ApiTechnicalException(KeyValueError keyValueError, String... args) {
        super(keyValueError.getMsgKey());

        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getCode(), args);
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
