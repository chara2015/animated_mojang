package net.labymod.v1_16_5.mixins.compatibility.optifine;

import java.io.File;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_16_5.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/compatibility/optifine/MixinOptiFineScreenshot.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({dkh.class})
public abstract class MixinOptiFineScreenshot {
    @Shadow
    private static File a(File file) {
        return null;
    }
}
