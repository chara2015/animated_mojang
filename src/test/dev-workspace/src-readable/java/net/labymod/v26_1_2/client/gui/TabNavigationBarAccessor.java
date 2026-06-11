package net.labymod.v26_1_2.client.gui;

import com.google.common.collect.ImmutableList;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab;
import net.labymod.api.client.render.font.ComponentMapper;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/TabNavigationBarAccessor.class */
public interface TabNavigationBarAccessor {
    ImmutableList<Tab> getTabs();

    TabManager getTabManager();

    VersionedTab versionedTabOf(Tab tab);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/TabNavigationBarAccessor$VersionedTab.class */
    public static class VersionedTab extends ComponentTab {
        private static final ComponentMapper COMPONENT_MAPPER = Laby.references().componentMapper();
        private final Tab tab;

        public VersionedTab(Tab tab) {
            this.tab = tab;
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab
        public Component createComponent() {
            return COMPONENT_MAPPER.fromMinecraftComponent(this.tab.getTabTitle());
        }

        @Override // net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab
        public ScreenInstance createScreen() {
            return null;
        }

        public Tab tab() {
            return this.tab;
        }
    }
}
