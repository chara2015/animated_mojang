package net.labymod.api.client.gui.screen.widget.widgets.navigation.tab;

import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/tab/ComponentTab.class */
public abstract class ComponentTab extends Tab {
    public abstract Component createComponent();

    public ComponentTab() {
    }

    public ComponentTab(boolean useSingleInstance) {
        super(useSingleInstance);
    }
}
