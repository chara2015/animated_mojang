package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.MoreObjects;
import java.util.List;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/categories/SpectatorPage.class */
public class SpectatorPage {
    public static final int NO_SELECTION = -1;
    private final List<SpectatorMenuItem> items;
    private final int selection;

    public SpectatorPage(List<SpectatorMenuItem> $$0, int $$1) {
        this.items = $$0;
        this.selection = $$1;
    }

    public SpectatorMenuItem getItem(int $$0) {
        if ($$0 < 0 || $$0 >= this.items.size()) {
            return SpectatorMenu.EMPTY_SLOT;
        }
        return (SpectatorMenuItem) MoreObjects.firstNonNull(this.items.get($$0), SpectatorMenu.EMPTY_SLOT);
    }

    public int getSelectedSlot() {
        return this.selection;
    }
}
