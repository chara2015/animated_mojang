package net.labymod.v1_12_2.mixins.client.screen;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/screen/MixinGuiScreenClipboard.class */
@Mixin({blk.class})
public class MixinGuiScreenClipboard {
    @Overwrite
    public static String o() {
        return Laby.labyAPI().minecraft().getClipboard();
    }

    @Overwrite
    public static void e(String value) {
        Laby.labyAPI().minecraft().setClipboard(value);
    }
}
