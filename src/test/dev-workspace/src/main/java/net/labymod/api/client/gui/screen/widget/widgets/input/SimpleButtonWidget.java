package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/SimpleButtonWidget.class */
@AutoWidget
public class SimpleButtonWidget extends DivWidget {
    private final ComponentWidget componentWidget;

    public SimpleButtonWidget(String text) {
        this(ComponentWidget.text(text));
    }

    public SimpleButtonWidget(Component component) {
        this(ComponentWidget.component(component));
    }

    public SimpleButtonWidget(ComponentWidget componentWidget) {
        this.componentWidget = componentWidget;
        this.componentWidget.addId("text");
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.componentWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (this.componentWidget != null && this.componentWidget.renderable().isClipped() && !hasHoverComponent()) {
            setHoverComponent(this.componentWidget.component());
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }
}
