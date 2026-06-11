package net.labymod.api.annotation.processing.exception;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/exception/ProcessingException.class */
public class ProcessingException extends RuntimeException {
    public ProcessingException() {
    }

    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessingException(Throwable cause) {
        super(cause);
    }
}
