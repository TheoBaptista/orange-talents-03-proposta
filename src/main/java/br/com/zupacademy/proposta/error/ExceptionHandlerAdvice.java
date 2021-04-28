package br.com.zupacademy.proposta.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private final MessageSource messageSource;

    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrors> validationHandle(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new FieldErrors(
                        errors.stream()
                                .map(e -> String.format("Campo %s :  %s", e.getField(), e.getDefaultMessage()))
                                .collect(Collectors.toList())));
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldErrors> bindHandle(BindException exception) {
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new FieldErrors(
                        errors.stream()
                                .map(e -> String.format("Campo %s :  %s", e.getField(), e.getDefaultMessage()))
                                .collect(Collectors.toList())));

    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<FieldErrors> sqlIntegrityConstraintViolationExceptionHandle(SQLIntegrityConstraintViolationException exception) {
        Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
        logger.error("Erro! Constraint de unicidade violada!", exception.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrors("O dado já existe no nosso sistema"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public FieldErrors springAssertionArgumentExceptionHandle(IllegalArgumentException exception) {
        return new FieldErrors(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public FieldErrors springAssertionStateExceptionHandle(IllegalStateException exception) {
        return new FieldErrors(exception.getMessage());
    }
}
