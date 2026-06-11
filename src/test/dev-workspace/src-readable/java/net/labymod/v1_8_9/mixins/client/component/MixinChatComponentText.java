package net.labymod.v1_8_9.mixins.client.component;

import java.util.Objects;
import net.labymod.api.client.component.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinChatComponentText.class */
@Mixin({fa.class})
public abstract class MixinChatComponentText extends MixinChatComponentStyle<TextComponent> implements TextComponent {

    @Shadow
    @Mutable
    @Final
    private String b;

    @Override // net.labymod.api.client.component.TextComponent
    public String getText() {
        return this.b;
    }

    @Override // net.labymod.api.client.component.TextComponent
    public TextComponent text(String text) {
        this.b = text;
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public TextComponent plainCopy() {
        return new fa(this.b);
    }

    @Override // net.labymod.v1_8_9.mixins.client.component.MixinChatComponentStyle
    public int hashCode() {
        return Objects.hash(b(), this.a, this.b);
    }
}
