package net.labymod.v1_18_2.mixins.client.gui.components;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/components/MixinSliderButton.class */
@Mixin({eav.class})
public class MixinSliderButton extends MixinAbstractSliderButton {

    @Shadow
    @Final
    private dyz c;

    @Override // net.labymod.v1_18_2.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMinValue() {
        return (float) this.c.b();
    }

    @Override // net.labymod.v1_18_2.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMaxValue() {
        return (float) this.c.c();
    }

    @Override // net.labymod.v1_18_2.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getSteps() {
        return this.c.getSteps();
    }
}
