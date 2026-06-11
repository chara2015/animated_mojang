package net.labymod.api.client.session.exceptions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/exceptions/InsufficientPrivilegesException.class */
public class InsufficientPrivilegesException extends AuthenticationException {
    public InsufficientPrivilegesException() {
    }

    public InsufficientPrivilegesException(String message) {
        super(message);
    }

    public InsufficientPrivilegesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientPrivilegesException(Throwable cause) {
        super(cause);
    }
}
