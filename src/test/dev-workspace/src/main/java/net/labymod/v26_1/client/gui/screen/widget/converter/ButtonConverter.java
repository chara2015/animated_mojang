package net.labymod.v26_1.client.gui.screen.widget.converter;

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
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.components.SpriteIconButton;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/screen/widget/converter/ButtonConverter.class */
public class ButtonConverter extends AbstractMinecraftWidgetConverter<AbstractButton, ButtonWidget> {
    final Map<ResourceLocation, String> mappedIcons;

    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.mappedIcons = new HashMap();
        prepareMappedIcons();
        registerMapper(new ImageButtonMapper(this));
        registerMapper(new LockIconButtonMapper(this));
        registerMapper(new SpriteIconButtonMapper(this));
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ButtonWidget createDefault(AbstractButton source) {
        ButtonWidget destination = ButtonWidget.component(source instanceof SpriteIconButton.CenteredIcon ? null : mapComponent(source.getMessage()), null, Pressable.NOOP);
        destination.setFocused(source.isFocused());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(AbstractButton source, ButtonWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.visible);
            destination.setEnabled(source.active);
            destination.setFocused(source.isFocused());
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
    public String getWidgetId(AbstractButton source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.getMessage());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(AbstractButton source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.getMessage());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/screen/widget/converter/ButtonConverter$ImageButtonMapper.class */
    class ImageButtonMapper implements Mapper<AbstractButton, ButtonWidget> {
        final /* synthetic */ ButtonConverter this$0;

        ImageButtonMapper(ButtonConverter this$0) {
            Objects.requireNonNull(this$0);
            this.this$0 = this$0;
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(AbstractButton source) {
            if (!(source instanceof ImageButton)) {
                return null;
            }
            ImageButtonAccessor accessor = (ImageButton) source;
            ResourceLocation location = accessor.getResourceLocation();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
            TextureSprite textureSprite = atlas.findSprite(location);
            Icon icon = Icon.sprite(atlas, textureSprite);
            icon.setHoverOffset(0, accessor.getYDiffTex());
            ButtonWidget button = new ButtonWidget();
            button.icon().set(icon);
            String id = this.this$0.mappedIcons.get(location);
            if (id != null) {
                button.addId(id);
            }
            return button;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper.class */
    class LockIconButtonMapper implements Mapper<AbstractButton, ButtonWidget> {
        LockIconButtonMapper(ButtonConverter this$0) {
            Objects.requireNonNull(this$0);
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(AbstractButton source) {
            LockIconSprite sprite;
            if (!(source instanceof LockIconButton)) {
                return null;
            }
            LockIconButton lockIconButton = (LockIconButton) source;
            source.getWidth();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            boolean locked = lockIconButton.isLocked();
            if (!lockIconButton.isActive()) {
                sprite = locked ? LockIconSprite.LOCKED_DISABLED : LockIconSprite.UNLOCKED_DISABLED;
            } else if (lockIconButton.isHoveredOrFocused()) {
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

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper$LockIconSprite.class */
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

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/screen/widget/converter/ButtonConverter$SpriteIconButtonMapper.class */
    class SpriteIconButtonMapper implements Mapper<AbstractButton, ButtonWidget> {
        SpriteIconButtonMapper(ButtonConverter this$0) {
            Objects.requireNonNull(this$0);
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(AbstractButton source) {
            ButtonWidget button;
            if (!(source instanceof SpriteIconButton)) {
                return null;
            }
            SpriteIconButtonAccessor accessor = (SpriteIconButton) source;
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
