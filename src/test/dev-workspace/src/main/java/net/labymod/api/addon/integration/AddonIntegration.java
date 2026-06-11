package net.labymod.api.addon.integration;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/integration/AddonIntegration.class */
public interface AddonIntegration {
    void load();

    default void onIntegratedAddonEnable() {
    }

    default void onIntegratedAddonDisable() {
    }
}
