package net.labymod.api.client.session.exceptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/exceptions/InvalidCredentialsException.class */
public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }
}
