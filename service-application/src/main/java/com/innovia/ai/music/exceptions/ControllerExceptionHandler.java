package com.innovia.ai.music.exceptions;


import com.innovia.ai.music.common.exceptions.ApiError;
import com.innovia.ai.music.common.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return response("Not Found: ", HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        return response("Bad contents in request: ", HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<Object> response( String msg, HttpStatus status, Exception ex,
                                            WebRequest request) {
        ApiError error = ApiError.builder().status(status.value()).message(msg +
                ex.getMessage()).build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        IllegalArgumentException e = new IllegalArgumentException(ex.getBindingResult().getFieldErrors().toString());
        return response("Malformed JSON request: ", HttpStatus.BAD_REQUEST, e, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return response("Malformed JSON request: ", HttpStatus.BAD_REQUEST, ex, request);
    }
}
