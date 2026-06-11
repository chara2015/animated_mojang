package net.labymod.v1_8_9.client.component;

import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Objects;
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
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.Color;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    public VersionedComponentService(EventBus eventBus) {
        eventBus.registerListener(new VersionedKeybindComponentSerializer());
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return new fa(text);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<Component> children) {
        fa component = new fa(text);
        return (TextComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return new ex(name, objective);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<Component> children) {
        ex component = new ex(name, objective);
        return (ScoreComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return (KeybindComponent) new VersionedKeybindComponent(keybind);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<Component> children) {
        VersionedKeybindComponent component = new VersionedKeybindComponent(keybind);
        return (KeybindComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) new VersionedIconComponent(icon);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<Component> children) {
        VersionedIconComponent component = new VersionedIconComponent(icon);
        return (IconComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createEmptyComponent() {
        return createTextComponent("");
    }

    @Override // net.labymod.api.client.component.ComponentService
    public <T> HoverEvent<T> createHoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T value) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return new ew<>(a.a, (eu) value);
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ClickEvent createClickEvent(ClickEvent.Action action, @NotNull String value) {
        a vanillaAction;
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        switch (action) {
            case OPEN_URL:
                vanillaAction = a.a;
                break;
            case OPEN_FILE:
                vanillaAction = a.b;
                break;
            case RUN_COMMAND:
                vanillaAction = a.c;
                break;
            case CHANGE_PAGE:
                vanillaAction = a.f;
                break;
            default:
                vanillaAction = a.e;
                break;
        }
        ClickEvent clickEvent = new et(vanillaAction, value);
        ((VersionedClickEvent) clickEvent).labyMod$setAction(action);
        return clickEvent;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor createTextColor(int value) {
        VersionedTextColor versionedTextColor = VersionedNamedTextColors.byValue(value);
        if (versionedTextColor != null) {
            return versionedTextColor;
        }
        return new VersionedTextColor(Color.of(value));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        if (value.startsWith("#")) {
            VersionedTextColor versionedTextColor = VersionedNamedTextColors.byHex(value);
            if (versionedTextColor != null) {
                return versionedTextColor;
            }
            return new VersionedTextColor(value);
        }
        return VersionedNamedTextColors.byName(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Component... arguments) {
        return new fb(key, arguments);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        fb component;
        if (arguments == null) {
            component = new fb(key, new Object[0]);
        } else {
            component = new fb(key, arguments.toArray(new Object[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        return (Style) CastUtil.cast(new ez());
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Class<?> getActualVersionedClass(Component component, Class<?> cls) {
        if (cls == fa.class) {
            return TextComponent.class;
        }
        if (cls == fb.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style createStyleFromBuilder(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent<?> hoverEvent, String insertion, GlyphSourceDescription glyphSourceDescription) {
        VersionedStyle ezVar = new ez();
        ezVar.a(bold);
        ezVar.b(italic);
        ezVar.d(underlined);
        ezVar.c(strikethrough);
        ezVar.e(obfuscated);
        ezVar.a((ew) hoverEvent);
        ezVar.a((et) clickEvent);
        ezVar.a(insertion);
        return ezVar.setLabyColor((VersionedTextColor) color);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Serializer createComponentSerializer() {
        return new a();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Serializer createStyleSerializer() {
        return new a();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public void applyGsonTypeAdapters(GsonBuilder builder) {
        builder.registerTypeAdapterFactory(new nr());
    }

    private Component applyStyleAndSiblings(es component, Style style, List<Component> children) {
        if (style != null) {
            component.a((ez) style);
        }
        Component result = (Component) component;
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
