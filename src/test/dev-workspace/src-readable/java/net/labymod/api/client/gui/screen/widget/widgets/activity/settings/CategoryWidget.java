package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.configuration.settings.Setting;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/CategoryWidget.class */
@AutoWidget
public class CategoryWidget extends ButtonWidget {
    private final Setting category;

    public CategoryWidget(Setting category) {
        this.category = category;
        this.component = category.displayName();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    public Setting category() {
        return this.category;
    }
}
