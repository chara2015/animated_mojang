package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/WidgetFieldWidget.class */
@AutoWidget
public class WidgetFieldWidget extends SimpleWidget {
    private Widget widget;

    public WidgetFieldWidget(Widget widget) {
        this.widget = widget;
        this.widget.addId("widget-field-widget");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.widget);
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
        this.widget.addId("widget-field-widget");
    }

    public Widget getWidget() {
        return this.widget;
    }
}
