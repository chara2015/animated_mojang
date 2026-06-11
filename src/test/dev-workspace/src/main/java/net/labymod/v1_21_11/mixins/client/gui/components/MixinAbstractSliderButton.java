package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinAbstractSliderButton.class */
@Mixin({giz.class})
public abstract class MixinAbstractSliderButton extends MixinAbstractWidget implements SliderButtonAccessor {

    @Shadow
    protected double e;

    @Shadow
    private boolean r;

    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(gir graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((gjc) this).B());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public double getRawValue() {
        return this.e;
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
        return this.r;
    }
}
