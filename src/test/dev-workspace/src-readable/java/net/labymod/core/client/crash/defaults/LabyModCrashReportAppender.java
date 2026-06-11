package net.labymod.core.client.crash.defaults;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.crash.CrashReportAppender;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.util.CharSequences;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/crash/defaults/LabyModCrashReportAppender.class */
public class LabyModCrashReportAppender extends CrashReportAppender {
    private static final String HEADER_NAME = "LabyMod";
    private static final String VERSION_KEY = "Version";
    private static final String COMMIT_REFERENCE_KEY = "Commit Reference";
    private static final String THEME_KEY = "Theme";
    private static final String LOADED_ADDONS_KEY = "Loaded Addons";
    private static final String ENABLED_HUD_WIDGETS_KEY = "Enabled HudWidgets";
    private static final String MOD_INFOS_KEY = "Mod Infos";
    private static final String UNKNOWN_THEME = "Unknown";

    @Override // net.labymod.api.client.crash.CrashReportAppender
    public void append(StringBuilder builder) {
        LabyAPI labyAPI = Laby.labyAPI();
        if (labyAPI == null) {
            return;
        }
        appendHeader("LabyMod");
        setDetail(VERSION_KEY, labyAPI.getVersion());
        setDetail(COMMIT_REFERENCE_KEY, BuildData.commitReference());
        setDetail(THEME_KEY, () -> {
            return CharSequences.capitalize(labyAPI.config().appearance().theme().getOrDefault(UNKNOWN_THEME));
        }, UNKNOWN_THEME);
        appendLoadedAddons(labyAPI);
        appendEnabledHudWidgets(labyAPI);
        appendLoadedMods(labyAPI);
    }

    private void appendLoadedAddons(LabyAPI labyAPI) {
        Collection<LoadedAddon> loadedAddons = labyAPI.addonService().getLoadedAddons();
        if (loadedAddons.isEmpty()) {
            setDetail(LOADED_ADDONS_KEY, "<No addons loaded>");
            return;
        }
        StringJoiner joiner = new StringJoiner(", ");
        for (LoadedAddon addon : loadedAddons) {
            String value = addon.info().getNamespace() + "(" + addon.info().getVersion() + ")";
            joiner.add(value);
        }
        setDetail(LOADED_ADDONS_KEY, joiner.toString());
    }

    private void appendLoadedMods(LabyAPI labyAPI) {
        ModLoaderRegistry modLoaderRegistry = labyAPI.modLoaderRegistry();
        if (modLoaderRegistry == null) {
            return;
        }
        for (ModLoader loader : modLoaderRegistry.values()) {
            setSubtitle(loader.getId());
            Collection<ModInfo> modInfos = loader.getModInfos();
            if (modInfos.isEmpty()) {
                setDetail(MOD_INFOS_KEY, "<No mods loaded>");
            } else {
                StringJoiner joiner = new StringJoiner(", ");
                for (ModInfo modInfo : modInfos) {
                    joiner.add(modInfo.getId() + " (" + String.valueOf(modInfo.version()) + ")");
                }
                setDetail(MOD_INFOS_KEY, joiner.toString());
            }
        }
    }

    private void appendEnabledHudWidgets(LabyAPI labyAPI) {
        HudWidgetRegistry hudWidgetRegistry = labyAPI.hudWidgetRegistry();
        if (hudWidgetRegistry == null) {
            setDetail(ENABLED_HUD_WIDGETS_KEY, "<Unable to resolve HudWidgets>");
            return;
        }
        List<HudWidget<?>> entries = hudWidgetRegistry.values();
        StringJoiner joiner = new StringJoiner(", ");
        for (HudWidget<?> entry : entries) {
            if (entry.isEnabled()) {
                joiner.add(entry.getId());
            }
        }
        setDetail(ENABLED_HUD_WIDGETS_KEY, joiner.toString());
    }
}
