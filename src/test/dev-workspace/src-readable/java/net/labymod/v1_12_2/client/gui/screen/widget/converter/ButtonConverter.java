package net.labymod.v1_12_2.client.gui.screen.widget.converter;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.function.Mapper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/widget/converter/ButtonConverter.class */
public class ButtonConverter extends AbstractMinecraftWidgetConverter<bja, ButtonWidget> {
    public ButtonConverter() {
        super(MinecraftWidgetType.BUTTON);
        registerMapper(new GuiButtonLanguageMapper());
        registerMapper(new GuiLockIconButtonMapper());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ButtonWidget createDefault(bja source) {
        ButtonWidget destination = ButtonWidget.text(source.j, null, Pressable.NOOP);
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(bja source, ButtonWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            if (source.l) {
                source.l = destination.pressable().get().booleanValue();
            }
            destination.setVisible(source.m);
            destination.setEnabled(source.l);
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(bja source) {
        return String.valueOf(source.k);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/widget/converter/ButtonConverter$GuiButtonLanguageMapper.class */
    static class GuiButtonLanguageMapper implements Mapper<bja, ButtonWidget> {
        GuiButtonLanguageMapper() {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(bja source) {
            if (!(source instanceof bji)) {
                return null;
            }
            int size = source.b();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            Icon icon = Icon.sprite(widgetsTexture, 0, 106, size, size, 256, 256);
            icon.setHoverOffset(0, size);
            ButtonWidget button = ButtonWidget.text("", icon, Pressable.NOOP);
            button.addId("icon-button-language");
            return button;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/widget/converter/ButtonConverter$GuiLockIconButtonMapper.class */
    static class GuiLockIconButtonMapper implements Mapper<bja, ButtonWidget> {
        GuiLockIconButtonMapper() {
        }

        @Override // net.labymod.api.util.function.Mapper
        public ButtonWidget map(bja source) {
            if (!(source instanceof bjl)) {
                return null;
            }
            bjl lockIconButton = (bjl) source;
            int size = source.b();
            ResourceLocation widgetsTexture = Laby.labyAPI().minecraft().textures().widgetsTexture();
            boolean locked = !lockIconButton.c();
            boolean enabled = lockIconButton.l;
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
