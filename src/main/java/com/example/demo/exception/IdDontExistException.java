package com.example.demo.exception;

public class IdDontExistException extends RuntimeException {
    public IdDontExistException(String message) {
        super(message);
    }
}