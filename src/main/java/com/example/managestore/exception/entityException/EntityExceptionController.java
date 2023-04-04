package com.example.managestore.exception.entityException;

import com.example.managestore.exception.ResponseClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionController {
    private final String APPLICATION = "MANAGE STORE";
    private final String ENTITY_EXISTED = "Entity have already been existed";
    @ExceptionHandler(value = EntityExistedException.class)
    public ResponseEntity<ResponseClient> exception(EntityExistedException exception){
        return ResponseEntity.badRequest().body(new ResponseClient(APPLICATION,ENTITY_EXISTED.toUpperCase(),exception.getMessage()));
    }
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> exception(EntityNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatusCode.valueOf(400));
    }
    @ExceptionHandler(value = RepositoryAccessException.class)
    public ResponseEntity<Object> exception(RepositoryAccessException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatusCode.valueOf(400));
    }
}
