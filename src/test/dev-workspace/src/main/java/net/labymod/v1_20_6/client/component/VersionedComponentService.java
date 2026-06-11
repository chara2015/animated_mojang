package net.labymod.v1_20_6.client.component;

import com.google.gson.GsonBuilder;
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
import net.labymod.v1_20_6.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_20_6.client.network.chat.VersionedTextComponent;
import net.labymod.v1_20_6.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_20_6.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    @Inject
    public VersionedComponentService() {
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return (TextComponent) xp.b(text).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<Component> children) {
        a contents = new a(text);
        return (TextComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return (ScoreComponent) xp.b(name, objective).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<Component> children) {
        yx contents = new yx(name, objective);
        return (ScoreComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return (KeybindComponent) xp.d(keybind).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<Component> children) {
        yt contents = new yt(keybind);
        return (KeybindComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) yd.a(new VersionedIconContents(icon)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<Component> children) {
        VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createEmptyComponent() {
        return (TextComponent) xp.i().getLabyComponent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.component.ComponentService
    public <T> HoverEvent<T> createHoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T t) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(t, "Value cannot be null!");
        if (action == HoverEvent.Action.SHOW_TEXT) {
            if (t instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> component = (VersionedBaseComponent) t;
                return new xv<>(a.a, component.holder);
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
        return new xn(vanillaAction, value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor createTextColor(int value) {
        return yo.a(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        DataResult<yo> result = yo.a(value);
        return (TextColor) result.result().get();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Component... arguments) {
        return (TranslatableComponent) xp.a(key, arguments).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        za contents;
        if (arguments == null) {
            contents = new za(key, key, new Object[0]);
        } else {
            contents = new za(key, key, arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        ym style = ym.a;
        if (style == null) {
            style = new ym((yo) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (xn) null, (xv) null, (String) null, (alf) null);
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
        alf alfVar;
        yo yoVar = (yo) color;
        xn xnVar = (xn) clickEvent;
        xv xvVar = (xv) hoverEvent;
        if (glyphSourceDescription instanceof GlyphSourceDescription.Resource) {
            try {
                ResourceLocation id = ((GlyphSourceDescription.Resource) glyphSourceDescription).id();
                alfVar = (alf) id.getMinecraftLocation();
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        } else {
            alfVar = null;
        }
        return new ym(yoVar, bold, italic, underlined, strikethrough, obfuscated, xnVar, xvVar, insertion, alfVar);
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
        builder.registerTypeAdapterFactory(new ayw());
    }

    private Component applyStyleAndSiblings(xq componentContents, Style style, List<Component> children) {
        ym vanillaStyle;
        if (style == null) {
            vanillaStyle = ym.a;
        } else {
            vanillaStyle = (ym) style;
        }
        Component result = new yd(componentContents, new ArrayList(), vanillaStyle).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
