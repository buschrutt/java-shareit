package ru.practicum.shareit.error;

public class ConflictException extends Throwable {
    public ConflictException() {
    }

    public ConflictException(final String message) {
        super(message);
    }

    public ConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConflictException(final Throwable cause) {
        super(cause);
    }
}
