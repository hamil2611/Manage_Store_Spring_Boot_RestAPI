package com.example.managestore.exception.controllerException;

import com.example.managestore.exception.EntityResponseClient;
import com.example.managestore.exception.entityException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EntityExceptionController {
    private final String APPLICATION = "MANAGE STORE";
    private final String ENTITY_EXISTED = "Entity have already been existed";

    @ExceptionHandler(value = EntityExistedException.class)
    public ResponseEntity<EntityResponseClient> exception(EntityExistedException exception) {
        return ResponseEntity.badRequest().body(new EntityResponseClient(APPLICATION, ENTITY_EXISTED, exception.getMessage()));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<EntityResponseClient> exception(EntityNotFoundException exception) {
        return ResponseEntity.badRequest().body(new EntityResponseClient(APPLICATION, ENTITY_EXISTED, exception.getMessage()));
    }

    @ExceptionHandler(value = RepositoryAccessException.class)
    public ResponseEntity<Object> exception(RepositoryAccessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = EmployeeNoActiveException.class)
    public ResponseEntity<EntityResponseClient> exception(EmployeeNoActiveException exception){
        return ResponseEntity.badRequest().body(new EntityResponseClient(APPLICATION,"NO ACTIVATED", exception.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> exception(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName =((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    public ResponseEntity<EntityResponseClient> exception(InternalAuthenticationServiceException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new EntityResponseClient(APPLICATION,"Invalid login information",exception.getMessage()));
    }

    @ExceptionHandler(value = AuthenticationUserException.class)
    public ResponseEntity<EntityResponseClient> exception(AuthenticationUserException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new EntityResponseClient(APPLICATION,"Invalid login information",exception.getMessage()));
    }
}
