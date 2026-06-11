package net.labymod.api.client.render.font;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/ComponentRenderer.class */
@Referenceable
public interface ComponentRenderer {
    ComponentRendererBuilder builder();

    ComponentFlattener getFlattener();

    ComponentFlattener getColorStrippingFlattener();

    void setFlattener(ComponentFlattener componentFlattener);

    PlainTextComponentSerializer plainSerializer();

    LegacyComponentSerializer legacySectionSerializer();

    float width(Component component);

    float height();

    RenderableComponent realignedMerge(List<RenderableComponent> list);

    ComponentFormatter formatter();

    RenderableComponent formatComponent(Component component, float f, HorizontalAlignment horizontalAlignment, float f2, TextOverflowStrategy textOverflowStrategy, boolean z, int i, boolean z2, boolean z3, boolean z4);

    void renderHoverComponent(ScreenContext screenContext, RenderableComponent renderableComponent, int i, boolean z, Rectangle rectangle);

    void invalidate();

    List<Component> split(Component component, float f);

    default void renderHoverComponent(ScreenContext context, RenderableComponent component) {
        renderHoverComponent(context, component, -1, true);
    }

    default void renderHoverComponent(ScreenContext context, RenderableComponent component, int color, boolean allowColors) {
        renderHoverComponent(context, component, color, allowColors, Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds());
    }
}
