package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/renderer/EntryRenderer.class */
public interface EntryRenderer<T> {
    float getWidth(T t, float f);

    float getHeight(T t, float f);

    @NotNull
    Widget createEntryWidget(T t);

    @NotNull
    default Widget createSelectedWidget(T entry) {
        return createEntryWidget(entry);
    }
}
