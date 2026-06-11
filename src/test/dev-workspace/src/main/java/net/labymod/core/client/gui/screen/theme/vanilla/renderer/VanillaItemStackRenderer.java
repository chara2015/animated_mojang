package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.ItemStackWidget;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaItemStackRenderer.class */
public class VanillaItemStackRenderer extends ThemeRenderer<ItemStackWidget> {
    public VanillaItemStackRenderer() {
        super("ItemStack");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderWidget(ItemStackWidget widget, ScreenContext context) {
        super.renderWidget(widget, context);
        Bounds bounds = widget.bounds();
        ItemStack itemStack = widget.itemStack();
        if (itemStack.getAsItem().isAir()) {
            return;
        }
        int x = MathHelper.floor(bounds.getCenterX() - 8.0f);
        int y = MathHelper.floor(bounds.getCenterY() - 8.0f);
        Laby.references().itemStackVisualizer().submitItem(context, itemStack, x, y, widget.decorate().get().booleanValue());
    }
}
