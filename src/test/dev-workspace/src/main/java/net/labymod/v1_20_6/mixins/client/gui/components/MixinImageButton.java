package net.labymod.v1_20_6.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/components/MixinImageButton.class */
@Mixin({fhs.class})
public abstract class MixinImageButton extends MixinAbstractButton implements ImageButtonAccessor {

    @Shadow
    @Final
    protected fit a;

    @Override // net.labymod.v1_20_6.mixins.client.gui.components.MixinAbstractButton
    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(fgt graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((fhe) this).y());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(graphics.c().stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public ResourceLocation getResourceLocation() {
        return this.a.a(B(), A());
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getXTexStart() {
        return C();
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYTexStart() {
        return D();
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
