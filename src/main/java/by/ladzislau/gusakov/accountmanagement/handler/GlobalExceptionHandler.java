package by.ladzislau.gusakov.accountmanagement.handler;

import by.ladzislau.gusakov.accountmanagement.dto.error.ErrorResponse;
import by.ladzislau.gusakov.accountmanagement.error.AuthenticationException;
import by.ladzislau.gusakov.accountmanagement.error.DuplicateException;
import by.ladzislau.gusakov.accountmanagement.error.IllegalOperationException;
import by.ladzislau.gusakov.accountmanagement.error.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        logger.error("Exception occurred: ", e);

        int status = HttpStatus.METHOD_NOT_ALLOWED.value();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), status, new Date());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        logger.error("Exception occurred: ", e);

        int status = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = new ErrorResponse("Resource not found", status, new Date());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler({
            AuthenticationException.class
    })
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException e) {
        logger.error("Exception occurred: ", e);

        int status = HttpStatus.UNAUTHORIZED.value();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), status, new Date());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler({
            DuplicateException.class,
            IllegalArgumentException.class,
            IllegalOperationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException e) {
        logger.error("Exception occurred: ", e);

        int status = HttpStatus.BAD_REQUEST.value();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), status, new Date());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleJakartaValidationException(MethodArgumentNotValidException e) {
        logger.error("Exception occurred: ", e);

        int status = HttpStatus.BAD_REQUEST.value();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        StringBuilder messageBuilder = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            messageBuilder.append(fieldError.getField()).append(" – ");
            messageBuilder.append(fieldError.getDefaultMessage()).append(".\n");
        });

        String message = messageBuilder.toString().trim();
        ErrorResponse response = new ErrorResponse(message, status, new Date());

        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler({
            NotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        logger.error("Exception occurred: ", e);

        int status = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), status, new Date());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
