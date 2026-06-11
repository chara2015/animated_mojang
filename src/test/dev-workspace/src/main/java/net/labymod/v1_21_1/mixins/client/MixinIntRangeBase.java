package net.labymod.v1_21_1.mixins.client;

import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import net.labymod.v1_21_1.client.gui.IntRangeBaseSliderableValueSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/MixinIntRangeBase.class */
@Mixin({g.class})
public interface MixinIntRangeBase {
    @Inject(method = {"xmap"}, at = {@At("HEAD")}, cancellable = true)
    private default <R> void labyMod$replaceInnerClass(IntFunction<? extends R> intFunction, ToIntFunction<? super R> mapperFunction, CallbackInfoReturnable<k<R>> cir) {
        cir.setReturnValue(new IntRangeBaseSliderableValueSet((g) this, intFunction, mapperFunction));
    }
}
