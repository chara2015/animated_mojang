package net.labymod.core.main.animation.old.animations;

import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/InventoryLayoutOldAnimation.class */
public class InventoryLayoutOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "inventory_layout";
    private final LabyAPI labyAPI;

    public InventoryLayoutOldAnimation() {
        super(NAME);
        this.labyAPI = Laby.labyAPI();
    }

    public boolean removeRecipeBook() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.shouldRemoveRecipeBook());
    }

    public boolean canUseOldSurvivalInventory() {
        if (!isOldSurvivalLayout()) {
            return false;
        }
        return canUseOldInventory();
    }

    public boolean canUseOldCreativeLayout() {
        if (!isOldCreativeLayout()) {
            return false;
        }
        return canUseOldInventory();
    }

    public int getEntityXShift() {
        return 88;
    }

    public int getEntityXShiftOld() {
        return 44;
    }

    public int getEntityYShift() {
        return 0;
    }

    private boolean canUseOldInventory() {
        boolean oldInventory = false;
        Collection<String> selectedPacks = this.labyAPI.renderPipeline().resourcePackRepository().getSelectedPacks();
        for (String selectedPack : selectedPacks) {
            oldInventory = selectedPack.equals(VanillaTheme.ID) || selectedPack.equals("programer_art");
            if (oldInventory) {
                break;
            }
        }
        return oldInventory;
    }

    private boolean isOldSurvivalLayout() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldSurvivalLayout());
    }

    private boolean isOldCreativeLayout() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldCreativeLayout());
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return true;
    }
}
