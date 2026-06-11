package net.labymod.v26_1_1.mixins.client;

import java.io.File;
import net.minecraft.client.Screenshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/MixinScreenshot.class */
@Mixin({Screenshot.class})
public abstract class MixinScreenshot {
    @Shadow
    private static File getFile(File file) {
        return null;
    }
}
