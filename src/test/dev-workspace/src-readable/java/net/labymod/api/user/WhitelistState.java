package net.labymod.api.user;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/WhitelistState.class */
public enum WhitelistState {
    LOADING,
    NOT_WHITELISTED,
    UNKNOWN,
    WHITELISTED;

    public boolean isWhitelisted() {
        return this == WHITELISTED;
    }

    public boolean isLoaded() {
        return (this == UNKNOWN || this == LOADING) ? false : true;
    }

    public static WhitelistState of(boolean whitelisted) {
        return whitelisted ? WHITELISTED : NOT_WHITELISTED;
    }
}
