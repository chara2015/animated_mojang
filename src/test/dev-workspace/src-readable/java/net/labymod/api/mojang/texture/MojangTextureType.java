package net.labymod.api.mojang.texture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mojang/texture/MojangTextureType.class */
public enum MojangTextureType {
    SKIN("skins"),
    CAPE("capes"),
    ELYTRA("elytra");

    public static final MojangTextureType[] VALUES = values();
    private final String locationPrefix;

    MojangTextureType(String locationPrefix) {
        this.locationPrefix = locationPrefix;
    }

    public String getLocationPrefix() {
        return this.locationPrefix;
    }
}
