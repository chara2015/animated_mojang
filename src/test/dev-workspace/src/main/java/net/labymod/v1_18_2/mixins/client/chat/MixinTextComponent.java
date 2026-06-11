package net.labymod.v1_18_2.mixins.client.chat;

import java.util.Objects;
import net.labymod.api.client.component.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/chat/MixinTextComponent.class */
@Mixin({qx.class})
@Implements({@Interface(iface = TextComponent.class, prefix = "textComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinTextComponent extends MixinBaseComponent<TextComponent> implements TextComponent {

    @Shadow
    @Mutable
    @Final
    private String e;

    @Shadow
    public abstract qx shadow$i();

    @Shadow
    public abstract String shadow$h();

    @Override // net.labymod.api.client.component.TextComponent
    public TextComponent text(String text) {
        this.e = text;
        return this;
    }

    @Intrinsic
    public String textComponent$getText() {
        return shadow$h();
    }

    @Intrinsic
    public TextComponent textComponent$plainCopy() {
        return shadow$i();
    }

    public int hashCode() {
        return Objects.hash(c(), this.a, this.e);
    }
}
