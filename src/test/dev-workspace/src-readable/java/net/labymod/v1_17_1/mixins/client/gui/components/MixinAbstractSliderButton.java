package net.labymod.v1_17_1.mixins.client.gui.components;

import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/gui/components/MixinAbstractSliderButton.class */
@Mixin({dwx.class})
public class MixinAbstractSliderButton implements SliderButtonAccessor {

    @Shadow
    protected double b;

    @Unique
    private boolean labymod$dragging;

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public double getRawValue() {
        return this.b;
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMinValue() {
        return 0.0f;
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMaxValue() {
        return 1.0f;
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getSteps() {
        return 0.0f;
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public boolean isDragging() {
        return this.labymod$dragging;
    }

    @Inject(method = {"onDrag"}, at = {@At("HEAD")})
    public void captureDragging(double lvt_1_1_, double lvt_3_1_, double lvt_5_1_, double lvt_7_1_, CallbackInfo ci) {
        this.labymod$dragging = true;
    }

    @Inject(method = {"onRelease"}, at = {@At("HEAD")})
    public void captureRelease(double lvt_1_1_, double lvt_3_1_, CallbackInfo ci) {
        this.labymod$dragging = false;
    }
}
