package net.labymod.api.configuration.labymod.main.laby.ingame;

import java.util.List;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/ChatInputConfig.class */
public interface ChatInputConfig extends ConfigAccessor {
    ConfigProperty<Boolean> autoText();

    ConfigProperty<Boolean> nameHistory();

    ConfigProperty<Boolean> chatSymbols();

    ConfigProperty<List<String>> favoriteSymbols();
}
