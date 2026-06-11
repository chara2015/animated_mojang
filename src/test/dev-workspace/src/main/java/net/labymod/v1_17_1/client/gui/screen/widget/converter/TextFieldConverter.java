package net.labymod.v1_17_1.client.gui.screen.widget.converter;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.v1_17_1.client.gui.EditBoxAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gui/screen/widget/converter/TextFieldConverter.class */
public class TextFieldConverter extends AbstractMinecraftWidgetConverter<dxi, TextFieldWidget> {
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public TextFieldWidget createDefault(dxi source) {
        EditBoxAccessor accessor = (EditBoxAccessor) source;
        TextFieldWidget destination = new TextFieldWidget();
        Objects.requireNonNull(destination);
        accessor.setValueConsumer(destination::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.b());
        destination.maximalLength(source.r());
        destination.setActive(source.o);
        destination.setCursorIndex(source.o());
        destination.setFocused(source.h());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(dxi source, TextFieldWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            source.i(destination.getCursorIndex());
            source.p = destination.isVisible();
            source.o = destination.isActive();
            source.g(destination.isEditable());
            if (!Objects.equals(source.b(), destination.getText())) {
                int pos = destination.getCursorIndex();
                source.a(destination.getText());
                source.i(pos);
                destination.setCursorIndex(pos);
            }
            destination.setFocused(source.h());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }
}
