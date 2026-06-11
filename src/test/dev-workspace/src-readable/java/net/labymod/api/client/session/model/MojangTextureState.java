package net.labymod.api.client.session.model;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/model/MojangTextureState.class */
public enum MojangTextureState {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private static final MojangTextureState[] VALUES = values();
    private final String id;

    MojangTextureState(String id) {
        this.id = id;
    }

    public static MojangTextureState of(String id) {
        for (MojangTextureState state : VALUES) {
            if (state.id.equalsIgnoreCase(id)) {
                return state;
            }
        }
        return null;
    }

    public String getId() {
        return this.id;
    }
}
