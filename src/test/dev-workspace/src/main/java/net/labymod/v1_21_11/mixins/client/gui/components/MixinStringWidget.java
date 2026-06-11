package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinStringWidget.class */
@Mixin({gko.class})
public abstract class MixinStringWidget extends MixinAbstractWidget implements StringWidgetAccessor {

    @Shadow
    private int b;

    @Shadow
    private a e;
    private boolean labyMod$basedOnTextWidth;

    @Inject(method = {"<init>(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V"}, at = {@At("TAIL")})
    private void labyMod$onInit(yh $$0, gio $$1, CallbackInfo ci) {
        this.labyMod$basedOnTextWidth = true;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public boolean isBasedOnTextWidth() {
        return this.labyMod$basedOnTextWidth;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public int getTextColor() {
        return -1;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public boolean isClipped() {
        return this.e == a.a;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public float getMaxWidth() {
        return this.b;
    }
}
