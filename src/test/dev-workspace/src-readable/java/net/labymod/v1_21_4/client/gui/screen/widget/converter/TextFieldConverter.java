package net.labymod.v1_21_4.client.gui.screen.widget.converter;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.v1_21_4.client.gui.EditBoxAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/gui/screen/widget/converter/TextFieldConverter.class */
public class TextFieldConverter extends AbstractMinecraftWidgetConverter<fpd, TextFieldWidget> {
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public TextFieldWidget createDefault(fpd source) {
        EditBoxAccessor accessor = (EditBoxAccessor) source;
        TextFieldWidget destination = new TextFieldWidget();
        Objects.requireNonNull(destination);
        accessor.setValueConsumer(destination::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.a());
        destination.maximalLength(source.k());
        destination.placeholder(mapComponent(accessor.getHint()));
        destination.setActive(source.j);
        destination.setCursorIndex(source.e());
        destination.setFocused(source.aM_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(fpd source, TextFieldWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            source.e(destination.getCursorIndex());
            source.k = destination.isVisible();
            source.j = destination.isActive();
            source.e(destination.isEditable());
            if (!Objects.equals(source.a(), destination.getText())) {
                int pos = destination.getCursorIndex();
                source.a(destination.getText());
                source.e(pos);
                destination.setCursorIndex(pos);
            }
            destination.setFocused(source.aM_());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }
}
