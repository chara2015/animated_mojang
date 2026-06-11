package net.labymod.core.client.gui.screen.theme.fancy.eventlistener;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.theme.ThemeEventListener;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/eventlistener/FancyVariableThemeEventListener.class */
public class FancyVariableThemeEventListener implements ThemeEventListener {
    private final FancyTheme theme;
    private final FancyThemeConfig themeConfig;

    public FancyVariableThemeEventListener(FancyTheme theme) {
        this.theme = theme;
        this.themeConfig = (FancyThemeConfig) Laby.labyAPI().themeService().getThemeConfig(theme, FancyThemeConfig.class);
    }

    @Subscribe
    public void onScreenOpen(ScreenOpenEvent event) {
        updateThemeVariables();
    }

    @Subscribe
    public void onActivityOpen(ActivityOpenEvent event) {
        updateThemeVariables();
    }

    @Subscribe
    public void onScreenDisplay(ScreenDisplayEvent event) {
        updateThemeVariables();
    }

    public void updateThemeVariables() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Window window = minecraft.minecraftWindow();
        boolean ingame = minecraft.isIngame();
        window.setVariable("--background-color", Integer.valueOf(getBackgroundColor(ingame)));
        window.setVariable("--background-color-border", Integer.valueOf(getBackgroundBorderColor(ingame)));
        window.setVariable("--border-radius", (Number) 15);
        window.setVariable("--border-thickness", (Number) 4);
        int primaryButtonColor = this.themeConfig.buttonColor().get().get();
        window.setVariable("--button-color", Integer.valueOf(primaryButtonColor));
        window.setVariable("--button-color-border", Integer.valueOf(modify(primaryButtonColor, -10, 1.33f)));
        window.setVariable("--button-color-hover", Integer.valueOf(modify(primaryButtonColor, -10, 1.66f)));
        window.setVariable("--button-color-disabled", Integer.valueOf(modify(primaryButtonColor, -20, 0.33f)));
        window.setVariable("--button-color-active", Integer.valueOf(modify(primaryButtonColor, 22, 1.66f)));
        window.setVariable("--button-color-text", Integer.valueOf(textColorForBackground(primaryButtonColor)));
        int accentButtonColor = this.themeConfig.accentColor().get().get();
        window.setVariable("--accent-button-color", Integer.valueOf(accentButtonColor));
        window.setVariable("--accent-button-color-border", Integer.valueOf(modify(accentButtonColor, 10, 1.33f)));
        window.setVariable("--accent-button-color-hover", Integer.valueOf(modify(accentButtonColor, -10, 1.66f)));
        window.setVariable("--accent-button-color-disabled", Integer.valueOf(modify(accentButtonColor, -20, 0.33f)));
        window.setVariable("--accent-button-color-active", Integer.valueOf(modify(accentButtonColor, 22, 1.66f)));
        window.setVariable("--accent-button-color-text", Integer.valueOf(textColorForBackground(accentButtonColor)));
        window.setVariable("--accent-navigation-color", Integer.valueOf(ColorFormat.ARGB32.mul(accentButtonColor, 3.5833f, 1.48f, 1.3125f, 1.0f)));
    }

    public int getBackgroundColor(boolean ingame) {
        return ColorFormat.ARGB32.pack(13, 14, 15, 150);
    }

    public int getBackgroundBorderColor(boolean ingame) {
        return ColorFormat.ARGB32.pack(13, 14, 15, 170);
    }

    private static int modify(int color, int rgbDelta, float alphaMultiplier) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        return colorFormat.mul(colorFormat.add(color, rgbDelta, rgbDelta, rgbDelta, 0), 1.0f, 1.0f, 1.0f, alphaMultiplier);
    }

    private static int textColorForBackground(int backgroundColor) {
        int red = (backgroundColor >> 16) & 255;
        int green = (backgroundColor >> 8) & 255;
        int blue = backgroundColor & 255;
        int alpha = (backgroundColor >> 24) & 255;
        int brightness = (((((red * 299) + (green * 587)) + (blue * 114)) / SubmissionOrders.DEBUG) * alpha) / 255;
        return brightness > 125 ? -16777216 : -921103;
    }
}
