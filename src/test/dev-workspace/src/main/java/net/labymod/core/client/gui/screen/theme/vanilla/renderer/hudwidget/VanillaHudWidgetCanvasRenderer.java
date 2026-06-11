package net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget;

import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.ExperienceBarRenderer;
import net.labymod.api.client.render.HotbarRenderer;
import net.labymod.api.client.render.RenderMode;
import net.labymod.api.client.render.StatusIcon;
import net.labymod.api.client.render.StatusIconRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/renderer/hudwidget/VanillaHudWidgetCanvasRenderer.class */
public class VanillaHudWidgetCanvasRenderer extends VanillaBackgroundRenderer {
    private static final StatusIcon EMPTY_HEART_ICON = new StatusIcon(ResourceLocation.create(Namespaces.MINECRAFT, "hud/heart/container"), 16, 0);
    private static final StatusIcon HEART_ICON = new StatusIcon(ResourceLocation.create(Namespaces.MINECRAFT, "hud/heart/full"), 52, 0);
    private static final StatusIcon EMPTY_SATURATION_ICON = new StatusIcon(ResourceLocation.create(Namespaces.MINECRAFT, "hud/food_empty"), 16, 27);
    private static final StatusIcon SATURATION_ICON = new StatusIcon(ResourceLocation.create(Namespaces.MINECRAFT, "hud/food_full"), 52, 27);
    private static final StatusIcon ARMOR_ICON = new StatusIcon(ResourceLocation.create(Namespaces.MINECRAFT, "hud/armor_full"), 43, 9);
    private static final int BACKGROUND_WIDTH = 1920;
    private static final int BACKGROUND_HEIGHT = 1080;
    private static final float ASPECT_RATIO = 1.7777778f;
    private final HotbarRenderer hotbarRenderer;
    private final ExperienceBarRenderer experienceBarRenderer;
    private final StatusIconRenderer statusIconRenderer;

    public VanillaHudWidgetCanvasRenderer() {
        super("HudWidgetCanvas");
        this.hotbarRenderer = Laby.references().hotbarRenderer();
        this.experienceBarRenderer = Laby.references().experienceBarRenderer();
        this.statusIconRenderer = Laby.references().statusIconRenderer();
    }

    @Override // net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer, net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        super.renderPre(widget, context);
        renderBackgroundImage(context, widget.bounds());
        renderHotbar(context, widget.bounds());
    }

    protected void renderBackgroundImage(ScreenContext context, Bounds bounds) {
        float aspectHeight = bounds.getWidth(BoundsType.INNER) / ASPECT_RATIO;
        float aspectWidth = bounds.getHeight(BoundsType.INNER) * ASPECT_RATIO;
        float verticalOverflow = aspectHeight - bounds.getHeight(BoundsType.INNER);
        float horizontalOverflow = aspectWidth - bounds.getWidth(BoundsType.INNER);
        float offsetX = horizontalOverflow > 0.0f ? horizontalOverflow : 0.0f;
        float offsetY = verticalOverflow > 0.0f ? verticalOverflow : 0.0f;
        float heightPercent = 1.0f - (offsetY / aspectHeight);
        float widthPercent = 1.0f - (offsetX / aspectWidth);
        context.canvas().submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.Hud.BACKGROUND), bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), UVCoordinates.of((1920.0f * (1.0f - widthPercent)) / 2.0f, (1080.0f * (1.0f - heightPercent)) / 2.0f, 1920.0f * widthPercent, 1080.0f * heightPercent, BACKGROUND_WIDTH, BACKGROUND_HEIGHT), -1);
    }

    protected void renderHotbar(ScreenContext context, Bounds bounds) {
        context.stack();
        float x = bounds.getCenterX();
        float y = bounds.getBottom();
        float hotbarHeight = this.hotbarRenderer.getHeight();
        this.hotbarRenderer.pos(x - (this.hotbarRenderer.getWidth() / 2.0f), y - hotbarHeight);
        float hotbarHeight2 = hotbarHeight + 1.0f;
        this.experienceBarRenderer.pos(x - (this.experienceBarRenderer.getWidth() / 2.0f), (y - hotbarHeight2) - this.experienceBarRenderer.getHeight());
        float hotbarHeight3 = hotbarHeight2 + this.experienceBarRenderer.getHeight() + 1.0f + 9.0f;
        this.statusIconRenderer.statusIcon(EMPTY_HEART_ICON, HEART_ICON).pos((x - this.statusIconRenderer.getWidth(10, 9.0f)) - 1.0f, y - hotbarHeight3).amount(10).render(context);
        this.statusIconRenderer.statusIcon(EMPTY_SATURATION_ICON, SATURATION_ICON).pos(x + 9.0f + 1.0f, y - hotbarHeight3).amount(10).render(context);
        this.statusIconRenderer.statusIcon(ARMOR_ICON).pos((x - this.statusIconRenderer.getWidth(10, 9.0f)) - 1.0f, y - (hotbarHeight3 + 10.0f)).amount(10).render(context);
        this.hotbarRenderer.mode(RenderMode.DUMMY).layout(HotbarRenderer.Layout.HORIZONTAL).render(context);
        this.experienceBarRenderer.mode(RenderMode.DUMMY).render(context);
    }
}
