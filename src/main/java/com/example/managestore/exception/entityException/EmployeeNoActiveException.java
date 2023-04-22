package com.example.managestore.exception.entityException;

public class EmployeeNoActiveException extends RuntimeException{
    public EmployeeNoActiveException(String msg){
        super(msg);
    }
}
