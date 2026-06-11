package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/NavigationElementWidget.class */
@AutoWidget
public class NavigationElementWidget extends SimpleWidget {
    private final NavigationWrapper wrapper;
    private final NavigationElement<?> element;
    private Widget widget;

    public NavigationElementWidget(NavigationWrapper wrapper, NavigationElement<?> element) {
        this.wrapper = wrapper;
        this.element = element;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.widget = this.element.createWidget(this.wrapper);
        this.widget.addId("navigation-element-content");
        addChild(this.widget);
        setVisible(this.element.isVisible());
    }

    public NavigationElement<?> getElement() {
        return this.element;
    }

    public Widget getWidget() {
        return this.widget;
    }
}
