package net.labymod.v1_21_10.mixins.client.chat;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/MixinStyle.class */
@Mixin({yv.class})
@Implements({@Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE)})
public abstract class MixinStyle implements Style {

    @Shadow
    @Final
    @Nullable
    private Boolean d;

    @Shadow
    @Final
    @Nullable
    private Boolean e;

    @Shadow
    @Final
    @Nullable
    private Boolean f;

    @Shadow
    @Final
    @Nullable
    private Boolean g;

    @Shadow
    @Final
    @Nullable
    private Boolean h;
    private GlyphSourceDescription labyMod$glyphSourceDescription;

    @Shadow
    @Final
    private yc l;

    @Shadow
    @Nullable
    public abstract ye shadow$j();

    @Shadow
    public abstract yv a(@Nullable ye yeVar);

    @Shadow
    @Nullable
    public abstract xv shadow$i();

    @Shadow
    public abstract yv a(@org.jetbrains.annotations.Nullable xv xvVar);

    @Shadow
    public abstract yv a(@org.jetbrains.annotations.Nullable yx yxVar);

    @Shadow
    @Nullable
    public abstract yx shadow$a();

    @Shadow
    @Nullable
    public abstract Integer shadow$b();

    @Shadow
    public abstract String shadow$k();

    @Shadow
    public abstract boolean c();

    @Shadow
    public abstract boolean d();

    @Shadow
    public abstract boolean f();

    @Shadow
    public abstract boolean e();

    @Shadow
    public abstract boolean g();

    @Shadow
    public abstract yv a(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract yv b(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract yv c(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract yv d(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract yv e(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract yv a(@org.jetbrains.annotations.Nullable String str);

    @Shadow
    public abstract yc shadow$l();

    @Shadow
    public abstract yv a(yc ycVar);

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$updateFont(CallbackInfo ci) {
        GlyphSourceDescription glyphSourceDescriptionFromMinecraft;
        if (this.l == null) {
            glyphSourceDescriptionFromMinecraft = null;
        } else {
            glyphSourceDescriptionFromMinecraft = MinecraftUtil.fromMinecraft(this.l);
        }
        this.labyMod$glyphSourceDescription = glyphSourceDescriptionFromMinecraft;
    }

    @Intrinsic
    public HoverEvent<?> style$getHoverEvent() {
        return shadow$j();
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style hoverEvent(HoverEvent<?> hoverEvent) {
        return (Style) CastUtil.cast(a((ye) CastUtil.cast(hoverEvent)));
    }

    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return shadow$i();
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style clickEvent(ClickEvent clickEvent) {
        return (Style) CastUtil.cast(a((xv) CastUtil.cast(clickEvent)));
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style insertion(String insertion) {
        return (Style) CastUtil.cast(a(insertion));
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style color(TextColor color) {
        return (Style) CastUtil.cast(a((yx) CastUtil.cast(color)));
    }

    @Override // net.labymod.api.client.component.format.Style
    public GlyphSourceDescription getGlyphSourceDescription() {
        return this.labyMod$glyphSourceDescription;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style glyphSourceDescription(GlyphSourceDescription description) {
        return (Style) CastUtil.cast(a(MinecraftUtil.toMinecraft(description)));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public boolean hasDecoration(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return c();
            case ITALIC:
                return d();
            case UNDERLINED:
                return f();
            case STRIKETHROUGH:
                return e();
            case OBFUSCATED:
                return g();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public boolean isDecorationSet(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return this.d != null;
            case ITALIC:
                return this.e != null;
            case UNDERLINED:
                return this.f != null;
            case STRIKETHROUGH:
                return this.g != null;
            case OBFUSCATED:
                return this.h != null;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public Style decorate(TextDecoration decoration) throws MatchException {
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
    public Style undecorate(TextDecoration decoration) throws MatchException {
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
    public Style unsetDecoration(TextDecoration decoration) throws MatchException {
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
        return this == yv.a;
    }

    @Intrinsic
    public TextColor style$getColor() {
        return (TextColor) CastUtil.cast(shadow$a());
    }

    @Intrinsic
    public Integer style$getShadowColor() {
        return shadow$b();
    }

    @Intrinsic
    public String style$getInsertion() {
        return shadow$k();
    }

    private Style setBold(Boolean value) {
        return set(value, this::c, this::a);
    }

    private Style setItalic(Boolean value) {
        return set(value, this::d, this::b);
    }

    private Style setUnderlined(Boolean value) {
        return set(value, this::f, this::c);
    }

    private Style setStrikethrough(Boolean value) {
        return set(value, this::e, this::d);
    }

    private Style setObfuscated(Boolean value) {
        return set(value, this::g, this::e);
    }

    private Style set(Boolean value, Supplier<Boolean> getter, Function<Boolean, yv> setter) {
        Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return (Style) CastUtil.cast(setter.apply(value));
    }
}
