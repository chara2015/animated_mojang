package net.labymod.v1_21_8.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.labymod.v1_21_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/gui/components/MixinAbstractSliderButton.class */
@Mixin({fxj.class})
public abstract class MixinAbstractSliderButton extends MixinAbstractWidget implements SliderButtonAccessor {

    @Shadow
    protected double d;

    @Unique
    private boolean labymod$dragging;

    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(fxb graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((fxm) this).A());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public double getRawValue() {
        return this.d;
    }

    public float getMinValue() {
        return 0.0f;
    }

    public float getMaxValue() {
        return 1.0f;
    }

    public float getSteps() {
        return 0.0f;
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public boolean isDragging() {
        return this.labymod$dragging;
    }

    @Inject(method = {"onDrag"}, at = {@At("HEAD")})
    public void captureDragging(double $$0, double $$1, double $$2, double $$3, CallbackInfo ci) {
        this.labymod$dragging = true;
    }

    @Inject(method = {"onRelease"}, at = {@At("HEAD")})
    public void captureRelease(double lvt_1_1_, double lvt_3_1_, CallbackInfo ci) {
        this.labymod$dragging = false;
    }
}
