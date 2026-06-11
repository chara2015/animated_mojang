package net.labymod.v1_21_11.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.v1_21_11.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineKeyUtils.class */
@Pseudo
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin(targets = {"net.optifine.util.KeyUtils"}, remap = false)
public class MixinOptiFineKeyUtils {
}
