package com.shopmax.exception;

public class OutofStockException extends RuntimeException{
    public OutofStockException(String message) {
        super(message);
    }
}
