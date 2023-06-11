package com.bilgeadam.rentacar.util.businessException;

import com.bilgeadam.rentacar.util.results.ErrorDataResult;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorDataResult<Object> handleValidationExceptions(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    Map<String, String> validationErrors = new HashMap<>();
    for (FieldError fieldError :
        methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
      validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    ErrorDataResult<Object> errorDataResult =
        new ErrorDataResult<>(validationErrors, "Validation Error: ");
    return errorDataResult;
  }

  @ExceptionHandler
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorDataResult<Object> handleBusinessExceptions(BusinessException businessException) {
    return new ErrorDataResult<>(businessException.getMessage(), "Business Error");
  }

  @ExceptionHandler
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ErrorDataResult<Object> handleHttpMessageNotReadableExceptions(
      HttpMessageNotReadableException httpMessageNotReadableException) {
    final String businessError = "JSON message format error";
    return new ErrorDataResult<>(businessError, "HttpMessageNotReadableException Error");
  }
}
