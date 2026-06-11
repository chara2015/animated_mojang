package net.labymod.v1_20_1.mixins.client.chat;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/chat/MixinStyle.class */
@Mixin({ts.class})
@Implements({@Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE)})
public abstract class MixinStyle implements Style {

    @Shadow
    @Final
    public static acq c;

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

    @Shadow
    @Final
    @Nullable
    private Boolean i;
    private GlyphSourceDescription labyMod$glyphSourceDescription;

    @Shadow
    @Final
    @javax.annotation.Nullable
    private acq m;

    @Shadow
    @Nullable
    public abstract tb shadow$i();

    @Shadow
    public abstract ts a(@Nullable tb tbVar);

    @Shadow
    @Nullable
    public abstract su shadow$h();

    @Shadow
    public abstract ts a(@Nullable su suVar);

    @Shadow
    public abstract ts a(@Nullable tu tuVar);

    @Shadow
    @Nullable
    public abstract tu shadow$a();

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
    public abstract ts a(@Nullable Boolean bool);

    @Shadow
    public abstract ts b(@Nullable Boolean bool);

    @Shadow
    public abstract ts c(@Nullable Boolean bool);

    @Shadow
    public abstract ts d(@Nullable Boolean bool);

    @Shadow
    public abstract ts e(@Nullable Boolean bool);

    @Shadow
    public abstract acq shadow$k();

    @Shadow
    public abstract ts a(@Nullable acq acqVar);

    @Shadow
    public abstract ts a(@Nullable String str);

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$updateFont(CallbackInfo ci) {
        GlyphSourceDescription glyphSourceDescriptionResource;
        acq location = this.m;
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
        return a((tb) hoverEvent);
    }

    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return shadow$h();
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style clickEvent(ClickEvent clickEvent) {
        return a((su) clickEvent);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style insertion(String insertion) {
        return a(insertion);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style color(TextColor color) {
        return a((tu) color);
    }

    @Override // net.labymod.api.client.component.format.Style
    public GlyphSourceDescription getGlyphSourceDescription() {
        return this.labyMod$glyphSourceDescription;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style glyphSourceDescription(GlyphSourceDescription description) {
        if (description instanceof GlyphSourceDescription.Resource) {
            GlyphSourceDescription.Resource resource = (GlyphSourceDescription.Resource) description;
            return (Style) CastUtil.cast(a((acq) resource.id().getMinecraftLocation()));
        }
        return this;
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
                return this.e != null;
            case ITALIC:
                return this.f != null;
            case UNDERLINED:
                return this.g != null;
            case STRIKETHROUGH:
                return this.h != null;
            case OBFUSCATED:
                return this.i != null;
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
        return this == ts.a;
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

    private Style set(Boolean value, Supplier<Boolean> getter, Function<Boolean, ts> setter) {
        Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return setter.apply(value);
    }
}
