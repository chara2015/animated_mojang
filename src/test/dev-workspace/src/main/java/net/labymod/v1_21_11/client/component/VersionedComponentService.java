package net.labymod.v1_21_11.client.component;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.ObjectComponent;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.object.ObjectSprite;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.gson.LowerCaseEnumTypeAdapterFactory;
import net.labymod.v1_21_11.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_11.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_11.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_11.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_11.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    @Inject
    public VersionedComponentService() {
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return (TextComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(yh.b(text), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<Component> children) {
        a contents = new a(text);
        return (TextComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return (ScoreComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(yh.b(name, objective), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<Component> children) {
        zo contents = new zo(Either.right(name), objective);
        return (ScoreComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return (KeybindComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(yh.d(keybind), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<Component> children) {
        zj contents = new zj(keybind);
        return (KeybindComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(yw.a(new VersionedIconContents(icon)), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<Component> children) {
        VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent) applyStyleAndSiblings(contents, style, children);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.ComponentService
    protected ObjectComponent createObjectComponent(ObjectSprite objectSprite, Style style, List<Component> children) throws MatchException {
        zy aabVar;
        try {
            if (objectSprite instanceof ObjectSprite.AtlasObjectSprite) {
                ObjectSprite.AtlasObjectSprite atlasObjectSprite = (ObjectSprite.AtlasObjectSprite) objectSprite;
                ResourceLocation atlas = atlasObjectSprite.atlas();
                ResourceLocation sprite = atlasObjectSprite.sprite();
                aabVar = new zy((amo) atlas.getMinecraftLocation(), (amo) sprite.getMinecraftLocation());
            } else {
                if (objectSprite instanceof ObjectSprite.PlayerObjectSprite) {
                    ObjectSprite.PlayerObjectSprite playerObjectSprite = (ObjectSprite.PlayerObjectSprite) objectSprite;
                    GlyphSourceDescription.PlayerSprite.Profile profile = playerObjectSprite.profile();
                    boolean hat = playerObjectSprite.hat();
                    if (1 != 0) {
                        if (profile instanceof GlyphSourceDescription.PlayerSprite.Profile.Dynamic) {
                            net.labymod.api.util.Either<String, UUID> nameOrId = ((GlyphSourceDescription.PlayerSprite.Profile.Dynamic) profile).nameOrId();
                            aabVar = new aab((doy) nameOrId.map(doy::a, doy::a), hat);
                        } else {
                            GlyphSourceDescription.PlayerSprite.Profile.Static staticProfile = (GlyphSourceDescription.PlayerSprite.Profile.Static) profile;
                            GameProfile playerProfile = (GameProfile) CastUtil.cast(staticProfile.profile());
                            aabVar = new aab(doy.a(playerProfile), hat);
                        }
                    }
                }
                throw new IllegalStateException("Unknown object sprite type: " + String.valueOf(objectSprite));
            }
            yw mutableComponent = yw.a(new zm(aabVar));
            return (ObjectComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(mutableComponent, MutableComponentAccessor.class)).getLabyComponent();
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createEmptyComponent() {
        return (TextComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(yh.i(), MutableComponentAccessor.class)).getLabyComponent();
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
        return zh.a(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        DataResult<zh> result = zh.a(value);
        return (TextColor) result.result().get();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Component... arguments) {
        return (TranslatableComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(yh.a(key, arguments), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        zq contents;
        if (arguments == null) {
            contents = new zq(key, key, new Object[0]);
        } else {
            contents = new zq(key, key, arguments.toArray(new Component[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        zf style = zf.a;
        if (style == null) {
            style = new zf((zh) null, (Integer) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (yf) null, (yo) null, (String) null, (ym) null);
        }
        return (Style) CastUtil.cast(style);
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

    @Override // net.labymod.api.client.component.ComponentService
    public Style createStyleFromBuilder(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent<?> hoverEvent, String insertion, GlyphSourceDescription glyphSourceDescription) {
        return (Style) CastUtil.cast(new zf((zh) color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, (yf) clickEvent, (yo) hoverEvent, insertion, MinecraftUtil.toMinecraft(glyphSourceDescription)));
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

    private Component applyStyleAndSiblings(yi componentContents, Style style, List<Component> children) {
        zf vanillaStyle;
        if (style == null) {
            vanillaStyle = zf.a;
        } else {
            vanillaStyle = (zf) CastUtil.cast(style);
        }
        yw component = new yw(componentContents, new ArrayList(), vanillaStyle);
        Component result = ((MutableComponentAccessor) CastUtil.requireInstanceOf(component, MutableComponentAccessor.class)).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
