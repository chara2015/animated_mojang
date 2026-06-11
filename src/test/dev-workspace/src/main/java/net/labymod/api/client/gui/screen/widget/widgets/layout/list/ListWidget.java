package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/list/ListWidget.class */
@AutoWidget
public abstract class ListWidget<T extends Widget> extends AbstractWidget<T> {
    protected ListWidget() {
    }

    public void updateVisibility(ListWidget<?> widget, Parent parent) {
        for (T child : this.children) {
            if (child instanceof ListWidget) {
                ((ListWidget) child).updateVisibility(widget, parent);
            }
        }
    }

    @Nullable
    protected Widget getClosestContentWidget() {
        Parent parent = this.parent;
        while (true) {
            Parent parent2 = parent;
            if (parent2 != null) {
                if (!(parent2 instanceof ListWidget)) {
                    return (Widget) parent2;
                }
                parent = parent2.getParent();
            } else {
                return null;
            }
        }
    }
}
