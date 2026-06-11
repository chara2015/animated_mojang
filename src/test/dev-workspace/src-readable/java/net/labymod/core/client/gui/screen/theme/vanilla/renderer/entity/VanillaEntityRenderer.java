package net.labymod.core.client.gui.screen.theme.vanilla.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity.EntityWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/entity/VanillaEntityRenderer.class */
public class VanillaEntityRenderer extends ThemeRenderer<EntityWidget> {
    public VanillaEntityRenderer() {
        super("Entity");
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderWidget(EntityWidget widget, ScreenContext context) {
        super.renderWidget(widget, context);
        Bounds bounds = widget.bounds();
        Float scale = widget.entityScale().get();
        Laby.references().entityVisualizer().submitEntity(context, widget.entity(), bounds.getCenterX(), bounds.getCenterY() + scale.floatValue(), widget.entityRotationX().get().floatValue(), widget.entityRotationY().get().floatValue(), widget.entityRotationZ().get().floatValue(), scale.floatValue());
    }
}
