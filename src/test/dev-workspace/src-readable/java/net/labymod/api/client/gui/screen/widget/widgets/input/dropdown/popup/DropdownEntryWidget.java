package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/popup/DropdownEntryWidget.class */
@AutoWidget
public class DropdownEntryWidget<T> extends AbstractWidget<Widget> {
    private final EntryRenderer<T> renderer;
    private final T entry;
    private final LssProperty<Float> fontSize = new LssProperty<>(Float.valueOf(1.0f));

    public DropdownEntryWidget(EntryRenderer<T> renderer, T entry) {
        this.renderer = renderer;
        this.entry = entry;
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Widget widget = this.renderer.createEntryWidget(this.entry);
        widget.addId("value");
        addChild(widget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        float scale = this.fontSize.get().floatValue();
        return this.renderer.getWidth(this.entry, bounds().getWidth(type) / scale) * scale;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        float scale = this.fontSize.get().floatValue();
        return this.renderer.getHeight(this.entry, bounds().getWidth(type) / scale) * scale;
    }

    public T getEntry() {
        return this.entry;
    }

    public LssProperty<Float> fontSize() {
        return this.fontSize;
    }
}
