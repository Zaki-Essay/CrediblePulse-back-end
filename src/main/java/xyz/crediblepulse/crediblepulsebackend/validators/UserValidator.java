package xyz.crediblepulse.crediblepulsebackend.validators;



import org.springdoc.api.ErrorMessage;
import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserRequestDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface UserValidator {

    static void validate(UserRequestDto userRequestDto) throws Exception {

        List<ErrorMessage> errorMessages = new ArrayList<>();
        validateEmail(userRequestDto.email(), errorMessages);
        validateFirstname(userRequestDto.firstName(), errorMessages);
        validateLastname(userRequestDto.lastName(), errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new RuntimeException();
        }
    }



    private static void validateEmail(String email, List<ErrorMessage> errorMessages) {
        if (Boolean.FALSE.equals(Validations.isValidEmail(email))) {
            errorMessages.add(new ErrorMessage(""));
        }
    }

    private static void validateLastname(String lastName, List<ErrorMessage> errorMessages) {
        if (Boolean.TRUE.equals(Validations.isEmptyField(lastName))) {
            errorMessages.add(new ErrorMessage(""));
        }
    }

    private static void validateFirstname(String firstName, List<ErrorMessage> errorMessages) {
        if (Boolean.TRUE.equals(Validations.isEmptyField(firstName))) {
            errorMessages.add(new ErrorMessage(""));
        }
    }
}
