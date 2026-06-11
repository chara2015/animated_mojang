package net.labymod.api.laby3d.foreign.texture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/texture/ForeignDeviceTextureRegistry.class */
public abstract class ForeignDeviceTextureRegistry {
    public abstract void register(ForeignDeviceTexture foreignDeviceTexture);

    public abstract void unregister(ForeignDeviceTexture foreignDeviceTexture);

    public abstract ForeignDeviceTexture get(Object obj);

    protected ForeignDeviceTextureRegistry() {
    }
}
