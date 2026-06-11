package net.labymod.v1_21_11.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineResUtils.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin(targets = {"net.optifine.util.ResUtils"})
public class MixinOptiFineResUtils {
}
