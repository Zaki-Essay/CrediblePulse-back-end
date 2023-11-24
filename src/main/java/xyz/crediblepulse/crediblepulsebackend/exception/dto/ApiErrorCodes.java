package xyz.crediblepulse.crediblepulsebackend.exception.dto;

public enum ApiErrorCodes implements KeyValueError {

    // User
    USER_NOT_VALID_ERROR(2601, "user.not.valid"),
    USER_CREATION_ERROR(2602, "user.creation.error"),
    USER_NOT_FOUND(2603, "user.not.found"),
    EMAIL_ALREADY_VERIFIED(2610, "email.already.verified"),
    USER_UPDATE_ERROR(2612, "user.update.error"),
    USER_NOT_ACTIVE(2613, "user.not.active"),
    USER_WITH_EMAIL_ALREADY_MEMBER(2671, "user.already.member"),

    // validation
    REQUIRED_FIELD(2606, "exception.required.field"),
    EMAIL_INVALID(2606, "exception.email.invalid"),
    PHONE_NUMBER_INVALID(2607, "phone.number.invalid"),
    PROVIDER_INVALID(2608, "provider.invalid"),
    EMAIL_SENDING_EXCEPTION(2611, "email.sending.exception"),
    TIMEZONE_INVALID(2614, "timezone.invalid"),
    STATUS_INVALID(2614, "status.invalid"),
    ACTION_INVALID(2614, "action.invalid"),
    ID_INVALID(2614, "id.invalid");



    private final Integer code;
    private final String msgKey;

    ApiErrorCodes(Integer code, String msgKey) {
        this.code = code;
        this.msgKey = msgKey;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
