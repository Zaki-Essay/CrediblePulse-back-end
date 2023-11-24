package xyz.crediblepulse.crediblepulsebackend.exception.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class ErrorMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String key = null;
    private Integer code = null;
    private String[] parameters = null;

    public ErrorMessage() {
        super();
    }

    public ErrorMessage(String key, Integer code) {
        this.key = key;
        this.code = code;
    }

    public ErrorMessage(String key, Integer code, String... parameters) {
        this.key = key;
        this.code = code;
        this.parameters = parameters;
    }

    public ErrorMessage(KeyValueError error, String... parameters) {
        this.key = error.getMsgKey();
        this.code = error.getCode();
        this.parameters = parameters;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "[" + key + "," + code + "," + Arrays.toString(parameters) + "]";
    }
}
