package net.labymod.v1_21_10.mixins.client.chat;

import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/MixinTextColor.class */
@Mixin({yx.class})
@Implements({@Interface(iface = TextColor.class, prefix = "textColor$", remap = Interface.Remap.NONE)})
public abstract class MixinTextColor implements TextColor {
    private Color labyMod$color;

    @Shadow
    public abstract int shadow$a();

    @Shadow
    public abstract String shadow$b();

    @Intrinsic
    public int textColor$getValue() {
        return shadow$a();
    }

    @Intrinsic
    public String textColor$serialize() {
        return shadow$b();
    }

    @Override // net.labymod.api.client.component.format.TextColor
    public Color color() {
        if (this.labyMod$color == null) {
            this.labyMod$color = Color.of(textColor$getValue());
        }
        return this.labyMod$color;
    }
}
