package net.labymod.v26_1_2.mixins.compatibility.optifine;

import java.io.File;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v26_1_2.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.minecraft.client.Screenshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/compatibility/optifine/MixinOptiFineScreenshot.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({Screenshot.class})
public abstract class MixinOptiFineScreenshot {
    @Shadow
    private static File getFile(File file) {
        return null;
    }
}
