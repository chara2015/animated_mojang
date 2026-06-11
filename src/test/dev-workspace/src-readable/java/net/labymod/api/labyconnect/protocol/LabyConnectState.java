package net.labymod.api.labyconnect.protocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/LabyConnectState.class */
public enum LabyConnectState {
    HELLO(-1),
    LOGIN(0),
    PLAY(1),
    ALL(2),
    OFFLINE(3);

    private final int id;

    LabyConnectState(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
