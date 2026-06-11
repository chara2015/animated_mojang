package net.labymod.core.main.user.shop.emote.exception;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/exception/EmoteException.class */
public class EmoteException extends RuntimeException {
    private final int emoteId;
    private final String reason;

    public EmoteException(int emoteId, String reason) {
        this.emoteId = emoteId;
        this.reason = reason;
    }

    public EmoteException(int emoteId, String message, String reason) {
        super(message);
        this.emoteId = emoteId;
        this.reason = reason;
    }

    public EmoteException(int emoteId, String message, String reason, Throwable cause) {
        super(message, cause);
        this.emoteId = emoteId;
        this.reason = reason;
    }

    public EmoteException(int emoteId, String reason, Throwable cause) {
        super(cause);
        this.emoteId = emoteId;
        this.reason = reason;
    }

    public int getEmoteId() {
        return this.emoteId;
    }

    public String getReason() {
        return this.reason;
    }
}
