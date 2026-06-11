package net.labymod.core.client.gui.screen;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/DefaultScreenCustomFontStack.class */
@Singleton
@Implements(ScreenCustomFontStack.class)
public class DefaultScreenCustomFontStack implements ScreenCustomFontStack {
    private final RenderAttributesStack renderAttributesStack;
    private final IngameFancyFontFrameContext ingameFancyFontFrameContext = new IngameFancyFontFrameContext();

    @Inject
    public DefaultScreenCustomFontStack(FrameContextRegistry frameContextRegistry, RenderEnvironmentContext renderEnvironmentContext) {
        this.renderAttributesStack = renderEnvironmentContext.renderAttributesStack();
        frameContextRegistry.register(this.ingameFancyFontFrameContext);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenCustomFontStack
    public void push(Object screen) {
        if (isIngameFancyFont()) {
            return;
        }
        if (screen instanceof LabyScreenAccessor) {
            LabyScreenAccessor labyScreenAccessor = (LabyScreenAccessor) screen;
            push(labyScreenAccessor.screen());
        } else if (screen instanceof LabyScreen) {
            LabyScreen labyScreen = (LabyScreen) screen;
            push(labyScreen);
        } else {
            push(GameScreenRegistry.from(screen));
        }
    }

    @Override // net.labymod.api.client.gui.screen.ScreenCustomFontStack
    public void pop(Object screen) {
        if (isIngameFancyFont()) {
            return;
        }
        if (screen instanceof LabyScreenAccessor) {
            LabyScreenAccessor labyScreenAccessor = (LabyScreenAccessor) screen;
            pop(labyScreenAccessor.screen());
        } else if (screen instanceof LabyScreen) {
            LabyScreen labyScreen = (LabyScreen) screen;
            pop(labyScreen);
        } else {
            pop(GameScreenRegistry.from(screen));
        }
    }

    private void push(LabyScreen screen) {
        RenderAttributes attributes = this.renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(!screen.allowCustomFont());
        attributes.apply();
    }

    private void push(GameScreen screen) {
        if (screen == null) {
            return;
        }
        RenderAttributes attributes = this.renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(!screen.allowCustomFont());
        attributes.apply();
    }

    private void pop(LabyScreen labyScreen) {
        this.renderAttributesStack.pop();
    }

    private void pop(GameScreen screen) {
        if (screen == null) {
            return;
        }
        this.renderAttributesStack.pop();
    }

    private boolean isIngameFancyFont() {
        return this.ingameFancyFontFrameContext.isIngameFancyFont();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/DefaultScreenCustomFontStack$IngameFancyFontFrameContext.class */
    private static class IngameFancyFontFrameContext implements FrameContext {
        private boolean ingameFancyFont;

        private IngameFancyFontFrameContext() {
        }

        @Override // net.labymod.api.client.gfx.pipeline.context.FrameContext
        public void beginFrame() {
            ThemeService themeService = Laby.references().themeService();
            FancyThemeConfig config = (FancyThemeConfig) themeService.getThemeConfig(FancyThemeConfig.class);
            if (config == null) {
                this.ingameFancyFont = true;
            } else {
                this.ingameFancyFont = config.isIngameFancyFont();
            }
        }

        @Override // net.labymod.api.client.gfx.pipeline.context.FrameContext
        public void endFrame() {
        }

        public boolean isIngameFancyFont() {
            return this.ingameFancyFont;
        }
    }
}
