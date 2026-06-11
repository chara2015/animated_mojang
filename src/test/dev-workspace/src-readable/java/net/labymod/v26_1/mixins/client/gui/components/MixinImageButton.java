package net.labymod.v26_1.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.labymod.v26_1.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/gui/components/MixinImageButton.class */
@Mixin({ImageButton.class})
public abstract class MixinImageButton extends MixinAbstractButton implements ImageButtonAccessor {

    @Shadow
    @Final
    protected WidgetSprites sprites;

    @Inject(method = {"extractContents"}, at = {@At("HEAD")}, cancellable = true)
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        getWatcher().update(this, ((AbstractWidget) this).getMessage());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public ResourceLocation getResourceLocation() {
        return this.sprites.get(isActive(), isHoveredOrFocused());
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getXTexStart() {
        return getX();
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYTexStart() {
        return getY();
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYDiffTex() {
        return 0;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getTextureWidth() {
        return this.width;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getTextureHeight() {
        return this.height;
    }
}
