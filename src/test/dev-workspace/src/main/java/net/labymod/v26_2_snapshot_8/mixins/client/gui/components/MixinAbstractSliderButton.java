package net.labymod.v26_2_snapshot_8.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.labymod.v26_2_snapshot_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/components/MixinAbstractSliderButton.class */
@Mixin({AbstractSliderButton.class})
public abstract class MixinAbstractSliderButton extends MixinAbstractWidget implements SliderButtonAccessor {

    @Shadow
    protected double value;

    @Insert(method = {"extractWidgetRenderState"}, at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((AbstractWidget) this).getMessage());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Override // net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public double getRawValue() {
        return this.value;
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
}
