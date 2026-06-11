package net.labymod.v1_21_1.mixins.client.chat;

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
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/chat/MixinStyle.class */
@Mixin({xw.class})
@Implements({@Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE)})
public abstract class MixinStyle implements Style {

    @Shadow
    @Final
    public static akr b;

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
    @Nullable
    private akr l;

    @Shadow
    @Nullable
    public abstract xf shadow$i();

    @Shadow
    public abstract xw a(@Nullable xf xfVar);

    @Shadow
    @Nullable
    public abstract wx shadow$h();

    @Shadow
    public abstract xw a(@org.jetbrains.annotations.Nullable wx wxVar);

    @Shadow
    public abstract xw a(@org.jetbrains.annotations.Nullable xy xyVar);

    @Shadow
    @Nullable
    public abstract xy shadow$a();

    @Shadow
    public abstract String shadow$j();

    @Shadow
    public abstract boolean b();

    @Shadow
    public abstract boolean c();

    @Shadow
    public abstract boolean e();

    @Shadow
    public abstract boolean d();

    @Shadow
    public abstract boolean f();

    @Shadow
    public abstract xw a(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract xw b(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract xw c(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract xw d(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract xw e(@org.jetbrains.annotations.Nullable Boolean bool);

    @Shadow
    public abstract xw a(@org.jetbrains.annotations.Nullable akr akrVar);

    @Shadow
    public abstract xw a(@org.jetbrains.annotations.Nullable String str);

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$updateFont(CallbackInfo ci) {
        GlyphSourceDescription glyphSourceDescriptionResource;
        akr location = this.l;
        if (location == null) {
            glyphSourceDescriptionResource = null;
        } else {
            glyphSourceDescriptionResource = GlyphSourceDescription.resource((ResourceLocation) CastUtil.cast(location));
        }
        this.labyMod$glyphSourceDescription = glyphSourceDescriptionResource;
    }

    @Intrinsic
    public HoverEvent<?> style$getHoverEvent() {
        return shadow$i();
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style hoverEvent(HoverEvent<?> hoverEvent) {
        return a((xf) hoverEvent);
    }

    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return shadow$h();
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style clickEvent(ClickEvent clickEvent) {
        return a((wx) clickEvent);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style insertion(String insertion) {
        return a(insertion);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style color(TextColor color) {
        return a((xy) color);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style glyphSourceDescription(GlyphSourceDescription description) {
        if (description instanceof GlyphSourceDescription.Resource) {
            GlyphSourceDescription.Resource resource = (GlyphSourceDescription.Resource) description;
            return (Style) CastUtil.cast(a((akr) resource.id().getMinecraftLocation()));
        }
        return this;
    }

    @Override // net.labymod.api.client.component.format.Style
    public GlyphSourceDescription getGlyphSourceDescription() {
        return this.labyMod$glyphSourceDescription;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.format.Style
    public boolean hasDecoration(TextDecoration decoration) throws MatchException {
        switch (decoration) {
            case BOLD:
                return b();
            case ITALIC:
                return c();
            case UNDERLINED:
                return e();
            case STRIKETHROUGH:
                return d();
            case OBFUSCATED:
                return f();
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
        return this == xw.a;
    }

    @Intrinsic
    public TextColor style$getColor() {
        return (TextColor) CastUtil.cast(shadow$a());
    }

    @Intrinsic
    public String style$getInsertion() {
        return shadow$j();
    }

    private Style setBold(Boolean value) {
        return set(value, this::b, this::a);
    }

    private Style setItalic(Boolean value) {
        return set(value, this::c, this::b);
    }

    private Style setUnderlined(Boolean value) {
        return set(value, this::e, this::c);
    }

    private Style setStrikethrough(Boolean value) {
        return set(value, this::d, this::d);
    }

    private Style setObfuscated(Boolean value) {
        return set(value, this::f, this::e);
    }

    private Style set(Boolean value, Supplier<Boolean> getter, Function<Boolean, xw> setter) {
        Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return setter.apply(value);
    }
}
