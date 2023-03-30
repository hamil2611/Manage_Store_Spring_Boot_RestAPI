package com.example.managestore.exception.repositoryException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

public class RepositoryAccessException extends RuntimeException{
    public RepositoryAccessException(String msg) {
        super(msg);
    }
}
