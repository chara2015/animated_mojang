package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/TreeWidget.class */
@AutoWidget
public class TreeWidget extends VerticalListWidget<Widget> {
    private final Widget root;

    public TreeWidget(Widget root) {
        this.root = root;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        for (T child : this.children) {
            child.addId("tree-child");
        }
        addChild(0, this.root);
    }
}
