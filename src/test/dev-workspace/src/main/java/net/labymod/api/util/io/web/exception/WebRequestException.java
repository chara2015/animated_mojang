package net.labymod.api.util.io.web.exception;

import java.io.IOException;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/exception/WebRequestException.class */
public class WebRequestException extends IOException {
    private final String url;
    private final int responseCode;
    private final long retryAfter;

    @Nullable
    private final String errorMessage;

    public WebRequestException(String url, int responseCode) {
        super("Response Code: " + responseCode);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = -1L;
        this.errorMessage = null;
    }

    public WebRequestException(String url, int responseCode, long retryAfter) {
        super("Response Code: " + responseCode);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = retryAfter;
        this.errorMessage = null;
    }

    public WebRequestException(Exception exception) {
        super(exception.getMessage());
        this.url = "Unknown";
        this.responseCode = -1;
        this.retryAfter = -1L;
        this.errorMessage = null;
        initCause(exception);
    }

    public WebRequestException(Exception exception, long retryAfter) {
        super(exception.getMessage());
        this.url = "Unknown";
        this.responseCode = -1;
        this.retryAfter = retryAfter;
        this.errorMessage = null;
        initCause(exception);
    }

    public WebRequestException(String url, Exception exception) {
        super(exception.getMessage());
        this.url = url;
        this.responseCode = -1;
        this.retryAfter = -1L;
        this.errorMessage = null;
        initCause(exception);
    }

    public WebRequestException(String url, Exception exception, long retryAfter) {
        super(exception.getMessage());
        this.url = url;
        this.responseCode = -1;
        this.retryAfter = retryAfter;
        this.errorMessage = null;
        initCause(exception);
    }

    public WebRequestException(String url, int responseCode, String message, Exception cause) {
        super(message, cause);
        this.url = url;
        this.responseCode = responseCode;
        this.retryAfter = -1L;
        this.errorMessage = message;
    }

    public WebRequestException(String url, int responseCode, String message, Exception cause, long retryAfter) {
        super(message, cause);
        this.url = url;
        this.responseCode = responseCode;
        this.errorMessage = message;
        this.retryAfter = retryAfter;
    }

    public WebRequestException(String url, int responseCode, String message) {
        super(message);
        this.url = url;
        this.responseCode = responseCode;
        this.errorMessage = message;
        this.retryAfter = -1L;
    }

    public WebRequestException(String url, int responseCode, String message, long retryAfter) {
        super(message);
        this.url = url;
        this.responseCode = responseCode;
        this.errorMessage = message;
        this.retryAfter = retryAfter;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public long getRetryAfter() {
        return this.retryAfter;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "(" + this.url + ")" + super.getMessage();
    }

    @Nullable
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
