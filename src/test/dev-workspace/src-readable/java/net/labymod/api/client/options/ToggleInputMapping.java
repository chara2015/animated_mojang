package net.labymod.api.client.options;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/options/ToggleInputMapping.class */
public interface ToggleInputMapping extends MinecraftInputMapping {
    boolean needsToggle();

    void forcePress();

    void forceUnpress();

    boolean toggle();
}
