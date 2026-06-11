package net.labymod.api.reference.exception;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/exception/CircularDependencyException.class */
public class CircularDependencyException extends RuntimeException {
    public CircularDependencyException() {
    }

    public CircularDependencyException(String message) {
        super(message);
    }

    public CircularDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularDependencyException(Throwable cause) {
        super(cause);
    }

    public CircularDependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
