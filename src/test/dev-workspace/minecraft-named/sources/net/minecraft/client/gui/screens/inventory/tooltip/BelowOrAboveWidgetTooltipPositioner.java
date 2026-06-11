package net.minecraft.client.gui.screens.inventory.tooltip;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.joml.Vector2i;
import org.joml.Vector2ic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/inventory/tooltip/BelowOrAboveWidgetTooltipPositioner.class */
public class BelowOrAboveWidgetTooltipPositioner implements ClientTooltipPositioner {
    private final ScreenRectangle screenRectangle;

    public BelowOrAboveWidgetTooltipPositioner(ScreenRectangle $$0) {
        this.screenRectangle = $$0;
    }

    @Override // net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner
    public Vector2ic positionTooltip(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        Vector2i $$6 = new Vector2i();
        $$6.x = this.screenRectangle.left() + 3;
        $$6.y = this.screenRectangle.bottom() + 3 + 1;
        if ($$6.y + $$5 + 3 > $$1) {
            $$6.y = ((this.screenRectangle.top() - $$5) - 3) - 1;
        }
        if ($$6.x + $$4 > $$0) {
            $$6.x = Math.max((this.screenRectangle.right() - $$4) - 3, 4);
        }
        return $$6;
    }
}
