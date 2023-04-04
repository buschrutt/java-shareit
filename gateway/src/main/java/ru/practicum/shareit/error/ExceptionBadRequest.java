package ru.practicum.shareit.error;

public class ExceptionBadRequest extends Throwable {

    public ExceptionBadRequest(final String message) {
        super(message);
    }
}
