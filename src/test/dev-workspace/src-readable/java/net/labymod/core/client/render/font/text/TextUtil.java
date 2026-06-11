package net.labymod.core.client.render.font.text;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/text/TextUtil.class */
public final class TextUtil {
    private static final RenderAttributesStack RENDER_ATTRIBUTES_STACK = Laby.references().renderEnvironmentContext().renderAttributesStack();

    private TextUtil() {
    }

    public static void pushAndApplyAttributes() {
        RenderAttributes attributes = RENDER_ATTRIBUTES_STACK.pushAndGet();
        attributes.setForceVanillaFont(!isIngameFancyFont());
        attributes.apply();
    }

    public static void popRenderAttributes() {
        RENDER_ATTRIBUTES_STACK.pop();
    }

    private static boolean isIngameFancyFont() {
        ThemeService themeService = Laby.references().themeService();
        FancyThemeConfig config = (FancyThemeConfig) themeService.getThemeConfig(FancyThemeConfig.class);
        if (config == null) {
            return false;
        }
        return config.isIngameFancyFont();
    }
}
