package net.labymod.api.client.network.server;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerType.class */
public enum ServerType {
    LAN,
    REALM,
    THIRD_PARTY,
    ONLINE;

    private static final ServerType[] VALUES = values();

    public static ServerType[] getValues() {
        return VALUES;
    }

    public static ServerType get(String name) {
        for (ServerType value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + ServerType.class.getCanonicalName() + "." + name);
    }

    public static ServerType getOrDefault(String name, ServerType defaultValue) {
        for (ServerType value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
}
