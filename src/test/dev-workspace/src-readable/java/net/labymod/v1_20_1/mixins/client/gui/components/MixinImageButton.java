package net.labymod.v1_20_1.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/gui/components/MixinImageButton.class */
@Mixin({ept.class})
public class MixinImageButton extends MixinAbstractWidget implements ImageButtonAccessor {

    @Shadow
    @Final
    private acq b;

    @Shadow
    @Final
    private int c;

    @Shadow
    @Final
    private int d;

    @Shadow
    @Final
    private int e;

    @Shadow
    @Final
    private int f;

    @Shadow
    @Final
    private int A;

    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(eox graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((epf) this).l());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(graphics.c().stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public ResourceLocation getResourceLocation() {
        return this.b;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getXTexStart() {
        return this.c;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYTexStart() {
        return this.d;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getYDiffTex() {
        return this.e;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getTextureWidth() {
        return this.f;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public int getTextureHeight() {
        return this.A;
    }
}
