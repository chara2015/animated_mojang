package net.labymod.v1_18_2.client.component;

import com.google.gson.GsonBuilder;
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
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    @Inject
    public VersionedComponentService() {
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return new qx(text);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<Component> children) {
        qx component = new qx(text);
        return (TextComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return new qs(name, objective);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<Component> children) {
        qs component = new qs(name, objective);
        return (ScoreComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return new qp(keybind);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<Component> children) {
        qp component = new qp(keybind);
        return (KeybindComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) new VersionedIconComponent(icon);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<Component> children) {
        VersionedIconComponent iconComponent = new VersionedIconComponent(icon);
        return (IconComponent) applyStyleAndSiblings(iconComponent, style, children);
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
            return new qo<>(a.a, (qk) value);
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ClickEvent createClickEvent(@NotNull ClickEvent.Action action, @NotNull String value) {
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
        return new qi(vanillaAction, value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor createTextColor(int value) {
        return qw.a(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        return qw.a(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        qu style = qu.a;
        if (style == null) {
            style = new qu((qw) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (qi) null, (qo) null, (String) null, (yt) null);
        }
        return (Style) style;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Component... arguments) {
        return new qy(key, arguments);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        qy component;
        if (arguments == null) {
            component = new qy(key);
        } else {
            component = new qy(key, arguments.toArray(new Object[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(component, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Class<?> getActualVersionedClass(Component component, Class<?> cls) {
        if (cls == qx.class) {
            return TextComponent.class;
        }
        if (cls == qy.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.ComponentService
    public Style createStyleFromBuilder(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent<?> hoverEvent, String insertion, GlyphSourceDescription glyphSourceDescription) throws MatchException {
        yt ytVar;
        qw qwVar = (qw) color;
        qi qiVar = (qi) clickEvent;
        qo qoVar = (qo) hoverEvent;
        if (glyphSourceDescription instanceof GlyphSourceDescription.Resource) {
            try {
                ResourceLocation id = ((GlyphSourceDescription.Resource) glyphSourceDescription).id();
                ytVar = (yt) id.getMinecraftLocation();
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        } else {
            ytVar = null;
        }
        return new qu(qwVar, bold, italic, underlined, strikethrough, obfuscated, qiVar, qoVar, insertion, ytVar);
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
        builder.registerTypeAdapterFactory(new aji());
    }

    private Component applyStyleAndSiblings(qg component, Style style, List<Component> children) {
        if (style != null) {
            component.a((qu) style);
        }
        Component result = (Component) component;
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
