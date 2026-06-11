package net.labymod.v1_21_8.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.labymod.v1_21_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/gui/components/MixinImageButton.class */
@Mixin({fya.class})
public abstract class MixinImageButton extends MixinAbstractButton implements ImageButtonAccessor {

    @Shadow
    @Final
    protected fzd a;

    @Override // net.labymod.v1_21_8.mixins.client.gui.components.MixinAbstractButton
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

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public ResourceLocation getResourceLocation() {
        return this.a.a(D(), C());
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getXTexStart() {
        return E();
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYTexStart() {
        return F();
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYDiffTex() {
        return 0;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getTextureWidth() {
        return this.g;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getTextureHeight() {
        return this.h;
    }
}
