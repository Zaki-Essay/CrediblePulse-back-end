package xyz.crediblepulse.crediblepulsebackend.exception.exceptions;

import java.io.Serial;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.ErrorMessage;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.KeyValueError;

public class ApiAuthorizationException extends IllegalArgumentException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessage errorMessage;

    public ApiAuthorizationException(KeyValueError keyValueError) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getCode());
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
