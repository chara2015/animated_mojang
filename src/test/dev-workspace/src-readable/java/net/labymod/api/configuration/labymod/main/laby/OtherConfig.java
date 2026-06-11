package net.labymod.api.configuration.labymod.main.laby;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.configuration.labymod.main.laby.other.WindowConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/OtherConfig.class */
public interface OtherConfig extends ConfigAccessor {
    ConfigProperty<Boolean> fastLanguageReload();

    DiscordConfig discord();

    ConfigProperty<Boolean> anonymousStatistics();

    ConfigProperty<Map<String, Boolean>> outOfMemoryWarnings();

    AdvancedConfig advanced();

    ConfigProperty<Set<String>> conversionAskedNamespaces();

    ConfigProperty<Integer> lastWidth();

    ConfigProperty<Integer> lastHeight();

    ConfigProperty<Map<String, Boolean>> serverSwitchChoices();

    ConfigProperty<List<String>> ignoredAddonRecommendations();

    ConfigProperty<String> preferredCurrency();

    WindowConfig window();
}
