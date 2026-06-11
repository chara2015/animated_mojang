package net.labymod.v1_8_9.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/compatibility/optifine/MixinOptiFineGameSettings.class */
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({avh.class})
public class MixinOptiFineGameSettings {
    @Redirect(method = {"loadOptions"}, at = @At(value = "FIELD", opcode = 180, ordinal = 0, target = "Lnet/minecraft/client/settings/GameSettings;enableVsync:Z"))
    @Dynamic
    public boolean labyMod$fixFpsLimit(avh settings) {
        return false;
    }
}
