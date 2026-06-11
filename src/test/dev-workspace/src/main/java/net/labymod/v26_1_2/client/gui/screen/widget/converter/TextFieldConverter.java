package net.labymod.v26_1_2.client.gui.screen.widget.converter;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.v26_1_2.client.gui.EditBoxAccessor;
import net.minecraft.client.gui.components.EditBox;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/screen/widget/converter/TextFieldConverter.class */
public class TextFieldConverter extends AbstractMinecraftWidgetConverter<EditBox, TextFieldWidget> {
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public TextFieldWidget createDefault(EditBox source) {
        EditBoxAccessor accessor = (EditBoxAccessor) source;
        TextFieldWidget destination = new TextFieldWidget();
        Objects.requireNonNull(destination);
        accessor.setValueConsumer(destination::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.getValue());
        destination.maximalLength(source.getMaxLength());
        destination.placeholder(mapComponent(accessor.getHint()));
        destination.setActive(source.active);
        destination.setCursorIndex(source.getCursorPosition());
        destination.setFocused(source.isFocused());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(EditBox source, TextFieldWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            source.setCursorPosition(destination.getCursorIndex());
            source.visible = destination.isVisible();
            source.active = destination.isActive();
            source.setEditable(destination.isEditable());
            if (!Objects.equals(source.getValue(), destination.getText())) {
                int pos = destination.getCursorIndex();
                source.setValue(destination.getText());
                source.setCursorPosition(pos);
                destination.setCursorIndex(pos);
            }
            destination.setFocused(source.isFocused());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }
}
