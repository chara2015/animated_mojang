package net.labymod.v1_17_1.mixins.client.gui.components;

import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/gui/components/MixinVolumeSlider.class */
@Mixin({dxu.class})
public class MixinVolumeSlider extends MixinAbstractSliderButton {
    @Override // net.labymod.v1_17_1.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMaxValue() {
        return 100.0f;
    }

    @Override // net.labymod.v1_17_1.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getSteps() {
        return 1.0f;
    }
}
