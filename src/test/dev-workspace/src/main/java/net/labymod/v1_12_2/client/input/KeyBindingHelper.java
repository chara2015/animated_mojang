package net.labymod.v1_12_2.client.input;

import net.labymod.api.client.options.MinecraftInputMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/input/KeyBindingHelper.class */
public final class KeyBindingHelper {
    public static void unpressKeybindings() {
        bid settings = bib.z().t;
        for (MinecraftInputMapping minecraftInputMapping : settings.as) {
            minecraftInputMapping.unpress();
        }
    }
}
