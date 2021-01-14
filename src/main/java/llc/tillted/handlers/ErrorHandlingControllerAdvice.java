package llc.tillted.handlers;

import llc.tillted.model.ApiError;
import llc.tillted.model.responses.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

//@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder().build();
        e.getConstraintViolations()
                .forEach(violation -> errorResponse.getErrors().add(
                        ApiError.builder()
                                .fieldName(violation.getPropertyPath().toString())
                                .message(violation.getMessage())
                                .build()
                ));
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder().build();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errorResponse.getErrors().add(
                        ApiError.builder()
                                .fieldName(fieldError.getField())
                                .fieldValue(getFieldValueAsString(fieldError.getRejectedValue()))
                                .message(fieldError.getDefaultMessage())
                                .build()
                ));
        return errorResponse;
    }

    private String getFieldValueAsString(Object fieldValue) {
        return fieldValue != null ? String.valueOf(fieldValue) : null;
    }

}
