package net.labymod.v1_8_9.client.gui.screen.widget.converter;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.v1_8_9.client.gui.GuiTextFieldAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/screen/widget/converter/TextFieldConverter.class */
public class TextFieldConverter extends AbstractMinecraftWidgetConverter<avw, TextFieldWidget> {
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public TextFieldWidget createDefault(avw source) {
        GuiTextFieldAccessor accessor = (GuiTextFieldAccessor) source;
        TextFieldWidget destination = new TextFieldWidget();
        Objects.requireNonNull(destination);
        accessor.setTextConsumer(destination::setText);
        destination.setText(source.b());
        destination.setEditable(((GuiTextFieldAccessor) source).isEnabled());
        destination.maximalLength(source.h());
        destination.setCursorIndex(source.i());
        destination.setFocused(source.m());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(avw source, TextFieldWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            source.e(destination.isVisible());
            source.c(destination.isEditable());
            source.e(destination.getCursorIndex());
            updateText(source, destination);
            destination.setFocused(source.m());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    public void updateText(avw mc, AbstractWidget<?> widget) {
        if (!(widget instanceof TextFieldWidget)) {
            return;
        }
        updateText(mc, (TextFieldWidget) widget);
    }

    public void updateText(avw mc, TextFieldWidget widget) {
        if (!Objects.equals(mc.b(), widget.getText())) {
            mc.a(widget.getText());
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(avw source) {
        return String.valueOf(source.d());
    }
}
