package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/LabeledWidget.class */
@AutoWidget
public class LabeledWidget extends HorizontalListWidget {
    private final Component label;
    private final Widget widget;
    private final LssProperty<HorizontalAlignment> labelAlignment = new LssProperty<>(HorizontalAlignment.LEFT);

    public LabeledWidget(Component label, Widget widget) {
        this.label = label;
        this.widget = widget;
        addId("labeled");
    }

    public static LabeledWidget fullWidth(Component label, Widget widget) {
        return new LabeledWidget(label, widget);
    }

    public static LabeledWidget alignedValue(Component label, Widget widget) {
        HorizontalListWidget list = new HorizontalListWidget();
        list.addChild(new HorizontalListEntry(widget));
        return fullWidth(label, list);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        HorizontalListEntry label = new HorizontalListEntry(ComponentWidget.component(this.label));
        HorizontalListEntry widget = new HorizontalListEntry(this.widget);
        label.addId("labeled-label");
        widget.addId("labeled-widget");
        switch (this.labelAlignment.get()) {
            case LEFT:
                label.alignment().set(HorizontalAlignment.LEFT);
                widget.alignment().set(HorizontalAlignment.RIGHT);
                break;
            case CENTER:
                label.alignment().set(HorizontalAlignment.CENTER);
                widget.alignment().set(HorizontalAlignment.RIGHT);
                break;
            case RIGHT:
                label.alignment().set(HorizontalAlignment.RIGHT);
                widget.alignment().set(HorizontalAlignment.LEFT);
                break;
        }
        addChild(label);
        addChild(widget);
    }

    public LssProperty<HorizontalAlignment> labelAlignment() {
        return this.labelAlignment;
    }
}
