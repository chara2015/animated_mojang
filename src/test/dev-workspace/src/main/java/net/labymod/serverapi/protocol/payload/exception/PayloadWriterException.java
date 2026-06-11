package net.labymod.serverapi.protocol.payload.exception;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/payload/exception/PayloadWriterException.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class PayloadWriterException extends PayloadException {
    public PayloadWriterException() {
    }

    public PayloadWriterException(String message) {
        super(message);
    }

    public PayloadWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayloadWriterException(Throwable cause) {
        super(cause);
    }

    public PayloadWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
