package com.gercev.exception;

public class CommentIsNotCreatedException extends RuntimeException{
    public CommentIsNotCreatedException(String message) {
        super(message);
    }
}
