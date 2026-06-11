package net.labymod.v1_21_11.mixins.client.chat;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/chat/MixinStyle.class */
@Mixin({Style.class})
@Implements({@Interface(iface = net.labymod.api.client.component.format.Style.class, prefix = "style$", remap = Interface.Remap.NONE)})
public abstract class MixinStyle implements net.labymod.api.client.component.format.Style {

    @Shadow
    @Final
    @Nullable
    private Boolean withObfuscated;

    @Shadow
    @Final
    @Nullable
    private Boolean isUnderlined;

    @Shadow
    @Final
    @Nullable
    private Boolean isObfuscated;

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
    public abstract Style withFont(@Nullable HoverEvent hoverEvent);

    @Shadow
    @Nullable
    public abstract ClickEvent shadow$getClickEvent();

    @Shadow
    public abstract Style withFont(@org.jetbrains.annotations.Nullable ClickEvent clickEvent);

    @Shadow
    public abstract Style withFont(@org.jetbrains.annotations.Nullable TextColor textColor);

    @Shadow
    @Nullable
    public abstract TextColor shadow$getColor();

    @Shadow
    @Nullable
    public abstract Integer shadow$getShadowColor();

    @Shadow
    public abstract String shadow$getInsertion();

    @Shadow
    public abstract boolean withUnderlined();

    @Shadow
    public abstract boolean withStrikethrough();

    @Shadow
    public abstract boolean isUnderlined();

    @Shadow
    public abstract boolean withObfuscated();

    @Shadow
    public abstract boolean isObfuscated();

    @Shadow
    public abstract Style withFont(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withItalic(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withUnderlined(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withStrikethrough(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withObfuscated(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract Style withFont(@org.jetbrains.annotations.Nullable String str);

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

    public net.labymod.api.client.component.format.Style hoverEvent(net.labymod.api.client.component.event.HoverEvent<?> hoverEvent) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withFont((HoverEvent) CastUtil.cast(hoverEvent)));
    }

    @Intrinsic
    public net.labymod.api.client.component.event.ClickEvent style$getClickEvent() {
        return shadow$getClickEvent();
    }

    public net.labymod.api.client.component.format.Style clickEvent(net.labymod.api.client.component.event.ClickEvent clickEvent) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withFont((ClickEvent) CastUtil.cast(clickEvent)));
    }

    public net.labymod.api.client.component.format.Style insertion(String insertion) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withFont(insertion));
    }

    public net.labymod.api.client.component.format.Style color(net.labymod.api.client.component.format.TextColor color) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withFont((TextColor) CastUtil.cast(color)));
    }

    public GlyphSourceDescription getGlyphSourceDescription() {
        return this.labyMod$glyphSourceDescription;
    }

    public net.labymod.api.client.component.format.Style glyphSourceDescription(GlyphSourceDescription description) {
        return (net.labymod.api.client.component.format.Style) CastUtil.cast(withFont(MinecraftUtil.toMinecraft(description)));
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.chat.MixinStyle$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/chat/MixinStyle$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$component$format$TextDecoration = new int[TextDecoration.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$client$component$format$TextDecoration[TextDecoration.BOLD.ordinal()] = 1;
            } catch (NoSuchFieldError withObfuscated) {
            }
            try {
                $SwitchMap$net$labymod$api$client$component$format$TextDecoration[TextDecoration.ITALIC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$component$format$TextDecoration[TextDecoration.UNDERLINED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$component$format$TextDecoration[TextDecoration.STRIKETHROUGH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$api$client$component$format$TextDecoration[TextDecoration.OBFUSCATED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public boolean hasDecoration(TextDecoration decoration) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$component$format$TextDecoration[decoration.ordinal()]) {
            case 1:
                return withUnderlined();
            case 2:
                return withStrikethrough();
            case 3:
                return isUnderlined();
            case 4:
                return withObfuscated();
            case 5:
                return isObfuscated();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public boolean isDecorationSet(TextDecoration decoration) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$component$format$TextDecoration[decoration.ordinal()]) {
            case 1:
                return this.withObfuscated != null;
            case 2:
                return this.isUnderlined != null;
            case 3:
                return this.isObfuscated != null;
            case 4:
                return this.strikethrough != null;
            case 5:
                return this.obfuscated != null;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public net.labymod.api.client.component.format.Style decorate(TextDecoration decoration) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$component$format$TextDecoration[decoration.ordinal()]) {
            case 1:
                return setBold(true);
            case 2:
                return setItalic(true);
            case 3:
                return setUnderlined(true);
            case 4:
                return setStrikethrough(true);
            case 5:
                return setObfuscated(true);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public net.labymod.api.client.component.format.Style undecorate(TextDecoration decoration) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$component$format$TextDecoration[decoration.ordinal()]) {
            case 1:
                return setBold(false);
            case 2:
                return setItalic(false);
            case 3:
                return setUnderlined(false);
            case 4:
                return setStrikethrough(false);
            case 5:
                return setObfuscated(false);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public net.labymod.api.client.component.format.Style unsetDecoration(TextDecoration decoration) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$component$format$TextDecoration[decoration.ordinal()]) {
            case 1:
                return setBold(null);
            case 2:
                return setItalic(null);
            case 3:
                return setUnderlined(null);
            case 4:
                return setStrikethrough(null);
            case 5:
                return setObfuscated(null);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

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
        return set(value, this::withUnderlined, this::withFont);
    }

    private net.labymod.api.client.component.format.Style setItalic(Boolean value) {
        return set(value, this::withStrikethrough, this::withItalic);
    }

    private net.labymod.api.client.component.format.Style setUnderlined(Boolean value) {
        return set(value, this::isUnderlined, this::withUnderlined);
    }

    private net.labymod.api.client.component.format.Style setStrikethrough(Boolean value) {
        return set(value, this::withObfuscated, this::withStrikethrough);
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

