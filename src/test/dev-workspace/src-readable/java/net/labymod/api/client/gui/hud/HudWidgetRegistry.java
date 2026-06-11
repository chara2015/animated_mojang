package net.labymod.api.client.gui.hud;

import java.util.function.Supplier;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/HudWidgetRegistry.class */
@Referenceable
public interface HudWidgetRegistry extends Registry<HudWidget<?>> {
    public static final Logging LOGGER = Logging.getLogger();

    void registerDefaults();

    void selectProfile(String str);

    String getSelectedProfile();

    void updateLinkedWidgets(HudWidget<?> hudWidget);

    void saveConfig();

    GlobalHudWidgetConfig globalHudWidgetConfig();

    RootSettingRegistry globalHudWidgetSettingRegistry();

    HudWidgetCategoryRegistry categoryRegistry();

    void updateHudWidgets(@NotNull String str);

    default void register(Supplier<HudWidget<?>> hudWidgetSupplier) {
        register(hudWidgetSupplier, throwable -> {
            LOGGER.error("Error while registering hud widget", throwable);
        });
    }

    @Deprecated
    default void updateHudWidgets() {
        updateHudWidgets("unknown");
    }
}
