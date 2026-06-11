package net.labymod.v26_1.mixins.client.chat;

import net.labymod.api.util.Color;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/chat/MixinTextColor.class */
@Mixin({TextColor.class})
@Implements({@Interface(iface = net.labymod.api.client.component.format.TextColor.class, prefix = "textColor$", remap = Interface.Remap.NONE)})
public abstract class MixinTextColor implements net.labymod.api.client.component.format.TextColor {
    private Color labyMod$color;

    @Shadow
    public abstract int shadow$getValue();

    @Shadow
    public abstract String shadow$serialize();

    @Intrinsic
    public int textColor$getValue() {
        return shadow$getValue();
    }

    @Intrinsic
    public String textColor$serialize() {
        return shadow$serialize();
    }

    @Override // net.labymod.api.client.component.format.TextColor
    public Color color() {
        if (this.labyMod$color == null) {
            this.labyMod$color = Color.of(textColor$getValue());
        }
        return this.labyMod$color;
    }
}
