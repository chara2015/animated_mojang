package net.labymod.api.client.gui.screen.theme.renderer;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/renderer/ThemeRenderer.class */
public abstract class ThemeRenderer<T extends Widget> implements WidgetRenderer<T> {
    protected Theme theme;
    protected String name;
    protected final LabyAPI labyAPI = Laby.labyAPI();
    protected final RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
    protected final ComponentRenderer componentRenderer = this.renderPipeline.componentRenderer();
    protected final ResourceRenderer resourceRenderer = this.renderPipeline.resourceRenderer();

    protected ThemeRenderer(String name) {
        this.name = name;
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(T widget, ScreenContext context) {
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(T widget, ScreenContext context) {
    }

    public void playInteractionSound(T widget) {
    }

    public boolean hasInteractionSound() {
        return false;
    }

    public String toString() {
        return getName();
    }

    public String getName() {
        return this.name;
    }

    public Theme theme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
