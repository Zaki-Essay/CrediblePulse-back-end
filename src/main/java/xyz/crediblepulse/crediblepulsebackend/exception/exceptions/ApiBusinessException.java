package xyz.crediblepulse.crediblepulsebackend.exception.exceptions;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.ErrorMessage;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.KeyValueError;

public class ApiBusinessException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessage errorMessage;
    private final List<ErrorMessage> subErrors;

    public ApiBusinessException(KeyValueError keyValueError, String... args) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getCode(), args);
        subErrors = new ArrayList<>();
    }

    public ApiBusinessException(Exception exception, KeyValueError keyValueError) {
        super(exception);
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getCode());
        subErrors = new ArrayList<>();
    }

    public ApiBusinessException(KeyValueError keyValueError, List<ErrorMessage> subErrors) {
        super(keyValueError.getMsgKey());
        errorMessage = new ErrorMessage(keyValueError.getMsgKey(), keyValueError.getCode());
        this.subErrors = subErrors;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public List<ErrorMessage> getSubErrors() {
        return subErrors;
    }
}
