package net.labymod.v1_8_9.client.input;

import net.labymod.api.client.options.MinecraftInputMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/input/KeyBindingHelper.class */
public final class KeyBindingHelper {
    public static void unpressKeybindings() {
        avh settings = ave.A().t;
        for (MinecraftInputMapping minecraftInputMapping : settings.ax) {
            minecraftInputMapping.unpress();
        }
    }
}
