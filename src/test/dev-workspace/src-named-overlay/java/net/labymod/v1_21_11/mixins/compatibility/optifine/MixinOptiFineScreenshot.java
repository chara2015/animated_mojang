package net.labymod.v1_21_11.mixins.compatibility.optifine;

import java.io.File;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.v1_21_11.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.minecraft.client.Screenshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineScreenshot.class */
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({Screenshot.class})
public abstract class MixinOptiFineScreenshot {
    @Shadow
    private static File getFile(File file) {
        return null;
    }
}

