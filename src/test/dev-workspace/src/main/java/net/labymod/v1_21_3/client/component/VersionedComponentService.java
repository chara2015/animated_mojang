package net.labymod.v1_21_3.client.component;

import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.v1_21_3.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_3.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_3.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_3.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    @Inject
    public VersionedComponentService() {
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return (TextComponent) xv.b(text).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<Component> children) {
        a contents = new a(text);
        return (TextComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return (ScoreComponent) xv.b(name, objective).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<Component> children) {
        zd contents = new zd(Either.right(name), objective);
        return (ScoreComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return (KeybindComponent) xv.d(keybind).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<Component> children) {
        yz contents = new yz(keybind);
        return (KeybindComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) yj.a(new VersionedIconContents(icon)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<Component> children) {
        VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createEmptyComponent() {
        return (TextComponent) xv.i().getLabyComponent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.component.ComponentService
    public <T> HoverEvent<T> createHoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T t) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(t, "Value cannot be null!");
        if (action == HoverEvent.Action.SHOW_TEXT) {
            if (t instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> component = (VersionedBaseComponent) t;
                return new yb<>(a.a, component.holder);
            }
            throw new IllegalStateException("Value must be a Component!");
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ClickEvent createClickEvent(ClickEvent.Action action, @NotNull String value) {
        a aVar;
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        switch (action) {
            case OPEN_URL:
                aVar = a.a;
                break;
            case OPEN_FILE:
                aVar = a.b;
                break;
            case RUN_COMMAND:
                aVar = a.c;
                break;
            case SUGGEST_COMMAND:
                aVar = a.d;
                break;
            case CHANGE_PAGE:
                aVar = a.e;
                break;
            case COPY_TO_CLIPBOARD:
                aVar = a.f;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(action));
        }
        a vanillaAction = aVar;
        return new xt(vanillaAction, value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor createTextColor(int value) {
        return yu.a(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        DataResult<yu> result = yu.a(value);
        return (TextColor) result.result().get();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Component... arguments) {
        return (TranslatableComponent) xv.a(key, arguments).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        zg contents;
        if (arguments == null) {
            contents = new zg(key, key, new Object[0]);
        } else {
            contents = new zg(key, key, arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        ys style = ys.a;
        if (style == null) {
            style = new ys((yu) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (xt) null, (yb) null, (String) null, (alz) null);
        }
        return (Style) style;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Class<?> getActualVersionedClass(Component component, Class<?> cls) {
        if (cls == VersionedTextComponent.class) {
            return TextComponent.class;
        }
        if (cls == VersionedTranslatableComponent.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.ComponentService
    public Style createStyleFromBuilder(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent<?> hoverEvent, String insertion, GlyphSourceDescription glyphSourceDescription) throws MatchException {
        alz alzVar;
        yu yuVar = (yu) color;
        xt xtVar = (xt) clickEvent;
        yb ybVar = (yb) hoverEvent;
        if (glyphSourceDescription instanceof GlyphSourceDescription.Resource) {
            try {
                ResourceLocation id = ((GlyphSourceDescription.Resource) glyphSourceDescription).id();
                alzVar = (alz) id.getMinecraftLocation();
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        } else {
            alzVar = null;
        }
        return new ys(yuVar, bold, italic, underlined, strikethrough, obfuscated, xtVar, ybVar, insertion, alzVar);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Serializer createComponentSerializer() {
        return new VersionedComponentSerializerWrapper();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Serializer createStyleSerializer() {
        return new b();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public void applyGsonTypeAdapters(GsonBuilder builder) {
        builder.registerTypeAdapterFactory(new bab());
    }

    private Component applyStyleAndSiblings(xw componentContents, Style style, List<Component> children) {
        ys vanillaStyle;
        if (style == null) {
            vanillaStyle = ys.a;
        } else {
            vanillaStyle = (ys) style;
        }
        Component result = new yj(componentContents, new ArrayList(), vanillaStyle).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
