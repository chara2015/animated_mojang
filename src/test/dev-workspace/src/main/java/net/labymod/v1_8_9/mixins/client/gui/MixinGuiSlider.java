package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_8_9.client.gui.GuiSliderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiSlider.class */
@Mixin({avx.class})
public abstract class MixinGuiSlider implements GuiSliderAccessor {

    @Shadow
    @Final
    private float r;

    @Shadow
    @Final
    private float s;

    @Shadow
    public boolean o;

    @Shadow
    public abstract float c();

    @Override // net.labymod.v1_8_9.client.gui.GuiSliderAccessor
    public boolean isDragging() {
        return this.o;
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiSliderAccessor
    public float getMinValue() {
        return this.r;
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiSliderAccessor
    public float getMaxValue() {
        return this.s;
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiSliderAccessor
    public float getValue() {
        return c();
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiSliderAccessor
    public float getStep() {
        return 0.0f;
    }

    @Override // net.labymod.v1_8_9.client.gui.GuiSliderAccessor
    public void labymod$mouseDragged(ave minecraft, MutableMouse mouse) {
    }
}
