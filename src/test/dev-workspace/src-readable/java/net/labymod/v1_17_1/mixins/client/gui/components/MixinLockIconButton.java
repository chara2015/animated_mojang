package net.labymod.v1_17_1.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/gui/components/MixinLockIconButton.class */
@Mixin({dxl.class})
public abstract class MixinLockIconButton extends MixinAbstractWidget {
    @Override // net.labymod.v1_17_1.mixins.client.gui.components.MixinAbstractWidget
    @Insert(method = {"renderButton"}, at = @At("HEAD"), cancellable = true)
    public void render(dql poseStack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((dwy) this).g());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(((VanillaStackAccessor) poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
