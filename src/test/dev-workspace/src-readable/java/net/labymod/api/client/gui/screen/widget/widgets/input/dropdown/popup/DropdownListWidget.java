package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.sound.SoundType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/dropdown/popup/DropdownListWidget.class */
@AutoWidget
public class DropdownListWidget<T> extends VerticalListWidget<DropdownEntryWidget<T>> {
    private final DropdownWidget<T> dropdown;

    public DropdownListWidget(DropdownWidget<T> dropdown) {
        this.dropdown = dropdown;
        setSelectCallback(value -> {
            Laby.references().soundService().play(SoundType.BUTTON_CLICK);
            dropdown.onSelect(value.getEntry());
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        EntryRenderer<T> entryRenderer = this.dropdown.entryRenderer();
        for (T entry : this.dropdown.entries()) {
            DropdownEntryWidget<T> widget = new DropdownEntryWidget<>(entryRenderer, entry);
            widget.addId("entry");
            addChild(widget);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return super.getContentHeight(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Background";
    }

    public DropdownWidget<T> getDropdown() {
        return this.dropdown;
    }
}
