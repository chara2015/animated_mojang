package net.minecraft.network.protocol.handshake;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/handshake/ClientIntent.class */
public enum ClientIntent {
    STATUS,
    LOGIN,
    TRANSFER;

    private static final int STATUS_ID = 1;
    private static final int LOGIN_ID = 2;
    private static final int TRANSFER_ID = 3;

    public static ClientIntent byId(int $$0) {
        switch ($$0) {
            case 1:
                return STATUS;
            case 2:
                return LOGIN;
            case 3:
                return TRANSFER;
            default:
                throw new IllegalArgumentException("Unknown connection intent: " + $$0);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public int id() throws MatchException {
        switch (this) {
            case STATUS:
                return 1;
            case LOGIN:
                return 2;
            case TRANSFER:
                return 3;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
