package net.labymod.v1_21_3.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_21_3.client.gui.IntRangeBaseSliderableValueSet;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/compatibility/optifine/MixinOptiFineOptionInstance.class */
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({fmj.class})
public class MixinOptiFineOptionInstance<T> {

    @Shadow
    @Final
    private n<T> f;

    @Inject(method = {"getIntRangeBase()Lnet/minecraft/client/OptionInstance$IntRangeBase;"}, at = {@At("HEAD")}, cancellable = true)
    @Dynamic
    private void labyMod$fixOptiFine(CallbackInfoReturnable<g> cir) {
        IntRangeBaseSliderableValueSet<?> intRangeBaseSliderableValueSet = this.f;
        if (intRangeBaseSliderableValueSet instanceof IntRangeBaseSliderableValueSet) {
            IntRangeBaseSliderableValueSet<?> set = intRangeBaseSliderableValueSet;
            cir.setReturnValue(set.base());
        }
    }
}
