package net.labymod.v1_21_8.client.component;

import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import java.net.URI;
import java.nio.file.Paths;
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
import net.labymod.api.util.gson.LowerCaseEnumTypeAdapterFactory;
import net.labymod.v1_21_8.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_8.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_8.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_8.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    @Inject
    public VersionedComponentService() {
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return (TextComponent) xo.b(text).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<Component> children) {
        a contents = new a(text);
        return (TextComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return (ScoreComponent) xo.b(name, objective).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<Component> children) {
        yw contents = new yw(Either.right(name), objective);
        return (ScoreComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return (KeybindComponent) xo.d(keybind).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<Component> children) {
        ys contents = new ys(keybind);
        return (KeybindComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) yc.a(new VersionedIconContents(icon)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<Component> children) {
        VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createEmptyComponent() {
        return (TextComponent) xo.i().getLabyComponent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.component.ComponentService
    public <T> HoverEvent<T> createHoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T t) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(t, "Value cannot be null!");
        if (action == HoverEvent.Action.SHOW_TEXT) {
            if (t instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> component = (VersionedBaseComponent) t;
                return new e<>(component.holder);
            }
            throw new IllegalStateException("Value must be a Component!");
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ClickEvent createClickEvent(ClickEvent.Action action, @NotNull String value) {
        f cVar;
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        switch (action) {
            case OPEN_URL:
                cVar = new f(URI.create(value));
                break;
            case OPEN_FILE:
                cVar = new e(Paths.get(value, new String[0]));
                break;
            case RUN_COMMAND:
                cVar = new g(value);
                break;
            case SUGGEST_COMMAND:
                cVar = new i(value);
                break;
            case CHANGE_PAGE:
                cVar = new b(Integer.parseInt(value));
                break;
            case COPY_TO_CLIPBOARD:
                cVar = new c(value);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
        }
        return (ClickEvent) cVar;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor createTextColor(int value) {
        return yn.a(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        DataResult<yn> result = yn.a(value);
        return (TextColor) result.result().get();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Component... arguments) {
        return (TranslatableComponent) xo.a(key, arguments).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        yz contents;
        if (arguments == null) {
            contents = new yz(key, key, new Object[0]);
        } else {
            contents = new yz(key, key, arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        yl style = yl.a;
        if (style == null) {
            style = new yl((yn) null, (Integer) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (xm) null, (xu) null, (String) null, (ame) null);
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
        ame ameVar;
        yn ynVar = (yn) color;
        xm xmVar = (xm) clickEvent;
        xu xuVar = (xu) hoverEvent;
        if (glyphSourceDescription instanceof GlyphSourceDescription.Resource) {
            try {
                ResourceLocation id = ((GlyphSourceDescription.Resource) glyphSourceDescription).id();
                ameVar = (ame) id.getMinecraftLocation();
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        } else {
            ameVar = null;
        }
        return new yl(ynVar, shadowColor, bold, italic, underlined, strikethrough, obfuscated, xmVar, xuVar, insertion, ameVar);
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
        builder.registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory());
    }

    private Component applyStyleAndSiblings(xp componentContents, Style style, List<Component> children) {
        yl vanillaStyle;
        if (style == null) {
            vanillaStyle = yl.a;
        } else {
            vanillaStyle = (yl) style;
        }
        Component result = new yc(componentContents, new ArrayList(), vanillaStyle).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
