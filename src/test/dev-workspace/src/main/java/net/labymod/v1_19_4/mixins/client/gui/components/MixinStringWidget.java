package net.labymod.v1_19_4.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/components/MixinStringWidget.class */
@Mixin({epc.class})
public abstract class MixinStringWidget extends MixinAbstractWidget implements StringWidgetAccessor {
    private boolean labyMod$basedOnTextWidth;

    @Inject(method = {"<init>(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V"}, at = {@At("TAIL")})
    private void labyMod$onInit(tj $$0, enp $$1, CallbackInfo ci) {
        this.labyMod$basedOnTextWidth = true;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public boolean isBasedOnTextWidth() {
        return this.labyMod$basedOnTextWidth;
    }

    @Inject(method = {"renderWidget"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderWidget(ehe poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        getWatcher().update(this, ((enz) this).k());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(((VanillaStackAccessor) poseStack).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
