package app.exception;

import app.exception.dao.Error;
import app.exception.dao.ErrorResponse;
import app.exception.ex.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
    return buildErrorResponse(new BadRequestException(Optional.ofNullable(exception.getMessage()).orElse("Bad Request"), Error.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
    return buildErrorResponse(new NotFoundException(Optional.ofNullable(exception.getMessage()).orElse("Not Found"), Error.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(AppException exception, HttpStatus httpStatus) {
    logger.info("Returning an error response. Message: {}, Reason: {}", exception.getMessage(), exception.getError().name());
    return new ResponseEntity<>(buildError(exception), httpStatus);
  }

  private ErrorResponse buildError(AppException appException) {
    return new ErrorResponse(appException.getMessage(), appException.getError().getCode());
  }
}
