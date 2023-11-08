package xyz.crediblepulse.crediblepulsebackend.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
public class ApiErrorResponse implements Serializable {

    private Integer code;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorMessage> subErrors;

    public ApiErrorResponse(Integer code, String message, List<ErrorMessage> subErrors) {
        this.code = code;
        this.message = message;
        this.timestamp = new Date();
        this.subErrors = subErrors;
    }

    public ApiErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.setCode(status);
        this.setMessage((String) errorAttributes.get("message"));
        this.setTimestamp(new Date());
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public static class ApiErrorResponseBuilder {

        private Integer code;
        private String message;
        private List<ErrorMessage> subErrors;

        public ApiErrorResponseBuilder setCode(Integer code) {
            this.code = code;
            return this;
        }

        public ApiErrorResponseBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiErrorResponseBuilder setSubErrors(List<ErrorMessage> subErrors) {
            this.subErrors = subErrors;
            return this;
        }

        public ApiErrorResponse createApiErrorResponse() {
            return new ApiErrorResponse(code, message, subErrors);
        }
    }
}
