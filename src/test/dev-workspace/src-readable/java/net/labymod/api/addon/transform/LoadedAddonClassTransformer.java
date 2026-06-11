package net.labymod.api.addon.transform;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/transform/LoadedAddonClassTransformer.class */
public interface LoadedAddonClassTransformer {
    void init();

    boolean shouldTransform(String str, String str2);

    byte[] transform(String str, String str2, byte[] bArr);
}
