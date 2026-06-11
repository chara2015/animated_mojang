package net.labymod.core.labynet.exception;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/exception/ScreenshotException.class */
public class ScreenshotException extends Exception {
    private int statusCode;

    public ScreenshotException(String message) {
        super(message);
        this.statusCode = -1;
    }

    public ScreenshotException(String message, int statusCode) {
        super(message);
        this.statusCode = -1;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
