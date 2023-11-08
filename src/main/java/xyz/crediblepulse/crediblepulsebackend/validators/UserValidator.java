package xyz.crediblepulse.crediblepulsebackend.validators;



import org.springdoc.api.ErrorMessage;
import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserRequestDto;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.ApiErrorCodes;
import xyz.crediblepulse.crediblepulsebackend.exception.exceptions.ApiBusinessException;
import xyz.crediblepulse.crediblepulsebackend.exception.holders.CommonErrorCodes;

import java.util.ArrayList;
import java.util.List;

public interface UserValidator {

    static void validate(UserRequestDto userRequestDto) throws ApiBusinessException {

        List<ErrorMessage> errorMessages = new ArrayList<>();
        validateEmail(userRequestDto.email(), errorMessages);
        validateFirstname(userRequestDto.firstName(), errorMessages);
        validateLastname(userRequestDto.lastName(), errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new ApiBusinessException(CommonErrorCodes.NOT_VALID_FIELD_ERROR, String.valueOf(errorMessages));
        }
    }


    private static void validateEmail(String email, List<ErrorMessage> errorMessages) {
        if (Boolean.FALSE.equals(Validations.isValidEmail(email))) {
            errorMessages.add(new ErrorMessage(ApiErrorCodes.EMAIL_INVALID.getMsgKey()));
        }
    }

    private static void validateLastname(String lastName, List<ErrorMessage> errorMessages) {
        if (Boolean.TRUE.equals(Validations.isEmptyField(lastName))) {
            errorMessages.add(new ErrorMessage(ApiErrorCodes.REQUIRED_FIELD.getMsgKey()));
        }
    }

    private static void validateFirstname(String firstName, List<ErrorMessage> errorMessages) {
        if (Boolean.TRUE.equals(Validations.isEmptyField(firstName))) {
            errorMessages.add(new ErrorMessage(ApiErrorCodes.REQUIRED_FIELD.getMsgKey()));
        }
    }
}
