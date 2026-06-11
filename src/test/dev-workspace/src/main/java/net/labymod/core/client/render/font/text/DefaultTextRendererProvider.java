package net.labymod.core.client.render.font.text;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.text.Fonts;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.event.EventBus;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ThreadSafe;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/text/DefaultTextRendererProvider.class */
@Singleton
@Implements(TextRendererProvider.class)
public class DefaultTextRendererProvider implements TextRendererProvider {
    private final RenderAttributesStack renderAttributesStack;
    private final ThemeService themeService;
    private final TextRenderer textRenderer;
    private boolean forceMinecraftRenderer;
    private boolean useCustomFont = true;

    @Inject
    public DefaultTextRendererProvider(EventBus eventBus, ThemeService themeService, RenderEnvironmentContext renderEnvironmentContext, TextRenderer textRenderer) {
        this.themeService = themeService;
        this.textRenderer = textRenderer;
        this.renderAttributesStack = renderEnvironmentContext.renderAttributesStack();
        eventBus.registerListener(new TextRendererListener(this));
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public TextRenderer getRenderer() {
        if (!this.themeService.isInitialized() || isMinecraftRendererForced()) {
            this.textRenderer.setCurrentFont(Fonts.MINECRAFT);
            return this.textRenderer;
        }
        Theme theme = this.themeService.currentTheme();
        this.textRenderer.setCurrentFont(theme.font());
        return this.textRenderer;
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public boolean useCustomFont() {
        return this.useCustomFont;
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public void setUseCustomFont(boolean useCustomFont) {
        if (this.useCustomFont == useCustomFont) {
            return;
        }
        this.useCustomFont = useCustomFont;
        if (useCustomFont) {
            forceMinecraftRenderer(false);
        }
        ThreadSafe.executeOnRenderThread(this::reloadActivities);
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public boolean isMinecraftRendererForced() {
        return this.forceMinecraftRenderer || !this.useCustomFont;
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public void forceMinecraftRenderer(boolean force) {
        this.forceMinecraftRenderer = force;
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public boolean shouldUseMinecraftFont() {
        if (isMinecraftRendererForced()) {
            return true;
        }
        RenderAttributes attributes = this.renderAttributesStack.last();
        return attributes.isForceVanillaFont();
    }

    @Override // net.labymod.api.client.render.font.text.TextRendererProvider
    public void forceVanillaFont(boolean condition, Runnable runnable) {
        if (condition) {
            boolean currentState = isMinecraftRendererForced();
            forceMinecraftRenderer(true);
            runnable.run();
            forceMinecraftRenderer(currentState);
            return;
        }
        runnable.run();
    }

    private void reloadActivities() {
        ReferenceStorage references = Laby.references();
        references.activityController().reloadOpenActivities();
        references.componentRenderer().invalidate();
    }
}
