package net.labymod.core.client.gui.screen.theme.fancy;

import javax.inject.Inject;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.renderer.text.Font;
import net.labymod.api.client.gfx.pipeline.renderer.text.Fonts;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.ExtendingTheme;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.InjectionNames;
import net.labymod.core.client.gui.screen.theme.fancy.controller.FancyActivityAnimator;
import net.labymod.core.client.gui.screen.theme.fancy.eventlistener.FancyVariableThemeEventListener;
import net.labymod.core.client.gui.screen.theme.fancy.eventlistener.TitleMenuThemeEventListener;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyAddonProfileButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyAddonProfileInstallButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyCheckBoxRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyDirtBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyDropdownPopupRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyDropdownRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyHrRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyItemStackPickerRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyNavigationButtonRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyNotificationRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyScrollbarRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySliderRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySlimSliderRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancySwitchRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyTabRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.FancyWindowRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.background.FancyScreenBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.fancy.renderer.hudwidget.FancyHudWidgetCanvasRenderer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/FancyTheme.class */
public class FancyTheme extends ExtendingTheme {
    public static final String ID = "fancy";
    private FancyVariableThemeEventListener variableListener;

    @Inject
    public FancyTheme() {
        super(ID, (Class<? extends ThemeConfig>) FancyThemeConfig.class);
        setDisplayName("Fancy");
    }

    @Override // net.labymod.api.client.gui.screen.theme.ExtendingTheme, net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    public void onPostLoad() {
        super.onPostLoad();
        registerWidgetRenderer(new FancyBackgroundRenderer());
        registerWidgetRenderer(new FancyButtonRenderer());
        registerWidgetRenderer(new FancySliderRenderer());
        registerWidgetRenderer(new FancySlimSliderRenderer());
        registerWidgetRenderer(new FancyNavigationButtonRenderer());
        registerWidgetRenderer(new FancyAddonProfileButtonRenderer());
        registerWidgetRenderer(new FancyAddonProfileInstallButtonRenderer());
        registerWidgetRenderer(new FancyDirtBackgroundRenderer(this));
        registerWidgetRenderer(new FancySwitchRenderer());
        registerWidgetRenderer(new FancyDropdownRenderer());
        registerWidgetRenderer(new FancyDropdownPopupRenderer());
        registerWidgetRenderer(new FancyScrollbarRenderer());
        registerWidgetRenderer(new FancyHrRenderer());
        registerWidgetRenderer(new FancyWindowRenderer());
        registerWidgetRenderer(new FancyCheckBoxRenderer());
        registerWidgetRenderer(new FancyTabRenderer());
        registerWidgetRenderer(new FancyNotificationRenderer());
        registerWidgetRenderer(new FancyHudWidgetCanvasRenderer());
        registerWidgetRenderer(new FancyItemStackPickerRenderer());
        registerBackgroundRenderer(new FancyScreenBackgroundRenderer(this));
        bindType(MinecraftWidgetType.BUTTON, "Button");
        bindType(MinecraftWidgetType.SLIDER, "Slider");
        Metadata metadata = metadata();
        metadata.set(DefaultThemeVariables.INTERPOLATE_SCROLL, true);
        metadata.set(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND, true);
        metadata.set(DefaultThemeVariables.HIDE_LIST_SEPARATORS, true);
        metadata.set(DefaultThemeVariables.HUD_WIDGET_FLOATING_POINT_POSITION, true);
        updateTransitionProperty();
        metadata.set(DefaultThemeVariables.ABOVE_NAME_ENTRY_Y_OFFSET, () -> {
            if (PlatformEnvironment.isAncientOpenGL()) {
                return Float.valueOf(-2.0f);
            }
            RenderAttributesStack renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
            RenderAttributes attributes = renderAttributesStack.last();
            if (attributes.isForceVanillaFont()) {
                return Float.valueOf(-3.0f);
            }
            if (Laby.references().textRendererProvider().useCustomFont()) {
                return Float.valueOf(-1.0f);
            }
            return Float.valueOf(-3.0f);
        });
        metadata.set(DefaultThemeVariables.BELOW_NAME_ENTRY_Y_OFFSET, Float.valueOf(9.0f));
        metadata.set(DefaultThemeVariables.LEGACY_LOADING_SCREEN_RENDERER_TILE_SIZE, Float.valueOf(0.0f));
        metadata.set(DefaultThemeVariables.LEGACY_LOADING_SCREEN_RENDERER_TILE_BRIGHTNESS, 255);
        metadata.set(DefaultThemeVariables.SHOW_DIRT_SEPARATOR, false);
        registerHoverBackgroundRenderer(Laby.references().hoverBackgroundEffect(InjectionNames.FANCY_HOVER_EFFECT));
        registerEventListener(new TitleMenuThemeEventListener(this));
        FancyVariableThemeEventListener fancyVariableThemeEventListener = new FancyVariableThemeEventListener(this);
        this.variableListener = fancyVariableThemeEventListener;
        registerEventListener(fancyVariableThemeEventListener);
        registerEventListener(new FancyActivityAnimator(this));
        SoundService soundService = Laby.references().soundService();
        soundService.bindConditionally(SoundType.BUTTON_CLICK, ID, Constants.Resources.SOUND_UI_BUTTON_CLICK, this::enabledFancySounds);
        soundService.bindConditionally(SoundType.SWITCH_TOGGLE_ON, ID, Constants.Resources.SOUND_UI_SWITCH_ON, this::enabledFancySounds);
        soundService.bindConditionally(SoundType.SWITCH_TOGGLE_OFF, ID, Constants.Resources.SOUND_UI_SWITCH_OFF, this::enabledFancySounds);
    }

    public void updateTransitionProperty() {
        FancyThemeConfig config = (FancyThemeConfig) Laby.labyAPI().themeService().getThemeConfig(this, FancyThemeConfig.class);
        if (config != null) {
            boolean transitions = config.activityTransitions().get().booleanValue();
            metadata().set("transition", Boolean.valueOf(transitions));
        }
    }

    private boolean enabledFancySounds() {
        FancyThemeConfig themeConfig;
        return this.labyAPI.themeService().currentTheme() == this && (themeConfig = (FancyThemeConfig) this.labyAPI.themeService().getThemeConfig(this, FancyThemeConfig.class)) != null && themeConfig.fancySounds().get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    @NotNull
    public Font font() {
        FancyThemeConfig themeConfig = (FancyThemeConfig) this.labyAPI.themeService().getThemeConfig(this, FancyThemeConfig.class);
        if (themeConfig != null && themeConfig.fancyFont().get().booleanValue()) {
            return Fonts.LEGACY_MSDF;
        }
        return super.parentTheme().font();
    }

    public FancyVariableThemeEventListener getVariableListener() {
        return this.variableListener;
    }
}
