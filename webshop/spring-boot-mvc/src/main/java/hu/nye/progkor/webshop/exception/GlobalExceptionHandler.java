package hu.nye.progkor.webshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
    return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  public ResponseEntity<ApiError> buildResponseEntity(HttpStatus status, String message) {
    ApiError apiError = new ApiError(status, message);
    return new ResponseEntity<>(apiError, status);
  }

}
