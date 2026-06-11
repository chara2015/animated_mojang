package net.labymod.api.client.gui.screen.widget.widgets.navigation.tab;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenInstance;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/tab/DefaultComponentTab.class */
public class DefaultComponentTab extends ComponentTab {
    private final Component component;
    private final Factory screenInstanceFactory;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/tab/DefaultComponentTab$Factory.class */
    public interface Factory {
        ScreenInstance create();
    }

    public DefaultComponentTab(String title, ScreenInstance screenInstance) {
        this(Component.text(title), screenInstance);
    }

    public DefaultComponentTab(String title, Factory screenInstanceFactory) {
        this(Component.text(title), screenInstanceFactory);
    }

    public DefaultComponentTab(Component component, ScreenInstance screenInstance) {
        this(component, () -> {
            return screenInstance;
        });
    }

    public DefaultComponentTab(Component component, Factory screenInstanceFactory) {
        this.component = component;
        this.screenInstanceFactory = screenInstanceFactory;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab
    public Component createComponent() {
        return this.component;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab
    public ScreenInstance createScreen() {
        return this.screenInstanceFactory.create();
    }
}
