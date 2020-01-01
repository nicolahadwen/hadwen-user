package co.hadwen.user.exception;

import lombok.NonNull;

public class CreateUserException extends RuntimeException {
    private static final String TYPE_FORMAT = "%s: %s";

    public CreateUserException(@NonNull Type type) {
        super(String.format(TYPE_FORMAT, type.name(), ""));
    }

    public CreateUserException(@NonNull Type type, @NonNull String message) {
        super(String.format(TYPE_FORMAT, type.name(), message));
    }

    public enum Type {
        UNKNOWN,
        HIBERNATE_CONFIG
    }
}
