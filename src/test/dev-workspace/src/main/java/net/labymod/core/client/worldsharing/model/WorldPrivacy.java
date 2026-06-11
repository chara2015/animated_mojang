package net.labymod.core.client.worldsharing.model;

import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/WorldPrivacy.class */
public enum WorldPrivacy {
    PUBLIC(0),
    FRIENDS(1),
    WHITELIST(2),
    LAN(3);

    private final byte id;

    WorldPrivacy(int id) {
        this.id = (byte) id;
    }

    public byte get() {
        return this.id;
    }

    public boolean isWhitelist() {
        return this == WHITELIST;
    }

    public boolean isLan() {
        return this == LAN;
    }

    public boolean isPublic() {
        return this == PUBLIC;
    }

    public boolean isFriends() {
        return this == FRIENDS;
    }

    public boolean inServerList() {
        switch (this) {
            case PUBLIC:
            case FRIENDS:
                return true;
            default:
                return false;
        }
    }

    @Override // java.lang.Enum
    public String toString() {
        return I18n.translate("labymod.activity.worldsharing.enums.privacy." + name().toLowerCase(), new Object[0]);
    }
}
