package net.labymod.v26_1.client.component;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
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
import net.labymod.v26_1.client.network.chat.MutableComponentAccessor;
import net.labymod.v26_1.client.network.chat.VersionedBaseComponent;
import net.labymod.v26_1.client.network.chat.VersionedTextComponent;
import net.labymod.v26_1.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v26_1.client.network.chat.serializer.VersionedComponentSerializerWrapper;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.network.chat.contents.ObjectContents;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.network.chat.contents.objects.PlayerSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/component/VersionedComponentService.class */
@Singleton
@Implements(ComponentService.class)
public class VersionedComponentService extends ComponentService {
    @Inject
    public VersionedComponentService() {
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text) {
        return (TextComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.literal(text), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createTextComponent(String text, Style style, List<net.labymod.api.client.component.Component> children) {
        PlainTextContents.LiteralContents contents = new PlainTextContents.LiteralContents(text);
        return (TextComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective) {
        return (ScoreComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.score(name, objective), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ScoreComponent createScoreComponent(String name, String objective, Style style, List<net.labymod.api.client.component.Component> children) {
        ScoreContents contents = new ScoreContents(Either.right(name), objective);
        return (ScoreComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind) {
        return (KeybindComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.keybind(keybind), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public KeybindComponent createKeybindComponent(String keybind, Style style, List<net.labymod.api.client.component.Component> children) {
        KeybindContents contents = new KeybindContents(keybind);
        return (KeybindComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon) {
        return (IconComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(MutableComponent.create(new VersionedIconContents(icon)), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    protected IconComponent createIconComponent(Icon icon, Style style, List<net.labymod.api.client.component.Component> children) {
        VersionedIconContents contents = new VersionedIconContents(icon);
        return (IconComponent) applyStyleAndSiblings(contents, style, children);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.ComponentService
    protected ObjectComponent createObjectComponent(ObjectSprite objectSprite, Style style, List<net.labymod.api.client.component.Component> children) throws MatchException {
        AtlasSprite playerSprite;
        try {
            if (objectSprite instanceof ObjectSprite.AtlasObjectSprite) {
                ObjectSprite.AtlasObjectSprite atlasObjectSprite = (ObjectSprite.AtlasObjectSprite) objectSprite;
                ResourceLocation atlas = atlasObjectSprite.atlas();
                ResourceLocation sprite = atlasObjectSprite.sprite();
                playerSprite = new AtlasSprite((Identifier) atlas.getMinecraftLocation(), (Identifier) sprite.getMinecraftLocation());
            } else {
                if (objectSprite instanceof ObjectSprite.PlayerObjectSprite) {
                    ObjectSprite.PlayerObjectSprite playerObjectSprite = (ObjectSprite.PlayerObjectSprite) objectSprite;
                    GlyphSourceDescription.PlayerSprite.Profile profile = playerObjectSprite.profile();
                    boolean hat = playerObjectSprite.hat();
                    if (1 != 0) {
                        if (profile instanceof GlyphSourceDescription.PlayerSprite.Profile.Dynamic) {
                            net.labymod.api.util.Either<String, UUID> nameOrId = ((GlyphSourceDescription.PlayerSprite.Profile.Dynamic) profile).nameOrId();
                            playerSprite = new PlayerSprite((ResolvableProfile) nameOrId.map(ResolvableProfile::createUnresolved, ResolvableProfile::createUnresolved), hat);
                        } else {
                            GlyphSourceDescription.PlayerSprite.Profile.Static staticProfile = (GlyphSourceDescription.PlayerSprite.Profile.Static) profile;
                            GameProfile playerProfile = (GameProfile) CastUtil.cast(staticProfile.profile());
                            playerSprite = new PlayerSprite(ResolvableProfile.createResolved(playerProfile), hat);
                        }
                    }
                }
                throw new IllegalStateException("Unknown object sprite type: " + String.valueOf(objectSprite));
            }
            MutableComponent mutableComponent = MutableComponent.create(new ObjectContents(playerSprite, Optional.empty()));
            return (ObjectComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(mutableComponent, MutableComponentAccessor.class)).getLabyComponent();
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextComponent createEmptyComponent() {
        return (TextComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.empty(), MutableComponentAccessor.class)).getLabyComponent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.component.ComponentService
    public <T> HoverEvent<T> createHoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T t) {
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(t, "Value cannot be null!");
        if (action == HoverEvent.Action.SHOW_TEXT) {
            if (t instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> component = (VersionedBaseComponent) t;
                return new HoverEvent.ShowText<>(component.holder);
            }
            throw new IllegalStateException("Value must be a Component!");
        }
        throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public ClickEvent createClickEvent(ClickEvent.Action action, @NotNull String value) {
        ClickEvent.OpenUrl copyToClipboard;
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(value, "Value cannot be null!");
        switch (action) {
            case OPEN_URL:
                copyToClipboard = new ClickEvent.OpenUrl(URI.create(value));
                break;
            case OPEN_FILE:
                copyToClipboard = new ClickEvent.OpenFile(Paths.get(value, new String[0]));
                break;
            case RUN_COMMAND:
                copyToClipboard = new ClickEvent.RunCommand(value);
                break;
            case SUGGEST_COMMAND:
                copyToClipboard = new ClickEvent.SuggestCommand(value);
                break;
            case CHANGE_PAGE:
                copyToClipboard = new ClickEvent.ChangePage(Integer.parseInt(value));
                break;
            case COPY_TO_CLIPBOARD:
                copyToClipboard = new ClickEvent.CopyToClipboard(value);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported action: " + String.valueOf(action));
        }
        return (net.labymod.api.client.component.event.ClickEvent) copyToClipboard;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor createTextColor(int value) {
        return net.minecraft.network.chat.TextColor.fromRgb(value);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TextColor getParsedTextColor(String value) {
        DataResult<net.minecraft.network.chat.TextColor> result = net.minecraft.network.chat.TextColor.parseColor(value);
        return (TextColor) result.result().get();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, net.labymod.api.client.component.Component... arguments) {
        return (TranslatableComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.translatable(key, arguments), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public TranslatableComponent createTranslatableComponent(String key, Style style, List<net.labymod.api.client.component.Component> children, List<net.labymod.api.client.component.Component> arguments) {
        TranslatableContents contents;
        if (arguments == null) {
            contents = new TranslatableContents(key, key, new Object[0]);
        } else {
            contents = new TranslatableContents(key, key, arguments.toArray(new net.labymod.api.client.component.Component[0]));
        }
        return (TranslatableComponent) applyStyleAndSiblings(contents, style, children);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style getEmptyStyle() {
        net.minecraft.network.chat.Style style = net.minecraft.network.chat.Style.EMPTY;
        if (style == null) {
            style = new net.minecraft.network.chat.Style((net.minecraft.network.chat.TextColor) null, (Integer) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (Boolean) null, (net.minecraft.network.chat.ClickEvent) null, (net.minecraft.network.chat.HoverEvent) null, (String) null, (FontDescription) null);
        }
        return (Style) CastUtil.cast(style);
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Class<?> getActualVersionedClass(net.labymod.api.client.component.Component component, Class<?> cls) {
        if (cls == VersionedTextComponent.class) {
            return TextComponent.class;
        }
        if (cls == VersionedTranslatableComponent.class) {
            return TranslatableComponent.class;
        }
        return cls;
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Style createStyleFromBuilder(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, net.labymod.api.client.component.event.ClickEvent clickEvent, net.labymod.api.client.component.event.HoverEvent<?> hoverEvent, String insertion, GlyphSourceDescription glyphSourceDescription) {
        return (Style) CastUtil.cast(new net.minecraft.network.chat.Style((net.minecraft.network.chat.TextColor) color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, (net.minecraft.network.chat.ClickEvent) clickEvent, (net.minecraft.network.chat.HoverEvent) hoverEvent, insertion, MinecraftUtil.toMinecraft(glyphSourceDescription)));
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Serializer createComponentSerializer() {
        return new VersionedComponentSerializerWrapper();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public Serializer createStyleSerializer() {
        return new Style.Serializer();
    }

    @Override // net.labymod.api.client.component.ComponentService
    public void applyGsonTypeAdapters(GsonBuilder builder) {
        builder.registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory());
    }

    private net.labymod.api.client.component.Component applyStyleAndSiblings(ComponentContents componentContents, net.labymod.api.client.component.format.Style style, List<net.labymod.api.client.component.Component> children) {
        net.minecraft.network.chat.Style vanillaStyle;
        if (style == null) {
            vanillaStyle = net.minecraft.network.chat.Style.EMPTY;
        } else {
            vanillaStyle = (net.minecraft.network.chat.Style) CastUtil.cast(style);
        }
        MutableComponent component = new MutableComponent(componentContents, new ArrayList(), vanillaStyle);
        net.labymod.api.client.component.Component result = ((MutableComponentAccessor) CastUtil.requireInstanceOf(component, MutableComponentAccessor.class)).getLabyComponent();
        if (children != null) {
            result.setChildren(children);
        }
        return result;
    }
}
