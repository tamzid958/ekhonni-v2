package com.ekhonni.backend.exception;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        var response = new ApiResponse<>(HTTPStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> AuthorizationDeniedException(AuthorizationDeniedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(403).body(response);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(PrivilegeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePrivilegeNotFoundException(PrivilegeNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.valueOf(404));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.valueOf(404));
    }

    @ExceptionHandler(RoleCannotBeDeletedException.class)
    public ResponseEntity<ErrorResponse> handleRoleCannotBeDeletedException(RoleCannotBeDeletedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRoleAlreadyExistsException(RoleAlreadyExistsException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.valueOf(400));
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedEncodingException(UnsupportedEncodingException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }


    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(InvalidVerificationTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidVerificationTokenException(InvalidVerificationTokenException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotVerifiedException(EmailNotVerifiedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    // product
    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ApiResponse<?>> handleProductNotSavedException(ProductNotCreatedException ex) {
        var response = new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleProductNotFoundException(ProductNotFoundException ex) {
        var response = new ApiResponse<>(HTTPStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotUpdatedException.class)
    public ResponseEntity<ApiResponse<?>> handleProductNotUpdatedException(ProductNotUpdatedException ex) {
        var response = new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //category
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        var response = new ApiResponse<>(HTTPStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Image
    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ApiResponse<?>> handleImageUploadException(ImageUploadException ex) {
        var response = new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(400).body("Error: " + ex.getMessage());
    }

}
