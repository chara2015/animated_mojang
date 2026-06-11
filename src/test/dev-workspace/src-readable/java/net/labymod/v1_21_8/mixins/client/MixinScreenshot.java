package net.labymod.v1_21_8.mixins.client;

import java.io.File;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/MixinScreenshot.class */
@Mixin({fum.class})
public abstract class MixinScreenshot {
    @Shadow
    private static File a(File file) {
        return null;
    }
}
