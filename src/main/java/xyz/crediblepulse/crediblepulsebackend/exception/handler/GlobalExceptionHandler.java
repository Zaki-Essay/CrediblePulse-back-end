package xyz.crediblepulse.crediblepulsebackend.exception.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import xyz.crediblepulse.crediblepulsebackend.exception.config.ApiMessageSource;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.ApiErrorResponse;
import xyz.crediblepulse.crediblepulsebackend.exception.exceptions.ApiAuthorizationException;
import xyz.crediblepulse.crediblepulsebackend.exception.exceptions.ApiBusinessException;
import xyz.crediblepulse.crediblepulsebackend.exception.exceptions.ApiTechnicalException;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static xyz.crediblepulse.crediblepulsebackend.exception.holders.CommonErrorCodes.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ApiMessageSource format;

    @Autowired
    public GlobalExceptionHandler(ApiMessageSource format) {
        this.format = format;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiBusinessException.class)
    public final ApiErrorResponse handleApiBusinessException(ApiBusinessException exception) {
        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(exception.getErrorMessage().getCode())
                .setMessage(format.getMessage(
                        exception.getErrorMessage().getKey(),
                        exception.getErrorMessage().getParameters()))
                .setSubErrors(exception.getSubErrors())
                .createApiErrorResponse();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiTechnicalException.class)
    public final ApiErrorResponse handleApiTechnicalException(ApiTechnicalException exception) {
        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(exception.getErrorMessage().getCode())
                .setMessage(format.getMessage(exception.getErrorMessage().getKey()))
                .createApiErrorResponse();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ApiAuthorizationException.class)
    public final ApiErrorResponse handleApiAuthorizationException(ApiAuthorizationException exception) {

        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(exception.getErrorMessage().getCode())
                .setMessage(format.getMessage(exception.getErrorMessage().getKey()))
                .createApiErrorResponse();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public final ApiErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {

        String supportedMethods = Arrays.toString(
                Optional.ofNullable(exception.getSupportedMethods()).orElse(new String[] {}));

        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(HTTP_REQUEST_NOT_SUPPORTED_METHOD.getCode())
                .setMessage(format.getMessage(
                        HTTP_REQUEST_NOT_SUPPORTED_METHOD.getMsgKey(), (Object[]) new String[] {supportedMethods}))
                .createApiErrorResponse();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public final ApiErrorResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {

        String supportedTypes = Optional.of(exception.getSupportedMediaTypes())
                .orElse(emptyList())
                .toString();

        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(HTTP_REQUEST_NOT_SUPPORTED_CONTET_TYPE.getCode())
                .setMessage(format.getMessage(
                        HTTP_REQUEST_NOT_SUPPORTED_CONTET_TYPE.getMsgKey(), (Object[]) new String[] {supportedTypes}))
                .createApiErrorResponse();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ApiErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(HTTP_REQUEST_INVALID_BODY.getCode())
                .setMessage(format.getMessage(HTTP_REQUEST_INVALID_BODY.getMsgKey()))
                .createApiErrorResponse();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ApiErrorResponse handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        String requiredType = "";
        try {
            var requiredTypeClass = exception.getRequiredType();
            if (requiredTypeClass != null) {
                requiredType = requiredTypeClass.getSimpleName();
            }
        } catch (NullPointerException e) {
            // This is intentional
        }

        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(HTTP_REQUEST_INVALID_ARGUMENT.getCode())
                .setMessage(format.getMessage(HTTP_REQUEST_INVALID_ARGUMENT.getMsgKey(), (Object[])
                        new String[] {exception.getName(), requiredType}))
                .createApiErrorResponse();
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public final ApiErrorResponse handleHttpMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException exception) {
        String supportedTypes = Optional.of(exception.getSupportedMediaTypes())
                .orElse(emptyList())
                .toString();

        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(HTTP_RESPONSE_TYPE_NOT_ACCEPTABLE.getCode())
                .setMessage(format.getMessage(
                        HTTP_RESPONSE_TYPE_NOT_ACCEPTABLE.getMsgKey(), (Object[]) new String[] {supportedTypes}))
                .createApiErrorResponse();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ApiErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        String missingParam = exception.getParameterName();
        return new ApiErrorResponse.ApiErrorResponseBuilder()
                .setCode(MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION_ERROR_CODE.getCode())
                .setMessage(
                        format.getMessage(MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION_ERROR_CODE.getMsgKey(), (Object[])
                                new String[] {missingParam}))
                .createApiErrorResponse();
    }
}
