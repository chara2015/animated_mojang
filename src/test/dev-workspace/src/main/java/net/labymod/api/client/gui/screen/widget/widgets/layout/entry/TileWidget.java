package net.labymod.api.client.gui.screen.widget.widgets.layout.entry;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/entry/TileWidget.class */
@AutoWidget
@Deprecated(forRemoval = true, since = "4.0.6")
public class TileWidget<T extends Widget> extends WrappedWidget {
    private final int widthUnits;
    private final int heightUnits;

    public TileWidget(@NotNull T childWidget, int widthUnits, int heightUnits) {
        super(childWidget);
        this.widthUnits = widthUnits;
        this.heightUnits = heightUnits;
    }

    @Override // net.labymod.api.client.gui.screen.widget.WrappedWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        if (!isVisible()) {
            return;
        }
        super.render(context);
    }

    public int getWidthUnits() {
        return this.widthUnits;
    }

    public int getHeightUnits() {
        return this.heightUnits;
    }

    @Override // net.labymod.api.client.gui.screen.widget.WrappedWidget
    @NotNull
    public T childWidget() {
        return (T) this.childWidget;
    }
}
