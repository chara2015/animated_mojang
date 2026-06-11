package net.labymod.api.client.gui.screen.widget.widgets.layout.entry;

import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/entry/HorizontalListEntry.class */
@AutoWidget
public class HorizontalListEntry extends WrappedWidget {
    private final LssProperty<Float> flex;
    private final LssProperty<HorizontalAlignment> alignment;
    private final LssProperty<Boolean> ignoreWidth;
    private final LssProperty<Boolean> ignoreHeight;
    private final LssProperty<Boolean> skipFill;

    public HorizontalListEntry(Widget widget) {
        super(widget);
        this.flex = new LssProperty<>(Float.valueOf(1.0f));
        this.alignment = new LssProperty<>(HorizontalAlignment.LEFT);
        this.ignoreWidth = new LssProperty<>(false);
        this.ignoreHeight = new LssProperty<>(false);
        this.skipFill = new LssProperty<>(false);
        addId("horizontal-list-entry");
    }

    public LssProperty<Float> flex() {
        return this.flex;
    }

    public LssProperty<HorizontalAlignment> alignment() {
        return this.alignment;
    }

    public LssProperty<Boolean> ignoreWidth() {
        return this.ignoreWidth;
    }

    public LssProperty<Boolean> ignoreHeight() {
        return this.ignoreHeight;
    }

    public LssProperty<Boolean> skipFill() {
        return this.skipFill;
    }
}
