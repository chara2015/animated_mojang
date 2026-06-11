package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinImageButton.class */
@Mixin({ImageButton.class})
public abstract class MixinImageButton extends MixinAbstractButton implements ImageButtonAccessor {

    @Shadow
    @Final
    protected WidgetSprites sprites;

    @Inject(method = {"renderContents"}, at = {@At("HEAD")}, cancellable = true)
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        getWatcher().update(this, ((AbstractWidget) this).getMessage());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create(JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    public ResourceLocation getResourceLocation() {
        return this.sprites.get(b(), D());
    }

    public int getXTexStart() {
        return aT_();
    }

    public int getYTexStart() {
        return aU_();
    }

    public int getYDiffTex() {
        return 0;
    }

    public int getTextureWidth() {
        return this.g;
    }

    public int getTextureHeight() {
        return this.h;
    }
}

