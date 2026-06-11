package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinStringWidget.class */
@Mixin({StringWidget.class})
public abstract class MixinStringWidget extends MixinAbstractWidget implements StringWidgetAccessor {

    @Shadow
    private int maxWidth;

    @Shadow
    private StringWidget.TextOverflow textOverflow;
    private boolean labyMod$basedOnTextWidth;

    @Inject(method = {"<init>(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V"}, at = {@At("TAIL")})
    private void labyMod$onInit(Component $$0, Font $$1, CallbackInfo ci) {
        this.labyMod$basedOnTextWidth = true;
    }

    public boolean isBasedOnTextWidth() {
        return this.labyMod$basedOnTextWidth;
    }

    public int getTextColor() {
        return -1;
    }

    public boolean isClipped() {
        return this.textOverflow == StringWidget.TextOverflow.CLAMPED;
    }

    public float getMaxWidth() {
        return this.maxWidth;
    }
}

