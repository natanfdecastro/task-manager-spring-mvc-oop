package edu.tec.ic6821.app.errors;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorDto {

    private final String timestamp;
    private final String message;
    private final String exception;

    public static ErrorDto from(Exception e) {
        return new ErrorDto(e.getMessage(), e.getClass().getName());
    }

    private ErrorDto(String message, String exception) {
        this.message = message;
        this.exception = exception;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
