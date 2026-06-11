package net.labymod.core.client.gui.screen.theme.vanilla;

import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.InjectionNames;
import net.labymod.core.client.gui.screen.theme.RootTheme;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBulletEntryContainerRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaCheckBoxRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaColorPreviewRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaCrosshairRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaDirtBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaItemStackPickerRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaItemStackRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaNotificationRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaPopupBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaScrollbarRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaSwitchRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaTextFieldRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaWheelRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaWindowRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaTexturedBackgroundRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonDropdownRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaIngameButtonRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaSliderRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat.VanillaChatSliderRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat.VanillaChatSwitchRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.entity.HealthStatusRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.entity.VanillaEntityRenderer;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget.VanillaHudWidgetCanvasRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/vanilla/VanillaTheme.class */
public class VanillaTheme extends RootTheme {
    public static final String ID = "vanilla";
    private static final boolean GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();

    @Inject
    public VanillaTheme() {
        super(ID, VanillaThemeConfig.class);
        setDisplayName("Vanilla");
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    public void onPostLoad() {
        super.onPostLoad();
        registerWidgetRenderer(new VanillaDirtBackgroundRenderer());
        registerWidgetRenderer(new VanillaButtonRenderer());
        registerWidgetRenderer(new VanillaIngameButtonRenderer());
        registerWidgetRenderer(new VanillaBackgroundRenderer());
        registerWidgetRenderer(new VanillaPopupBackgroundRenderer());
        registerWidgetRenderer(new VanillaScrollbarRenderer());
        registerWidgetRenderer(new VanillaSwitchRenderer());
        registerWidgetRenderer(new VanillaSliderRenderer());
        registerWidgetRenderer(new VanillaTextFieldRenderer());
        registerWidgetRenderer(new VanillaButtonDropdownRenderer());
        registerWidgetRenderer(new VanillaButtonDropdownRenderer());
        registerWidgetRenderer(new VanillaWheelRenderer());
        registerWidgetRenderer(new VanillaCrosshairRenderer());
        registerWidgetRenderer(new VanillaCheckBoxRenderer());
        registerWidgetRenderer(new VanillaWindowRenderer());
        registerWidgetRenderer(new VanillaNotificationRenderer());
        registerWidgetRenderer(new VanillaColorPreviewRenderer());
        registerWidgetRenderer(new VanillaButtonDropdownRenderer());
        registerWidgetRenderer(new VanillaChatSwitchRenderer());
        registerWidgetRenderer(new VanillaChatSliderRenderer());
        registerWidgetRenderer(new VanillaBulletEntryContainerRenderer());
        registerWidgetRenderer(new VanillaHudWidgetCanvasRenderer());
        registerWidgetRenderer(new VanillaEntityRenderer());
        registerWidgetRenderer(new HealthStatusRenderer());
        registerWidgetRenderer(new VanillaItemStackPickerRenderer());
        registerWidgetRenderer(new VanillaTexturedBackgroundRenderer(this));
        registerWidgetRenderer(new VanillaItemStackRenderer());
        registerBackgroundRenderer(new VanillaScreenBackgroundRenderer());
        registerHoverBackgroundRenderer(Laby.references().hoverBackgroundEffect(InjectionNames.VANILLA_HOVER_EFFECT));
        bindType(MinecraftWidgetType.BUTTON, "Button");
        bindType(MinecraftWidgetType.SLIDER, "Slider");
        Metadata metadata = metadata();
        metadata.set(DefaultThemeVariables.SCROLL_BACKGROUND, "DirtBackground");
        metadata.set(DefaultThemeVariables.ABOVE_NAME_ENTRY_Y_OFFSET, () -> {
            return Float.valueOf(PlatformEnvironment.isAncientOpenGL() ? -2.0f : -3.0f);
        });
        metadata.set(DefaultThemeVariables.BELOW_NAME_ENTRY_Y_OFFSET, Float.valueOf(9.0f));
        metadata.set(DefaultThemeVariables.SERVER_SELECTION_TEXTURE_FEATURE, Boolean.valueOf(GUI_ATLAS));
    }
}
