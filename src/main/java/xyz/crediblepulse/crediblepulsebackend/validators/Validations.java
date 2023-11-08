package xyz.crediblepulse.crediblepulsebackend.validators;

import static xyz.crediblepulse.crediblepulsebackend.constants.DateFormats.LDT_FORMAT;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import xyz.crediblepulse.crediblepulsebackend.utils.DatesUtils;

public interface Validations {

    String VALID_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    String VALID_PHONE_NUMBER_REGEX = "^\\+?\\d+$";
    String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}";
    String VALID_UUID_STRING_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
    String ZULU_DATETIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$";

    Pattern emailPattern = Pattern.compile(VALID_EMAIL_REGEX);
    Pattern phoneNumberPattern = Pattern.compile(VALID_PHONE_NUMBER_REGEX);
    Pattern uuidStringPattern = Pattern.compile(VALID_UUID_STRING_REGEX);
    Pattern zuluDatetimePattern = Pattern.compile(ZULU_DATETIME_REGEX);

    static Boolean isValidEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }

    static Boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumberPattern.matcher(phoneNumber).matches();
    }

    static boolean isEmptyField(String field) {
        return field == null || field.isEmpty();
    }

    static boolean isEmptyField(Object field) {
        return field == null || field.toString().isEmpty();
    }

    static boolean isNull(Object field) {
        return field == null;
    }

    static boolean isInValidSize(String input, int minLength, int maxLength) {
        return input != null && minLength <= input.length() && input.length() <= maxLength;
    }

    static boolean isValidLink(String link) {
        return (link.startsWith("http") || link.startsWith("https"));
    }

    static boolean isContainJustNumbers(String value) {
        return value.matches("\\d+");
    }

    static boolean isMatchingOfDatePattern(String date, String pattern) {
        return Pattern.matches(pattern, date);
    }

    static boolean isValidZuluDateTime(String datetime) {
        return zuluDatetimePattern.matcher(datetime).matches();
    }

    static boolean isValidDate(OffsetDateTime date) {
        if (date == null) {
            return false;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(LDT_FORMAT);

        String formattedDate = DatesUtils.format(dtf, date.toLocalDateTime());

        return Pattern.matches(DATE_REGEX, formattedDate);
    }

    static boolean isValidTimeZone(String timeZone) {
        try {
            ZoneId.of(timeZone);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static boolean isValidUUIDString(String uuid) {
        return (uuid != null && uuidStringPattern.matcher(uuid).matches());
    }

    static boolean areValidUUIDStrings(List<String> uuidStrings) {
        return uuidStrings.stream().allMatch(Validations::isValidUUIDString);
    }
}
