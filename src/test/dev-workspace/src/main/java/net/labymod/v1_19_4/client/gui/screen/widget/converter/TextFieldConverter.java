package net.labymod.v1_19_4.client.gui.screen.widget.converter;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.v1_19_4.client.gui.EditBoxAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gui/screen/widget/converter/TextFieldConverter.class */
public class TextFieldConverter extends AbstractMinecraftWidgetConverter<eol, TextFieldWidget> {
    public TextFieldConverter() {
        super(MinecraftWidgetType.TEXT_FIELD);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public TextFieldWidget createDefault(eol source) {
        EditBoxAccessor accessor = (EditBoxAccessor) source;
        TextFieldWidget destination = new TextFieldWidget();
        Objects.requireNonNull(destination);
        accessor.setValueConsumer(destination::setText);
        destination.setEditable(accessor.isEditable());
        destination.setText(source.b());
        destination.placeholder(mapComponent(accessor.getHint()));
        destination.maximalLength(source.y());
        destination.setActive(source.v);
        destination.setCursorIndex(source.v());
        destination.setFocused(source.aD_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(eol source, TextFieldWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            source.l(destination.getCursorIndex());
            source.w = destination.isVisible();
            source.v = destination.isActive();
            source.c(destination.isEditable());
            if (!Objects.equals(source.b(), destination.getText())) {
                int pos = destination.getCursorIndex();
                source.a(destination.getText());
                source.l(pos);
                destination.setCursorIndex(pos);
            }
            destination.setFocused(source.aD_());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }
}
