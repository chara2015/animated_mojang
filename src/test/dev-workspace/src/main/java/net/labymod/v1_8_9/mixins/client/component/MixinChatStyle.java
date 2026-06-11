package net.labymod.v1_8_9.mixins.client.component;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.v1_8_9.client.component.VersionedNamedTextColors;
import net.labymod.v1_8_9.client.component.VersionedStyle;
import net.labymod.v1_8_9.client.component.VersionedTextColor;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinChatStyle.class */
@Mixin({ez.class})
@Implements({@Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE)})
public abstract class MixinChatStyle implements VersionedStyle {

    @Shadow
    private a b;

    @Shadow
    private String j;

    @Shadow
    private et h;

    @Shadow
    private ew i;

    @Shadow
    private Boolean c;

    @Shadow
    private Boolean d;

    @Shadow
    private Boolean e;

    @Shadow
    private Boolean f;

    @Shadow
    private Boolean g;

    @Shadow
    private ez a;
    private VersionedTextColor labyMod$color;

    @Shadow
    public abstract ez m();

    @Shadow
    public abstract boolean shadow$g();

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
    protected abstract ez o();

    @Override // net.labymod.api.client.component.format.Style
    public HoverEvent<?> getHoverEvent() {
        return this.i;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style hoverEvent(HoverEvent<?> hoverEvent) {
        VersionedStyle versionedStyleM = m();
        versionedStyleM.setLabyColor(this.labyMod$color);
        versionedStyleM.a((ew) hoverEvent);
        return versionedStyleM;
    }

    @Override // net.labymod.api.client.component.format.Style
    public ClickEvent getClickEvent() {
        return this.h;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style clickEvent(ClickEvent clickEvent) {
        VersionedStyle versionedStyleM = m();
        versionedStyleM.setLabyColor(this.labyMod$color);
        versionedStyleM.a((et) clickEvent);
        return versionedStyleM;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style insertion(String insertion) {
        Style styleM = m();
        styleM.a(insertion);
        return styleM;
    }

    @Override // net.labymod.api.client.component.format.Style
    public TextColor getColor() {
        if (this.labyMod$color != null) {
            return this.labyMod$color;
        }
        if (this.a == null) {
            return null;
        }
        return this.a.getColor();
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style color(TextColor color) {
        VersionedStyle shallowCopy = m();
        shallowCopy.setLabyColor((VersionedTextColor) color);
        return shallowCopy;
    }

    @Override // net.labymod.api.client.component.format.Style
    public GlyphSourceDescription getGlyphSourceDescription() {
        return null;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style glyphSourceDescription(GlyphSourceDescription description) {
        return this;
    }

    @Override // net.labymod.api.client.component.format.Style
    public boolean hasDecoration(TextDecoration decoration) {
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
                return false;
        }
    }

    @Override // net.labymod.api.client.component.format.Style
    public boolean isDecorationSet(TextDecoration decoration) {
        switch (decoration) {
            case BOLD:
                if (this.c != null) {
                }
                break;
            case ITALIC:
                if (this.d != null) {
                }
                break;
            case UNDERLINED:
                if (this.e != null) {
                }
                break;
            case STRIKETHROUGH:
                if (this.f != null) {
                }
                break;
            case OBFUSCATED:
                if (this.g != null) {
                }
                break;
        }
        return false;
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style decorate(TextDecoration decoration) {
        return updateDecoration(decoration, true);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style undecorate(TextDecoration decoration) {
        return updateDecoration(decoration, false);
    }

    @Override // net.labymod.api.client.component.format.Style
    public Style unsetDecoration(TextDecoration decoration) {
        return updateDecoration(decoration, null);
    }

    @Override // net.labymod.api.client.component.format.Style
    public String getInsertion() {
        return this.j;
    }

    @Override // net.labymod.v1_8_9.client.component.VersionedStyle
    public Style setLabyColor(VersionedTextColor color) {
        this.labyMod$color = color;
        if (color == null) {
            this.b = null;
        } else {
            this.b = color.getFormatting();
        }
        return this;
    }

    @Intrinsic
    public boolean style$isEmpty() {
        return shadow$g();
    }

    @Inject(method = {"setColor"}, at = {@At("HEAD")})
    private void setColor(a formatting, CallbackInfoReturnable<ez> cir) {
        this.labyMod$color = VersionedNamedTextColors.byFormatting(formatting);
    }

    private Style updateDecoration(TextDecoration decoration, Boolean value) {
        switch (decoration) {
            case BOLD:
                return set(value, this::b, style -> {
                    style.a(value);
                });
            case ITALIC:
                return set(value, this::c, style2 -> {
                    style2.b(value);
                });
            case UNDERLINED:
                return set(value, this::e, style3 -> {
                    style3.d(value);
                });
            case STRIKETHROUGH:
                return set(value, this::d, style4 -> {
                    style4.c(value);
                });
            case OBFUSCATED:
                return set(value, this::f, style5 -> {
                    style5.e(value);
                });
            default:
                return this;
        }
    }

    private Style set(Boolean value, Supplier<Boolean> getter, Consumer<ez> setter) {
        if (value != null) {
            Boolean currentState = getter.get();
            if (Objects.equals(currentState, value)) {
                return this;
            }
        }
        VersionedStyle versionedStyleM = m();
        versionedStyleM.setLabyColor(this.labyMod$color);
        setter.accept(versionedStyleM);
        return versionedStyleM;
    }

    @Overwrite
    public int hashCode() {
        return Objects.hash(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
    }

    @Inject(method = {"createDeepCopy"}, at = {@At("RETURN")})
    private void labyMod$createDeepCopy(CallbackInfoReturnable<ez> cir) {
        ((VersionedStyle) cir.getReturnValue()).setLabyColor(this.labyMod$color);
    }

    @Inject(method = {"createShallowCopy"}, at = {@At("RETURN")})
    private void labyMod$createShallowCopy(CallbackInfoReturnable<ez> cir) {
        ((VersionedStyle) cir.getReturnValue()).setLabyColor(this.labyMod$color);
    }
}
