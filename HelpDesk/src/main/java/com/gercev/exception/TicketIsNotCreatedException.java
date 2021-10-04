package com.gercev.exception;

public class TicketIsNotCreatedException extends RuntimeException{
    public TicketIsNotCreatedException(String message) {
        super(message);
    }
}
