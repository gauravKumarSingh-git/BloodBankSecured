package com.bnl.bloodbank.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<ErrorInfo> usernameAlreadyPresentExceptionHandler(AlreadyPresentException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(exception.getMessage());
        errorInfo.setErrorCode(HttpStatus.CONFLICT.value());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorInfo> usernameNotFoundExceptionHandler(UsernameNotFoundException exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(exception.getMessage());
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
		ErrorInfo error = new ErrorInfo();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(LocalDateTime.now());

		String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(", "));

		error.setErrorMessage(errorMsg);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> globalExceptionHandler(Exception exception){
        ErrorInfo error = new ErrorInfo();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(LocalDateTime.now());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
