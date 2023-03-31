package ru.practicum.shareit.error;

public class NotFoundException extends Throwable {
    public NotFoundException(final String message) {
        super(message);
    }
}
