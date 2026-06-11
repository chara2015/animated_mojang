package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/popup/DropdownPopupWidget.class */
@AutoWidget
public class DropdownPopupWidget<T> extends AbstractWidget<Widget> {
    private final DropdownWidget<T> dropdown;
    private final DropdownListWidget<T> listWidget;

    public DropdownPopupWidget(DropdownWidget<T> dropdown) {
        this.dropdown = dropdown;
        this.listWidget = new DropdownListWidget<>(dropdown);
        this.listWidget.addId("list");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) this.listWidget);
        scrollWidget.addId("scroll");
        addChild(scrollWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return this.listWidget.getContentHeight(type);
    }

    public DropdownWidget<T> getDropdown() {
        return this.dropdown;
    }
}
