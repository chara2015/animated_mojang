package net.labymod.v1_20_4.client.gui;

import com.google.common.collect.ImmutableList;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab;
import net.labymod.api.client.render.font.ComponentMapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/gui/TabNavigationBarAccessor.class */
public interface TabNavigationBarAccessor {
    ImmutableList<ezh> getTabs();

    ezi getTabManager();

    VersionedTab versionedTabOf(ezh ezhVar);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/gui/TabNavigationBarAccessor$VersionedTab.class */
    public static class VersionedTab extends ComponentTab {
        private static final ComponentMapper COMPONENT_MAPPER = Laby.references().componentMapper();
        private final ezh tab;

        public VersionedTab(ezh tab) {
            this.tab = tab;
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab
        public Component createComponent() {
            return COMPONENT_MAPPER.fromMinecraftComponent(this.tab.a());
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab
        public ScreenInstance createScreen() {
            return null;
        }

        public ezh tab() {
            return this.tab;
        }
    }
}
