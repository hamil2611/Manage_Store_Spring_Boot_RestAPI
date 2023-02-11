package com.example.managestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RepositoryAccessException extends RestClientException {
    public RepositoryAccessException(String msg) {
        super(msg);
    }
}
