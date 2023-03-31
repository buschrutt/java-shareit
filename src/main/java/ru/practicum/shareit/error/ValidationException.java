package ru.practicum.shareit.error;

public class ValidationException extends Throwable {
    public ValidationException(final String message) {
        super(message);
    }
}