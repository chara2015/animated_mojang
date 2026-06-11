package net.labymod.v1_21_11.client.gui.screen.widget.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.util.function.Mapper;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/ButtonConverter.class */
public class ButtonConverter extends AbstractMinecraftWidgetConverter<giu, ButtonWidget> {
    final Map<ResourceLocation, String> mappedIcons;

    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.mappedIcons = new HashMap();
        prepareMappedIcons();
        registerMapper(new ImageButtonMapper());
        registerMapper(new LockIconButtonMapper(this));
        registerMapper(new SpriteIconButtonMapper(this));
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ButtonWidget createDefault(giu source) {
        ButtonWidget destination = ButtonWidget.component(source instanceof b ? null : mapComponent(source.B()), null, Pressable.NOOP);
        destination.setFocused(source.aP_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(giu source, ButtonWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.l);
            destination.setEnabled(source.k);
            destination.setFocused(source.aP_());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    private void prepareMappedIcons() {
        ResourceLocationFactory factory = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
        this.mappedIcons.put(factory.createMinecraft("textures/gui/recipe_button.png"), "icon-button-recipe-book");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/accessibility.png"), "icon-button-accessibility");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/widgets.png"), "icon-button-language");
        this.mappedIcons.put(factory.createMinecraft("textures/gui/social_interactions.png"), "icon-social-interactions");
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(giu source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.B());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(giu source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.B());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/ButtonConverter$ImageButtonMapper.class */
    class ImageButtonMapper implements Mapper<giu, ButtonWidget> {
        ImageButtonMapper() {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(giu source) {
            if (!(source instanceof gjq)) {
                return null;
            }
            ImageButtonAccessor accessor = (gjq) source;
            ResourceLocation location = accessor.getResourceLocation();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
            TextureSprite textureSprite = atlas.findSprite(location);
            Icon icon = Icon.sprite(atlas, textureSprite);
            icon.setHoverOffset(0, accessor.getYDiffTex());
            ButtonWidget button = new ButtonWidget();
            button.icon().set(icon);
            String id = ButtonConverter.this.mappedIcons.get(location);
            if (id != null) {
                button.addId(id);
            }
            return button;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper.class */
    class LockIconButtonMapper implements Mapper<giu, ButtonWidget> {
        LockIconButtonMapper(ButtonConverter this$0) {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(giu source) {
            LockIconSprite sprite;
            if (!(source instanceof gjv)) {
                return null;
            }
            gjv lockIconButton = (gjv) source;
            source.aS_();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            boolean locked = lockIconButton.a();
            if (!lockIconButton.b()) {
                sprite = locked ? LockIconSprite.LOCKED_DISABLED : LockIconSprite.UNLOCKED_DISABLED;
            } else if (lockIconButton.D()) {
                sprite = locked ? LockIconSprite.LOCKED_HOVER : LockIconSprite.UNLOCKED_HOVER;
            } else {
                sprite = locked ? LockIconSprite.LOCKED : LockIconSprite.UNLOCKED;
            }
            TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
            TextureSprite textureSprite = atlas.findSprite(sprite.sprite());
            Icon icon = Icon.sprite(atlas, textureSprite);
            LockIconSprite lockIconSprite = locked ? LockIconSprite.LOCKED_HOVER : LockIconSprite.UNLOCKED_HOVER;
            Objects.requireNonNull(lockIconSprite);
            icon.setHoverSprite(lockIconSprite::sprite);
            ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
            button.addId("lock-icon-button");
            return button;
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper$LockIconSprite.class */
        enum LockIconSprite {
            LOCKED("widget/locked_button"),
            LOCKED_HOVER("widget/locked_button_highlighted"),
            LOCKED_DISABLED("widget/locked_button_disabled"),
            UNLOCKED("widget/unlocked_button"),
            UNLOCKED_HOVER("widget/unlocked_button_highlighted"),
            UNLOCKED_DISABLED("widget/unlocked_button_disabled");

            private final ResourceLocation sprite;

            LockIconSprite(String spritePath) {
                this.sprite = ResourceLocation.create(Namespaces.MINECRAFT, spritePath);
            }

            public ResourceLocation sprite() {
                return this.sprite;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/ButtonConverter$SpriteIconButtonMapper.class */
    class SpriteIconButtonMapper implements Mapper<giu, ButtonWidget> {
        SpriteIconButtonMapper(ButtonConverter this$0) {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(giu source) {
            ButtonWidget button;
            if (!(source instanceof gkn)) {
                return null;
            }
            SpriteIconButtonAccessor accessor = (gkn) source;
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            ResourceLocation sprite = accessor.getResourceLocation();
            TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
            TextureSprite textureSprite = atlas.findSprite(sprite);
            Icon icon = Icon.sprite(atlas, textureSprite);
            if (accessor.iconOnly()) {
                button = ButtonWidget.icon(icon, Pressable.NOOP);
            } else {
                button = ButtonWidget.component(accessor.getMessage(), icon, Pressable.NOOP);
            }
            button.setHoverComponent(accessor.getTooltip());
            return button;
        }
    }
}
