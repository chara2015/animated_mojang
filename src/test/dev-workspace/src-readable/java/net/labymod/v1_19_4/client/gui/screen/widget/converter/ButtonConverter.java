package net.labymod.v1_19_4.client.gui.screen.widget.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gui/screen/widget/converter/ButtonConverter.class */
public class ButtonConverter extends AbstractMinecraftWidgetConverter<ent, ButtonWidget> {
    final Map<ResourceLocation, String> mappedIcons;

    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        this.mappedIcons = new HashMap();
        prepareMappedIcons();
        registerMapper(new ImageButtonMapper());
        registerMapper(new LockIconButtonMapper(this));
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ButtonWidget createDefault(ent source) {
        ButtonWidget destination = ButtonWidget.component(mapComponent(source.k()), null, Pressable.NOOP);
        destination.setFocused(source.aD_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(ent source, ButtonWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.w);
            destination.setEnabled(source.v);
            destination.setFocused(source.aD_());
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
    public String getWidgetId(ent source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.k());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(ent source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.k());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gui/screen/widget/converter/ButtonConverter$ImageButtonMapper.class */
    class ImageButtonMapper implements Mapper<ent, ButtonWidget> {
        ImageButtonMapper() {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(ent source) {
            if (!(source instanceof eom)) {
                return null;
            }
            ImageButtonAccessor accessor = (eom) source;
            ResourceLocation location = accessor.getResourceLocation();
            Icon icon = Icon.sprite(location, accessor.getXTexStart(), accessor.getYTexStart(), source.j(), source.h(), accessor.getTextureWidth(), accessor.getTextureHeight());
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

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gui/screen/widget/converter/ButtonConverter$LockIconButtonMapper.class */
    class LockIconButtonMapper implements Mapper<ent, ButtonWidget> {
        LockIconButtonMapper(ButtonConverter this$0) {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(ent source) {
            if (!(source instanceof eop)) {
                return null;
            }
            eop lockIconButton = (eop) source;
            int size = source.j();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            boolean locked = !lockIconButton.a();
            boolean enabled = lockIconButton.aG_();
            Icon icon = Icon.sprite(widgetsTexture, locked ? 20 : 0, 146 + (enabled ? 0 : size * 2), size, size, 256, 256);
            if (enabled) {
                icon.setHoverOffset(0, size);
            }
            ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
            button.addId("lock-icon-button");
            return button;
        }
    }
}
