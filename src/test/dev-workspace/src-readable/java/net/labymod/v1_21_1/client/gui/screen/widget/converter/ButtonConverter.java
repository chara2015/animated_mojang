package net.labymod.v1_21_1.client.gui.screen.widget.converter;

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
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gui/screen/widget/converter/ButtonConverter.class */
public class ButtonConverter extends AbstractMinecraftWidgetConverter<fid, ButtonWidget> {
    final Map<ResourceLocation, String> mappedIcons;

    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.mappedIcons = new HashMap();
        prepareMappedIcons();
        registerMapper(new ImageButtonMapper());
        registerMapper(new LockIconButtonMapper(this));
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ButtonWidget createDefault(fid source) {
        ButtonWidget destination = ButtonWidget.component(source instanceof b ? null : mapComponent(source.z()), null, Pressable.NOOP);
        destination.setFocused(source.aO_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(fid source, ButtonWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.k);
            destination.setEnabled(source.j);
            destination.setFocused(source.aO_());
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
    public String getWidgetId(fid source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.z());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(fid source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.z());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gui/screen/widget/converter/ButtonConverter$ImageButtonMapper.class */
    class ImageButtonMapper implements Mapper<fid, ButtonWidget> {
        ImageButtonMapper() {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(fid source) {
            if (!(source instanceof fiy)) {
                return null;
            }
            ImageButtonAccessor accessor = (fiy) source;
            ResourceLocation location = accessor.getResourceLocation();
            Icon icon = Icon.sprite(location, accessor.getXTexStart(), accessor.getYTexStart(), source.y(), source.w(), accessor.getTextureWidth(), accessor.getTextureHeight());
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

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper.class */
    class LockIconButtonMapper implements Mapper<fid, ButtonWidget> {
        LockIconButtonMapper(ButtonConverter this$0) {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(fid source) {
            LockIconSprite sprite;
            if (!(source instanceof fjc)) {
                return null;
            }
            fjc lockIconButton = (fjc) source;
            source.y();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            boolean locked = lockIconButton.a();
            if (!lockIconButton.C()) {
                sprite = locked ? LockIconSprite.LOCKED_DISABLED : LockIconSprite.UNLOCKED_DISABLED;
            } else if (lockIconButton.B()) {
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

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper$LockIconSprite.class */
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
}
