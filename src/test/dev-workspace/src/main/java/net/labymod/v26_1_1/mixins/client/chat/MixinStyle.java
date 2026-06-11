package net.labymod.v26_1_1.mixins.client.chat;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1_1.client.util.MinecraftUtil;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/chat/MixinStyle.class */
@Mixin({Style.class})
@Implements({@Interface(iface = net.labymod.api.client.component.format.Style.class, prefix = "style$", remap = Interface.Remap.NONE)})
public abstract class MixinStyle implements net.labymod.api.client.component.format.Style {

    @Shadow
    @Final
    @Nullable
    private Boolean bold;

    @Shadow
    @Final
    @Nullable
    private Boolean italic;

    @Shadow
    @Final
    @Nullable
    private Boolean underlined;

    @Shadow
    @Final
    @Nullable
    private Boolean strikethrough;

    @Shadow
    @Final
    @Nullable
    private Boolean obfuscated;
    private GlyphSourceDescription labyMod$glyphSourceDescription;

    @Shadow
    @Final
    private FontDescription font;

    @Shadow
    @Nullable
    public abstract HoverEvent shadow$getHoverEvent();

    @Shadow
    public abstract Style withHoverEvent(@Nullable HoverEvent hoverEvent);

    @Shadow
    @Nullable
    public abstract ClickEvent shadow$getClickEvent();

    @Shadow
    public abstract Style withClickEvent(@org.jetbrains.annotations.Nullable ClickEvent clickEvent);

    @Shadow
    public abstract Style withColor(@org.jetbrains.annotations.Nullable TextColor textColor);

    @Shadow
    @Nullable
    public abstract TextColor shadow$getColor();

    @Shadow
    @Nullable
    public abstract Integer shadow$getShadowColor();

    @Shadow
    public abstract String shadow$getInsertion();

    @Shadow
    public abstract boolean isBold();

    @Shadow
    public abstract boolean isItalic();

    @Shadow
    public abstract boolean isUnderlined();

    @Shadow
    public abstract boolean isStrikethrough();

    @Shadow
    public abstract boolean isObfuscated();

    @Shadow
    public abstract Style withBold(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withItalic(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withUnderlined(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withStrikethrough(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withObfuscated(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withInsertion(@org.jetbrains.annotations.Nullable String str);

    @Shadow
    public abstract FontDescription shadow$getFont();

    @Shadow
    public abstract Style withFont(FontDescription fontDescription);

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$updateFont(CallbackInfo ci) {
        GlyphSourceDescription glyphSourceDescriptionFromMinecraft;
        if (this.font == null) {
            glyphSourceDescriptionFromMinecraft = null;
        } else {
            glyphSourceDescriptionFromMinecraft = MinecraftUtil.fromMinecraft(this.font);
        }
        this.labyMod$glyphSourceDescription = glyphSourceDescriptionFromMinecraft;
    }

    @Intrinsic
    public net.labymod.api.client.component.event.HoverEvent<?> style$getHoverEvent() {
        return shadow$getHoverEvent();
    }

    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style hoverEvent(net.labymod.api.client.component.event.HoverEvent<?> hoverEvent) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withHoverEvent((HoverEvent) CastUtil.cast(hoverEvent)));
    }

    @Intrinsic
    public net.labymod.api.client.component.event.ClickEvent style$getClickEvent() {
        return shadow$getClickEvent();
    }

    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style clickEvent(net.labymod.api.client.component.event.ClickEvent clickEvent) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withClickEvent((ClickEvent) CastUtil.cast(clickEvent)));
    }

    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style insertion(String insertion) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withInsertion(insertion));
    }

    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style color(net.labymod.api.client.component.format.TextColor color) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withColor((TextColor) CastUtil.cast(color)));
    }

    @Override // net.labymod.api.client.component.format.Style
    public GlyphSourceDescription getGlyphSourceDescription() {
        return this.labyMod$glyphSourceDescription;
    }

    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style glyphSourceDescription(GlyphSourceDescription description) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withFont(MinecraftUtil.toMinecraft(description)));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public boolean hasDecoration(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return isBold();
            case ITALIC:
                return isItalic();
            case UNDERLINED:
                return isUnderlined();
            case STRIKETHROUGH:
                return isStrikethrough();
            case OBFUSCATED:
                return isObfuscated();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public boolean isDecorationSet(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return this.bold != null;
            case ITALIC:
                return this.italic != null;
            case UNDERLINED:
                return this.underlined != null;
            case STRIKETHROUGH:
                return this.strikethrough != null;
            case OBFUSCATED:
                return this.obfuscated != null;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style decorate(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return setBold(true);
            case ITALIC:
                return setItalic(true);
            case UNDERLINED:
                return setUnderlined(true);
            case STRIKETHROUGH:
                return setStrikethrough(true);
            case OBFUSCATED:
                return setObfuscated(true);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style undecorate(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return setBold(false);
            case ITALIC:
                return setItalic(false);
            case UNDERLINED:
                return setUnderlined(false);
            case STRIKETHROUGH:
                return setStrikethrough(false);
            case OBFUSCATED:
                return setObfuscated(false);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public net.labymod.api.client.component.format.Style unsetDecoration(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return setBold(null);
            case ITALIC:
                return setItalic(null);
            case UNDERLINED:
                return setUnderlined(null);
            case STRIKETHROUGH:
                return setStrikethrough(null);
            case OBFUSCATED:
                return setObfuscated(null);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.component.format.Style
    public boolean isEmpty() {
        return this == Style.EMPTY;
    }

    @Intrinsic
    public net.labymod.api.client.component.format.TextColor style$getColor() {
        return (net.labymod.api.client.component.format.TextColor) CastUtil.cast(shadow$getColor());
    }

    @Intrinsic
    public Integer style$getShadowColor() {
        return shadow$getShadowColor();
    }

    @Intrinsic
    public String style$getInsertion() {
        return shadow$getInsertion();
    }

    private net.labymod.api.client.component.format.Style setBold(Boolean value) {
        return set(value, this::isBold, this::withBold);
    }

    private net.labymod.api.client.component.format.Style setItalic(Boolean value) {
        return set(value, this::isItalic, this::withItalic);
    }

    private net.labymod.api.client.component.format.Style setUnderlined(Boolean value) {
        return set(value, this::isUnderlined, this::withUnderlined);
    }

    private net.labymod.api.client.component.format.Style setStrikethrough(Boolean value) {
        return set(value, this::isStrikethrough, this::withStrikethrough);
    }

    private net.labymod.api.client.component.format.Style setObfuscated(Boolean value) {
        return set(value, this::isObfuscated, this::withObfuscated);
    }

    private net.labymod.api.client.component.format.Style set(Boolean value, Supplier<Boolean> getter, Function<Boolean, Style> setter) {
        Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(setter.apply(value));
    }
}
