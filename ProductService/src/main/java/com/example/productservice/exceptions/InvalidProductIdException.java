package com.example.productservice.exceptions;

public class InvalidProductIdException extends Exception{
    public InvalidProductIdException(String message) {
        super(message);
    }
}
