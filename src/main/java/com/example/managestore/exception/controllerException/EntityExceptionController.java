package com.example.managestore.exception.controllerException;

import com.example.managestore.exception.EntityResponseClient;
import com.example.managestore.exception.entityException.EmployeeNoActiveException;
import com.example.managestore.exception.entityException.EntityExistedException;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionController {
    private final String APPLICATION = "MANAGE STORE";
    private final String ENTITY_EXISTED = "Entity have already been existed";

    @ExceptionHandler(value = EntityExistedException.class)
    public ResponseEntity<EntityResponseClient> exception(EntityExistedException exception) {
        return ResponseEntity.badRequest().body(new EntityResponseClient(APPLICATION, ENTITY_EXISTED, exception.getMessage()));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> exception(EntityNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = RepositoryAccessException.class)
    public ResponseEntity<Object> exception(RepositoryAccessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(value = EmployeeNoActiveException.class)
    public ResponseEntity<EntityResponseClient> exception(EmployeeNoActiveException exception){
        return ResponseEntity.badRequest().body(new EntityResponseClient(APPLICATION,"NO ACTIVATED", exception.getMessage()));
    }

}
