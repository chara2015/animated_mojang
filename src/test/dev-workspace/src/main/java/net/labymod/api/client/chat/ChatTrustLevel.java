package net.labymod.api.client.chat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatTrustLevel.class */
public enum ChatTrustLevel {
    SECURE(10526880),
    NOT_SECURE(15224664),
    FILTERED(15386724),
    MODIFIED(15386724),
    SYSTEM(13684944);

    private final int hexColor;

    ChatTrustLevel(int hexColor) {
        this.hexColor = hexColor;
    }

    public int getHexColor() {
        return this.hexColor;
    }
}
