package net.labymod.api.labyconnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/LanWorldRejectReason.class */
public enum LanWorldRejectReason {
    REQUEST_RETRACTED(0),
    REQUEST_REJECTED(1),
    REQUEST_EXPIRED(2),
    USER_DISCONNECTED(3),
    INCOMPATIBLE_MINECRAFT_VERSION(4),
    UNSUPPORTED_CLIENT_VERSION(5);

    private final int id;

    LanWorldRejectReason(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static LanWorldRejectReason fromId(int id) {
        for (LanWorldRejectReason reason : values()) {
            if (reason.id == id) {
                return reason;
            }
        }
        return null;
    }
}
