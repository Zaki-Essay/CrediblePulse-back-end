package xyz.crediblepulse.crediblepulsebackend.exception.dto;

import xyz.crediblepulse.crediblepulsebackend.exception.dto.KeyValueError;

public enum CommonErrorCodes implements KeyValueError {
    MISSING_FIELD_ERROR(0, "field.not.found"),
    NOT_VALID_FIELD_ERROR(1, "field.not.valid"),
    MISSING_PAYLOAD_ERROR(3, "payload.not.valid"),
    HTTP_REQUEST_NOT_SUPPORTED_METHOD(4, "http.method.not.supported"),
    HTTP_REQUEST_NOT_SUPPORTED_CONTET_TYPE(5, "http.content.type.not.supported"),
    HTTP_REQUEST_INVALID_BODY(6, "http.invalid.body"),
    HTTP_REQUEST_INVALID_ARGUMENT(7, "http.invalid.argument"),
    HTTP_RESPONSE_TYPE_NOT_ACCEPTABLE(8, "http.accept.type.not.supported"),
    TECHNICAL_ERROR(500, "technical.error"),

    // GLOBAL
    ARGUMENT_EXCEPTION_ERROR_CODE(901, "request.illegal.parameter"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION_ERROR_CODE(902, "request.missing.parameter"),
    ACCESS_DENIED_EXCEPTION(903, "user.error.access.denied"),
    EXCEPTION_ACCESS_DENIED_CODE(403, "user.error.access.denied"),

    PASSWORD_UPDATE_ERROR(1457, "password.update.error"),
    INVALID_OLD_PASSWORD(1458, "invalid.old.password"),
    HTTP_HEADER_INVALID_TENANT_ID(1458, "http.invalid.header.tenantId"),

    MISSING_TOKEN_CLAIMS(1459, "missing.token.claims"),
    INVALID_TOKEN(1460, "invalid.token"),

    ACCOUNT_VERIFICATION_ERROR(1461, "account.verification.error"),

    NOTE_TYPE_UPDATE_ERROR(1462, "note.type.update.error"),

    DELETE_NOTE_ERROR(1463, "delete.note.error"),
    PARENT_MEETING_NOT_FOUND(1464, "parent.meeting.not.found");

    private final Integer code;
    private final String msgKey;

    CommonErrorCodes(Integer code, String msgKey) {
        this.code = code;
        this.msgKey = msgKey;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsgKey() {
        return msgKey;
    }
}
