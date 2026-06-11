package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiScreenOptionsSoundsButton.class */
@Mixin(targets = {"net/minecraft/client/gui/GuiScreenOptionsSounds$Button"})
public abstract class MixinGuiScreenOptionsSoundsButton extends bja implements GuiSliderAccessor {

    @Shadow
    public float o;

    @Shadow
    public boolean p;

    @Shadow
    @Final
    private qg r;

    @Shadow
    public abstract boolean b(bib bibVar, int i, int i2);

    public MixinGuiScreenOptionsSoundsButton(int p_i1844_1_, int p_i1844_2_, int p_i1844_3_, String p_i1844_4_) {
        super(p_i1844_1_, p_i1844_2_, p_i1844_3_, p_i1844_4_);
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public boolean isDragging() {
        return this.p;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getMinValue() {
        return 0.0f;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getMaxValue() {
        return 1.0f;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getValue() {
        return this.o;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getStep() {
        return 0.0f;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public void labymod$mouseDragged(bib minecraft, MutableMouse mouse) {
        if (this.p) {
            b(minecraft, mouse.getX(), this.i);
        }
    }
}
