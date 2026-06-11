package net.labymod.core.client.session;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/SessionUtil.class */
public final class SessionUtil {
    private SessionUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static boolean isPremium(String accessToken) {
        return accessToken != null && accessToken.length() > 10;
    }
}
