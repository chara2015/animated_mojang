package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/VanillaBulletEntryContainerRenderer.class */
public class VanillaBulletEntryContainerRenderer extends VanillaBackgroundRenderer {
    public VanillaBulletEntryContainerRenderer() {
        this.name = "BulletEntryContainer";
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPre(widget, context);
        Bounds bounds = widget.bounds();
        Material bulletMaterial = GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.SpriteCommon.TEXTURE);
        ScreenCanvas canvas = context.canvas();
        canvas.submitGuiBlit(bulletMaterial, bounds.getX(), bounds.getY(), 6, bounds.getHeight(), UVCoordinates.of(32 * 2, 80 * 2, 6 * 2, 16 * 2), -1);
        canvas.submitGuiBlit(bulletMaterial, bounds.getRight() - 6, bounds.getY(), 6, bounds.getHeight(), UVCoordinates.of((48 - 6) * 2, 80 * 2, 6 * 2, 16 * 2), -1);
        int repeatPartWidth = 16 - (6 * 2);
        int times = (int) (MathHelper.ceil(bounds.getWidth() - (6 * 2)) / repeatPartWidth);
        for (int i = 0; i < times; i++) {
            canvas.submitGuiBlit(bulletMaterial, bounds.getX() + 6 + (i * repeatPartWidth), bounds.getY(), repeatPartWidth + 0.1f, bounds.getHeight(), UVCoordinates.of((32 + 6) * 2, 80 * 2, repeatPartWidth * 2, 16 * 2), -1);
        }
        float leftOverWidth = bounds.getWidth() - ((times * repeatPartWidth) + (6 * 2));
        if (leftOverWidth > 0.0f) {
            canvas.submitGuiBlit(bulletMaterial, (bounds.getRight() - 6) - leftOverWidth, bounds.getY(), leftOverWidth, 16.0f, UVCoordinates.of((32 + 6) * 2, 80 * 2, leftOverWidth * 2, 16 * 2), -1);
        }
    }
}
