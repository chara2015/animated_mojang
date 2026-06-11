package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiOptionSlider.class */
@Mixin({bjs.class})
public abstract class MixinGuiOptionSlider extends bja implements GuiSliderAccessor {

    @Shadow
    @Final
    private a q;

    @Shadow
    private float p;

    @Shadow
    public boolean o;

    public MixinGuiOptionSlider(int a, int b, int c, String d) {
        super(a, b, c, d);
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public boolean isDragging() {
        return this.o;
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getMinValue() {
        return this.q.getMinValue();
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getMaxValue() {
        return this.q.f();
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getValue() {
        return this.q.d(this.p);
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public float getStep() {
        return this.q.getStep();
    }

    @Override // net.labymod.v1_12_2.client.gui.GuiSliderAccessor
    public void labymod$mouseDragged(bib mc, MutableMouse mouse) {
        if (this.m && this.o) {
            this.p = (mouse.getX() - (this.h + 4)) / (this.f - 8);
            this.p = MathHelper.clamp(this.p, 0.0f, 1.0f);
            float value = this.q.d(this.p);
            mc.t.a(this.q, value);
            this.p = this.q.c(value);
            this.j = mc.t.c(this.q);
        }
    }
}
